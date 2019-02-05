package com.redhat.fuse7.poc;

import com.redhat.fuse7.poc.model.Person;
import org.apache.camel.CamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootSwaggerApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CamelContext camelContext;

    @Test
    public void personApiTest() {
        ResponseEntity<Person> response = restTemplate.exchange("/api/person", HttpMethod.GET, null, new ParameterizedTypeReference<Person>() {
        });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Person person = response.getBody();
        assertThat(person).isNotNull();
    }
}
