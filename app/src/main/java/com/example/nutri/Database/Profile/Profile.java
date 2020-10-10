package com.example.nutri.Database.Profile;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Profile_table")
public class Profile {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private int age;

    private String sex;

    private int height;

    private int weight;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public Profile(String name, int age, String sex, int height, int weight) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
    }
}
