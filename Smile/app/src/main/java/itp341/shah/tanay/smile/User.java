package itp341.shah.tanay.smile;

import java.io.Serializable;

/**
 * Created by tanay on 4/18/2018.
 */

public class User implements Serializable{
    private String Name;
    private String age;
    private String classNum;
    private String race;
    private String gender;
    private String fnum;
    private String mnum;
    private String tnum;
    private String imgUrl;

    public User(String name, String age, String classNum, String race,String gender, String fnum, String mnum, String tnum, String imgUrl) {
        Name = name;
        this.age = age;
        this.gender = gender;
        this.classNum = classNum;
        this.race = race;
        this.fnum = fnum;
        this.mnum = mnum;
        this.tnum = tnum;
        this.imgUrl = imgUrl;
    }

    public String getGender() {return gender;}

    public String getName() {
        return Name;
    }

    public String getAge() {
        return age;
    }

    public String getClassNum() {
        return classNum;
    }

    public String getRace() {
        return race;
    }

    public String getFnum() {return fnum;}

    public String getMnum() {
        return mnum;
    }

    public String getTnum() {
        return tnum;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
