package com.google.sites.medcare.Hospital;

public class BookAppointmentList {
    private String Name;
    private String Number;
    private String Location;
    private int Approval;
    private String Comments;
    private String Date;
    private String HospitalName;
    private String Email;
    private String Specialist;
    private String Time;
    private String Prescription;
    private int Status;
    private int avisited;

    public BookAppointmentList(){

    }

    public BookAppointmentList(String name, String number, String location, int approval, String comments, String date, String hospitalName, String email, String specialist, String time, String prescription, int status, int avisited) {
        Name = name;
        Number = number;
        Location = location;
        Approval = approval;
        Comments = comments;
        Date = date;
        HospitalName = hospitalName;
        Email = email;
        Specialist = specialist;
        Time = time;
        Prescription = prescription;
        Status = status;
        this.avisited = avisited;
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

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSpecialist() {
        return Specialist;
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

    public String getPrescription() {
        return Prescription;
    }

    public void setPrescription(String prescription) {
        Prescription = prescription;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getAvisited() {
        return avisited;
    }

    public void setAvisited(int avisited) {
        this.avisited = avisited;
    }
}
