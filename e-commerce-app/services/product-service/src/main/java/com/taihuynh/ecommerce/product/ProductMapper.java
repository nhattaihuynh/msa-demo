package com.taihuynh.ecommerce.product;

import com.taihuynh.ecommerce.category.Category;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request) {
        return Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .availableQuantity(request.quantity())
                .category(Category.builder().id(request.categoryId()).build())
                .build();
    }

    public Product updateEntity(Product product, ProductRequest request) {
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setAvailableQuantity(request.quantity());
        product.setCategory(Category.builder().id(request.categoryId()).build());
        return product;
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                (int) product.getAvailableQuantity(),
                product.getCategory().getId()
        );
    }
}
