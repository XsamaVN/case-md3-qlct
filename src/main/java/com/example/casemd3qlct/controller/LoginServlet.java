package com.example.casemd3qlct.controller;

import com.example.casemd3qlct.model.User;
import com.example.casemd3qlct.service.UserServiceImpl;

import java.io.*;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    UserServiceImpl userService = new UserServiceImpl();
    public static Integer idUserLogin = null;


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "register":
                showSignUpPage(request, response);
                break;
            default:
                showLoginPage(request, response);
        }
    }

    private void showSignUpPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("user/register.jsp").forward(request, response);
    }

    private void showLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("user/login.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "register":
                registerForm(request, response);
                break;
            default:
                loginForm(request, response);
        }

    }

    private void registerForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<User> userList = userService.getUserList();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String usernameError = null;
        String passwordError = null;
        if ((username == null || username.trim().isEmpty()) && (password == null || password.trim().isEmpty())) {
            usernameError = "Please do not leave username blank!";
            request.setAttribute("usernameError", usernameError);
            passwordError = "Please do not leave password blank!";
            request.setAttribute("passwordError", passwordError);
            request.getRequestDispatcher("user/register.jsp").forward(request, response);
        } else if (username == null || username.trim().isEmpty()) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("user/register.jsp");
            usernameError = "Please do not leave username blank!";
            request.setAttribute("usernameError", usernameError);
            requestDispatcher.forward(request, response);
        }

        // Validate password
        else if (password == null || password.trim().isEmpty()) {
            passwordError = "Please do not leave password blank!";
            request.setAttribute("passwordError", passwordError);
            request.getRequestDispatcher("user/register.jsp").forward(request, response);
        } else {
            if (userService.sigin(username)) {
                String username1 = "Tk ton tai";
                request.setAttribute("usernameError", username1);
                request.getRequestDispatcher("user/register.jsp").forward(request, response);

            } else {
                User user = new User(username, password);
                userList.add(user);
                userService.create(user);
                response.sendRedirect("/login");
            }
        }
    }


    private void loginForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username != null || password != null) {
            List<User> userList = userService.getUserList();
            boolean check = false;
            int index = -1;
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUsername().equals(username) && userList.get(i).getPassword().equals(password)) {
                    check = true;
                    index = i;
                }
            }
            if (check == true) {
                response.sendRedirect("/home");
                idUserLogin = userList.get(index).getId();
                System.out.println(idUserLogin);

            } else {
                response.sendRedirect("/login");
            }
        } else {
            response.sendRedirect("/login");
        }
    }
}

