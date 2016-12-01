import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static HashMap<String, User> users = new HashMap<>();
    //static HashMap<String, Message> messages = new HashMap<>();

    public static void main(String[] args) {


        Spark.init();
        Spark.get(
                "/",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    String enteredPassword = session.attribute("enteredPassword");
                    HashMap m = new HashMap();
                    ArrayList<Message> threads = new ArrayList<>();

                    if (userName == null) {
                        return new ModelAndView(m, "index.html");
                    } else {

                        User user = users.get(userName);
                        String userPassword = user.password;
                        if (userPassword.equals(enteredPassword)) {
                            for (int i = 0; i < user.messages.size(); i++) {
                                    Message temp = user.messages.get(i+1);
                                    temp.id=i+1;
                                    threads.add(user.messages.get(i+1));
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
                    Session session = request.session();
                    session.attribute("userName", userName);
                    session.attribute("enteredPassword", enteredPassword);
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
                    User user = users.get(userName);

                    String text = request.queryParams("messageText");
                    Message m = new Message(user.messages.size()+1, text);
                                  //System.out.println("Hashmap size :"+user.messages.size());
                                  //System.out.println("ID #: "+m.id);
                    user.messages.put(user.messages.size()+1, m);
                                  System.out.println("Hashmap size :"+user.messages.size());

                    response.redirect(request.headers("Referer"));
                    return "";
                })
        );

        Spark.post(
                "/edit-message",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    if (userName == null) {
                        throw new Exception("Not logged in.");
                    }
                    User user = users.get(userName);
                    String temp = request.queryParams("messageId");
                    int id = Integer.parseInt(temp);
                    String text = request.queryParams("messageText");
                    Message m = new Message(id, text);
                    user.messages.replace(id, m);

                    response.redirect(request.headers("Referer"));
                    return "";
                })
        );

        Spark.post(
                "/delete-message",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    if (userName == null) {
                        throw new Exception("Not logged in.");
                    }
                    User user = users.get(userName);
                    String temp = request.queryParams("messageId");
                    int id = Integer.parseInt(temp);
                    user.messages.remove(id);
                    for (int i=id+1; i<=user.messages.size()+1; i++){
                        user.messages.put(i-1, user.messages.remove(i));
                    }

                    response.redirect(request.headers("Referer"));
                    return "";
                })
        );


    }

}


