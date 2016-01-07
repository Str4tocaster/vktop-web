package project;

import java.io.Serializable;

/**
 * Created by valer on 06.01.2016.
 */
public class FilterTop implements Serializable {
    private int idUser;
    private int sex;
    private int age;
    private int typeTop;
    private int size;


    public FilterTop(int idUser, int sex, int age, int size, int typeTop) {
        this.idUser = idUser;
        this.sex = sex;
        this.age = age;
        this.size = size;
        this.typeTop = typeTop;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public int getTypeTop() {
        return typeTop;
    }

    public int getSize() {
        return size;
    }


}
