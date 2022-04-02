package com.marcofaccani.app.repository;

import com.marcofaccani.app.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, PagingAndSortingRepository<Customer, Long> {


  @RestResource(path = "firstnameStartsWith", rel = "firstnameStartsWith")
  public Page<Customer> findByFirstnameStartsWith(@Param("firstname") String firstname, Pageable page);


}
