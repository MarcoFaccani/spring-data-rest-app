package com.marcofaccani.app.repository.event_handler;



import com.marcofaccani.app.entity.Customer;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Component
@RepositoryEventHandler
public class CustomerEventHandler {

  // Triggered for POST
  @HandleBeforeCreate
  public void onBeforeCreate(@Valid Customer entity) {
    log.info("Saving new entity {}", entity);
  }

  // Triggered for PUT / PATCH
  @HandleBeforeSave
  public void onBeforeSave(@Valid Customer entity) {
    log.info("Saving new entity {}", entity);
  }

}