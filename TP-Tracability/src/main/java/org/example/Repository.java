package org.example;

import exceptions.ProductAlreadyExistsException;
import exceptions.ProductNotFoundException;
import models.Product;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


import java.util.ArrayList;
import java.util.List;


public class Repository {

    private static final Logger LOGGER = Logger.getLogger( Repository.class.getName() );
    private FileHandler fileHandler;
    private long ID;
    private List<Product> products;

    public Repository(long ID, List<Product> products) {
        this.ID = ID;
        this.products = products;
        try {
            fileHandler = new FileHandler("Repository.log", false);
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Repository() {
        this.products=new ArrayList<>();
        try {
            fileHandler = new FileHandler("Repository.log", false);
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        for(Product product:products){
            if(product.getID()==id){
                LOGGER.log(Level.INFO,"{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\", \"ProductId\":"+id+ "}");
                return product;
            }
        }
        LOGGER.log(Level.SEVERE, "{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\",\"msg\": \"Produit non existant\" }");
        throw new ProductNotFoundException("Le produit ayant l\' id saisi n'existe pas ");
    }
    public void addProduct(Product p) throws ProductAlreadyExistsException {
        Product product=new Product(p.getID(),p.getName(),p.getPrice(),p.getExpirationDate());
        try{
            fetchProductByID(p.getID());
            LOGGER.log(Level.SEVERE, "{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\", \"msg\": \"Produit pas ajouté\"}");
            throw new ProductAlreadyExistsException("Le produit ayant l\'id saisi existe déjà");
        }catch(ProductNotFoundException e){
            LOGGER.log(Level.INFO,"{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\", \"ProductId\":"+p.getID()+ "}");
            this.products.add(product);
        }
    }
    public void deleteProductByID(long id) throws ProductNotFoundException {
        try{
            Product p=fetchProductByID(id);
            products.remove(p);
            LOGGER.log(Level.INFO,"{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\", \"ProductId\":"+id+ "}");
        }catch(ProductNotFoundException e){
            LOGGER.log(Level.SEVERE, "{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\", \"msg\": \"Produit pas supprimé \"}");
            throw new ProductNotFoundException("Le produit ayant l\' id saisi n'existe pas ");
        }
    }
    public void updateProduct(Product p) throws ProductNotFoundException, ProductAlreadyExistsException {
        try{
            Product oldproduct=fetchProductByID(p.getID());
            deleteProductByID(oldproduct.getID());
            addProduct(p);
            LOGGER.log(Level.INFO,"{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\", \"ProductId\":"+p.getID()+ "}");
        }catch(ProductNotFoundException e){
            LOGGER.log(Level.SEVERE, "{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\", \"msg\": \"Produit pas mise a jour\"1 }");
            throw new ProductNotFoundException("Le produit ayant l\' id saisi n'existe pas ");
        }
    }
    public void displayAllProducts(){
        LOGGER.log(Level.INFO,"{ \"UserId\":"+Main.getCurrentUser().getID()+",\"UserEmail\": \""+Main.getCurrentUser().getEmail()+"\"}");
        for(Product product:products){
            System.out.println(product.toString());
        }
    }
    public List<Product> fetchExpensiveProducts(){
        List<Product> expensiveProducts=new ArrayList<>();
        for(Product product:products){
            if(product.getPrice()>=10){
                LOGGER.log(Level.INFO,"{ \"UserId\":"+Main.getCurrentUser().getID()+", \"UserEmail\":\""+Main.getCurrentUser().getEmail()+"\", \"ProductId\":"+product.getID()+ "}");
                expensiveProducts.add(product);
            }
        }
        return expensiveProducts;
    }
}
