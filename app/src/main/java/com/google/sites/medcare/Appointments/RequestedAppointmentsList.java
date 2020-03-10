package com.google.sites.medcare.Appointments;

public class RequestedAppointmentsList {
    private String HospitalName;
    private String Specialist;
    private String Date;
    private String Time;
    private String Prescription;
    private String Comments;
    private String Location;
    private String Email;
    private int Approval;
    private int avisited;


    public RequestedAppointmentsList() {
    }

    public RequestedAppointmentsList(String hospitalName, String specialist, String date, String time, String prescription, String comments, String location, String email, int approval, int avisited) {
        HospitalName = hospitalName;
        Specialist = specialist;
        Date = date;
        Time = time;
        Prescription = prescription;
        Comments = comments;
        Location = location;
        Approval = approval;
        Email = email;
        this.avisited = avisited;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getSpecialist() {
        return Specialist;
    }

    public void setSpecialist(String specialist) {
        Specialist = specialist;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
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

    public int getApproval() {
        return Approval;
    }

    public void setApproval(int approval) {
        Approval = approval;
    }

    public int getAvisited() {
        return avisited;
    }

    public void setAvisited(int avisited) {
        this.avisited = avisited;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
