package fr.pizzeria.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import fr.pizzeria.model.Pizza;

public class Database implements IPizzaDao {

	Properties prop = new Properties();
	String driver;
	String url, pass, user;
	InputStream input = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	String sql = null;
	private List<Pizza> pizzas = null;

	/**
	 * constructeur - initialisation de BDD
	 */
	public Database() {

		try {

			// Fichier jdbc.properties à la racine du projet (dans le dossier Pizzeria)
			// input = new FileInputStream("jdbc.properties");

			// Fichier dans le dossier src de Pizzeria
			input = new FileInputStream("src/jdbc.properties");
			prop.load(input);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.driver = prop.getProperty("driver");
		this.url = prop.getProperty("database");
		this.user = prop.getProperty("dbuser");
		this.pass = prop.getProperty("dbpassword");

		try {
			// obtenir les infos
			Class.forName(this.driver);

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

	}

	/**
	 * initaliser la bade de données en restaurant les données des pizzas
	 */
	@Override
	public void initBDD() {

		try {

			this.connection = DriverManager.getConnection(getUrl(), getUser(), getPass());
			this.statement = connection.createStatement();

			PreparedStatement deleteAllPizzaSt = connection.prepareStatement("TRUNCATE pizza");
			deleteAllPizzaSt.executeUpdate();

			int nbInit = statement
					.executeUpdate("INSERT INTO PIZZA(code,libelle,prix)	VALUES ('PEP', 'Pépéroni', 12.50),"
							+ "('MAR', 'Margherita', 14.00)," + "('REIN', 'La Reine', 11.50),"
							+ "('FRO', 'La 4 fromages', 12.00)," + "('CAN', 'La cannibale', 12.50),"
							+ "('SAV', 'La savoyarde', 13.00)," + "('ORI', 'L’orientale', 13.50),"
							+ "('IND', 'L’indienne', 14.00)  ");

			System.out.println(nbInit + " pizza inséré(s)");
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			closeConnection();
		}

	}

	/**
	 * fermer la connection
	 */
	public void closeConnection() {
		try {

			if (rs != null)
				this.rs.close();
			this.statement.close();
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Liste tous les pizzas
	 */

	@Override
	public List<Pizza> findAllPizzas() {

		pizzas = new ArrayList<>();

		try {
			this.connection = DriverManager.getConnection(getUrl(), getUser(), getPass());
			this.statement = connection.createStatement();

			rs = statement.executeQuery("SELECT * from pizza");
			while (rs.next()) {
				pizzas.add(new Pizza(rs.getString("code"), rs.getString("libelle"), rs.getDouble("prix")));
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			closeConnection();
		}

		return pizzas;

	}

	/**
	 * Ajout un nouveay pizza
	 * 
	 * @param pizza
	 */
	@Override
	public void saveNewPizza(Pizza pizza) {

		try {

			this.connection = DriverManager.getConnection(getUrl(), getUser(), getPass());
			this.statement = connection.createStatement();

			PreparedStatement savePizzaSt = connection
					.prepareStatement("INSERT INTO pizza (code,libelle,prix) VALUES (?,?,?)");

			savePizzaSt.setString(1, pizza.getCode());
			savePizzaSt.setString(2, pizza.getLibelle());
			savePizzaSt.setDouble(3, pizza.getPrix());
			savePizzaSt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			closeConnection();
		}

	}

	/**
	 * Mettre à jour l'information d'un pizza existant
	 * 
	 * @param codePizza,pizza
	 */
	@Override
	public void updatePizza(String codePizza, Pizza pizza) {

		try {

			this.connection = DriverManager.getConnection(getUrl(), getUser(), getPass());
			this.statement = connection.createStatement();

			PreparedStatement updatePizzaSt = connection
					.prepareStatement("UPDATE pizza SET code=?, libelle = ? , prix= ?  WHERE code =?");
			updatePizzaSt.setString(1, pizza.getCode());
			updatePizzaSt.setString(2, pizza.getLibelle());
			updatePizzaSt.setDouble(3, pizza.getPrix());
			updatePizzaSt.setString(4, codePizza);

			updatePizzaSt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			closeConnection();
		}

	}

	/**
	 * Supprime un pizza
	 * 
	 * @param codePizza
	 */
	@Override
	public void deletePizza(String codePizza) {

		try {

			this.connection = DriverManager.getConnection(getUrl(), getUser(), getPass());
			this.statement = connection.createStatement();

			PreparedStatement deletePizzaSt = connection.prepareStatement("DELETE FROM pizza WHERE code = ?");
			deletePizzaSt.setString(1, codePizza);
			deletePizzaSt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			closeConnection();
		}

	}

	/**
	 * Vérifie l'existance d 'un pizza
	 * 
	 * @param codePizza
	 * @return Pizza
	 */
	@Override
	public Pizza findPizzaByCode(String codePizza) {
		Pizza newPizza = null;

		try {
			this.connection = DriverManager.getConnection(getUrl(), getUser(), getPass());
			this.statement = connection.createStatement();

			PreparedStatement findPizzaSt = connection.prepareStatement("SELECT * FROM pizza WHERE code = ?");
			findPizzaSt.setString(1, codePizza);
			rs = findPizzaSt.executeQuery();

			while (rs.next()) {
				pizzas.add(new Pizza(rs.getString("code"), rs.getString("libelle"), rs.getDouble("prix")));
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			closeConnection();
		}

		return newPizza;
	}

	/**
	 * Vérifie si un pizza existe via le code
	 * 
	 * @param codePizza
	 */
	@Override
	public boolean pizzaExists(String codePizza) {

		try {
			this.connection = DriverManager.getConnection(getUrl(), getUser(), getPass());
			this.statement = connection.createStatement();

			PreparedStatement findPizzaSt = connection.prepareStatement("SELECT * FROM pizza WHERE code = ?");
			findPizzaSt.setString(1, codePizza);
			rs = findPizzaSt.executeQuery();

			rs.getBoolean(1);

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			closeConnection();
		}
		return false;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}



}
