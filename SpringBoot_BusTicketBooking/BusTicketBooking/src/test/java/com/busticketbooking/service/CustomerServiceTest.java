package com.busticketbooking.service;

import com.busticketbooking.dto.CustomerResponseDto;
import com.busticketbooking.exception.ResourceNotFoundException;
import com.busticketbooking.mapper.CustomerMapper;
import com.busticketbooking.model.Customer;
import com.busticketbooking.model.User;
import com.busticketbooking.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    // Which repository(s) are u mocking

    @Mock
    private CustomerRepository customerRepository;

    private CustomerMapper customerMapper;

    // In which service are u testing

    private CustomerService customerService;

    private Customer customer;
    private Customer customer1;

    // Common Sample data for all test cases

    @BeforeEach
    public void sampleData(){

        customerMapper = new CustomerMapper();

        customerService = new CustomerService(
                customerRepository,
                customerMapper
        );

        User user = new User();
        user.setEmail("ram@gmail.com");

        User user1 = new User();
        user1.setEmail("jai@gmail.com");

        customer = new Customer();
        customer.setCustomerId(1);
        customer.setCustomerName("Ram");
        customer.setPhone("9876543210");
        customer.setAddress("Hyderabad");
        customer.setUser(user);

        customer1 = new Customer();
        customer1.setCustomerId(2);
        customer1.setCustomerName("Jai");
        customer1.setPhone("9999999999");
        customer1.setAddress("Chennai");
        customer1.setUser(user1);
    }

    @Test
    void getByEmail_customerExists(){

        when(customerRepository.findByUserEmail("ram@gmail.com")).thenReturn(customer);

        Customer actualCall = customerService.getByEmail("ram@gmail.com");

        assertThat(actualCall.getCustomerName()).isEqualTo("Ram");
    }

    @Test
    void getById_customerExists(){

        when(customerRepository.findById(100))
                .thenReturn(Optional.of(customer));

        assertThat(
                customerService.getById(100)
                        .getCustomerId()).isEqualTo(1);

        assertThat(customerService.getById(100).getCustomerName()).isEqualTo("Ram");
    }

    @Test
    void getById_customerDoesNotExist(){

        when(customerRepository.findById(100))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getById(100))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Invalid Customer ID");

        verify(customerRepository,times(1)).findById(100);
    }

    @Test
    void getCustomerById_mustReturnDto(){

        when(customerRepository.findById(100)).thenReturn(Optional.of(customer));

        CustomerResponseDto dto =
                customerService.getCustomerById(100);

        assertThat(dto.customerName()).isEqualTo("Ram");

        assertThat(dto.email()).isEqualTo("ram@gmail.com");
    }

    @Test
    public void getAllCustomers_MustReturnSomething(){

        when(customerRepository.findAll())
                .thenReturn(List.of(customer,customer1));

        List<CustomerResponseDto> actualCall =
                customerService.getAllCustomers();

        assertThat(actualCall).hasSize(2);
        assertThat(actualCall.getFirst().customerName()).isEqualToIgnoringCase("Ram");
        assertThat(actualCall.get(1).customerName()).isEqualToIgnoringCase("Jai");
    }

    @Test
    public void getAllCustomers_ReturnsEmptyList(){

        when(customerRepository.findAll()).thenReturn(List.of());

        List<CustomerResponseDto> actualCall = customerService.getAllCustomers();

        assertThat(actualCall).isEmpty();
    }

    @Test
    void getCustomerCount_mustReturnCount(){

        when(customerRepository.count()).thenReturn(2L);

        long count = customerService.getCustomerCount();

        assertThat(count).isEqualTo(2L);

        verify(customerRepository,times(1)).count();
    }
}