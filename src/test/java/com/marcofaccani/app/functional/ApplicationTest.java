package com.marcofaccani.app.functional;

import java.net.URI;
import java.time.LocalDate;

import com.marcofaccani.app.entity.Customer;
import com.marcofaccani.app.entity.projection.CustomerView;
import com.marcofaccani.app.repository.CustomerRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.client.Hop;
import org.springframework.hateoas.client.Traverson;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.data.rest.webmvc.RestMediaTypes.HAL_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ApplicationTest {

  @LocalServerPort
  private int port;

  @Value("${spring.data.rest.base-path}")
  private String basePath;

  private String baseUrl;

  @Autowired
  CustomerRepository repository;

  @Autowired
  WebTestClient client;

  ParameterizedTypeReference<PagedModel<Customer>> pagedCustomer = new ParameterizedTypeReference<>(){};


  @BeforeEach
  @SneakyThrows
  void setup() {
    baseUrl = "http://localhost:" + port + basePath;
  }

  @Test
  public void contextLoadTest() { }

  @Nested
  class LinksTests {

    @Test
    @SneakyThrows
    void shouldRetrieveCustomersEntitiesFollowingLinkFromBaseUrl() {
      Traverson client = new Traverson(new URI(baseUrl), MediaTypes.HAL_JSON);
      var customers = client
          .follow("customers")
          .toObject(pagedCustomer)
          .getContent();
      assertThat(customers).hasSize(3);
    }

    @Test
    @SneakyThrows
    void shouldRetrieveFirstCustomerByFollowingLinkFromGetAllCustomers() {
      Traverson traverson = new Traverson(new URI(baseUrl + "/customers"), MediaTypes.HAL_JSON);
      var customer = traverson
          .follow("$._embedded.customers[0]._links.self.href")
          .toObject(new ParameterizedTypeReference<EntityModel<Customer>>() {} )
          .getContent();
      assertNotNull(customer);
      assertNull(customer.getId());
      assertEquals("marco", customer.getFirstname());
      assertEquals("faccani", customer.getLastname());
      assertEquals(LocalDate.of(1995, 7, 10), customer.getBirthDate());
      assertEquals("sensitiveDataOne", customer.getSensitiveDataOne());
      assertEquals("sensitiveDataTwo", customer.getSensitiveDataTwo());
    }

  }

  @Test
  void shouldRetrieveCustomerEntityByIdAndShouldNotReturnId() {
    client.get().uri(baseUrl.concat("/customers/1")).accept(HAL_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(HAL_JSON)
        .expectBody(new ParameterizedTypeReference<Customer>() {})
        .consumeWith(response -> {
          var customer = response.getResponseBody();
          assertNotNull(customer);
          assertNull(customer.getId());
          assertEquals("marco", customer.getFirstname());
          assertEquals("faccani", customer.getLastname());
          assertEquals(LocalDate.of(1995, 7, 10), customer.getBirthDate());
          assertEquals("sensitiveDataOne", customer.getSensitiveDataOne());
          assertEquals("sensitiveDataTwo", customer.getSensitiveDataTwo());
        });
  }

  @Test
  @SneakyThrows
  void shouldRetrieveFirstCustomerEntity() {
    Traverson client = new Traverson(new URI(baseUrl), MediaTypes.HAL_JSON);
    var customer = client
        .follow("customers", "$._embedded.customers[0]._links.self.href")
        .toObject(new ParameterizedTypeReference<EntityModel<Customer>>() {} )
        .getContent();
    assertNotNull(customer);
    assertNull(customer.getId());
    assertEquals("marco", customer.getFirstname());
    assertEquals("faccani", customer.getLastname());
    assertEquals(LocalDate.of(1995, 7, 10), customer.getBirthDate());
    assertEquals("sensitiveDataOne", customer.getSensitiveDataOne());
    assertEquals("sensitiveDataTwo", customer.getSensitiveDataTwo());
  }


}
