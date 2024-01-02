package com.example.casemd3qlct.controller;

import com.example.casemd3qlct.model.Wallet;
import com.example.casemd3qlct.service.WalletServiceImpl;

import java.io.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "HomeServletServlet", value = "/home")
public class HomeServlet extends HttpServlet {
    WalletServiceImpl walletService = new WalletServiceImpl();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String action = request.getParameter("action");
    if(action == null){
        action = "";
    }
    switch (action){
        default:
            showHomePage(request,response);
    }
    }

    private void showHomePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("user/home.jsp").forward(request,response);
        List<Wallet> walletList = walletService.showAll(LoginServlet.idUserLogin);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}