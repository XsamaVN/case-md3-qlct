package com.example.casemd3qlct.model;

public class Transaction {
    private int id;
    private Category category;
    private Wallet wallet;
    private double amount;
    private String time;
    private String type;
    private String description;

    public Transaction() {
    }

    public Transaction(int id, Category category, Wallet wallet, double amount, String time, String type, String description) {
        this.id = id;
        this.category = category;
        this.wallet = wallet;
        this.amount = amount;
        this.time = time;
        this.type = type;
        this.description = description;
    }

    public Transaction(Category category, Wallet wallet, double amount, String time, String type, String description) {
        this.category = category;
        this.wallet = wallet;
        this.amount = amount;
        this.time = time;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
