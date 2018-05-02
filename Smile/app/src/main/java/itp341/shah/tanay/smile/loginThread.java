package itp341.shah.tanay.smile;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by tanay on 5/1/2018.
 */

interface response{
    void callback(boolean isLoggedIn,User u);
}

public class loginThread extends Thread {
    response c;
    private String username;
    private String password;

    public loginThread(String username, String password,response c){
        Log.d("EORROR: " , username + "" +password);
        this.username = username;
        this.password = password;
        this.c = c;
    }


    public void run(){

        boolean isLoggedIn = false;
        User u =null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.43.128:3306/smile?user=root&password=tShah0713!&useSSL=false");

            PreparedStatement ps = null;
            ps = conn.prepareStatement("SELECT * FROM Users WHERE userName= ? and pwd = ?");
            ps.setString(1,username);
            ps.setString(2,password);
            Log.d("DEBUG",username + " " + password);
            ResultSet rs = ps.executeQuery();

            if(rs==null){
                isLoggedIn = false;
            }else{
                rs.next();
                if(rs.getString("userName") == null){
                    isLoggedIn = false;
                }else{
                    u = new User(rs.getString("fullName"),rs.getString("age"),rs.getString("class"),rs.getString("race"),rs.getString("gender"),rs.getString("fatherNum"),rs.getString("motherNum"),rs.getString("teacherNum"),rs.getString("imgUrl"));
                    isLoggedIn = true;
                }
            }
        }catch(SQLException e){
            Log.d("ERROR","SQL Exception " + e.getMessage());
        }catch (ClassNotFoundException e){
            Log.d("ERROR","class not found Exception " + e.getMessage());
        }finally {
            this.c.callback(isLoggedIn,u);
        }
    }
}


