package com.example.casemd3qlct.controller;

import com.example.casemd3qlct.model.Category;
import com.example.casemd3qlct.model.Transaction;
import com.example.casemd3qlct.model.Wallet;
import com.example.casemd3qlct.service.*;

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
    CategoryServiceImpl categoryService = new CategoryServiceImpl();
    TransactionServiceImpl transactionService = new TransactionServiceImpl();
    public static Integer idWallet = null;


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "profile":
                showFormEditProfile(request, response);
                break;
            case "deleteProfile":
                deleteProfile(request, response);
                break;
            case "editWallet":
                showEditWalletForm(request, response);
                break;
            case "createWallet":
                showCreateWalletForm(request, response);
                break;
            case "deleteWallet":
                deleteWallet(request,response);
                break;
            case "showCategoryList":
                showCategoryList(request, response);
                break;
            case "createCategory":
                showCreateCategoryForm(request, response);
                break;
            case "editCategory":
                showEditCategoryForm(request, response);
                break;
            case "deleteCategory":
                deleteCategory(request,response);
                break;
            case "logout":
                logouts(request, response);
                break;
            case "showDetail":
                showDetail(request, response);
                break;
            case "createTran":
                showFormCreateTran(request, response);
                break;
            case "deleteTran":
                deleteTran(request, response);
                break;
            case "editTran":
                editTran(request, response);
                break;
            default:
                showHomePage(request, response);
        }
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idDelete = Integer.parseInt(request.getParameter("idDelete1"));
        categoryService.delete(idDelete);
        response.sendRedirect("/home?action=showCategoryList");
    }

    private void deleteProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        userService.deleteTransactions(username);
        userService.deleteWallets(username);
        userService.deleteUserRecord(username);
        response.sendRedirect("/login");
    }
    private void deleteWallet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idDelete = Integer.parseInt(request.getParameter("idDelete"));
        walletService.delete(idDelete);
        response.sendRedirect("/home");
    }

    private void showFormEditProfile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user/profile.jsp");
        if (LoginServlet.idUserLogin != null) {
            String username = userService.findByid(LoginServlet.idUserLogin).getUsername();
            System.out.println(username);
            request.setAttribute("username", username);
        }
        requestDispatcher.forward(request, response);
    }

    private void showEditWalletForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("wallet/edit.jsp");
        Integer id = LoginServlet.idUserLogin;
        if (id != null) {
            String username = userService.findByid(LoginServlet.idUserLogin).getUsername();
            System.out.println(username);
            request.setAttribute("username1", username);
            int idEdit = Integer.parseInt(request.getParameter("idEdit"));
            request.setAttribute("editWallet", walletService.findByid(idEdit));
        }
        requestDispatcher.forward(request, response);
    }

    private void showCreateWalletForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("wallet/create.jsp");
        Integer id = LoginServlet.idUserLogin;
        if (id != null) {
            String username = userService.findByid(LoginServlet.idUserLogin).getUsername();
            System.out.println(username);
            request.setAttribute("username1", username);

        }
        requestDispatcher.forward(request, response);
    }

    private void showEditCategoryForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("category/edit.jsp");
        Integer id = LoginServlet.idUserLogin;
        if (id != null) {
            String username = userService.findByid(LoginServlet.idUserLogin).getUsername();
            System.out.println(username);
            request.setAttribute("username1", username);
            int id1 = Integer.parseInt(request.getParameter("idEdit"));
            request.setAttribute("categoryEdit", categoryService.findByid(id1));
        }
        requestDispatcher.forward(request, response);
    }

    private void showCreateCategoryForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("category/create.jsp");
        Integer id = LoginServlet.idUserLogin;
        if (id != null) {
            String username = userService.findByid(LoginServlet.idUserLogin).getUsername();
            System.out.println(username);
            request.setAttribute("username1", username);

        }
        requestDispatcher.forward(request, response);
    }

    private void showCategoryList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("category/list.jsp");
        Integer id = LoginServlet.idUserLogin;
        if (id != null) {
            String username = userService.findByid(LoginServlet.idUserLogin).getUsername();
            System.out.println(username);
            request.setAttribute("username1", username);
            request.setAttribute("idWalletShow", idWallet);
            List<Category> categoryList = new ArrayList<>(categoryService.getCategoryList());
            request.setAttribute("categoryList", categoryList);

        }
        requestDispatcher.forward(request, response);
    }

    private void showFormCreateTran(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("transaction/create.jsp");
        Integer id = LoginServlet.idUserLogin;
        if (id != null) {
            String username = userService.findByid(LoginServlet.idUserLogin).getUsername();
            System.out.println(username);
            request.setAttribute("username1", username);
            request.setAttribute("idWallet", idWallet);
            request.setAttribute("categoryList",categoryService.getCategoryList());
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
            int idEdit = Integer.parseInt(request.getParameter("idTran"));
            Transaction transaction = transactionService.findByid(idEdit);
            request.setAttribute("tranEdit", transaction);
            request.setAttribute("categoryList",categoryService.getCategoryList());
        }
        requestDispatcher.forward(request, response);
    }

    private void deleteTran(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            request.setAttribute("walletName", walletService.findByid(idWallet).getName());
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
            case "editWallet":
                EditWallet(request, response);
                break;
            case "createWallet":
                createWallet(request, response);
                break;
            case "editTran":
                editTranPost(request, response);
                break;
            case "createTran":
                createTran(request, response);
                break;
            case "createCategory":
                createCategory(request, response);
                break;
            case "editCategory":
                editCategory(request, response);
                break;
            case "profile":
                editProfile(request,response);
                break;
        }
    }
    private void editProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        userService.changeprofile(username, password);
        response.sendRedirect("/home");
    }
    private void EditWallet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idEdit = Integer.parseInt(request.getParameter("idEdit"));
        String name = request.getParameter("name");
        double initialBalance = Double.parseDouble(request.getParameter("initial"));
        Wallet wallet = new Wallet(idEdit, initialBalance,name);
        walletService.edit(idEdit, wallet);
        response.sendRedirect("/home");
    }


    private void createWallet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        double initialBalance = Double.parseDouble(request.getParameter("initial"));
        String name = request.getParameter("name");
        int idUser = LoginServlet.idUserLogin;
        Wallet wallet = new Wallet(userService.findByid(idUser), initialBalance,name);
        walletService.create(wallet);
        response.sendRedirect("/home");
    }

    private void editCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id1 = Integer.parseInt(request.getParameter("idEdit"));
        String name = request.getParameter("name");
        Category category = new Category(name);
        categoryService.edit(id1, category);
        response.sendRedirect("/home?action=showCategoryList");
    }

    private void createCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        Category category = new Category(name);
        categoryService.create(category);
        response.sendRedirect("/home?action=showCategoryList");
    }

    private void createTran(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int idCategory = Integer.parseInt(request.getParameter("idCategory"));

        double amount = Double.parseDouble(request.getParameter("amount"));
        String time = request.getParameter("time");
        String type = request.getParameter("type");
        String description = request.getParameter("description");
        Transaction transaction = new Transaction(categoryService.findByid(idCategory), walletService.findByid(idWallet), amount, time, type, description);
        transactionService.create(transaction);
        response.sendRedirect("/home?action=showDetail&idWallet=" + idWallet);
    }

    private void editTranPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idTran = Integer.parseInt(request.getParameter("idTran"));
        int idCategory = Integer.parseInt(request.getParameter("idCategory"));
        double amount = Double.parseDouble(request.getParameter("amount"));
        String time = request.getParameter("time");
        String type = request.getParameter("type");
        String description = request.getParameter("description");
        Transaction transaction = new Transaction(idTran, categoryService.findByid(idCategory), walletService.findByid(idWallet), amount, time, type, description);
        transactionService.edit(idTran, transaction);
        response.sendRedirect("/home?action=showDetail&idWallet=" + idWallet);
    }
}