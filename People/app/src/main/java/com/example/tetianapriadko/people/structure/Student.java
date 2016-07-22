package com.example.tetianapriadko.people.structure;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class Student {
    private String surname;
    private String email;
    private String name;
    private String speciality;
    private String placeOfStudy;
    private String objectId;
    private java.util.Date updated;
    private java.util.Date created;
    private String phoneNumber;
    private String ownerId;
    private String avatarUrl;
    private GeoPoint GeoPoint;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getPlaceOfStudy() {
        return placeOfStudy;
    }

    public void setPlaceOfStudy(String placeOfStudy) {
        this.placeOfStudy = placeOfStudy;
    }

    public String getObjectId() {
        return objectId;
    }

    public java.util.Date getUpdated() {
        return updated;
    }

    public java.util.Date getCreated() {
        return created;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public Student save() {
        return Backendless.Data.of(Student.class).save(this);
    }

    public void saveAsync(AsyncCallback<Student> callback) {
        Backendless.Data.of(Student.class).save(this, callback);
    }

    public Long remove() {
        return Backendless.Data.of(Student.class).remove(this);
    }

    public void removeAsync(AsyncCallback<Long> callback) {
        Backendless.Data.of(Student.class).remove(this, callback);
    }

    public static Student findById(String id) {
        return Backendless.Data.of(Student.class).findById(id);
    }

    public static void findByIdAsync(String id, AsyncCallback<Student> callback) {
        Backendless.Data.of(Student.class).findById(id, callback);
    }

    public static Student findFirst() {
        return Backendless.Data.of(Student.class).findFirst();
    }

    public static void findFirstAsync(AsyncCallback<Student> callback) {
        Backendless.Data.of(Student.class).findFirst(callback);
    }

    public static Student findLast() {
        return Backendless.Data.of(Student.class).findLast();
    }

    public static void findLastAsync(AsyncCallback<Student> callback) {
        Backendless.Data.of(Student.class).findLast(callback);
    }

    public static BackendlessCollection<Student> find(BackendlessDataQuery query) {
        return Backendless.Data.of(Student.class).find(query);
    }

    public static void findAsync(BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Student>> callback) {
        Backendless.Data.of(Student.class).find(query, callback);
    }
}