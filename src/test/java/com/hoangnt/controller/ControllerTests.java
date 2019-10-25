package com.hoangnt.controller;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoangnt.repository.UserRepository;
import com.hoangnt.service.UserService;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.operation.Operation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;

//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class FindMultipleProductsTest {

  private static DbSetupTracker dbSetupTracker = new DbSetupTracker();
  
  
  @Autowired
  PasswordEncoder encode;
  
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DataSource dataSource;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  //@BeforeEach
  void prepare() {
	 final Operation insertAccount =
		        insertInto("account")
		            .row()
		            .column("id", 13)
		            .column("username", "hoang3")
		            .column("password", encode.encode("123"))
		            .end()
		            .build();
    final Operation insertUsers =
        insertInto("user")
            .row()
            .column("id", 12)
            .column("email", "haru@example.com")
            .column("account_id", 13)
            .column("role_id", 2)
            .end()
            .build();


    Operation operation = sequenceOf(insertUsers);

    Destination dest = new DataSourceDestination(dataSource);
    DbSetup dbSetup = new DbSetup(dest, operation);
    dbSetupTracker.launchIfNecessary(dbSetup);
  }

//  @DisplayName("Test Controller Find Multiple Products success")
//  @Test
  void findMultipleProductsTest() throws Exception {

    dbSetupTracker.skipNextLaunch();

//    parameters.add("shCds", "0010010010001,0010010010002,0010010010009");
//    parameters.add("fields", "small");
//    mockMvc
//        .perform(MockMvcRequestBuilders.get("/v1/products").accept(MediaType.APPLICATION_JSON_VALUE)
//            .contentType(MediaType.APPLICATION_JSON_VALUE).params(parameters))
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$.products.length()", is(2)))
//        .andExpect(jsonPath("$.products[0].shCd", is("0010010010001")))
//        .andExpect(jsonPath("$.products[0].bookmark", is(true)))
//        .andExpect(jsonPath("$.products[1].shCd", is("0010010010002")))
//        .andExpect(jsonPath("$.products[1].bookmark", is(false)));
  }
}
