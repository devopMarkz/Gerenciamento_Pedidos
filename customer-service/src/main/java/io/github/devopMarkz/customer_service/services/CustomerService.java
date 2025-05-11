package io.github.devopMarkz.customer_service.services;

import io.github.devopMarkz.customer_service.dto.CustomerCreateDTO;
import io.github.devopMarkz.customer_service.dto.CustomerResponseDTO;
import io.github.devopMarkz.customer_service.model.Customer;
import io.github.devopMarkz.customer_service.repositories.CustomerRepository;
import io.github.devopMarkz.customer_service.util.CustomerMapper;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional
    public CustomerResponseDTO createCustomer(CustomerCreateDTO customerCreateDTO) {
        Customer customer = customerMapper.toCustomer(customerCreateDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toCustomerResponseDTO(savedCustomer);
    }

    @Transactional(readOnly = true)
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        return customerMapper.toCustomerResponseDTO(customer);
    }

}
