/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.securedistributedapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.Response;
import spark.Request;
import static spark.Spark.*;

public class Spark {

    private static Map<String, User> hashCodes = new HashMap<>();
    private static GFG sha = new GFG();

    // View example at https://localhost:4567/secureHello
    public static void main(String[] args) {
        port(getPort());
        staticFiles.location("/static");
        secure("deploy/KeyStorege.jks", "secureApp1zy", "deploy/truststore", "secureServer1zy");
        //get("/login", (req, res) -> "Hello Secure World");

        get("/", (request, response) -> {
            response.redirect("index.html");
            return null;
        });

        get("/login", (request, response) -> {
            return login(request, response);
        });

        get("/date", (request, response) -> {
            return getDateOfServer();
        });

        post("/register", (request, response) -> {
            register(request);
            response.redirect("index.html");
            return null;
        });
    }

    private static void register(Request request) {
        String name = request.queryParams("name");
        String userName = request.queryParams("user");
        String email = request.queryParams("email");
        String passw = request.queryParams("passw");
        if (hashCodes.containsKey(userName)) {
            System.out.println("El usuario ya existe, elija otro nombre");
        } else {
            String passwHash = sha.convert(passw);
            User user = new User(name, userName, email, passwHash);
            hashCodes.put(userName, user);
            System.out.println("El usuario se registró correctamente.");
        }
    }

    private static String login(Request request, Response response) {
        System.out.println("Entro a iicar sesion");
        String perfil = "";
        String userName = request.queryParams("userName");
        String passwordToVerify = request.queryParams("password");
        User user = hashCodes.get(userName);
        String hashCodesToVerify = sha.convert(passwordToVerify);
        if (!hashCodes.containsKey(userName)) {
            System.err.println("El usuario no existe");
            response.redirect("/notFound.html");
        } else if (user.getPasswHash().equals(hashCodesToVerify)) {
            System.err.println("Es correto");
            perfil = getProfile(user);
        } else {
            perfil = "La contraseña es incorrecta.";
            System.out.println(perfil);
        }
        return perfil;
    }

    private static String getProfile(User user) {
        java.util.Date fecha = new Date();
        String profileHtml = "<!DOCTYPE html>\n"
                + "\n"
                + "<html lang=\"en\">\n"
                + "    <head>\n"
                + "        <title>Login</title>\n"
                + "        <meta charset=\"UTF-8\">        \n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\"/>        \n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "        <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>\n"
                + "        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\" integrity=\"sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1\" crossorigin=\"anonymous\"></script>\n"
                + "        <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>\n"
                + "\n"
                + "        <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\n"
                + "    </head>\n"
                + "\n"
                + "    <body style=\"background-image: url(https://cdn.pixabay.com/photo/2013/07/13/12/08/background-159244_960_720.png); background-position: center center; background-repeat: no-repeat; background-attachment: fixed; background-size: cover;\">\n"
                + "        <nav class=\"navbar navbar-expand-lg navbar-dark navbar-custom fixed-top\" style=\"font-family: serif\">\n"
                + "            <div class=\"container\">\n"
                + "                <a class=\"navbar-brand\" >AREP: Secure Distributed Web App - AWS</a>\n"
                + "                <br>\n"
                + "                <a class=\"navbar-brand\" >Amalia Alfonso</a>\n"
                + "            </div>\n"
                + "        </nav>\n"
                + "        <br><br><br>\n"
                + "        <div class=\"container\">\n"
                + "            <div class=\"row\">\n"
                + "                <div class=\"col-md-4\"></div>\n"
                + "                <div class=\"col-md-4\" style=\"text-align: center; font-family: serif\">\n"
                + "                    <form class=\"form-horizontal\" style=\"text-align: center\">\n"
                + "                        <fieldset>\n"
                + "                            <legend class=\"text-center header\">Perfil</legend>\n"
                + "                            <div class=\"form-group\" style=\"text-align: center\">\n"
                + "                                <img src=\"https://img.icons8.com/clouds/2x/user.png\" style=\"width: 50%\">\n"
                + "                            </div>\n"
                + "                            <div class=\"card\" style=\"background-color: #ccdfea;\">\n"
                + "                                <ul class=\"list-group list-group-flush\">\n"
                + "                                    <li class=\"list-group-item\" style=\"background-color: #ccdfea;\">Usuario: " + user.getUser() + "</li>\n"
                + "                                    <br>\n"
                + "                                    <li class=\"list-group-item\" style=\"background-color: #ccdfea;\">Nombre: " + user.getName() + "</li>\n"
                + "                                    <br>\n"
                + "                                    <li class=\"list-group-item\" style=\"background-color: #ccdfea;\">Correo: " + user.getEmail() + "</li>\n"
                + "                                    <br>\n"
                + "                                </ul>\n"
                + "                            </div>\n"
                + "                            <br>\n"
                + "                            <br>\n"
                + "                            <div class=\"form-group\">\n"
                + "                                <div class=\"text-center\">\n"
                + "                                    <form>\n"
                + "                                        <button type=\"submit\" class=\"btn btn-primary btn-lg\" style=\"background-color: darkturquoise\" onclick=\"window.location.href = 'index.html'\" >Salir</button>\n"
                + "                                    </form>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                            <br>\n"
                + "                            <label style=\"color: #99ffff; font-size: 1.2rem;\">Fecha: " + fecha + "</label>\n"
                + "                        </fieldset>\n"
                + "                    </form>\n"
                + "                </div>\n"
                + "                <div class=\"col-md-4\"></div>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </body>\n"
                + "</html>";
        return profileHtml;
    }

    static {
        //for localhost testing only
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier() {

            public boolean verify(String hostname,
                    javax.net.ssl.SSLSession sslSession) {
                if (hostname.equals("localhost")) {
                    return true;
                }
                return false;
            }
        });
    }

    private static String getDateOfServer() throws IOException {
        String date = "";
        try {
            URL url = new URL("https://localhost:4568");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String dataLine = "";
            while ((dataLine = reader.readLine()) != null) {
                date += dataLine;
                //System.out.println(urlData);   
            }
            System.out.println("Fin main :" + date);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Spark.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Spark.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e.on localhost)
    }
}
