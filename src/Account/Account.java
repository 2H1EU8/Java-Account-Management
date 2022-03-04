package Account;

public class Account {
    private static int idAccess;
    private static String userName;
    private String password;

    public Account(){};

    public Account(String userName, String password,int idAccess) {
        Account.userName = userName;
        this.password = password;
        this.setIdAccess(idAccess);
    }


    public static String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        Account.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static int getIdAccess() {
        return idAccess;
    }

    public void setIdAccess(int idAccess) {
        Account.idAccess = idAccess;
    }

}
