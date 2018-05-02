package itp341.shah.tanay.smile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by tanay on 4/21/2018.
 */

public class Database extends Thread {
    private String name;
    private String username;
    private String pwd;
    private String age;
    private String classNum;
    private String race;
    private String gender;
    private String fNumber;
    private String mNumber;
    private String tNumber;
    private String imgUrl;

    public Database(String name,String username,String pwd, String age, String classNum, String race, String gender, String fNumber, String mNumber, String tNumber,String imgUrl) {
        this.username = username;
        this.pwd = pwd;
        this.name = name;
        this.age = age;
        this.classNum = classNum;
        this.race = race;
        this.gender = gender;
        this.fNumber = fNumber;
        this.mNumber = mNumber;
        this.tNumber = tNumber;
        this.imgUrl = imgUrl;
    }

    public void run(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.43.128:3306/smile?user=root&password=tShah0713!&useSSL=false");

            PreparedStatement ps = null;
            ps = conn.prepareStatement("INSERT INTO Users(userName,pwd,fullName,age,class,gender,race,fatherNum,motherNum,teacherNum,imgUrl) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1,username);
            ps.setString(2,pwd);
            ps.setString(3,name);
            ps.setString(4,age);
            ps.setString(5,classNum);
            ps.setString(6,gender);
            ps.setString(7,race);
            ps.setString(8,fNumber);
            ps.setString(9,mNumber);
            ps.setString(10,tNumber);
            ps.setString(11,imgUrl);
            int x = ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
