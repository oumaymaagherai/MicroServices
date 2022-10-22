package com.example.ensetbillingservice.service;

import com.example.ensetbillingservice.dtos.InvoiceRequestDTO;
import com.example.ensetbillingservice.dtos.InvoiceResponseDTO;

import java.util.List;

public interface InvoiceService {
    public InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO) ;
    InvoiceResponseDTO getInvoice(String invoiceId) ;
    List<InvoiceResponseDTO> invoicesByCustomerId(String customerId);
    List<InvoiceResponseDTO> allInvoices();

}
