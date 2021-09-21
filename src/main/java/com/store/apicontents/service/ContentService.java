package com.store.apicontents.service;

import com.store.apicontents.exceptions.CryptoStoreException;
import com.store.apicontents.integration.dto.PaymentStatus;
import com.store.apicontents.integration.dto.PurchaseDto;
import com.store.apicontents.integration.service.PurchaseService;
import com.store.apicontents.model.ContentEntity;
import com.store.apicontents.model.dto.ContentGetDto;
import com.store.apicontents.model.dto.ContentPostDto;
import com.store.apicontents.repository.ContentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContentService {

    private static final ModelMapper modelMapper = new ModelMapper();
    private ContentRepository contentRepository;
    private PurchaseService purchaseService;

    @Autowired
    public ContentService(ContentRepository contentRepository, PurchaseService purchaseService) {
        this.contentRepository = contentRepository;
        this.purchaseService = purchaseService;
    }

    public Page<ContentGetDto> findPage(PageRequest pageRequest, String userId) {
        Page<ContentEntity> contentPage = contentRepository.findAll(pageRequest);
            List<PurchaseDto> purchases = purchaseService.getPurchaseByUser(userId);
        return contentPage.map(c -> mapToContentGetDto(purchases, c));
    }

    private ContentGetDto mapToContentGetDto(List<PurchaseDto> purchases, ContentEntity c) {
        ContentGetDto contentDto = modelMapper.map(c, ContentGetDto.class);
        contentDto.setPaymentStatus(getPaymentStatus(purchases, contentDto));
        return contentDto;
    }

    private PaymentStatus getPaymentStatus(List<PurchaseDto> purchases, ContentGetDto contentDto) {
        Optional<PurchaseDto> purchaseDtoOptional =
                purchases
                        .stream()
                        .filter(p -> p.getContentId() == contentDto.getId())
                        .sorted((c1, c2) -> c2.getExpirationDateTime().compareTo(c1.getExpirationDateTime()))
                        .findFirst();

        if(purchaseDtoOptional.isPresent()) {
            if(purchaseDtoOptional.get().getPaymentStatus().equals(PaymentStatus.PAYMENT_NOT_MADE)
                    && isExpired(purchaseDtoOptional.get())) {
                return PaymentStatus.NOT_BOUGHT;
            }

            return purchaseDtoOptional.get().getPaymentStatus();
        } else
            return PaymentStatus.NOT_BOUGHT;
    }

    private boolean isExpired(PurchaseDto paymentStatus) {
        return paymentStatus.getExpirationDateTime().isBefore(LocalDateTime.now());
    }

    private Boolean isDownloadAllowed(ContentGetDto content, List<PurchaseDto> purchases) {
        Optional<PurchaseDto> purchase = purchases.stream().filter(p -> p.getContentId().equals(content.getId()) && p.getPaymentStatus() == PaymentStatus.PAYMENT_MADE).findFirst();
        return purchase.isPresent();
    }

    public ContentEntity create(ContentPostDto newContent) {
        ContentEntity content = modelMapper.map(newContent, ContentEntity.class);
        return contentRepository.save(content);
    }

    public ContentGetDto findById(Long contentId) {
        Optional<ContentEntity> contentOptional = contentRepository.findById(contentId);
        return modelMapper.map(contentOptional.orElseThrow(() -> new CryptoStoreException(HttpStatus.NO_CONTENT, "conteúdo inválido")), ContentGetDto.class);
    }

    public File getFileById(String userId, Long contentId) {

        Optional<ContentEntity> contentOptional = contentRepository.findById(contentId);
        ContentEntity content = contentOptional.orElseThrow(() -> new CryptoStoreException(HttpStatus.NO_CONTENT, "conteúdo inválido"));

        List<PurchaseDto> purchases = purchaseService.getPurchaseByUser(userId);

        Optional<PurchaseDto> purchaseDtoOptional = purchases.stream().filter(p -> p.getContentId() == content.getId()
        && p.getPaymentStatus() == PaymentStatus.PAYMENT_MADE).findFirst();
        purchaseDtoOptional.orElseThrow(() -> new CryptoStoreException(HttpStatus.NO_CONTENT, "Conteúdo não comprado"));

        return new File(content.getPath());
    }
}
