package com.nema.rajshree.ui;

public class People {
    String name;
    String contact;
    String address;
    String aadhaar;
    String gender;


     People(String name,String contact,String address,String aadhaar,String gender){
        this.name=name;
        this.contact=contact;
        this.address=address;
        this.aadhaar=aadhaar;
        this.gender=gender;

    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public String getGender() {
        return gender;
    }
}