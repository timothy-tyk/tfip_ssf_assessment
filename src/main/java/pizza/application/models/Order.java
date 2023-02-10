package pizza.application.models;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Random;


import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Order implements Serializable{
  private Integer id;
  private String hexId;
  private Pizza pizza;

  private Integer pizzaId;
  private Integer pizzaQty;

  @NotEmpty(message = "Please input your name")
  @Size(min = 3, message = "Name must be minimum 3 characters long")
  private String name;

  @NotEmpty(message = "Please input your address")
  private String address;

  @Pattern(regexp = "[0-9]{8}", message = "Phone Number must be 8 digits")
  private String phoneNumber;
  private boolean rush = false;
  private String comments;
  private double pizzaCost;
  private double totalCost;

  public Order(){
    this.hexId = generateHexId(8);
  }
  public Order(Pizza pizza){
    this.hexId = generateHexId(8);
    this.pizza = pizza;
  }

  // Getters and Setters
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public Integer getPizzaId() {
    return pizzaId;
  }
  public void setPizzaId(Integer pizzaId) {
    this.pizzaId = pizzaId;
  }
  public Integer getPizzaQty() {
    return pizzaQty;
  }
  public void setPizzaQty(Integer pizzaQty) {
    this.pizzaQty = pizzaQty;
  }
  public String getHexId() {
    return hexId;
  }
  public void setHexId(String hexId) {
    this.hexId = hexId;
  }
  public Pizza getPizza() {
    return pizza;
  }
  public void setPizza(Pizza pizza) {
    this.pizza = pizza;
  }
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getPhoneNumber() {
    return phoneNumber;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  public boolean isRush() {
    return rush;
  }
  public void setRush(boolean rush) {
    this.rush = rush;
  }
  public String getComments() {
    return comments;
  }
  public void setComments(String comments) {
    this.comments = comments;
  }
  public double getPizzaCost() {
    return pizzaCost;
  }
  public void setPizzaCost(double pizzaCost) {
    this.pizzaCost = pizzaCost;
  }
  public double getTotalCost() {
    return totalCost;
  }
  public void setTotalCost(double totalCost) {
    this.totalCost = totalCost;
  }
  // Methods
  public String generateHexId(int numChars){
    String hexId = "";
    for(int i=0; i<numChars;i++){
      Random ran = new Random();
      boolean digitOrLetter = ran.nextBoolean();
      if (digitOrLetter){
        // digit
        int digit = ran.nextInt(0, 9);
        hexId+=digit;
      }else{
        char letter = (char)ran.nextInt(97, 123);
        hexId+=letter;
      }
    }
    return hexId;
  }

  public double calculatePizzaCost(Pizza pizza){
    String pizzaName = pizza.getPizzaName();
    System.out.println(pizzaName);
    double pizzaCost = 0;
    if(pizzaName.equals("bella") || pizzaName.equals("marinara") || pizzaName.equals("spianatacalabrese")){
      pizzaCost = (float) (pizza.getQuantity()*30);
      if(pizza.getSize().equals("md")){
        pizzaCost = (float) (pizzaCost*1.2);
      }else if(pizza.getSize().equals("lg")){
        pizzaCost = (float) (pizzaCost*1.5);
      }
    }
    else if(pizzaName.equals("margherita")){
      pizzaCost = (float) (pizza.getQuantity()*22);
      if(pizza.getSize().equals("md")){
        pizzaCost = (float) (pizzaCost*1.2);
      }else if(pizza.getSize().equals("lg")){
        pizzaCost = (float) (pizzaCost*1.5);
      }
    }else if(pizzaName.equals("trioformaggio")){
      pizzaCost = (float) (pizza.getQuantity()*25);
      if(pizza.getSize().equals("md")){
        pizzaCost = (float) (pizzaCost*1.2);
      }else if(pizza.getSize().equals("lg")){
        pizzaCost = (float) (pizzaCost*1.5);
      }
    }
    return Math.round(pizzaCost);
  }

  public String toJson(Pizza pizza){
    return Json.createObjectBuilder()
            .add("orderId", this.getId())
            .add("hexId", this.getHexId())
            .add("name", this.getName())
            .add("address", this.getAddress())
            .add("phone", this.getPhoneNumber())
            .add("rush", this.isRush())
            .add("comments", this.getComments())
            .add("pizza", pizza.getPizzaName())
            .add("size", pizza.getSize())
            .add("quantity", this.getPizzaQty())
            .add("pizzaCost", this.getPizzaCost())
            .add("total", this.getTotalCost())
            .build().toString();
  }

  public static Order createFromJson(String json){
    InputStream is = new ByteArrayInputStream(json.getBytes());
    JsonReader jReader = Json.createReader(is);
    JsonObject jObj = jReader.readObject();
    Order newOrder = new Order();
    Pizza newPizza = new Pizza();
    newOrder.setId(jObj.getInt("orderId"));
    newOrder.setHexId(jObj.getString("hexId"));
    newOrder.setName(jObj.getString("name"));
    newOrder.setAddress(jObj.getString("address"));
    newOrder.setPhoneNumber(jObj.getString("phone"));
    newOrder.setRush(jObj.getBoolean("rush"));
    newOrder.setComments(jObj.getString("comments"));
    newPizza.setPizzaName(jObj.getString("pizza"));
    newPizza.setSize(jObj.getString("size"));
    newPizza.setQuantity(jObj.getInt("quantity"));
    newOrder.setPizzaCost(Double.parseDouble(jObj.getJsonNumber("pizzaCost").toString()));
    newOrder.setTotalCost(Double.parseDouble(jObj.getJsonNumber("total").toString()));
    newOrder.setPizza(newPizza);
    return newOrder;
  }

  
}
