package com.example.tetianapriadko.people.structure;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class Teacher {
    private String placeofWork;
    private String email;
    private java.util.Date updated;
    private java.util.Date created;
    private String ownerId;
    private String surname;
    private String phoneNumber;
    private String speciality;
    private String objectId;
    private String name;
    private String avatarUrl;
    private GeoPoint GeoPoint;



    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPlaceofWork() {
        return placeofWork;
    }

    public void setPlaceofWork(String placeofWork) {
        this.placeofWork = placeofWork;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public java.util.Date getUpdated() {
        return updated;
    }

    public java.util.Date getCreated() {
        return created;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public GeoPoint getGeoPoint() {
        return GeoPoint;
    }

    public void setGeoPoint(GeoPoint GeoPoint) {
        this.GeoPoint = GeoPoint;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Teacher save() {
        return Backendless.Data.of(Teacher.class).save(this);
    }

    public void saveAsync(AsyncCallback<Teacher> callback) {
        Backendless.Data.of(Teacher.class).save(this, callback);
    }

    public Long remove() {
        return Backendless.Data.of(Teacher.class).remove(this);
    }

    public void removeAsync(AsyncCallback<Long> callback) {
        Backendless.Data.of(Teacher.class).remove(this, callback);
    }

    public static Teacher findById(String id) {
        return Backendless.Data.of(Teacher.class).findById(id);
    }

    public static void findByIdAsync(String id, AsyncCallback<Teacher> callback) {
        Backendless.Data.of(Teacher.class).findById(id, callback);
    }

    public static Teacher findFirst() {
        return Backendless.Data.of(Teacher.class).findFirst();
    }

    public static void findFirstAsync(AsyncCallback<Teacher> callback) {
        Backendless.Data.of(Teacher.class).findFirst(callback);
    }

    public static Teacher findLast() {
        return Backendless.Data.of(Teacher.class).findLast();
    }

    public static void findLastAsync(AsyncCallback<Teacher> callback) {
        Backendless.Data.of(Teacher.class).findLast(callback);
    }

    public static BackendlessCollection<Teacher> find(BackendlessDataQuery query) {
        return Backendless.Data.of(Teacher.class).find(query);
    }


    public static void findAsync(BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Teacher>> callback) {
        Backendless.Data.of(Teacher.class).find(query, callback);
    }
}