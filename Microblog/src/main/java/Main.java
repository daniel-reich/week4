import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static HashMap<String, User> users = new HashMap<>();
    static HashMap<String, Message> messages = new HashMap<>();

    public static void main(String[] args) {


        Spark.init();
        Spark.get(
                "/",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    System.out.println("User "+userName);
                    String enteredPassword = session.attribute("enteredPassword");
                    System.out.println("Password "+enteredPassword);
                    HashMap m = new HashMap();
                    ArrayList<Message> threads = new ArrayList<>();

                    if (userName == null) {
                        return new ModelAndView(m, "index.html");
                    } else {

                        User user = users.get(userName);
                        String userPassword = user.password;
                        System.out.println("User"+userPassword);
                        System.out.println("entered"+enteredPassword);
                        if (userPassword.equals(enteredPassword)) {

                            for (int i = 0; i < messages.size(); i++) {
                                if (messages.get(userName + i) != null) {
                                    threads.add(messages.get(userName + i));
                                }
                            }

                            m.put("messages", threads);
                            m.put("userName", userName);
                            return new ModelAndView(m, "messages.html");
                        } else {
                            return new ModelAndView(m, "index.html");
                        }

                    }

                }),
                new MustacheTemplateEngine()
        );


        Spark.post(
                "/create-user",
                ((request, response) -> {
                    String userName = request.queryParams("userName");
                    String enteredPassword = request.queryParams("password");
                    User user = users.get(userName);
                    if (user == null) {
                        user = new User(userName);
                        user.password = enteredPassword;
                        users.put(userName, user);
                    }
                    System.out.println(user.password);/////////////////
                    Session session = request.session();
                    session.attribute("userName", userName);
                    session.attribute("enteredPassword", enteredPassword);
                    response.redirect("/");
                    return "";
                })
        );


        Spark.post(
                "/login",
                ((request, response) -> {
                    String userName = request.queryParams("loginName");
                    if (userName == null) {
                        throw new Exception("Login name not found.");
                    }

                    User user = users.get(userName);
                    if (user == null) {
                        user = new User(userName);
                        users.put(userName, user);
                    }

                    Session session = request.session();
                    session.attribute("userName", userName);

                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/create-message",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    if (userName == null) {
                        throw new Exception("Not logged in.");
                    }

                    String text = request.queryParams("messageText");

                    Message m = new Message(messages.size(), userName, text);
                    messages.put(userName+messages.size(), m);

                    response.redirect(request.headers("Referer"));
                    return "";
                })
        );


    }

}


