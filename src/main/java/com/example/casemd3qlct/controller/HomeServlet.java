package com.example.casemd3qlct.controller;

import com.example.casemd3qlct.model.Wallet;
import com.example.casemd3qlct.service.UserServiceImpl;
import com.example.casemd3qlct.service.WalletServiceImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "HomeServletServlet", value = "/home")
public class HomeServlet extends HttpServlet {
    UserServiceImpl userService = new UserServiceImpl();
    WalletServiceImpl walletService = new WalletServiceImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String action = request.getParameter("action");
    if(action == null){
        action = "";
    }
    switch (action){
        case "showListWallet":
            showListWallet(request,response);
            break;
        case"logout":
            logouts(request,response);
            break;
        default:
            showHomePage(request,response);
    }
    }

    private void logouts(HttpServletRequest request, HttpServletResponse response) throws IOException {
    LoginServlet.idUserLogin = null;
    response.sendRedirect("/login");
    }

    private void showListWallet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/wallet");
    }

    private void showHomePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher= request.getRequestDispatcher("user/home.jsp");

        if(LoginServlet.idUserLogin != null){
        String username = userService.findByid(LoginServlet.idUserLogin).getUsername();
        int id = LoginServlet.idUserLogin;
        List<Wallet> walletList = walletService.showAll(id);
        System.out.println(username);
        request.setAttribute("username", username);
        request.setAttribute("initialBalance", walletService.getInitialBalanceById(LoginServlet.idUserLogin));
        request.setAttribute("totalIncome", walletService.getTotalIncomeById(LoginServlet.idUserLogin));
        request.setAttribute("totalExpense", walletService.getTotalExpenseById(LoginServlet.idUserLogin));
        }
        requestDispatcher.forward(request,response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}