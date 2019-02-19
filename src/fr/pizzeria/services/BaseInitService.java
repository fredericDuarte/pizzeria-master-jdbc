package fr.pizzeria.services;

import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;

public class BaseInitService extends MenuService {

	@Override
	public void executeUC(Scanner scanner, IPizzaDao dao) {

dao.initBDD();


	}

}
