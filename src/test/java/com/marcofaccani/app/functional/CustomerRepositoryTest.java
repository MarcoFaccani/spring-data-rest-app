package com.marcofaccani.app.functional;

import java.util.List;
import java.util.Optional;

import com.marcofaccani.app.entity.Customer;
import com.marcofaccani.app.entity.projection.CustomerView;
import com.marcofaccani.app.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CustomerRepositoryTest {

  @Autowired
  CustomerRepository underTest;

  @AfterEach
  void tearDown() {
    underTest.deleteAll();
  }

  @Test
  void loadContextTest() { }

  @Nested
  class ProjectionsTest {

    @Test
    void shouldGetAllCustomersUsingView() {
      var all = underTest.findAll();
      assertNotNull(all);
      assertThat(all).hasSize(3);
      assertThat(all.get(0)).isInstanceOf(CustomerView.class);
    }

    @Test
    void shouldGetCustomerView() {
      Optional<Customer> optCustomer = underTest.findById(1L);
      assertThat(optCustomer).isPresent();
      assertThat(optCustomer.get()).isInstanceOf(CustomerView.class);
    }

  }

}
