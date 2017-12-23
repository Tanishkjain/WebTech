package com.example.windows.webtech.model;

/**
 * Created by Windows on 12/22/2017.
 */

public class BrandData {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDescreption() {
        return descreption;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public BrandData(String id, String brandName, String descreption, String createdAt) {
        this.id = id;
        this.brandName = brandName;
        this.descreption = descreption;
        this.createdAt = createdAt;
    }

    public BrandData( String brandName, String descreption, String createdAt) {

        this.brandName = brandName;
        this.descreption = descreption;
        this.createdAt = createdAt;
    }

    public BrandData(){

    }
    String id; //primaryKey
    String  brandName;
    String descreption;
    String createdAt;

}
