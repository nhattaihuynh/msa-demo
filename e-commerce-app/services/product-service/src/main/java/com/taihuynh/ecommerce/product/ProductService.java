package com.taihuynh.ecommerce.product;

import com.taihuynh.ecommerce.exception.ProductPurchaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = productMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return productMapper.toResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse updateProduct(Integer id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        Product updatedProduct = productMapper.updateEntity(product, request);
        return productMapper.toResponse(productRepository.save(updatedProduct));
    }

    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> requests) {
        List<Product> products = productRepository.findAllById(
                requests.stream().map(ProductPurchaseRequest::productId).toList()
        );

        if (products.size() != requests.size()) {
            throw new ProductPurchaseException("Some products were not found");
        }

        Map<Integer, Integer> requestQuantities = requests.stream()
                .collect(Collectors.toMap(ProductPurchaseRequest::productId, ProductPurchaseRequest::quantity));

        List<ProductPurchaseResponse> responses = new ArrayList<>();
        
        for (Product product : products) {
            int requestedQuantity = requestQuantities.get(product.getId());
            if (product.getAvailableQuantity() < requestedQuantity) {
                throw new ProductPurchaseException("Insufficient quantity available for product: " + product.getId());
            }
            
            product.setAvailableQuantity(product.getAvailableQuantity() - requestedQuantity);
            
            responses.add(new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                requestedQuantity,
                product.getPrice()
            ));
        }

        productRepository.saveAll(products);
        return responses;
    }
}
