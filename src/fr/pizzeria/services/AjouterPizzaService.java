package fr.pizzeria.services;

import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.model.Pizza;

public class AjouterPizzaService extends MenuService {

	@Override
	public void executeUC(Scanner scanner, IPizzaDao dao) {
		System.out.println("Ajouter une pizza");
		
		System.out.println("Code :");
		String code = scanner.next();
		
		System.out.println("Libellé :");
		String libelle = scanner.next();
		
		System.out.println("Prix :");
		double prix = scanner.nextDouble();
		
		Pizza newPizza = new Pizza(code, libelle, prix);
		dao.saveNewPizza(newPizza);

	}

}
