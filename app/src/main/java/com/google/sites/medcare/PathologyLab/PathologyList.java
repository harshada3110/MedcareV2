package com.google.sites.medcare.PathologyLab;

public class PathologyList {
    private String Address;
    private String Loc;

    private String Location;
    private String Name;
    private String Phone;
    private String Facilities;

    public PathologyList(){

    }

    public PathologyList(String address, String loc, String location, String name, String phone, String facilities) {
        Address = address;
        Loc = loc;
        Location = location;
        Name = name;
        Phone = phone;
        Facilities = facilities;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLoc() {
        return Loc;
    }

    public void setLoc(String loc) {
        Loc = loc;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getFacilities() {
        return Facilities;
    }

    public void setFacilities(String facilities) {
        Facilities = facilities;
    }
}
