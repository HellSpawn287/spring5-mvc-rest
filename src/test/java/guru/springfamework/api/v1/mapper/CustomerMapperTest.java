package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerMapperTest {

    private static final String NAME = "someNAME";
    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {
//      given
        Customer customer = new Customer();
        customer.setFirstname(NAME);
        customer.setLastname(NAME);
//      when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

//      then
        assertEquals(customer.getFirstname(), customerDTO.getFirstname());
        assertEquals(customer.getLastname(), customerDTO.getLastname());
    }

    @Test
    public void customerDTOToCustomer() {
//        given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(NAME);
        customerDTO.setLastname(NAME);

//        when
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

//        then
        assertEquals(customerDTO.getFirstname(), customer.getFirstname());
        assertEquals(customerDTO.getLastname(), customer.getLastname());
    }
}