package com.marcofaccani.app.repository;

import com.marcofaccani.app.entity.Customer;
import com.marcofaccani.app.entity.QCustomer;
import com.marcofaccani.app.entity.projection.CustomerView;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(excerptProjection = CustomerView.class) // defaults CustomerView to be used for resource collections
public interface CustomerRepository extends JpaRepository<Customer, Long>,
    QuerydslPredicateExecutor<Customer>, QuerydslBinderCustomizer<QCustomer>  {

  @RestResource(path = "firstnameStartsWith", rel = "firstnameStartsWith")
  public Page<Customer> findByFirstnameStartsWith(@Param("firstname") String firstname, Pageable page);

  @Override
  default void customize(QuerydslBindings bindings, QCustomer root) {
    bindings.bind(String.class).first( (StringPath path, String value) -> path.containsIgnoreCase(value));
    //bindings.excluding(root.firstname);
  }


  /*
    * Wish to disable an automatically-exposed feature such as deleteById?
    * Simple, just override the method and annotate it with @RestResource(exported = false)
    @Override
    @RestResource(exported = false)
    void deleteById(Long id);
   */

}
