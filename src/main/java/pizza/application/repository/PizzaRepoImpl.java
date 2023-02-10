package pizza.application.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pizza.application.models.Pizza;
import pizza.application.models.Order;
import pizza.application.repository.Queries;
import org.springframework.dao.EmptyResultDataAccessException;



@Repository
public class PizzaRepoImpl implements PizzaRepo{

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public Pizza getPizzaFromDb(Integer id){
    Pizza pizzaFromDb;
    try{
      pizzaFromDb = jdbcTemplate.queryForObject(Queries.getPizzaById, BeanPropertyRowMapper.newInstance(Pizza.class), id);
      return pizzaFromDb;
    }catch(EmptyResultDataAccessException e){
      return null;
    }
  }

  @Override
  public Pizza addPizzaToDB(Pizza pizza){
    // Check if Pizza name + size already in DB
    Pizza pizzaFromDb;
    try{
      pizzaFromDb = jdbcTemplate.queryForObject(Queries.getPizza, BeanPropertyRowMapper.newInstance(Pizza.class),pizza.getPizzaName(), pizza.getSize());
    }catch(EmptyResultDataAccessException e){
      // If pizza not in DB, add pizza to db and return it
      Integer added = jdbcTemplate.update(Queries.createPizza, pizza.getPizzaName(), pizza.getSize());
      pizzaFromDb = jdbcTemplate.queryForObject(Queries.getPizza, BeanPropertyRowMapper.newInstance(Pizza.class),pizza.getPizzaName(), pizza.getSize());
    }
    return pizzaFromDb;
  }

  @Override
  public Integer addOrderToDb(Order order, Pizza pizza, Integer qty){
    Integer added = jdbcTemplate.update(Queries.createOrder, order.getHexId(), pizza.getId(), qty, order.getName(),order.getAddress(),order.getPhoneNumber(),order.isRush(), order.getComments(),order.getPizzaCost(),order.getTotalCost());
    return added;
  }

  @Override
  public Order getOrderFromDb(String hexId){
    Order ord = jdbcTemplate.queryForObject(Queries.getOrder, BeanPropertyRowMapper.newInstance(Order.class), hexId);
    System.out.println(ord.getPizzaId());
    return ord;
  }
  
}
