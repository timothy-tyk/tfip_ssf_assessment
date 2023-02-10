package pizza.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import pizza.application.models.Order;
import pizza.application.service.PizzaService;


@RestController
@RequestMapping(path = "/order", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class PizzaRestController {
  
  @Autowired
  PizzaService pizzaSvc;
  @PostMapping(value="{id}")
  public ResponseEntity<String> retrieveOrderById(@PathVariable String id){
    Order order = pizzaSvc.retrieveFromRedisById(id);
    if(order == null){
      JsonObject notFound = Json.createObjectBuilder().add("message",String.format("Order %s not found",id)).build();

      return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(notFound.toString());
    }
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(order.toJson());
  }
  
}
