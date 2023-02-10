package pizza.application.repository;

import pizza.application.models.Pizza;
import pizza.application.models.Order;


public interface PizzaRepo {
  public Pizza getPizzaFromDb(Integer id);
  public Pizza addPizzaToDB(Pizza pizza);
  public Integer addOrderToDb(Order order, Pizza pizza, Integer qty);
  public Order getOrderFromDb(String hexId);
}
