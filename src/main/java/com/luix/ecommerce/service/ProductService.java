package com.luix.ecommerce.service;

import com.luix.ecommerce.dto.product.ProductRequestDTO;
import com.luix.ecommerce.dto.product.ProductResponseDTO;
import com.luix.ecommerce.dto.product.ProductUpdateDTO;
import com.luix.ecommerce.entity.Category;
import com.luix.ecommerce.entity.Product;
import com.luix.ecommerce.exception.ResourceNotFoundException;
import com.luix.ecommerce.mapper.ProductMapper;
import com.luix.ecommerce.repository.CategoryRepository;
import com.luix.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ProductMapper mapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Product product = mapper.toEntity(dto);
        Set<Category> categories = dto.categoriesIds()
                .stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(id)))
                .collect(Collectors.toSet());
        product.getCategories().addAll(categories);

        return mapper.toDto(
                productRepository.save(product)
        );
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findProductById(Long id) {
        return mapper.toDto(
                productRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(id))
        );
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAllProducts() {
        return productRepository.findAllByActiveTrue()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductUpdateDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        product.updateInfo(dto);

        if(dto.categoriesIds() != null) {
            Set<Category> categories = dto.categoriesIds()
                    .stream()
                    .map(catId -> categoryRepository.findById(catId)
                            .orElseThrow(() -> new ResourceNotFoundException(catId)))
                    .collect(Collectors.toSet());

            product.getCategories().clear();
            product.getCategories().addAll(categories);
        }

        return mapper.toDto(productRepository.save(product));
    }

    @Transactional
    public void deactivateProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        product.setActive(false);
        productRepository.save(product);
    }

}
