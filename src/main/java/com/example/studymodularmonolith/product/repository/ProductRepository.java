package com.example.studymodularmonolith.product.repository;

import com.example.studymodularmonolith.product.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProductRepository {
    private final ConcurrentHashMap<UUID, Product> products = new ConcurrentHashMap<>();

    public Product save(Product product) {
        products.put(product.getId(), product);
        return product;
    }

    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public void deleteById(UUID id) {
        products.remove(id);
    }

    public List<Product> findByNameContaining(String name) {
        return products.values().stream()
                .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    public List<Product> findInStock() {
        return products.values().stream()
                .filter(Product::isInStock)
                .toList();
    }
}