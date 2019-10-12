package com.google.sites.medcare.Pharmacy;

public class PharmacyList {
    private String Address;
    private String Loc;
    private String Location;
    private String Name;
    private String Phone;
    /*
    private String Aspirin;
    private String Benadryl;
    private String Ibuprofen;
    private String Lisinopril;
    private String Morphine;
    private String Paracetamol;
    */

    public PharmacyList(){

    }

    public PharmacyList(String address, String loc, String location, String name, String phone) {
        Address = address;
        Loc = loc;
        Location = location;
        Name = name;
        Phone = phone;
    }

    public String getPhone() { return Phone; }

    public void setPhone(String phone) { Phone = phone; }


    public String getLoc() { return Loc;}

    public void setLoc(String loc) {
        Loc = loc;
    }


    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
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
}
