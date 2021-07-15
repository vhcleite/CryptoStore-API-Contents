package com.store.apicontents.integration.client;

import com.store.apicontents.integration.dto.PurchaseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "purchase", url = "http://localhost:8083/store/v1/users")
public interface PurchaseClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/purchase", produces = "application/json")
    public ResponseEntity<List<PurchaseDto>> getPurchaseByUserId(@PathVariable("userId") String userId);
}
