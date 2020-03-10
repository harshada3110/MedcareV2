package com.google.sites.medcare.PatientHistory;

public class PrescriptionList {
    String Dose;
    String Duration;
    String Name;

    public PrescriptionList(){

    }

    public PrescriptionList(String dose, String duration, String name) {
        Dose = dose;
        Duration = duration;
        Name = name;
    }

    public String getDose() {
        return Dose;
    }

    public void setDose(String dose) {
        Dose = dose;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
