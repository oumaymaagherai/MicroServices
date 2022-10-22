package org.openlab.openlabcustomerservice.services;

import lombok.AllArgsConstructor;
import org.openlab.openlabcustomerservice.dtos.CustomerRequestDTO;
import org.openlab.openlabcustomerservice.dtos.CustomerResponseDTO;
import org.openlab.openlabcustomerservice.entities.Customer;
import org.openlab.openlabcustomerservice.mappers.CustomerMapper;
import org.openlab.openlabcustomerservice.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional

public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository ;
    private CustomerMapper customerMapper ;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerResponseDTO save(CustomerRequestDTO customerRequestDTO) {
        Customer customer = customerMapper.cutomerRequestDTOToCustomer(customerRequestDTO) ;
        Customer savedCustomer = customerRepository.save(customer) ;
        return customerMapper.cutomerToCustomerResponseDTO(savedCustomer) ;

    }

    @Override
    public CustomerResponseDTO getCustumer(String id) {
    Customer customer = customerRepository.getById(id);
    return  customerMapper.cutomerToCustomerResponseDTO(customer);
    }

    @Override
    public CustomerResponseDTO update(CustomerRequestDTO customerRequestDTO) {
        Customer customer = customerMapper.cutomerRequestDTOToCustomer(customerRequestDTO) ;
        Customer updatedCustomer = customerRepository.save(customer) ;
        return customerMapper.cutomerToCustomerResponseDTO(updatedCustomer) ;

    }

    @Override
    public List<CustomerResponseDTO> listCustomers() {
        List<Customer> customers =customerRepository.findAll() ;
        List<CustomerResponseDTO> customerResponseDTOS =customers.stream()
                .map(customer -> customerMapper.cutomerToCustomerResponseDTO(customer))
                .collect(Collectors.toList());
        return  customerResponseDTOS;
    }
}
