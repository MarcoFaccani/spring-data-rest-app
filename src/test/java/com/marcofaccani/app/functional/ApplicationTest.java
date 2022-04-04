package com.marcofaccani.app.functional;

import java.net.URI;
import java.util.List;

import com.marcofaccani.app.entity.Customer;
import com.marcofaccani.app.entity.projection.CustomerView;
import com.marcofaccani.app.repository.CustomerRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.client.Traverson.TraversalBuilder;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

  @LocalServerPort
  private int port;

  @Value("${spring.data.rest.base-path}")
  private String basePath;

  private String baseUrl;

  @Autowired
  TestRestTemplate client;

  @Autowired
  CustomerRepository repository;

  Traverson traverson;


  @BeforeEach
  @SneakyThrows
  void setup() {
    baseUrl = "http://localhost:" + port + basePath;
    traverson = new Traverson(new URI(baseUrl.concat("/customers")), MediaTypes.HAL_JSON);
  }

  @AfterEach
  void tearDown() {
    repository.deleteAll();
  }

  @Test
  public void contextLoadTest() { }

  @Test
  void shouldGetAllCustomersUsingView() {
    var response = client.getForEntity(baseUrl.concat("/customers"), Customer[].class);
    assertNotNull(response);
    var customers = response.getBody();
    assertThat(customers).hasSize(3);
    assertThat(customers[0]).isInstanceOf(CustomerView.class);
  }

  @Test
  void prova2() {
    var builder = traverson.follow("$._embedded.customers");

    var resourceParameterizedTypeReference = new TypeReferences.ResourcesType<Resources<Customer>>(){};

    Resources<Resources<Customer>> taskResources = builder.toObject(resourceParameterizedTypeReference);
  }

/*
  @Test
  @SneakyThrows
  void prova() {
    Traverson traverson = new Traverson(new URI(baseUrl.concat("/customers")), MediaTypes.HAL_JSON);
    TraversalBuilder tb = traverson.follow("customers");

    ParameterizedTypeReference<List<CustomerView>> typeRefDevices = new ParameterizedTypeReference<>();

    Resources<CustomerView> resUsers = tb.toObject(typeRefDevices);
    Collection<UserJson> users= resUsers .getContent();
  }

 */

}
