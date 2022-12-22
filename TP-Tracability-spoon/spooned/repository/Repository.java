package repository;
import exceptions.ProductAlreadyExistsException;
import exceptions.ProductNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Logger;
import models.Product;
public class Repository {
    private static final Logger LOGGER;

    private Handler fileHandler;

    private long ID;

    private List<Product> products;

    public Repository(long ID, List<Product> products) {
        this.ID = ID;
        this.products = products;
        this.LOGGER = java.util.logging.Logger.getLogger(Repository.class.getName());;
        try {
           fileHandler  = new java.util.logging.FileHandler("Repository.log");
           LOGGER.addHandler(fileHandler);
           fileHandler.setLevel(java.util.logging.Level.ALL);
           LOGGER.setLevel(java.util.logging.Level.ALL);
        } catch (java.io.IOException e) {
           LOGGER.log(java.util.logging.Level.SEVERE, "Error occur in FileHandler.", e);
        };
    }

    public Repository() {
        this.products = new ArrayList<>();
        this.LOGGER = java.util.logging.Logger.getLogger(Repository.class.getName());;
        try {
           fileHandler  = new java.util.logging.FileHandler("Repository.log");
           LOGGER.addHandler(fileHandler);
           fileHandler.setLevel(java.util.logging.Level.ALL);
           LOGGER.setLevel(java.util.logging.Level.ALL);
        } catch (java.io.IOException e) {
           LOGGER.log(java.util.logging.Level.SEVERE, "Error occur in FileHandler.", e);
        };
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Product fetchProductByID(long id) throws ProductNotFoundException {
        for (Product product : products) {
            if (product.getID() == id) {
                 LOGGER.log(Level.INFO,"{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\", \"ProductId\":"+id+ "}");
                return product;
            }
        }
         LOGGER.log(Level.SEVERE,"{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\", \"msg\":"+Produit non existant+ "}");
        throw new ProductNotFoundException("Le produit ayant l\' id saisi n\'existe pas ");
    }

    public void addProduct(Product p) throws ProductAlreadyExistsException {
        Product product = new Product(p.getID(), p.getName(), p.getPrice(), p.getExpirationDate());
        try {
            fetchProductByID(p.getID());
             LOGGER.log(Level.SEVERE, "{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\", \"msg\": \"Produit pas ajouté\"}");
             throw new ProductAlreadyExistsException("Le produit ayant l\'id saisi existe déjà");;
        } catch (ProductNotFoundException e) {
            LOGGER.log(Level.INFO,"{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\", \"ProductId\":"+p.getID()+ "}") 
             this.products.add(product) ;
        }
    }

    public void deleteProductByID(long id) throws ProductNotFoundException {
        try {
            Product p=fetchProductByID(id);
             products.remove(p);
             LOGGER.log(Level.INFO,"{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\", \"ProductId\":"+id+ "}");
        } catch (ProductNotFoundException e) {
             LOGGER.log(Level.SEVERE,"{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\", \"msg\":"+Produit pas supprimé+ "}");
            throw new ProductNotFoundException("Le produit ayant l\' id saisi n\'existe pas ");
        }
    }

    public void updateProduct(Product p) throws ProductNotFoundException, ProductAlreadyExistsException {
        try {
            Product oldproduct=fetchProductByID(p.getID());
             deleteProductByID(oldproduct.getID());
             addProduct(p);
             LOGGER.log(Level.INFO,"{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\", \"ProductId\":"+p.getID()+ "}");
        } catch (ProductNotFoundException e) {
             LOGGER.log(Level.SEVERE,"{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\", \"msg\":"+Produit pas ajouté+ "}");
            throw new ProductNotFoundException("Le produit ayant l\' id saisi n\'existe pas ");
        }
    }

    public void displayAllProducts() {
        LOGGER.log(Level.INFO,"{ \"UserId\":"+Main.getCurrentUser().getID()+",\"UserEmail\": \""+Main.getCurrentUser().getEmail()+"\"}");
        for (int i = 0; i < this.products.size(); i++) {
            System.out.println(products.get(i).toString());
        }
    }

    public List<Product> fetchExpensiveProducts() {
        List<Product> expensiveProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() >= 10)
                LOGGER.log(Level.INFO,"{ "UserId":"+Main.getCurrentUser().getID()+", "UserEmail":""+Main.getCurrentUser().getEmail()+"", "ProductId":"+product.getID()+ "}");
                                expensiveProducts.add(product);

        }
        return expensiveProducts;
    }
}