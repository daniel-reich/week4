package com.Daniel.servlets;

import com.Daniel.database.DBConnection;
import com.Daniel.model.Guest;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class MvcController extends HttpServlet {

    //static HashMap<Integer, Guest> allGuests = new HashMap<>();////////////////HASHMAP METHOD

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uri = request.getRequestURI();
        System.out.println("Requested URI: " + uri);
        String jspName = uri.substring(uri.lastIndexOf('/') + 1);
        System.out.println("JSP Name: " + jspName);

        DBConnection dbConnection = (DBConnection) getServletContext().getAttribute("DBManager");

        Connection con = dbConnection.getConn();


        try {

            dbConnection.update(
                    "CREATE TABLE guest_table (guestId INTEGER, inDate VARCHAR(256)," +
                            "firstName VARCHAR(256), lastName VARCHAR(256), comment VARCHAR(256), signedOut " +
                            "BOOLEAN, outDate VARCHAR(256))");

        } catch (SQLException ex2) {

        }

        ArrayList<Guest> guests = new ArrayList<>();


        if (jspName.equalsIgnoreCase("viewAllGuests")) {

            request.setAttribute("guests",guests);


        } else if (jspName.equalsIgnoreCase("addGuest")) {

            LocalDate inDate = LocalDate.now();
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String comment = request.getParameter("comment");
            boolean signedOut = false;
            LocalDate outDate = null;

            try {
                dbConnection.update("INSERT INTO guest_table (inDate, firstName, lastName, comment, signedOut, outDate) " +
                              "VALUES('"+inDate+"','"+firstName+"','"+lastName+"','"+comment+"','"+signedOut+"','"+outDate+"')");

            } catch (SQLException ex3) {
                ex3.printStackTrace();
            }



            request.setAttribute ("guests", guests);
            jspName = "viewAllGuests";


        } else if (jspName.equalsIgnoreCase("adminView")) {

            //guests = new ArrayList<>(allGuests.values());
            request.setAttribute("guests",guests);

        } else if (jspName.equalsIgnoreCase("signOut")){

            int guestId = Integer.parseInt(request.getParameter("guestId"));

            try{
                dbConnection.update("UPDATE guest_table SET signedOut = 'true', outDate = '"+LocalDate.now()+"' WHERE guestId = "+guestId+"");

            } catch (SQLException ex3) {
            ex3.printStackTrace();
        }

            request.setAttribute ("guests", guests);
            jspName = "adminView";

        } else if (jspName.equalsIgnoreCase("checkPassword")) {
            String enteredPassword = request.getParameter("password");
            if (enteredPassword.equals("ADMIN")) {
                //guests = new ArrayList<>(allGuests.values());
                request.setAttribute("guests", guests);
                jspName = "adminView";
            } else {
                jspName = "adminLogin";
            }

        } else if (jspName.equalsIgnoreCase("addGuestAdmin")) {
            LocalDate inDate = LocalDate.now();
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String comment = request.getParameter("comment");
            boolean signedOut = false;
            LocalDate outDate = null;

            try {
                dbConnection.update("INSERT INTO guest_table (inDate, firstName, lastName, comment, signedOut, outDate) " +
                        "VALUES('"+inDate+"','"+firstName+"','"+lastName+"','"+comment+"','"+signedOut+"','"+outDate+"')");

            } catch (SQLException ex3) {
                ex3.printStackTrace();
            }


                request.setAttribute("guests", guests);
                jspName = "adminView";
        }

        try {
            Statement st = null;
            ResultSet rs = null;

            st = con.createStatement();//con?
            rs = st.executeQuery("SELECT * FROM guest_table");
            for (; rs.next(); ) {
                Object o = rs.getObject(1);
                String temp = o.toString();
                int guestId = Integer.parseInt(temp);
                o = rs.getObject(2);
                temp = o.toString();
                LocalDate inDate = LocalDate.parse(temp);
                o = rs.getObject(3);
                String firstName = o.toString();
                o = rs.getObject(4);
                String lastName = o.toString();
                o = rs.getObject(5);
                String comment = o.toString();
                o = rs.getObject(6);
                temp = o.toString();
                Boolean signedOut = Boolean.parseBoolean(temp);
                o = rs.getObject(7);
                temp = o.toString();
                LocalDate outDate = null;
                if (!(temp.equals("null"))) {
                    outDate = LocalDate.parse(temp);
                }
                Guest guest = new Guest(guestId,inDate,signedOut,firstName,lastName,comment,outDate);
                guests.add(guest);
            }

        } catch (SQLException ex3) {

        }




        RequestDispatcher view = request.getRequestDispatcher("/"+jspName+".jsp");
        view.forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request,response);
    }

    public void updateArray(){

    }
}
