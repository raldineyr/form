package form.controller;

import form.Reponse.CustomerResponse;
import form.Request.CustomerRequest;
import form.converter.CustomerConverter;
import form.model.Customer;
import form.repository.CustomerRepository;
import form.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/customers",produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController extends RepresentationModel<CustomerResponse> {

    private CustomerRepository customerRepository;
    private CustomerService customerService;
    private CustomerConverter customerConverter;
    private ModelMapper modelMapper;

    @GetMapping
    public List<CustomerResponse> listCustomers(){
        return customerConverter.toCollectionRepresentational(customerRepository.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findCustomer(@PathVariable("id") Long id){

        return customerRepository.findById(id).map(foundUser -> ResponseEntity
                .ok(customerConverter.toRepresentational(foundUser))).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@Valid @RequestBody CustomerRequest customerRequest){

        Customer newCustomer = customerService.saveCustomer(customerRequest);
        newCustomer.setCreationDate(new Date());

        return customerConverter.toRepresentational(newCustomer);
    }

    private CustomerResponse toRepresentational(Customer customer) {
        CustomerResponse linkCustomer = modelMapper.map(customer, CustomerResponse.class);

        linkCustomer.add(WebMvcLinkBuilder.linkTo(CustomerController.class)
                .slash(customer.getId()).withSelfRel());

        return modelMapper.map(customer, CustomerResponse.class);
    }


    @PutMapping("/{id}")
    public CustomerResponse fullyUpdateCustomer(
            @PathVariable("id") Long id, @RequestBody CustomerRequest customerRequest){
        customerRequest.setId(id);
        Customer customerFullyChecked = customerService.saveFullyUpdates(customerRequest);

        return customerConverter.toRepresentational(customerFullyChecked);
    }


    @PatchMapping("/{id}")
    public CustomerResponse incrementallyUpdateCustomer(
            @PathVariable("id") Long id, @RequestBody CustomerRequest customerRequest){

        customerRequest.setId(id);
        Customer customerIncrementallyChecked = customerService.saveIncrementallyUpdates(customerRequest);
        return customerConverter.toRepresentational(customerIncrementallyChecked);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") Long id){
        customerService.deleteCustomer(id);
    }


}