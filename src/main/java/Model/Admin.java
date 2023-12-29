package Model;

public class Admin {
    private int id;
    private String username;
    private String password;
    public Admin(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public int getId() {
        return id;
    }

    public String getUsernaame() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

