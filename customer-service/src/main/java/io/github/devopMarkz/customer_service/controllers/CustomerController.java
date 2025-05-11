package io.github.devopMarkz.customer_service.controllers;

import io.github.devopMarkz.customer_service.dto.CustomerCreateDTO;
import io.github.devopMarkz.customer_service.dto.CustomerResponseDTO;
import io.github.devopMarkz.customer_service.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.github.devopMarkz.customer_service.util.UriGenerator.generateUri;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerCreateDTO createDTO){
        CustomerResponseDTO customerResponseDTO = customerService.createCustomer(createDTO);
        return ResponseEntity.created(generateUri(customerResponseDTO.id())).body(customerResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable("id") Long id){
        CustomerResponseDTO customerResponseDTO = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerResponseDTO);
    }

}
