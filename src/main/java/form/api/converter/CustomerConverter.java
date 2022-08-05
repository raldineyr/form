package form.api.converter;


import form.api.controller.CustomerController;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

import form.api.Reponse.CustomerResponse;
import form.api.Request.CustomerRequest;
import form.domain.model.Customer;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor
public class CustomerConverter {

    private ModelMapper modelMapper;

    public CustomerResponse toRepresentational(Customer customer) {
        CustomerResponse linkCustomer = modelMapper.map(customer, CustomerResponse.class);

        linkCustomer.add(WebMvcLinkBuilder.linkTo(CustomerController.class)
                .slash(customer.getId()).withSelfRel());

        return modelMapper.map(customer, CustomerResponse.class);
    }

    public Customer toEntity(CustomerRequest customerRequest) {

        return modelMapper.map(customerRequest, Customer.class);
    }

    public List<CustomerResponse> toCollectionRepresentational(List<Customer> customers) {
        return customers.stream().map(
                this::toRepresentational).collect(Collectors.toList());
    }
}