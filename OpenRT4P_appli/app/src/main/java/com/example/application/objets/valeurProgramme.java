package com.example.application.objets;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class valeurProgramme {
    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo

    public String tableSteps;

    @ColumnInfo

    public String acceleration;

    @ColumnInfo

    public String speed;

    @ColumnInfo
    public Boolean direction;

    @ColumnInfo
    public String timeBetweenPhotosNumber;

    @ColumnInfo

    public String camera_number;

    @ColumnInfo

    public String frame;


    @ColumnInfo
    public Boolean focusStacking;


    public String getId() {
        return id;
    }
}
