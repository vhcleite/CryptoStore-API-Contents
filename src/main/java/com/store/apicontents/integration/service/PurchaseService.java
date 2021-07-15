package com.store.apicontents.integration.service;

import com.store.apicontents.integration.client.PurchaseClient;
import com.store.apicontents.integration.dto.PurchaseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {

    private PurchaseClient purchaseClient;

    public PurchaseService(PurchaseClient purchaseClient) {
        this.purchaseClient = purchaseClient;
    }

    public List<PurchaseDto> getPurchaseByUser(String userId) {
        ResponseEntity<List<PurchaseDto>> purchasesResponse = purchaseClient.getPurchaseByUserId(userId);
        List<PurchaseDto> purchases = purchasesResponse.getBody();
        return CollectionUtils.isEmpty(purchases) ? new ArrayList<>() : purchases;
    }
}
