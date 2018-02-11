package com.vulgivagus.androidapp.learningfragmentsapex.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Nika on 11.02.2018.
 */

@DatabaseTable(tableName = "authors")
public class Author {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String FName;
    @DatabaseField
    private String LName;

    public Author(){

    }
    public Author(int passedId, String firstName, String lastName){
        id=passedId;
        FName = firstName;
        LName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFName() {
        return FName;
    }

    public String getLName() {
        return LName;
    }

    public void setId(int newId) {
        id = newId;
    }

    public void setFName(String firstName) {
        FName = firstName;
    }

    public void setLName(String lastName) {
        LName = lastName;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("id=").append(id);
        sb.append(", ").append("first name=").append(FName);
        sb.append(", ").append("last name=").append(LName);
        return sb.toString();
    }
}

