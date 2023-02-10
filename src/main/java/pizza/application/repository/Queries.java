package pizza.application.repository;

public class Queries {
  public static final String getPizzaById = "SELECT * FROM pizza WHERE id=?";
  public static final String getPizza = "SELECT * FROM pizza WHERE pizza_name=? AND size=?";
  public static final String createPizza = "INSERT INTO pizza(pizza_name, size) VALUES (?,?)";
  public static final String createOrder = "INSERT INTO orders (hex_id, pizza_id, pizza_qty, name,address, phone_number, rush, comments, pizza_cost, total_cost) VALUES (?,?,?,?,?,?,?,?,?,?)";
  public static final String getOrder = "SELECT * FROM orders WHERE hex_id=?";
  
}
