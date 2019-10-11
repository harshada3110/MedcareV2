package com.google.sites.medcare.Hospital;

public class HospitalList {
    private String Location;
    private String Photo;
    private String Address;
    private String Name;
    private String About;
    private String Contact;
    private String Email;
    private String Facilities;
    private String Loc;
    private String Website;

    public HospitalList(String location, String photo, String address, String name, String about, String contact, String email, String facilities, String loc, String website) {
        Location = location;
        Photo = photo;
        Address = address;
        Name = name;
        About = about;
        Contact = contact;
        Email = email;
        Facilities = facilities;
        Loc = loc;
        Website = website;
    }

    public HospitalList(){

    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        this.Photo = photo;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFacilities() {
        return Facilities;
    }

    public void setFacilities(String facilities) {
        Facilities = facilities;
    }

    public String getLoc() {
        return Loc;
    }

    public void setLoc(String loc) {
        Loc = loc;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }
}
