package pizza.application.models;

import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Pizza implements Serializable{
  private Integer id;
  @NotNull(message = "Please a Select a Pizza")
  private String pizzaName;

  @NotNull(message = "Please a Selecta Size")
  private String size;

  @Min(value = 1, message = "Minimum 1 Pizza")
  @Max(value = 10, message = "Maximum 10 Pizzas")
  @NotNull(message = "Minimum 1 Pizza")
  private Integer quantity;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getPizzaName() {
    return pizzaName;
  }
  public void setPizzaName(String pizzaName) {
    this.pizzaName = pizzaName;
  }
  public String getSize() {
    return size;
  }
  public void setSize(String size) {
    this.size = size;
  }
  public Integer getQuantity() {
    return quantity;
  }
  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
  
  // // Methods
  // public JsonObject toJson(){
  //   return Json.createObjectBuilder()
  //               .add("pizzaName", this.getPizzaName())
  //               .add("size", this.getSize())
  //               .add("quantity", this.getQuantity()) 
  //               .build();
  //              }

  
}
