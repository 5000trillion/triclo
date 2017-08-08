package org.t_robop.triclo.triclo;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tomi on 2017/08/07.
 */

public class ClothesDb extends RealmObject {
    private String name;
    private int id;
    private int genre;
    private int season;
    private String color;
    private int year;
    private int month;
    private int day;
    private String memo;


    @PrimaryKey
    private int sessionId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGenre(){
        return genre;
    }

    public void setGenre(int genre){
        this.genre = genre;
    }

    public int getSeason(){
        return season;
    }

    public void setSeason(){
        this.season = season;
    }

    public String getColor() {
        return color;
    }

    public void setColor() {
        this.color = color;
    }

    public int getYear(){
        return year;
    }

    public void setYear(){
        this.year = year;
    }

    public int getMonth(){
        return month;
    }

    public void setMonth(){
        this.month = month;
    }

    public int getDay(){
        return day;
    }

    public void setDay(){
        this.day = day;
    }

    public String getMemo(){
        return memo;
    }

    public void setMemo(){
        this.memo = memo;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }


}