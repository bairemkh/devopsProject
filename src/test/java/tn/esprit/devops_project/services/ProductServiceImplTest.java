package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
//@SpringJUnitConfig
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @Test
    void addProduct() {
        Stock stock = new Stock();
        Product product = new Product();
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        when(productRepository.save(product)).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            savedProduct.setIdProduct(1L);
            return savedProduct;
        });
        Product addedProduct = productService.addProduct(product, 1L);

        assertNotNull(addedProduct);
        assertEquals(stock, addedProduct.getStock());

        verify(stockRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void retrieveProduct() {
        Product mockProduct = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

        Product retrievedProduct = productService.retrieveProduct(1L);

        assertNotNull(retrievedProduct);
        assertEquals(mockProduct, retrievedProduct);

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void retreiveAllProduct() {
        List<Product> mockProducts = new ArrayList<>();
        Product mockProduct1 = new Product();
        Product mockProduct2 = new Product();
        mockProducts.add(mockProduct1);
        mockProducts.add(mockProduct2);

        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> products = productService.retreiveAllProduct();

        assertNotNull(products);
        assertEquals(2, products.size());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void retrieveProductByCategory() {
        List<Product> mockProducts = new ArrayList<>();
        Product mockProduct1 = new Product();
        mockProduct1.setCategory(ProductCategory.ELECTRONICS);
        Product mockProduct2 = new Product();
        mockProduct2.setCategory(ProductCategory.BOOKS);
        mockProducts.add(mockProduct1);
        mockProducts.add(mockProduct2);

        when(productRepository.findByCategory(ProductCategory.ELECTRONICS)).thenReturn(List.of(mockProduct1));

        List<Product> products = productService.retrieveProductByCategory(ProductCategory.ELECTRONICS);

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(ProductCategory.ELECTRONICS, products.get(0).getCategory());

        verify(productRepository, times(1)).findByCategory(ProductCategory.ELECTRONICS);
    }

    @Test
    void deleteProduct() {
        doNothing().when(productRepository).deleteById(1L);
        assertDoesNotThrow(() -> productService.deleteProduct(1L));
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void retrieveProductStock() {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        Product mockProduct = new Product();
        mockProduct.setStock(stock);

        when(productRepository.findByStockIdStock(1L)).thenReturn(List.of(mockProduct));

        List<Product> products = productService.retreiveProductStock(1L);

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(stock.getIdStock(), products.get(0).getStock().getIdStock());

        verify(productRepository, times(1)).findByStockIdStock(1L);
    }
}