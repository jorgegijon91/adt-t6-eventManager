package model;

import jdk.jfr.Event;

import java.util.ArrayList;
import java.util.List;

public class Venue {

    private long id;

    private String name;

    private String location;

    private String image;


    public Venue() {}

    public Venue( String name, String location, String image) {
        this.name = name;
        this.location = location;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", image='" + image + '\'' +
                "}";
    }
}
