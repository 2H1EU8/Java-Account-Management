package Common;

import Account.Account;
import Account.AccountData;

import java.sql.*;
import java.util.ArrayList;

public class DAO {
    public static void addAcc(AccountData acc) throws Exception {
        String sql = "Insert Into Data(username,password,type,idaccess,link) values (?,?,?,?,?)";
        try(
                Connection connection = ConnectionDB.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ){
            ps.setString(1,acc.getUserName());
            ps.setString(2,acc.getPassword());
            ps.setString(3,acc.getTypeAcc());
            ps.setInt(4, Account.getIdAccess());
            ps.setString(5,acc.getAddress());


            ps.executeUpdate();
        }
    }

    public static void deleteAcc(String username,String type, int id) throws Exception {
        String sql = "Delete from data where username = '"+username+"' and type = '"+type+"' and idaccess ="+id ;
        try (
                Connection connection = ConnectionDB.getConnection();
                Statement st = connection.createStatement();
        ){
            st.execute(sql);
        }
    }

    public static void updateAcc(AccountData acc) throws Exception {
        String sql = "Update data set password= ? ,type= ?,modified= getDate() , link =? where username ='"+acc.getUserName()+"' " +
                "and idaccess="+Account.getIdAccess()+" and type ='"+acc.getTypeAcc()+"'";
        try(
                Connection connection = ConnectionDB.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ){
            ps.setString(1,acc.getPassword());
            ps.setString(2,acc.getTypeAcc());
            ps.setString(3, acc.getAddress());

            ps.executeUpdate();
        }
    }
    public static void signUp(Account acc) throws Exception {
        String sql = "Insert Into Login(username,passwords) values (?,?)";
        try(
                Connection connection = ConnectionDB.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ){
            ps.setString(1,acc.getUserName());
            ps.setString(2,acc.getPassword());
            ps.executeUpdate();
        }
    }

    public static ArrayList<AccountData> Total(){
        String sql = "Select * from data where idaccess="+Account.getIdAccess();
        ArrayList<AccountData> list = new ArrayList<>();
        try(
                Connection connection = ConnectionDB.getConnection();
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sql);
                ){
            while (rs.next()){
                AccountData acc = new AccountData(rs.getString("Username"),rs.getString("Password"),
                        rs.getString("modified"),rs.getString("Type"), rs.getInt("idaccess"),rs.getString("link"));
                list.add(acc);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
    public static ArrayList<AccountData> weekPass(){
        String sql = "Select * from data where idaccess="+Account.getIdAccess();
        ArrayList<AccountData> list = new ArrayList<>();
        try(
                Connection connection = ConnectionDB.getConnection();
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sql);
        ){
            String passReg = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            while (rs.next()){
                if(!rs.getString("Password").trim().matches(passReg)){
                    AccountData acc = new AccountData(rs.getString("Username"),rs.getString("Password"),
                            rs.getString("modified"),rs.getString("Type"), rs.getInt("idaccess"),rs.getString("link"));
                    list.add(acc);
                }
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

}
