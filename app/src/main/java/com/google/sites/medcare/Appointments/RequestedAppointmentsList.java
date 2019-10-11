package com.google.sites.medcare.Appointments;

public class RequestedAppointmentsList {
    private String HospitalName;
    private String Specialist;
    private String Date;
    private String Time;
    private String Prescription;
    private String Comments;
    private String Location;
    private int Approval;
    private int Visited;

    public RequestedAppointmentsList() {
    }

    public RequestedAppointmentsList(String HospitalName, String Specialist, String Date, String Time, String Prescription, String Comments, String Location, int Approval, int Visited) {
        this.HospitalName = HospitalName;
        this.Specialist = Specialist;
        this.Date = Date;
        this.Time = Time;
        this.Prescription = Prescription;
        this.Comments = Comments;
        this.Location = Location;
        this.Approval = Approval;
        this.Visited = Visited;
    }

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

    public String getPrescription() {
        return Prescription;
    }

    public void setPrescription(String prescription) {
        Prescription = prescription;
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

    public int getVisited() {
        return Visited;
    }

    public void setVisited(int visited) {
        Visited = visited;
    }
}
