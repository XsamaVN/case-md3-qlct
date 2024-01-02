package com.example.casemd3qlct.controller;

import com.example.casemd3qlct.model.Wallet;
import com.example.casemd3qlct.service.WalletServiceImpl;

import java.io.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "WalletServletServlet", value = "/wallet")
public class WalletServlet extends HttpServlet {
    WalletServiceImpl walletService = new WalletServiceImpl();
    List<Wallet> walletList = walletService.showAll(LoginServlet.idUserLogin);
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            case "showListWallet":

                break;
            default:
                showListWallet(request,response);
        }
    }

    private void showListWallet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("wallet/list.jsp").forward(request,response);
        request.setAttribute("walletList",walletList);
        
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}