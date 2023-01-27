package pizza.application.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import pizza.application.models.Order;
import pizza.application.models.Pizza;

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

  @PostMapping(path = "/pizza/order", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String postOrderDetails(@Valid Order order,BindingResult bindingResult ,Model model, HttpSession session){
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
    model.addAttribute("order", order);
    
    return "deliverydetails";
  }
}
