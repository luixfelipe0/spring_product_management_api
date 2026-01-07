package com.luix.spring_product_management_api.service;

import com.luix.ecommerce.dto.product.ProductRequestDTO;
import com.luix.ecommerce.dto.product.ProductResponseDTO;
import com.luix.ecommerce.entity.Category;
import com.luix.ecommerce.entity.Product;
import com.luix.ecommerce.exception.ResourceNotFoundException;
import com.luix.ecommerce.mapper.ProductMapper;
import com.luix.ecommerce.repository.CategoryRepository;
import com.luix.ecommerce.repository.ProductRepository;
import com.luix.ecommerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProductSuccess() {
        ProductRequestDTO dto = new ProductRequestDTO(
                "Mouse gamer",
                "High precision mouse",
                BigDecimal.valueOf(129.90),
                "img.png",
                Set.of(1L)
        );

        Product productEntity = new Product();
        productEntity.setName(dto.name());
        productEntity.setDescription(dto.description());
        productEntity.setPrice(dto.price());
        productEntity.setStockQuantity(30);

        Category category = new Category();
        category.setId(1L);
        category.setName("Peripherals");

        when(productMapper.toEntity(dto)).thenReturn(productEntity);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productMapper.toDto(productEntity)).thenReturn(
                new ProductResponseDTO(
                        1L,
                        productEntity.getName(),
                        productEntity.getDescription(),
                        productEntity.getPrice(),
                        productEntity.getImgUrl(),
                        true,
                        Instant.now(),
                        Instant.now(),
                        Set.of(1L)
                )
        );

        ProductResponseDTO responseDTO = productService.createProduct(dto);

        assertNotNull(responseDTO);
        assertEquals("Mouse gamer", responseDTO.name());
        assertEquals(BigDecimal.valueOf(129.90), responseDTO.price());
        assertTrue(responseDTO.active());

        verify(productMapper).toEntity(dto);
        verify(categoryRepository).findById(1L);
        verify(productRepository).save(productEntity);
        verify(productMapper).toDto(productEntity);
    }

    @Test
    void testCreateProductCategoryNotFound() {
        ProductRequestDTO dto = new ProductRequestDTO(
                "Mouse gamer",
                "High precision mouse",
                BigDecimal.valueOf(129.90),
                "img.png",
                Set.of(99L)
        );

        Product productEntity = new Product();
        productEntity.setName(dto.name());
        productEntity.setDescription(dto.description());
        productEntity.setPrice(dto.price());
        productEntity.setStockQuantity(30);

        when(productMapper.toEntity(dto)).thenReturn(productEntity);
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.createProduct(dto));

        verify(productMapper).toEntity(dto);
        verify(categoryRepository).findById(99L);
        verify(productRepository, never()).save(any(Product.class));
    }

}
