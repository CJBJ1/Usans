package com.example.usans;

public class Facility {

    private String id;
    private String name;
    private String address;
    private String[] photo;
    private String information;
    private String lat;
    private String lng;
    private String likes;

    Facility(String id, String name, String address, String[] photo, String information, String lat, String lng, String likes){
        setId(id);
        setAddress(address);
        setPhoto(photo);
        setInformation(information);
        setLat(lat);
        setLng(lng);
        setLikes(likes);
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getPhoto() {
        return photo;
    }

    public void setPhoto(String[] photo) {
        this.photo = photo;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}

