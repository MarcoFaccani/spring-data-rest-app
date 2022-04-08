package com.marcofaccani.app.functional;

import java.net.URI;
import java.util.LinkedHashMap;
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
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.client.Traverson.TraversalBuilder;
import org.springframework.hateoas.config.HypermediaWebTestClientConfigurer;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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


  @BeforeEach
  @SneakyThrows
  void setup() {
    baseUrl = "http://localhost:" + port + basePath;
  }

  @AfterEach
  void tearDown() {
    repository.deleteAll();
  }

  @Test
  public void contextLoadTest() { }

  @Test
  void test() {
    client.get().uri(baseUrl.concat("/customers")).accept(HAL_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(new TypeReferences.CollectionModelType<EntityModel<CustomerView>>() {})
        .consumeWith(result -> {
          // How to get the Page<CustomerView> ?
          CollectionModel<EntityModel<CustomerView>> model = result.getResponseBody(); // <- it doesn't seem to contain the proper response
          assertThat(model.getContent()).hasSize(3);
        });
  }

  @Test
  void test2() {
    client.get().uri(baseUrl.concat("/customers")).accept(HAL_JSON)
        .exchange()
        .expectBody(new TypeReferences.EntityModelType<CustomerView>() {})
        .consumeWith(result -> {
          // How to get the Page<CustomerView> ?
          Object o = ((LinkedHashMap) ((LinkedHashMap) result.getResponseBody().getContent()).get("_embedded")).get(
              "customers"); // <-- it contains what I am looking for in the debugger but is a crazy approach
        });
  }

  @Test
  void test3() {
    var resourceParameterizedTypeReference = new TypeReferences.CollectionModelType<EntityModel<CustomerView>>() {};
    Traverson traverson = new Traverson(URI.create(baseUrl.concat("/customers")), MediaTypes.HAL_JSON);
    var customers = traverson.follow("$._embedded.customers").toObject(resourceParameterizedTypeReference);
  }

  @Test
  void test4() {
    ParameterizedTypeReference<EntityModel<CustomerView>> resourceParameterizedTypeReference = new ParameterizedTypeReference<EntityModel<CustomerView>>() {};
    new Traverson(URI.create(baseUrl.concat("/customers")), MediaTypes.HAL_JSON).follow("");
  }

  @Test
  void test5() {
    var resourceParameterizedTypeReference = new ParameterizedTypeReference<PagedModel<CustomerView>>() {};
    Traverson traverson = new Traverson(URI.create(baseUrl.concat("/customers")), MediaTypes.HAL_JSON);
    var customers = traverson
          .follow("$._embedded.customers")
        .toObject(resourceParameterizedTypeReference);
  }


}
