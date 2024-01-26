/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seatsync_filmreservationsystem;

import java.sql.Date;

/**
 *
 * @author Iris Jewel
 */
public class moviesData {
    
    private Integer id;
    private String title;
    private String genre;
    private String duration;
    private String image;
    private Date date;
    
    public moviesData(Integer id, String title, String genre,String duration, String image, Date date){
    
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.image = image;
        this.date = date;
        
    }
    
    public Integer getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getGenre(){
        return genre;
    }
    public String getDuration(){
        return duration;
    }
    public String getImage(){
        return image;
    }
    public Date getDate(){
        return date;
    }
    
}
