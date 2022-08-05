package form.service;

import exceptions.BusinessException;
import form.Request.CustomerRequest;
import form.controller.CustomerController;
import form.converter.CustomerConverter;
import form.model.Customer;
import form.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Optional;

@Service @AllArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;
    private CustomerConverter customerConverter;
    private ModelMapper modelMapper;

    @Transactional
    public Customer saveCustomer(CustomerRequest customerRequest) {

        checkEmailAvailability(customerRequest);


        Customer newCustomer = customerConverter.toEntity(customerRequest);
        return customerRepository.save(newCustomer);
    }

    @Transactional
    public Customer saveFullyUpdates(CustomerRequest customerRequest) {

        Customer foundCustomer = findCustomer(customerRequest.getId());


        foundCustomer.setName(foundCustomer.getName());
        foundCustomer.setRg(foundCustomer.getRg());
        foundCustomer.setCpf(foundCustomer.getCpf());
        foundCustomer.setEmail(foundCustomer.getEmail());
        foundCustomer.setPhone(foundCustomer.getPhone());

        foundCustomer.setFatherName(foundCustomer.getFatherName());
        foundCustomer.setMotherName(foundCustomer.getMotherName());
        return customerRepository.save(foundCustomer);
    }

    @Transactional
    public Customer saveIncrementallyUpdates(CustomerRequest customerRequest) {

        Customer foundCustomer = findCustomer(customerRequest.getId());

        foundCustomer.setName(Optional.ofNullable(
                customerRequest.getName()).orElse(foundCustomer.getName()));

        foundCustomer.setRg(Optional.ofNullable(
                customerRequest.getRg()).orElse(foundCustomer.getRg()));

        foundCustomer.setCpf(Optional.ofNullable(
                customerRequest.getCpf()).orElse(foundCustomer.getCpf()));

        foundCustomer.setEmail(Optional.ofNullable(
                customerRequest.getEmail()).orElse(foundCustomer.getEmail()));

        foundCustomer.setPhone(Optional.ofNullable(
                customerRequest.getPhone()).orElse(foundCustomer.getPhone()));

        foundCustomer.setFatherName(Optional.ofNullable(
                customerRequest.getFatherName()).orElse(foundCustomer.getFatherName()));

        foundCustomer.setMotherName(Optional.ofNullable(
                customerRequest.getMotherName()).orElse(foundCustomer.getMotherName()));

        return customerRepository.save(foundCustomer);
    }


    @Transactional
    public void deleteCustomer(Long id) {
        Customer foundCustomer = findCustomer(id);
        customerRepository.deleteById(id);
    }

    @Transactional
    public void checkEmailAvailability(CustomerRequest customerRequest) {

        boolean emailInUse = customerRepository
                .findByEmail(customerRequest.getEmail())
                .stream()
                .anyMatch(existentUser -> !existentUser.equals(customerRequest));

        if (emailInUse)
            throw new BusinessException("Email em uso por outro cliente, favor cadastrar outro." );

    }
    @Transactional
    public Customer findCustomer(Long id) {

        return customerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }



}