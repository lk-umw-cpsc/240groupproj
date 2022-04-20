package code.schedule;

import java.time.LocalDate;
import java.time.Period;
/*
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.time.Instant;
import java.text.SimpleDateFormat;
import java.util.MissingFormatArgumentException;
import java.nio.charset.MalformedInputException;
*/

import java.util.ArrayList;
import java.net.MalformedURLException;
import java.text.ParseException;
import code.schedule.scheduleTools.Gift;


public class Birthday{
    //Java file for containing birthdays
    private String name;
    private LocalDate dob;
    private ArrayList<Gift> gifts; // = new ArrayList<String>();
    private int age;
    

    public Birthday(String name, LocalDate date) {
        this.name = name;
        /*SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date d = formatter.parse(date);
        Instant i = d.toInstant();
        ZonedDateTime zone = i.atZone(ZoneId.systemDefault());
        dob = zone.toLocalDate();
        */
        dob = date;
        Period p = Period.between(dob, LocalDate.now());
        age = p.getYears();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String s) {
        this.name = s;
    }

    public String getDOB() {
        return dob.toString();
    }

    public void setDOB(String d) {
        dob = LocalDate.parse(d);
        Period p = Period.between(dob, LocalDate.now());
        age = p.getYears();
    }

    public int getAge() {
        return this.age;
    }

    public void updateAge() {
        LocalDate today = LocalDate.now();
        if (today.getDayofMonth() == dob.getDayOfMonth())
        Period p = Period.between(dob, LocalDate.now());
        age = p.getYears();
    }

    public void addGift(String n) {
        Gift g = new Gift(n);
        gifts.add(g);
    }
    public void addGifts(String n, String url){
        try {
            Gift g = new Gift(n, url);
            gifts.add(g);       
        } catch (MalformedURLException e) {
            System.out.println("URL error");
        }
    }
    
    /*
    @Override
    public String toString() {
        return this.name + " " + this.dob + " " + this.age;
    }
    */
}

