package com.redhat.fuse7.poc;

import com.redhat.fuse7.poc.model.Person;
import org.apache.camel.CamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

// http://www.springboottutorial.com/integration-testing-for-spring-boot-rest-services

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestApiTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CamelContext camelContext;

    @LocalServerPort
    private int port;

    HttpHeaders headers = new HttpHeaders();

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void personApiTest() throws Exception {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<Person> response = restTemplate.exchange("/api/person", HttpMethod.GET, entity, new ParameterizedTypeReference<Person>() {
        });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Person person = response.getBody();
        assertThat(person).isNotNull();

        logger.info("response: " + response.toString());
        logger.info("person: " + person);

        String expected = "{'firstname': 'FirstName', 'lastname': 'LastName'}";


        JSONAssert.assertEquals(expected, response.getBody().toString(), false);
    }

    @Test
    public void helloApiTest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/hello", String.class);
        logger.info("response: " + response.toString());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void extApiTest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/ext", String.class);
        logger.info("response: " + response.toString());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
