# spring-data-rest-app
In this app I'm testing Spring Data Rest features to validate them in a production project.  
Default page size is 20.  

## Entities
- Customer: it holds info about the customer including two sensitive data that the client shall not access (`sensitiveDataOne`, `sensitiveDataTwo`)

## Decoupling Entities
Hold on! I don't want to expose to the client all the data in the DB!  
Easy, use projections: we can define views and expose only those.  
For automating the conversion from entity to view for resource collections annotate the repository with `@RepositoryRestResource(excerptProjection = MyView.class)`

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

## Customizaion

### Disable a specific out-of-the-box CRUD operation:
Simply in your repository override the method of your choice (explore the JPARepository and the extended interfaces) and annotate it with `@RestResource(exported = false)`
Lunch the app and delete a customer by using its ID (find the curl methods down below).
Then uncomment the deleteById override method in the repository, reboot and try again the same operation, you won't be able to delete the customer

## Custom endpoints
### Search API
_Note: page number is zero-index based_
- search customers having _firstname_ starting with: `curl http://localhost:8081/customers/search/firstnameStartsWith?firstname={firstname_param}`
- search customers having _firstname_ starting with and get specific page: `curl 'http://localhost:8081/customers/search/firstnameStartsWith?firstname={firstname_param}&page={page_num}'`
- search customers having _firstname_ starting with and get specific page with given page size: `curl 'http://localhost:8081/customers/search/firstnameStartsWith?firstname={firstname_param}&page={page_num}&size={page_size}'`
- search customers having _firstname_ starting with and sort by given field and direction: `curl 'http://localhost:8081/customers/search/firstnameStartsWith?firstname={firstname_param}&sort={field},{asc/desc}'`


## Documentation: Application-Level Profile Semantics (ALPS)
An automatically-generated endpoint describing the entities, the endpoints and the operations allowed.  
You can access it as follows: `curl http://localhost:8081/profile`  
For [more information](https://docs.spring.io/spring-data/rest/docs/current/reference/html/#metadata.alps)
