package com.google.sites.medcare.Schemes;

public class Schemes {
    private String Name;
    private String RationColor;
    private String Category;
    private String About;
    private String Link;
    private String Helpline;

    public Schemes(){

    }

    public Schemes(String name, String rationColor, String category, String about, String link, String helpline) {
        Name = name;
        RationColor = rationColor;
        Category = category;
        About = about;
        Link = link;
        Helpline = helpline;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRationColor() {
        return RationColor;
    }

    public void setRationColor(String rationColor) {
        RationColor = rationColor;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getHelpline() {
        return Helpline;
    }

    public void setHelpline(String helpline) {
        Helpline = helpline;
    }

}
