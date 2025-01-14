package com.taihuynh.ecommerce.product;

import com.taihuynh.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductClient {

    @Value("${application.product-service.url}")
    private String productUrl;

    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> purchaseRequests) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<List<PurchaseRequest>> request = new HttpEntity<>(purchaseRequests, headers);
        ParameterizedTypeReference<List<PurchaseResponse>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<PurchaseResponse>> response =
                restTemplate.exchange(
                        productUrl + "/purchase",
                        HttpMethod.POST,
                        request,
                        responseType
                );
        if (response.getStatusCode().isError()) {
            throw new BusinessException("Failed to purchase products");
        }
        return response.getBody();
    }
}
