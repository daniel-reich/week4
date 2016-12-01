import java.util.HashMap;

public class User {
    String name;
    String password;
    HashMap<Integer, Message> messages = new HashMap<>();

    public User(String name) {
        this.name = name;
    }
}

