package com.jimi.spring6restmvc.mappers;

import com.jimi.spring6restmvc.entities.Customer;
import com.jimi.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtotoCustomer(CustomerDTO dto);
    CustomerDTO customertoCustomerDto(Customer customer);

}
