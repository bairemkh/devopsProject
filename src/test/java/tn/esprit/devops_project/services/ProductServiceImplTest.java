package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        when(productRepository.save(product)).thenReturn(product);
        Product addedProduct = productService.addProduct(product, 1L);

        assertNotNull(addedProduct);
        assertEquals(stock, addedProduct.getStock());

        verify(stockRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void retrieveProduct() {
    }

    @Test
    void retreiveAllProduct() {
    }

    @Test
    void retrieveProductByCategory() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void retreiveProductStock() {
    }
}