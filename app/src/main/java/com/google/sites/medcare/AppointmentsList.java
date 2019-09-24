package com.google.sites.medcare;

public class AppointmentsList {
    private String hospName;
    private String specialists;
    private String date;

    public AppointmentsList() {
    }

    public AppointmentsList(String hospName, String specialists, String date) {
        this.hospName = hospName;
        this.specialists = specialists;
        this.date = date;
    }

    public String getHospName() {
        return hospName;
    }

    public void setHospName(String hospName) {
        this.hospName = hospName;
    }

    public String getSpecialists() {
        return specialists;
    }

    public void setSpecialists(String specialists) {
        this.specialists = specialists;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
