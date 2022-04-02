package com.marcofaccani.app.repository;

import com.marcofaccani.app.entity.Customer;
import com.marcofaccani.app.entity.projection.CustomerView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(excerptProjection = CustomerView.class) // defaults CustomerView to be used for resource collections
public interface CustomerRepository extends JpaRepository<Customer, Long> {


  @RestResource(path = "firstnameStartsWith", rel = "firstnameStartsWith")
  public Page<Customer> findByFirstnameStartsWith(@Param("firstname") String firstname, Pageable page);

  /*
    * Wish to disable an automatically-exposed feature such as deleteById?
    * Simple, just override the method and annotate it with @RestResource(exported = false)

    @Override
    @RestResource(exported = false)
    void deleteById(Long id);

   */


}
