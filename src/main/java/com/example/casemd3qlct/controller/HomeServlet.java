package com.example.casemd3qlct.controller;

import com.example.casemd3qlct.model.Transaction;
import com.example.casemd3qlct.model.Wallet;
import com.example.casemd3qlct.service.CategoryServiceImpl;
import com.example.casemd3qlct.service.TransactionServiceImpl;
import com.example.casemd3qlct.service.UserServiceImpl;
import com.example.casemd3qlct.service.WalletServiceImpl;

import java.io.*;

import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "HomeServletServlet", value = "/home")
public class HomeServlet extends HttpServlet {
    UserServiceImpl userService = new UserServiceImpl();
    WalletServiceImpl walletService = new WalletServiceImpl();
    CategoryServiceImpl categoryService = new CategoryServiceImpl();
    TransactionServiceImpl transactionService = new TransactionServiceImpl();
    public static Integer idWallet = null;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "showListWallet":
                showListWallet(request, response);
                break;
            case "logout":
                logouts(request, response);
                break;
            case "showDetail":
                showDetail(request, response);
                break;
            case "createTran":
                showFormCreateTran(request,response);
                break;
            case "deleteTranThu":
                deleteTranThu(request, response);
                break;
            case "editTran":
                editTran(request, response);
                break;
            default:
                showHomePage(request, response);
        }
    }

    private void showFormCreateTran(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("transaction/create.jsp");
        Integer id = LoginServlet.idUserLogin;
        if (id != null) {
            String username = userService.findByid(LoginServlet.idUserLogin).getUsername();
            System.out.println(username);
            request.setAttribute("username1", username);
            request.setAttribute("idWalletShow", idWallet);
        }
        requestDispatcher.forward(request, response);
    }

    private void editTran(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("transaction/edit.jsp");
        Integer id = LoginServlet.idUserLogin;
        if (id != null) {
            String username = userService.findByid(LoginServlet.idUserLogin).getUsername();
            System.out.println(username);
            request.setAttribute("username1", username);
            request.setAttribute("idWalletShow", idWallet);
            int idEdit = Integer.parseInt(request.getParameter("idEdit"));
            Transaction transaction =  transactionService.findByid(idEdit);
            request.setAttribute("tranEdit", transaction);
        }
        requestDispatcher.forward(request, response);
    }

    private void deleteTranThu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idDelete = Integer.parseInt(request.getParameter("idTran"));
        transactionService.delete(idDelete);
        response.sendRedirect("/home?action=showDetail&idWallet=" + idWallet);
    }

    private void showDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("transaction/list.jsp");
        Integer id = LoginServlet.idUserLogin;
        if (id != null) {
            String username = userService.findByid(LoginServlet.idUserLogin).getUsername();
            System.out.println(username);
            request.setAttribute("username1", username);
            idWallet = Integer.parseInt(request.getParameter("idWallet"));
            request.setAttribute("idWalletShow", idWallet);
            request.setAttribute("bangChi", transactionService.findTransactionListByWalletId(idWallet, "chi"));
            request.setAttribute("bangThu", transactionService.findTransactionListByWalletId(idWallet, "thu"));
        }

        requestDispatcher.forward(request, response);
    }

    private void logouts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginServlet.idUserLogin = null;
        idWallet = null;
        response.sendRedirect("/login");
    }

    private void showListWallet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/wallet");
    }

    private void showHomePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user/home.jsp");

        if (LoginServlet.idUserLogin != null) {
            String username = userService.findByid(LoginServlet.idUserLogin).getUsername();
            int id = LoginServlet.idUserLogin;
            List<Wallet> walletList = walletService.showAll(id);
            System.out.println(username);
            request.setAttribute("username", username);
            request.setAttribute("walletList", walletList);
        }
        requestDispatcher.forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "editTran":
                editTranPost(request,response);
                break;
            case "createTran":
                createTran(request,response);
                break;
        }
    }

    private void createTran(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int idCategory = Integer.parseInt(request.getParameter("idCategory"));

        double amount = Double.parseDouble(request.getParameter("amount"));
        String time = request.getParameter("time");
        String type = request.getParameter("type");
        String description = request.getParameter("description");
        Transaction transaction = new Transaction(categoryService.findByid(idCategory),walletService.findByid(idWallet),amount, time,type,description);
        transactionService.create(transaction);
        response.sendRedirect("/home?action=showDetail&idWallet=" + idWallet);
    }

    private void editTranPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idTran = Integer.parseInt(request.getParameter("id"));
        int idCategory = Integer.parseInt(request.getParameter("idCategory"));

        double amount = Double.parseDouble(request.getParameter("amount"));
        String time = request.getParameter("time");
        String type = request.getParameter("type");
        String description = request.getParameter("description");
        Transaction transaction = new Transaction(idTran,categoryService.findByid(idCategory),walletService.findByid(idWallet),amount, time,type,description);
        transactionService.edit(idTran,transaction);
        response.sendRedirect("/home?action=showDetail&idWallet=" + idWallet);
    }
}