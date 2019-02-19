package fr.pizzeria.services;

import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;

public class SupprimerPizzaService extends MenuService {

	@Override
	public void executeUC(Scanner scanner, IPizzaDao dao) {
		System.out.println("Supprimer une pizza");
		
		System.out.println("Code de la pizza à supprimer:");
		String codePizzaSupp = scanner.next();
		
		dao.deletePizza(codePizzaSupp);

	}

}
