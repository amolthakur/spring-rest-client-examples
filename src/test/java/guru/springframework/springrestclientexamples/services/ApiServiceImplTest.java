package guru.springframework.springrestclientexamples.services;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import guru.springframework.api.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiServiceImplTest {

  public static final String API_ROOT = "https://api.predic8.de:443/shop";
  @Autowired
  ApiService apiService;
  @Autowired
  RestTemplate restTemplate;

  @Before
  public void setUp() throws Exception {
  }


  @Test
  public void testGetUsers() throws Exception {

    List<User> users = apiService.getUsers(3);

    assertEquals(4, users.size());
  }


  ////with query parameters

  @Test
  public void getPosts() {
    //https://stackoverflow.com/questions/35998790/resttemplate-how-to-send-url-and-query-parameters-together
    String url = "https://jsonplaceholder.typicode.com/comments";
    UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
        .fromUriString(url)
        .queryParam("postId", 1);
    JsonNode jsonNode = restTemplate
        .getForObject(uriComponentsBuilder.toUriString(), JsonNode.class);
    System.out.println(jsonNode);

  }


  ///////without query parameters:
  @Test
  public void getCustomers() {
    JsonNode jsonNode = restTemplate.getForObject(API_ROOT + "/customers/", JsonNode.class);
    System.out.println(jsonNode);

  }

  @Test
  public void getSpecificCustomer() {
    String url = API_ROOT + "/customers/{id}";
    Map<String, String> map = new HashMap<>();//used for path parameters.
    map.put("id", "435");
    JsonNode jsonNode = restTemplate.getForObject(url, JsonNode.class, map);
    System.out.println(jsonNode);
  }

  @Test
  public void postCustomer() {
    String url = API_ROOT + "/customers/";
    Map<String, Object> map = new HashMap<>();
    map.put("firstname", "amol");
    map.put("lastname", "thakur");
    JsonNode jsonNode = restTemplate.postForObject(url, map, JsonNode.class);
    System.out.println(jsonNode);
  }

  @Test
  public void deleteCustomer() {
    String url = API_ROOT + "/customers/{id}";
    Map<String, String> map = new HashMap<>();
    map.put("id", "434");
    getSpecificCustomer();
    restTemplate.delete(url, map);
    getSpecificCustomer();
  }

  @Test
  public void updateCustomer() {
    String url = API_ROOT + "/customers/{id}";
    Map<String, String> map = new HashMap<>();
    map.put("id", "435");
    Map<String, Object> objectMap = new HashMap<>();
    objectMap.put("firstname", "amol");
    objectMap.put("lastname", "changed");
    restTemplate.put(url, objectMap, map);
  }


}