package fr.pizzeria.model;

import java.util.Comparator;

public class ComparatorPizza implements Comparator<Pizza> {
	
	private boolean asc;
	
	public ComparatorPizza(boolean asc){
		this.asc = asc;
	}

	@Override
	public int compare(Pizza p1, Pizza p2) {
		if (asc){
			return p1.getCode().compareTo(p2.getCode());
		}
		return p2.getCode().compareTo(p1.getCode());
	}

}
