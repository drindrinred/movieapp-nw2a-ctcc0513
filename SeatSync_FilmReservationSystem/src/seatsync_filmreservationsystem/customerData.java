/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seatsync_filmreservationsystem;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Iris Jewel
 */
public class customerData {
    
    private int id;
    private String type;
    private String title;

    private double total;
    private Date date;
    private Time time;
    
    public customerData(Integer id, String type, String title, double total, Date date, Time time){
        
        this.id = id;
        this.type = type;
        this.title = title;
        
        this.total = total;
        this.date = date;
        this.time = time;
    }

    
    public Integer getId(){
        return id;
    }
    public String getType(){
        return type;
    }
    public String getTitle(){
        return title;
    }

    
    public double getTotal(){
        return total;
    }
    public Date getDate(){
        return date;
    }
    public Time getTime(){
        return time;
    }
    
}
