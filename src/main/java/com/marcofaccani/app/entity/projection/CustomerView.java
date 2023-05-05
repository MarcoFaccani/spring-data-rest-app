package com.marcofaccani.app.entity.projection;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.marcofaccani.app.entity.Customer;
import org.springframework.data.rest.core.config.Projection;

/*
  @Projection interface found in the same package as your entity definitions (or one of its sub-packages) is registered.
  Placing them else where will be ignored.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Projection(name = "customer-view", types = { Customer.class })
public interface CustomerView {

  String getFirstname();
  String getLastname();
  LocalDate getBirthDate();

}
