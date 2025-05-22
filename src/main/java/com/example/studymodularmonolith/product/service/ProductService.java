package com.example.studymodularmonolith.product.service;

import com.example.studymodularmonolith.product.domain.Product;
import com.example.studymodularmonolith.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(String name, String description, BigDecimal price, int stockQuantity) {
        // In a real application, we would validate inputs
        Product product = new Product(name, description, price, stockQuantity);
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(UUID id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }

    public List<Product> getProductsInStock() {
        return productRepository.findInStock();
    }

    public Product updateProduct(UUID id, String name, String description, BigDecimal price) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(name);
                    product.setDescription(description);
                    product.setPrice(price);
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public Product updateStock(UUID id, int quantity) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setStockQuantity(quantity);
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}