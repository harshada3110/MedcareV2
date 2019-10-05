package com.google.sites.medcare.Ambulance;

public class AmbulanceList {
    private String Location;
    private String Name;
    private String Number;


    public AmbulanceList(){

    }
    public AmbulanceList(String location, String name, String number) {
        Location = location;
        Name = name;
        Number = number;
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

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
