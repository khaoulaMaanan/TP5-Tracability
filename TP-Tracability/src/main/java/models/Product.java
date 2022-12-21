package models;

import org.example.Main;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Product {
    private long ID;
    private String name;
    private double price;
    private String expirationDate;

    public Product(long ID, String name, double price, String expirationDate) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.expirationDate = expirationDate;

    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        String str="Produit d'ID "+this.ID+", de nom "+this.name+", de prix "+this.price+" et d'une date d'expiration "+this.expirationDate+"\n";
        return str;
    }
}
