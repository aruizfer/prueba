package com.crowd.coffeeshop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@SpringBootTest
public class StandaloneContextTests {
 
  //@Autowired
  //CoffeeRestController controller;

  @Test
  @DisplayName("contextLoads")
  public void contextLoads() {
	    //Assertions.assertThat(controller).isNotNull();
  }


}