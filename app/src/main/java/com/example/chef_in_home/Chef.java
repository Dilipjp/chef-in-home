package com.example.chef_in_home;

public class Chef {
    private String id;
    private String name;
    private String specialty;
    private boolean available;
    private String contact;
    private String experience;
    private double rating;
    private String bio;

    public Chef() {
        // Default constructor required for Firebase
    }

    public Chef(String id, String name, String specialty, boolean available, String contact, String experience, double rating, String bio) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.available = available;
        this.contact = contact;
        this.experience = experience;
        this.rating = rating;
        this.bio = bio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
