package com.example.casemd3qlct.model;

public class Wallet {
    private int id;
    private User user;
    private double currentBalance;
    private double totalIncome;
    private double totalExpense;
    private double initialBalance;

    public Wallet() {
    }

    public Wallet(int id, User user, double currentBalance, double totalIncome, double totalExpense, double initialBalance) {
        this.id = id;
        this.user = user;
        this.currentBalance = currentBalance;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.initialBalance = initialBalance;
    }

    public Wallet(User user, double currentBalance, double totalIncome, double totalExpense, double initialBalance) {
        this.user = user;
        this.currentBalance = currentBalance;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.initialBalance = initialBalance;
    }

    public Wallet(User user, double initialBalance) {
        this.user = user;
        this.initialBalance = initialBalance;
    }

    public Wallet(int id, double initialBalance) {
        this.id = id;
        this.initialBalance = initialBalance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }
}
