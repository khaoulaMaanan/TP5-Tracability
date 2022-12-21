package repository;
import exceptions.ProductAlreadyExistsException;
import exceptions.ProductNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import models.Product;
import models.User;
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    static Repository repository = new Repository();

    static User user = new User();

    public static void main(String[] args) throws IOException, ProductAlreadyExistsException, ProductNotFoundException {
        System.out.println("Bienvenue");
        initRepo();
        getUserInfo();
        menu();
    }

    public static void getUserInfo() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Veuillez entrer l'id de l'utilisateur");
        long user_id = Long.parseLong(reader.readLine());
        System.out.println("Veuillez entrer le nom de l'utilisateur");
        String user_name = reader.readLine();
        System.out.println("Veuillez entrer l'âge de l'utilisateur");
        int age = Integer.parseInt(reader.readLine());
        System.out.println("Veuillez entrer l'email de l'utilisateur");
        String email = reader.readLine();
        System.out.println("Veuillez entrer le mot de passe de l'utilisateur");
        String password = reader.readLine();
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
        System.out.println("3-Ajouter un produit");
        System.out.println("4-Modifier un produit");
        System.out.println("5-Supprimer un produit");
        System.out.println("6-Supprimer un produit");
        System.out.println("0- Quitter l'application");
        String choix = reader.readLine();
        switch (choix) {
            case "1" :
                repository.displayAllProducts();
                menu();
                break;
            case "2" :
                System.out.println("Veuillez saisir l'ID du produit que vous cherchez");
                long id = Long.parseLong(reader.readLine());
                System.out.println(repository.fetchProductByID(id).toString());
                menu();
                break;
            case "3" :
                System.out.println("Veuillez entrer l'id du produit");
                long product_id = Long.parseLong(reader.readLine());
                System.out.println("Veuillez entrer le nom du produit");
                String product_name = reader.readLine();
                System.out.println("Veuillez entrer le prix du produit");
                double prix = Double.parseDouble(reader.readLine());
                System.out.println("Veuillez entrer la date d'expiration du produit");
                String expiration_date = reader.readLine();
                Product product = new Product(product_id, product_name, prix, expiration_date);
                repository.addProduct(product);
                System.out.println("Produit ajouté avec succés");
                menu();
                break;
            case "4" :
                System.out.println("Veuillez entrer l'id du produit que voulez modifier");
                long product_id1 = Long.parseLong(reader.readLine());
                System.out.println("Veuillez entrer le nouveau nom du produit");
                String product_name1 = reader.readLine();
                System.out.println("Veuillez entrer le nouveau prix du produit");
                double prix1 = Double.parseDouble(reader.readLine());
                System.out.println("Veuillez entrer la nouvelle date d'expiration du produit");
                String expiration_date1 = reader.readLine();
                Product product1 = new Product(product_id1, product_name1, prix1, expiration_date1);
                repository.updateProduct(product1);
                System.out.println("Produit modifié avec succés");
                menu();
                break;
            case "5" :
                System.out.println("Veuillez entrer l'id du produit que voulez supprimer");
                long product_id_to_delete = Long.parseLong(reader.readLine());
                repository.deleteProductByID(product_id_to_delete);
                System.out.println("Produit supprimé avec succés");
                menu();
                break;
            case "6" :
                System.out.println("La liste des produits les plus chères");
                repository.fetchExpensiveProducts();
            default :
                System.out.println("au revoir");
                break;
        }
    }

    public static void initRepo() throws ProductAlreadyExistsException {
        Product p1 = new Product(1, "fresh ground steak", 20.0, "11/11/26");
        Product p2 = new Product(2, "cheese omelette", 10.0, "11/11/27");
        Product p3 = new Product(3, "baguette", 1.0, "11/11/28");
        Product p4 = new Product(4, "croissant", 2.0, "11/11/29");
        Product p5 = new Product(5, "pain aux chocolat", 2.5, "11/12/01");
        try {
            repository.addProduct(p1);
            repository.addProduct(p2);
            repository.addProduct(p3);
            repository.addProduct(p4);
            repository.addProduct(p5);
        } catch (ProductAlreadyExistsException e) {
            throw new ProductAlreadyExistsException("Produit déjà existant");
        }
    }

    public static User getCurrentUser() {
        LOGGER.info("user called");
        return user;
    }
}