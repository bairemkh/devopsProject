package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OperatorServiceImplTest {

    @InjectMocks
    private OperatorServiceImpl operatorService;

    @Mock
    private OperatorRepository operatorRepository;

    @Test
    void retrieveAllOperators() {
        List<Operator> mockOperators = new ArrayList<>();
        Operator operator1 = new Operator();
        operator1.setIdOperateur(1L);
        Operator operator2 = new Operator();
        operator2.setIdOperateur(2L);
        mockOperators.add(operator1);
        mockOperators.add(operator2);
        when(operatorRepository.findAll()).thenReturn(mockOperators);
        List<Operator> operators = operatorService.retrieveAllOperators();
        assertNotNull(operators);
        assertEquals(2, operators.size());
    }

    @Test
    void addOperator() {
        Operator operator = new Operator();
        operator.setIdOperateur(1L);
        when(operatorRepository.save(operator)).thenReturn(operator);
        Operator addedOperator = operatorService.addOperator(operator);
        assertNotNull(addedOperator);
        assertEquals(1L, addedOperator.getIdOperateur());
    }

    @Test
    void deleteOperator() {
        Operator operator = new Operator();
        operator.setIdOperateur(1L);
        doNothing().when(operatorRepository).deleteById(1L);
        assertDoesNotThrow(() -> operatorService.deleteOperator(1L));
    }

    @Test
    void updateOperator() {
        Operator operator = new Operator();
        operator.setIdOperateur(1L);
        when(operatorRepository.save(Mockito.any(Operator.class))).thenReturn(operator);
        Operator updatedOperator = operatorService.updateOperator(operator);
        assertNotNull(updatedOperator);
        assertEquals(1L, updatedOperator.getIdOperateur());
    }

    @Test
    void retrieveOperator() {
        Operator operator = new Operator();
        operator.setIdOperateur(1L);
        when(operatorRepository.findById(1L)).thenReturn(Optional.of(operator));
        Operator retrievedOperator = operatorService.retrieveOperator(1L);
        assertNotNull(retrievedOperator);
        assertEquals(1L, retrievedOperator.getIdOperateur());
    }
}
