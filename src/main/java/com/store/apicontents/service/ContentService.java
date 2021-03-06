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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.store.apicontents.integration.dto.PaymentStatus.*;

@Service
public class ContentService {

    private static final ModelMapper modelMapper = new ModelMapper();

    private final ContentRepository contentRepository;

    private final PurchaseService purchaseService;

    @Value("${store.account}")
    private String storeAccount;

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

        Optional<PurchaseDto> purchaseOptional = purchases
                .stream()
                .filter(p -> p.getContentId().equals(contentDto.getId()))
                .min((c1, c2) -> c2.getExpirationDateTime().compareTo(c1.getExpirationDateTime()));

        contentDto.setPaymentStatus(purchaseOptional.isPresent() ? getPaymentStatus(purchaseOptional.get()) : NOT_BOUGHT);

        if (contentDto.getPaymentStatus() == PENDING_PAYMENT && purchaseOptional.isPresent()) {
            contentDto.setPendingPaymentMessage(
                    String.format(
                            "Pague %.3f WIZ com o identificador %d na transfer??ncia para a conta %s",
                            purchaseOptional.get().getValue() ,purchaseOptional.get().getPurchaseId(), storeAccount)
            );
        }

        return contentDto;
    }

    private PaymentStatus getPaymentStatus(PurchaseDto purchaseDto) {
        if (purchaseDto.getPaymentStatus().equals(PAYMENT_NOT_MADE)
                && isExpired(purchaseDto)) {
            return NOT_BOUGHT;
        }

        return purchaseDto.getPaymentStatus();
    }

    private boolean isExpired(PurchaseDto paymentStatus) {
        return paymentStatus.getExpirationDateTime().isBefore(LocalDateTime.now());
    }

    public ContentEntity create(ContentPostDto newContent) {
        ContentEntity content = modelMapper.map(newContent, ContentEntity.class);
        return contentRepository.save(content);
    }

    public ContentGetDto findById(Long contentId) {
        Optional<ContentEntity> contentOptional = contentRepository.findById(contentId);
        return modelMapper.map(contentOptional.orElseThrow(() -> new CryptoStoreException(HttpStatus.NO_CONTENT, "conte??do inv??lido")), ContentGetDto.class);
    }

    public File getFileById(String userId, Long contentId) {

        Optional<ContentEntity> contentOptional = contentRepository.findById(contentId);
        ContentEntity content = contentOptional.orElseThrow(() -> new CryptoStoreException(HttpStatus.NO_CONTENT, "conte??do inv??lido"));

        List<PurchaseDto> purchases = purchaseService.getPurchaseByUser(userId);

        Optional<PurchaseDto> purchaseDtoOptional = purchases.stream().filter(p -> p.getContentId().equals(content.getId())
                && p.getPaymentStatus() == PaymentStatus.PAYMENT_MADE).findFirst();
        purchaseDtoOptional.orElseThrow(() -> new CryptoStoreException(HttpStatus.NO_CONTENT, "Conte??do n??o comprado"));

        return new File(content.getPath());
    }
}
