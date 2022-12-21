package org.example;

import exceptions.ProductAlreadyExistsException;
import exceptions.ProductNotFoundException;
import models.Product;
import models.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

public class Main {
    private static final Logger LOGGER = Logger.getLogger( Main.class.getName());
    static Repository repository=new Repository();
    static User user = new User();

    public static void main(String[] args) throws IOException, ProductAlreadyExistsException, ProductNotFoundException {

        System.out.println("Bienvenue");

        initRepo();
        getUserInfo();
        menu();
        LOGGER.info("bonjour");


    }
    public static void getUserInfo() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Veuillez entrer l'id de l'utilisateur");
        long user_id = Long.parseLong(reader.readLine());
        System.out.println("Veuillez entrer le nom de l'utilisateur");
        String user_name=reader.readLine();
        System.out.println("Veuillez entrer l'âge de l'utilisateur");
        int age= Integer.parseInt(reader.readLine());
        System.out.println("Veuillez entrer l'email de l'utilisateur");
        String email=reader.readLine();
        System.out.println("Veuillez entrer le mot de passe de l'utilisateur");
        String password=reader.readLine();

        user.setID(user_id);
        user.setName(user_name);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);

    }
    public static void menu() throws IOException, ProductNotFoundException, ProductAlreadyExistsException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Veuillez choisir une opération parmi cette liste ");
        System.out.println("1-Afficher la liste de produits");
        System.out.println("2-Chercher un produit par ID");
        System.out.println("3-Chercher les produits les plus chers");
        System.out.println("4-Ajouter un produit");
        System.out.println("5-Modifier un produit");
        System.out.println("6-Supprimer un produit");
        System.out.println("7-Lancer les scenarios");
        System.out.println("0- Quitter l'application");
        String choix=reader.readLine();
        switch (choix){
            case "1":
                repository.displayAllProducts();
                menu();
                break;
            case "2":
                System.out.println("Veuillez saisir l'ID du produit que vous cherchez");
                long id= Long.parseLong(reader.readLine());
                System.out.println(repository.fetchProductByID(id).toString());
                menu();
                break;
            case "3":
                System.out.println(repository.fetchExpensiveProducts().toString());
                menu();
                break;
            case "4":
                System.out.println("Veuillez entrer l'id du produit");
                long product_id = Long.parseLong(reader.readLine());
                System.out.println("Veuillez entrer le nom du produit");
                String product_name=reader.readLine();
                System.out.println("Veuillez entrer le prix du produit");
                double prix= Double.parseDouble(reader.readLine());
                System.out.println("Veuillez entrer la date d'expiration du produit");
                String expiration_date=reader.readLine();
                Product product=new Product(product_id,product_name,prix,expiration_date);
                repository.addProduct(product);
                System.out.println("Produit ajouté avec succés");
                menu();
                break;
            case "5":
                System.out.println("Veuillez entrer l'id du produit que voulez modifier");
                long product_id1 = Long.parseLong(reader.readLine());
                System.out.println("Veuillez entrer le nouveau nom du produit");
                String product_name1=reader.readLine();
                System.out.println("Veuillez entrer le nouveau prix du produit");
                double prix1= Double.parseDouble(reader.readLine());
                System.out.println("Veuillez entrer la nouvelle date d'expiration du produit");
                String expiration_date1=reader.readLine();
                Product product1=new Product(product_id1,product_name1,prix1,expiration_date1);
                repository.updateProduct(product1);
                System.out.println("Produit modifié avec succés");
                menu();
                break;
            case "6":
                System.out.println("Veuillez entrer l'id du produit que voulez supprimer");
                long product_id_to_delete = Long.parseLong(reader.readLine());
                repository.deleteProductByID(product_id_to_delete);
                System.out.println("Produit supprimé avec succés");
                menu();
                break;
            case "7":
                System.out.println("Les scenarios sont executés");
                scenario1();
                scenario2();
                scenario3();
                scenario4();
                scenario5();
                menu();
                break;

            default:
                System.out.println("au revoir");
                break;



        }

    }
    public static void initRepo() throws ProductAlreadyExistsException {

        Product p1 = new Product(1, "charcuterie", 20.0, "11/11/26");
        Product p2 = new Product(2, "fromage", 10.0, "11/11/27");
        Product p3 = new Product(3, "yaourt", 1.0, "11/11/28");
        Product p4 = new Product(4, "croissant", 2.0, "11/11/29");
        Product p5 = new Product(5, "pain de mie", 2.5, "11/12/01");
        try {
            repository.addProduct(p1);
            repository.addProduct(p2);
            repository.addProduct(p3);
            repository.addProduct(p4);
            repository.addProduct(p5);
        } catch (ProductAlreadyExistsException e) {
            System.err.println("produit exist deja !");
        }
    }

    public static User getCurrentUser() {
        return user;
    }

    public static void scenario1(){
        user = new User(1, "rihab", 22, "rihab@gmail.com", "123");
         repository.displayAllProducts();
        try {
            repository.fetchProductByID(1);
        } catch (ProductNotFoundException e) {
            System.err.println(e.getMessage());
        }
        Product product = new Product(8,"rihabproduct", 123, "12/12/25");
        try {
            repository.addProduct(product);
        } catch (ProductAlreadyExistsException e) {
            System.err.println(e.getMessage());
        }

    }
    public static void scenario2(){
        user = new User(2, "khaoula", 22, "khaoula@gmail.com", "124");
         repository.displayAllProducts();
        try {
            repository.deleteProductByID(1);
        } catch (ProductNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
    public static void scenario3(){
        user = new User(3, "kai", 22, "kai@gmail.com", "1233");
        Product product = new Product(2,"kai", 123, "12/12/25");
        repository.fetchExpensiveProducts();
    }
    public static void scenario4(){
        user = new User(4, "jc", 22, "jc@gmail.com", "1233");
        Product product = new Product(2,"kai", 123, "12/12/25");
        try {
            repository.updateProduct(product);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ProductAlreadyExistsException e) {
            System.err.println(e.getMessage());
        }
    }
    public static void scenario5(){
        user = new User(4, "jc", 22, "jc@gmail.com", "1233");
        Product product = new Product(3,"kai", 123, "12/12/25");
        try {
            repository.updateProduct(product);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ProductAlreadyExistsException e) {
            System.err.println(e.getMessage());
        }
    }







}