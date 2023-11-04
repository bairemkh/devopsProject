package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.InvoiceServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

//@SpringBootTest
//@SpringJUnitConfig
@ExtendWith(MockitoExtension.class)
class InvoiceServiceImplTest {

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private OperatorRepository operatorRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Test
    void retrieveAllInvoices() {
        // Create a list of mock invoices
        List<Invoice> mockInvoices = List.of(
                new Invoice(1L, 100.0f, 200.0f, new Date(), new Date(), false, null, null),
                new Invoice(2L, 150.0f, 250.0f, new Date(), new Date(), false, null, null)
        );
        when(invoiceRepository.findAll()).thenReturn(mockInvoices);
        List<Invoice> invoices = invoiceService.retrieveAllInvoices();
        assertNotNull(invoices);
        assertEquals(2, invoices.size());
    }

    @Test
    void cancelInvoice() {
        Invoice mockInvoice = new Invoice(1L, 100.0f, 200.0f, new Date(), new Date(), false, null, null);
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(mockInvoice));
        when(invoiceRepository.save(mockInvoice)).thenReturn(mockInvoice);
        invoiceService.cancelInvoice(1L);
        assertTrue(mockInvoice.getArchived());
    }

    @Test
    void retrieveInvoice() {
        Invoice mockInvoice = new Invoice(1L, 100.0f, 200.0f, new Date(), new Date(), false, null, null);
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(mockInvoice));
        Invoice retrievedInvoice = invoiceService.retrieveInvoice(1L);
        assertNotNull(retrievedInvoice);
        assertEquals(1L, retrievedInvoice.getIdInvoice());
    }

    @Test
    void getInvoicesBySupplier() {
        Supplier mockSupplier = new Supplier();
        mockSupplier.setIdSupplier(1L);
        Set<Invoice> mockInvoices = Set.of(
                new Invoice(1L, 100.0f, 200.0f, new Date(), new Date(), false, null, mockSupplier),
                new Invoice(2L, 150.0f, 250.0f, new Date(), new Date(), false, null, mockSupplier)
        );
        mockSupplier.setInvoices(mockInvoices);
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(mockSupplier));
        List<Invoice> invoices = invoiceService.getInvoicesBySupplier(1L);
        assertNotNull(invoices);
        assertEquals(2, invoices.size());
    }

    @Test
    void getTotalAmountInvoiceBetweenDates() {
        when(invoiceRepository.getTotalAmountInvoiceBetweenDates(Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(500f);
        float totalAmount = invoiceService.getTotalAmountInvoiceBetweenDates(new Date(), new Date());
        assertEquals(500.0, totalAmount);
    }
}
