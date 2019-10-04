/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.securedistributedapp;
import static spark.Spark.*;


public class Spark {
    // View example at https://localhost:4567/secureHello
    public static void main(String[] args) {
        port(getPort());
        staticFiles.location("/static"); 
        secure("deploy/KeyStorege.jks", "secureApp1zy", null, null);
        //get("/login", (req, res) -> "Hello Secure World");
        
        get("/login", (request, response) -> {
            response.redirect("index.html");
            return null;
        });
    }
    
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e.on localhost)
    }
}
  
