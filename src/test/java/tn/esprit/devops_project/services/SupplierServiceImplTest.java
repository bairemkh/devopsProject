package tn.esprit.devops_project.services;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.entities.SupplierCategory;
import tn.esprit.devops_project.repositories.SupplierRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@SpringJUnitConfig
class SupplierServiceImplTest {
    @InjectMocks
    private SupplierServiceImpl supplierService;

    @Mock
    private SupplierRepository supplierRepository;

    @Test
    void retrieveAllSuppliers() {
        List<Supplier> mockSuppliers = new ArrayList<>();
        Supplier mockSupplier1= new Supplier();
        mockSupplier1.setSupplierCategory(SupplierCategory.ORDINAIRE);
        mockSupplier1.setCode("1233");
        mockSupplier1.setLabel("supplier 1");
        Supplier mockSupplier2= new Supplier();
        mockSupplier2.setSupplierCategory(SupplierCategory.CONVENTIONNE);
        mockSupplier2.setCode("1333");
        mockSupplier2.setLabel("supplier 2");
        mockSuppliers.add(mockSupplier1);
        mockSuppliers.add(mockSupplier2);
        when(supplierRepository.findAll()).thenReturn(mockSuppliers);
        List<Supplier> suppliers = supplierService.retrieveAllSuppliers();
        assertNotNull(suppliers);
        assertEquals(2, suppliers.size());
    }

    @Test
    void addSupplier() {
        Supplier mockSupplier = new Supplier();
        mockSupplier.setSupplierCategory(SupplierCategory.ORDINAIRE);
        mockSupplier.setCode("auuef");
        mockSupplier.setLabel("supplier 1");
        when(supplierRepository.save(mockSupplier)).thenReturn(mockSupplier);
        Supplier addedSupplier = supplierService.addSupplier(mockSupplier);
        assertNotNull(addedSupplier.getIdSupplier());
    }

    @Test
    void updateSupplier() {
        Supplier mockSupplier = new Supplier();
        mockSupplier.setSupplierCategory(SupplierCategory.ORDINAIRE);
        mockSupplier.setCode("auuef");
        mockSupplier.setLabel("supplier 1");
        when(supplierRepository.save(mockSupplier)).thenReturn(mockSupplier);
        Supplier addedSupplier = supplierService.addSupplier(mockSupplier);
        addedSupplier.setSupplierCategory(SupplierCategory.CONVENTIONNE);
        addedSupplier.setCode("testUpdate");
        addedSupplier.setLabel("supplier updated 1");
        when(supplierRepository.save(addedSupplier)).thenReturn(addedSupplier);
        Supplier updatedSupplier = supplierService.updateSupplier(addedSupplier);
        assertNotNull(addedSupplier.getIdSupplier());
        assertEquals(addedSupplier.getIdSupplier(), updatedSupplier.getIdSupplier());
        assertEquals(addedSupplier.getSupplierCategory(), updatedSupplier.getSupplierCategory());
    }

    @Test
    void deleteSupplier() {
        Supplier mockSupplier = new Supplier();
        mockSupplier.setSupplierCategory(SupplierCategory.ORDINAIRE);
        mockSupplier.setCode("test delete");
        mockSupplier.setLabel("supplier delete");
        when(supplierRepository.save(mockSupplier)).thenReturn(mockSupplier);
        Supplier addedSupplier = supplierService.addSupplier(mockSupplier);
        when(supplierRepository.findById(addedSupplier.getIdSupplier())).thenReturn(Optional.of(addedSupplier));
        doNothing().when(supplierRepository).delete(addedSupplier);
        assertDoesNotThrow(() -> supplierService.deleteSupplier(addedSupplier.getIdSupplier()));
        assertThrows(IllegalArgumentException.class, () -> supplierService.retrieveSupplier(addedSupplier.getIdSupplier()));
    }

    @Test
    void retrieveSupplier() {
        Supplier mockSupplier = new Supplier();
        mockSupplier.setSupplierCategory(SupplierCategory.ORDINAIRE);
        mockSupplier.setCode("test retrieve");
        mockSupplier.setLabel("supplier retrieve");
        when(supplierRepository.save(any(Supplier.class))).thenReturn(mockSupplier);
        Supplier addedSupplier = supplierService.addSupplier(mockSupplier);
        when(supplierRepository.findById(addedSupplier.getIdSupplier())).thenReturn(Optional.of(addedSupplier));
        Supplier retrievedSupplier = supplierService.retrieveSupplier(addedSupplier.getIdSupplier());
        assertEquals(addedSupplier.getIdSupplier(), retrievedSupplier.getIdSupplier());
    }
}