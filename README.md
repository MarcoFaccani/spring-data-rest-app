# spring-data-rest-app
In this app I'm testing Spring Data Rest features to validate them in a production project.  
Default page size is 20.  
_Note: page number is zero-index based_

## Entities

## Automatically exposed endpoints:

### CRUD
- automatic documentation (to be explored further): `curl http://localhost:8081/profile`
- get all customers: `curl http://localhost:8081/customers`
- get specific customer: `curl http://localhost:8081/customers/{id}`
- get view (projection) of specific customer: `curl 'http://localhost:8081/customers/{id}?projection=customer-view'`
- update customer: `curl -d '{ "firstname": "John"}' -H "Content-Type: application/json" -X PUT http://localhost:8081/customers/{id}`
- create customer: `curl -d '{ "firstname": "Varys", "lastname": "unknown"}' -H "Content-Type: application/json" -X POST http://localhost:8081/customers`   

### Paging
_Note: page number is zero-index based_  
- get all customer specifying page size: `curl 'http://localhost:8081/customers?size={page_number}'`
- get all customer specifying page number: `curl 'http://localhost:8081/customers?page={page_number}'`

## Custom endpoints
### Search API
_Note: page number is zero-index based_
- search customers having _firstname_ starting with: `curl http://localhost:8081/customers/search/firstnameStartsWith?firstname={firstname_param}`
- search customers having _firstname_ starting with and get specific page: `curl 'http://localhost:8081/customers/search/firstnameStartsWith?firstname={firstname_param}&page={page_num}'`
- search customers having _firstname_ starting with and get specific page with given page size: `curl 'http://localhost:8081/customers/search/firstnameStartsWith?firstname={firstname_param}&page={page_num}&size={page_size}'`
- search customers having _firstname_ starting with and sort by given field and direction: `curl 'http://localhost:8081/customers/search/firstnameStartsWith?firstname={firstname_param}&sort={field},{asc/desc}'`




## Application-Level Profile Semantics (ALPS)
An automatically-generated endpoint describing the entities, the endpoints and the operations allowed.  
You can access it as follows: `curl http://localhost:8081/profile`  
For [more information](https://docs.spring.io/spring-data/rest/docs/current/reference/html/#metadata.alps)
