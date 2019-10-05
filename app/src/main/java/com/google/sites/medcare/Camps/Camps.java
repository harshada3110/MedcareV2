package com.google.sites.medcare.Camps;

public class Camps {
    private String Type;
    private String Venue;
    private String Date;
    private String Description;
    private String Link;

    public Camps(){

    }

    public Camps(String Type, String Venue, String Date, String Description, String Link) {
        this.Type = Type;
        this.Venue = Venue;
        this.Date = Date;
        this.Description = Description;
        this.Link = Link;
    }


    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        Type = Type;
    }

    public String getVenue() {
        return Venue;
    }

    public void setVenue(String Venue) {
        Venue = Venue;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        Date = Date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        Description = Description;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}
