package com.example.casemd3qlct.controller;

import com.example.casemd3qlct.model.User;
import com.example.casemd3qlct.service.UserServiceImpl;

import java.io.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    UserServiceImpl userService = new UserServiceImpl();
    public static Integer idUserLogin = null;
    List<User> userList = userService.getUserList();

        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            String action = request.getParameter("action");
            if(action == null){
                action = "";
            }
            switch (action){
                case "register":
                    showSignUpPage(request,response);
                    break;
                default:
                    showLoginPage(request,response);
            }
    }

    private void showSignUpPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("user/register.jsp").forward(request,response);
    }

    private void showLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.getRequestDispatcher("user/login.jsp").forward(request,response);

        }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            case "register":
                registerForm(request,response);
                break;
            default:
                loginForm(request,response);
        }

    }

    private void registerForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User(username,password);
        userList.add(user);
        userService.create(user);
        response.sendRedirect("/login");
    }

    private void loginForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean check = false;
        int index = -1;
        for (int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getUsername().equals(username) && userList.get(i).getPassword().equals(password)){
                check = true;
                index = i;
            }
        }
        if(check == true){
            response.sendRedirect("/home");
            idUserLogin = userList.get(index).getId();
        }
        else {
            response.sendRedirect("/login");
        }
    }
}

