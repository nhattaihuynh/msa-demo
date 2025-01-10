package com.taihuynh.ecommerce.product;

import com.taihuynh.ecommerce.category.Category;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_seq")
    @SequenceGenerator(name = "products_seq", sequenceName = "products_seq", allocationSize = 1)
    private Integer id;

    private String name;
    private String description;

    private int availableQuantity;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Default constructor
    public Product() {
    }

    // All-args constructor
    public Product(Integer id, String name, String description, int availableQuantity, BigDecimal price, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.availableQuantity = availableQuantity;
        this.price = price;
        this.category = category;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Builder pattern implementation
    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private Integer id;
        private String name;
        private String description;
        private int availableQuantity;
        private BigDecimal price;
        private Category category;

        ProductBuilder() {
        }

        public ProductBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder availableQuantity(int availableQuantity) {
            this.availableQuantity = availableQuantity;
            return this;
        }

        public ProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public Product build() {
            return new Product(id, name, description, availableQuantity, price, category);
        }
    }
}
