package com.google.sites.medcare.PatientHistory;

import com.google.firebase.database.Exclude;

public class AppointmentsList {
    private String HospitalName;
    private String Specialist;
    private String Date;
    private String Time;
    private String Comments;
    private String Location;

    public AppointmentsList() {
    }

    public AppointmentsList(String HospitalName, String Specialist, String Date, String Time, String Comments, String Location) {
        this.HospitalName = HospitalName;
        this.Specialist = Specialist;
        this.Date = Date;
        this.Time = Time;
        this.Comments = Comments;
        this.Location = Location;
    }

    @Exclude
    String key;

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String HospitalName) {
        this.HospitalName = HospitalName;
    }

    public String getSpecialist() {
        return Specialist;
    }

    public void setSpecialists(String Specialist) {
        this.Specialist = Specialist;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setSpecialist(String specialist) {
        Specialist = specialist;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
