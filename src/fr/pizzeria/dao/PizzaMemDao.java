package fr.pizzeria.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.pizzeria.model.Pizza;

public class PizzaMemDao implements IPizzaDao {

	private List<Pizza> pizzas = new ArrayList<>();
	
	public PizzaMemDao(){
		pizzas.add(new Pizza("PEP", "Pépéroni", 12.50));
		pizzas.add(new Pizza("MAR", "Margherita", 14.00));
		pizzas.add(new Pizza("REIN", "La Reine", 11.50));
		pizzas.add(new Pizza("FRO", "La 4 fromages", 12.00));
		pizzas.add(new Pizza("CAN", "La cannibale", 12.50));
		pizzas.add(new Pizza("SAV", "La savoyarde", 13.00));
		pizzas.add(new Pizza("ORI", "L’orientale", 13.50));
		pizzas.add(new Pizza("IND", "L’indienne", 14.00));
	}
	
	@Override
	public void initBDD() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Pizza> findAllPizzas() {
		return pizzas;
	}

	@Override
	public void saveNewPizza(Pizza pizza) {
		pizzas.add(pizza);

	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) {
		for (Pizza p: pizzas){
			if (p.getCode().equals(codePizza)){
				p.setCode(pizza.getCode());
				p.setLibelle(pizza.getLibelle());
				p.setPrix(pizza.getPrix());
				return;
			}
		}

	}

	@Override
	public void deletePizza(String codePizza) {
		Iterator<Pizza> it = pizzas.iterator();
		while (it.hasNext()){
			Pizza p = it.next();
			if (p.getCode().equals(codePizza)){
				it.remove();
			}
		}
	}

	@Override
	public Pizza findPizzaByCode(String codePizza) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean pizzaExists(String codePizza) {
		// TODO Auto-generated method stub
		return false;
	}



}
