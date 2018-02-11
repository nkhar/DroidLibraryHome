package com.vulgivagus.androidapp.learningfragmentsapex.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Nika on 11.02.2018.
 */

@DatabaseTable(tableName = "books")
public class Book {
    @DatabaseField(generatedId = true)
    private int bookId;
    @DatabaseField
    private String name;
    @DatabaseField
    private int authorId;
    @DatabaseField
    private int genreId;
    @DatabaseField
    private int Date;
    @DatabaseField
    private String lang;

    public Book() {

    }
}

