package com.google.sites.medcare.BloodBank;

public class BloodBankList {
    private String Name;
    private String Address;
    private String Phone;
    private int ABneg;
    private int ABpos;
    private int Apos;
    private int Aneg;
    private int Bpos;
    private int Bneg;
    private int Opos;
    private int Oneg;

    private String Link;
    public BloodBankList(){

    }

    public BloodBankList(String name, String address, String link, String phone, int ABneg, int ABpos, int apos, int aneg, int bpos, int bneg, int opos, int oneg) {
        Name = name;
        this.Address = address;
        this.Link = link;
        this.ABneg = ABneg;
        this.ABpos = ABpos;
        this.Phone=phone;
        this.Apos = apos;
        this.Aneg = aneg;
        this.Bpos = bpos;
        this.Bneg = bneg;
        this.Opos = opos;
        this.Oneg = oneg;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getABneg() {
        return ABneg;
    }

    public void setABneg(int ABneg) {
        this.ABneg = ABneg;
    }

    public int getABpos() {
        return ABpos;
    }

    public void setABpos(int ABpos) {
        this.ABpos = ABpos;
    }

    public int getApos() {
        return Apos;
    }

    public void setApos(int apos) {
        Apos = apos;
    }

    public int getAneg() {
        return Aneg;
    }

    public void setAneg(int aneg) {
        Aneg = aneg;
    }

    public int getBpos() {
        return Bpos;
    }

    public void setBpos(int bpos) {
        Bpos = bpos;
    }

    public int getBneg() {
        return Bneg;
    }

    public void setBneg(int bneg) {
        Bneg = bneg;
    }

    public int getOpos() {
        return Opos;
    }

    public void setOpos(int opos) {
        Opos = opos;
    }

    public int getOneg() {
        return Oneg;
    }

    public void setOneg(int oneg) {
        Oneg = oneg;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}
