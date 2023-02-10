package pizza.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import pizza.application.models.Pizza;
import pizza.application.repository.PizzaRepoImpl;

import pizza.application.models.Order;

@Service
public class PizzaService {

  @Autowired
  RedisTemplate<String, Object> redisTemplate;
  
  public int saveToRedis(Order order){
    String key = order.getHexId();
    redisTemplate.opsForValue().set(key, order);
    // test retrieval from redis
    Order fromRedis = (Order) redisTemplate.opsForValue().get(key);
    if(fromRedis==null){
      return 0;
    }else{
      return 1;
    }
  }

  public Order retrieveFromRedisById(String id){
    Order order = (Order) redisTemplate.opsForValue().get(id);
    return order;
  }

  // SQL Services
  @Autowired
  PizzaRepoImpl pizzaRepoImpl;

  public Pizza getPizzaFromDb(Integer id){
    Pizza fromDb = pizzaRepoImpl.getPizzaFromDb(id);
    return fromDb;
  }

  public Pizza addPizzaToDb(Pizza pizza){
   Pizza fromDb = pizzaRepoImpl.addPizzaToDB(pizza);
   return fromDb;
  }

  public void addOrderToDb(Order order, Pizza pizza, Integer qty){
    Integer added = pizzaRepoImpl.addOrderToDb(order, pizza, qty);
  }

  public Order getOrderFromDb(String hexId){
    Order order = pizzaRepoImpl.getOrderFromDb(hexId);
    return order;
  }
}
