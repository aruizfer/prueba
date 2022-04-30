package com.crowd.coffeeshop;

import static org.mockito.ArgumentMatchers.anyDouble;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.crowd.coffeeshop.model.Coffee;
import com.crowd.coffeeshop.model.Purchase;
import com.crowd.coffeeshop.model.PurchaseItem;
import com.crowd.coffeeshop.model.PurchaseSummary;
 
//@ActiveProfiles("test")
//@Sql(scripts = "classpath:data_test.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SystemTests {
 
	@LocalServerPort
	int randomServerPort;
    
    String GET_COFFEES_ENDPOINT_URL;
    String CREATE_COFFEES_ENDPOINT_URL;
    String UPDATE_COFFEES_ENDPOINT_URL;
    String DELETE_COFFEES_ENDPOINT_URL;

    String CREATE_PURCHASES_ENDPOINT_URL;
    String GET_PURCHASES_SUMMARY_ENDPOINT_URL;
    
    String WRONG_URL;

    
    
    @BeforeEach
    public void initializeURLS() {
        GET_COFFEES_ENDPOINT_URL    = "http://localhost:" + randomServerPort + "/api/v1/coffees";
        CREATE_COFFEES_ENDPOINT_URL = "http://localhost:" + randomServerPort + "/api/v1/coffees";
        UPDATE_COFFEES_ENDPOINT_URL = "http://localhost:" + randomServerPort + "/api/v1/coffees/{id}";
        DELETE_COFFEES_ENDPOINT_URL = "http://localhost:" + randomServerPort + "/api/v1/coffees/{id}";

        CREATE_PURCHASES_ENDPOINT_URL      = "http://localhost:" + randomServerPort + "/api/v1/purchases";
        GET_PURCHASES_SUMMARY_ENDPOINT_URL = "http://localhost:" + randomServerPort + "/api/v1/purchases/summary";

        WRONG_URL = "http://localhost:" + randomServerPort + "/api/v1/wrong";
    }
    
  @Test
  @Order(2)
  public void testCreateAndDeleteCoffees() {
    RestTemplate restTemplate = new RestTemplate();
 
    Coffee coffee1 = new Coffee("Capuchino", 1);
    ResponseEntity<Coffee> entity = restTemplate.postForEntity(CREATE_COFFEES_ENDPOINT_URL, coffee1, Coffee.class);
    Assertions.assertThat(entity.getStatusCode() == HttpStatus.OK).isTrue();
    Assertions.assertThat(entity.getBody().getName().equalsIgnoreCase("Capuchino")).isTrue();
    coffee1 = entity.getBody();

    ResponseEntity<Object[]> entity1 = restTemplate.getForEntity(GET_COFFEES_ENDPOINT_URL, Object[].class);

    Map<String,String> params = new HashMap<String,String>();
    params.put("id", String.valueOf(coffee1.getId()));

    restTemplate.put(UPDATE_COFFEES_ENDPOINT_URL, coffee1, params);
    
    restTemplate.delete(DELETE_COFFEES_ENDPOINT_URL, params);

    
//    try {
//      restTemplate.getForEntity(url, String.class);
//    } catch (HttpClientErrorException e) {
//      Assertions.assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
  }	
	
  @Test
  @Order(2)
  public void testCreateCoffeesAndPurchase() throws URISyntaxException {
    RestTemplate restTemplate = new RestTemplate();

    
    Coffee coffee1 = new Coffee("Capuchino", 1);
    ResponseEntity<Coffee> entity = restTemplate.postForEntity(CREATE_COFFEES_ENDPOINT_URL, coffee1, Coffee.class);
    Assertions.assertThat(entity.getStatusCode() == HttpStatus.OK).isTrue();
    Assertions.assertThat(entity.getBody().getName().equalsIgnoreCase("Capuchino")).isTrue();
    coffee1 = entity.getBody();

    Coffee coffee2 = new Coffee("Latte", 2);
    ResponseEntity<Coffee> entity2 = restTemplate.postForEntity(CREATE_COFFEES_ENDPOINT_URL, coffee2, Coffee.class);
    Assertions.assertThat(entity2.getStatusCode() == HttpStatus.OK).isTrue();
    Assertions.assertThat(entity2.getBody().getName().equalsIgnoreCase("Latte")).isTrue();
    coffee2 = entity2.getBody();

    //Assertions.assertThat(entity.getBody().getCustomers().size() == 2).isTrue();

    Purchase purchaseErr = new Purchase();
    purchaseErr.setCardnumber("1234567890");
    purchaseErr.setCardexpirydate("06/21");
    Set<PurchaseItem> psErr = new HashSet<PurchaseItem>();
    psErr.add(new PurchaseItem(purchaseErr, coffee1, 2));
    psErr.add(new PurchaseItem(purchaseErr, coffee2, 3));
    purchaseErr.setItems(psErr);
    System.out.println(purchaseErr.toString());

    try {
        restTemplate.postForEntity(CREATE_PURCHASES_ENDPOINT_URL, purchaseErr, Purchase.class);
      } catch (HttpClientErrorException e) {
        Assertions.assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
      }
    //Assertions.assertThat(entityErr.getStatusCode() == HttpStatus.BAD_REQUEST).isTrue();

    
    Purchase purchase1 = new Purchase();
    purchase1.setCardnumber("1234567890");
    purchase1.setCardexpirydate("06/22");
    Set<PurchaseItem> ps = new HashSet<PurchaseItem>();
    ps.add(new PurchaseItem(purchase1, coffee1, 2));
    ps.add(new PurchaseItem(purchase1, coffee2, 3));
    purchase1.setItems(ps);
    System.out.println(purchase1.toString());

    ResponseEntity<Purchase> entity3 = restTemplate.postForEntity(CREATE_PURCHASES_ENDPOINT_URL, purchase1, Purchase.class);
    Assertions.assertThat(entity3.getStatusCode() == HttpStatus.OK).isTrue();

    ResponseEntity<Object[]> entity4 = restTemplate.getForEntity(GET_PURCHASES_SUMMARY_ENDPOINT_URL, Object[].class);
    Assertions.assertThat(entity4.getStatusCode() == HttpStatus.OK).isTrue();

  }
  
  @Test
  @Order(3)
  public void testErrorHandlingReturnsNotFound() {
    RestTemplate restTemplate = new RestTemplate();
 
    String url = "http://localhost:" + randomServerPort + "/wrong-url";

    try {
      restTemplate.getForEntity(url, String.class);
    } catch (HttpClientErrorException e) {
      Assertions.assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
  }

  @Test
  @Order(3)
  public void testErrorHandlingReturnsCardInvalid() {
    RestTemplate restTemplate = new RestTemplate();

    try {
      restTemplate.getForEntity(WRONG_URL, String.class);
    } catch (HttpClientErrorException e) {
      Assertions.assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
  }
}