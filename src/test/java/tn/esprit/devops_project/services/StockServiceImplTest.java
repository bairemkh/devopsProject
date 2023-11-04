package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@SpringJUnitConfig
@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockRepository stockRepository;



    @Test
    void addStock() {
        Stock mockStock = new Stock();
        mockStock.setTitle("Stock 1");
        Mockito.when(stockRepository.save(mockStock)).thenReturn(mockStock);
        Stock addedStock = stockService.addStock(mockStock);
        assertNotNull(addedStock);
    }

    @Test
    void retrieveStock() {
        Stock mockStock = new Stock();
        mockStock.setIdStock(1L);
        mockStock.setTitle("Stock 1");
        Mockito.when(stockRepository.save(mockStock)).thenReturn(mockStock);
        Mockito.when(stockRepository.findById(1L)).thenReturn(Optional.of(mockStock));
        Stock addedStock = stockService.addStock(mockStock);
        Stock retrievedStock = stockService.retrieveStock(addedStock.getIdStock());
        assertEquals(addedStock.getIdStock(), retrievedStock.getIdStock());
    }

    @Test
    void retrieveAllStock() {
        List<Stock> mockStocks = new ArrayList<>();
        Stock stock1 = new Stock();
        stock1.setTitle("Stock 1");
        Stock stock2 = new Stock();
        stock2.setTitle("Stock 2");
        mockStocks.add(stock1);
        mockStocks.add(stock2);
        Mockito.when(stockRepository.findAll()).thenReturn(mockStocks);
        List<Stock> stocks = stockService.retrieveAllStock();
        assertNotNull(stocks);
    }
}