package com.example.ensetbillingservice.service;

import com.example.ensetbillingservice.dtos.InvoiceRequestDTO;
import com.example.ensetbillingservice.dtos.InvoiceResponseDTO;
import com.example.ensetbillingservice.entities.Customer;
import com.example.ensetbillingservice.entities.Invoice;
import com.example.ensetbillingservice.exceptions.CustomerNotFoundException;
import com.example.ensetbillingservice.mappers.InvoiceMapper;
import com.example.ensetbillingservice.openfeign.CustomerRestClient;
import com.example.ensetbillingservice.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceRepository invoiceRepository ;
    private InvoiceMapper invoiceMapper ;
    private CustomerRestClient customerRestClient ;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, CustomerRestClient customerRestClient) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.customerRestClient = customerRestClient;
    }

    @Override
    public InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO) {
        Customer customer =null;
        try{
           customer = customerRestClient.getCustomer(invoiceRequestDTO.getCustomerId());
        }catch (Exception e){
            throw  new CustomerNotFoundException("Customer "+invoiceRequestDTO.getCustomerId()+" not found");
        }

        Invoice invoice = invoiceMapper.fromInvoiceDTO(invoiceRequestDTO);
        invoice.setId(UUID.randomUUID().toString());
        invoice.setDate(new Date());

        Invoice savedInvoice = invoiceRepository.save(invoice);
    savedInvoice.setCustomer(customer);

        return invoiceMapper.fromInvoice(savedInvoice);
    }

    @Override
    public InvoiceResponseDTO getInvoice(String invoiceId) {
        Invoice invoice=invoiceRepository.findById(invoiceId).get();

        Customer customer = customerRestClient.getCustomer(invoice.getCustomerId());

        invoice.setCustomer(customer);
        return invoiceMapper.fromInvoice(invoice);
    }

    @Override
    public List<InvoiceResponseDTO> invoicesByCustomerId(String customerId) {
        List<Invoice> invoices = invoiceRepository.findByCustomerId(customerId);
        invoices.forEach(i->i.setCustomer(customerRestClient.getCustomer(i.getCustomerId())));
        return invoices.stream().map(invoice -> invoiceMapper.fromInvoice(invoice)).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceResponseDTO> allInvoices() {
       List<Invoice> invoices =invoiceRepository.findAll() ;
       return invoices.stream().map(invoice -> invoiceMapper.fromInvoice(invoice)).collect(Collectors.toList());
    }
}
