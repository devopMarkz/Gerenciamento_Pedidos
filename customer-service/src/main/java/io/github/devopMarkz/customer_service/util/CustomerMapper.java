package io.github.devopMarkz.customer_service.util;

import io.github.devopMarkz.customer_service.dto.CustomerCreateDTO;
import io.github.devopMarkz.customer_service.dto.CustomerResponseDTO;
import io.github.devopMarkz.customer_service.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponseDTO toCustomerResponseDTO(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getAddress()
        );
    }

    public Customer toCustomer(CustomerCreateDTO customerCreateDTO) {
        return new Customer(
                null,
                customerCreateDTO.name(),
                customerCreateDTO.email(),
                customerCreateDTO.phoneNumber(),
                customerCreateDTO.address()
        );
    }

}
