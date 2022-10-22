package org.openlab.openlabcustomerservice.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.openlab.openlabcustomerservice.dtos.CustomerRequestDTO;
import org.openlab.openlabcustomerservice.dtos.CustomerResponseDTO;
import org.openlab.openlabcustomerservice.entities.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponseDTO cutomerToCustomerResponseDTO(Customer customer);
    Customer cutomerRequestDTOToCustomer(CustomerRequestDTO customerRequestDTO);

}

