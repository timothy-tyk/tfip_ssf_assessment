package pizza.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import pizza.application.models.Order;
import pizza.application.models.Pizza;
import pizza.application.service.PizzaService;

@Controller
@RequestMapping("/")
public class PizzaController {
  @GetMapping()
  public String getMenuPage(Model model){
    model.addAttribute("pizza", new Pizza());
    return "index";
  }

  @PostMapping(path = "/pizza", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String getOrderDeliveryDetails(@Valid Pizza pizza, BindingResult bindingResult,Model model, HttpSession session){
    if(bindingResult.hasErrors()){
      return "index";
    }
    session.setAttribute("pizza", pizza);
    Order order = new Order();
    order.setPizza(pizza);
    model.addAttribute("order", order);
    // model.addAttribute("pizza", pizza);
    return "orderdetails";
  }

  @Autowired
  PizzaService pizzaSvc;

  @PostMapping(path = "/pizza/order", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String postOrderDetails(@Valid Order order,BindingResult bindingResult ,Model model, HttpSession session, HttpServletResponse response){
    if(bindingResult.hasErrors()){
      return "orderdetails";
    }
    Pizza pizza = (Pizza) session.getAttribute("pizza");
    order.setPizza(pizza);
    order.setPizzaCost(order.calculatePizzaCost(pizza));
    order.setTotalCost(order.getPizzaCost());
    if(order.isRush()){
      order.setTotalCost(order.getPizzaCost()+2.0);
    }
    int result = pizzaSvc.saveToRedis(order);
    if(result == 0){
      // Failed to save
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return "orderdetails";
    }
    response.setStatus(HttpServletResponse.SC_CREATED);
    model.addAttribute("order", order);
    
    return "deliverydetails";
  }

  @Autowired
  PizzaRestController pizzaRestController;

  @GetMapping(path = "/order/{id}")
  public String getOrderPageById(@PathVariable String id, Model model){
    ResponseEntity<String> orderJson = pizzaRestController.retrieveOrderById(id);
    if(orderJson.getStatusCode()==HttpStatus.NOT_FOUND){
      // throws Whitelabel error page with Status Code 404.
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,orderJson.getBody());
    }
    Order newOrder = Order.createFromJson(orderJson.getBody().toString());
    model.addAttribute("order", newOrder);
    return "deliverydetails";
  }

}
