package com.example.ensetbillingservice;

import com.example.ensetbillingservice.dtos.InvoiceRequestDTO;
import com.example.ensetbillingservice.dtos.InvoiceResponseDTO;
import com.example.ensetbillingservice.repositories.InvoiceRepository;
import com.example.ensetbillingservice.service.InvoiceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
@EnableFeignClients
public class EnsetBillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnsetBillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(InvoiceService invoiceService){
        return args ->{
            invoiceService.save(new InvoiceRequestDTO(BigDecimal.valueOf(76000),"C01"));
            invoiceService.save(new InvoiceRequestDTO(BigDecimal.valueOf(545600),"C01"));
            invoiceService.save(new InvoiceRequestDTO(BigDecimal.valueOf(7825000),"C02"));

         //  InvoiceResponseDTO invoiceResponseDTO = invoiceService.getInvoice(invoiceRepository.findAll().get(0).getId());
           // System.out.println(invoiceResponseDTO.getAmount());;
        };
    }
}
