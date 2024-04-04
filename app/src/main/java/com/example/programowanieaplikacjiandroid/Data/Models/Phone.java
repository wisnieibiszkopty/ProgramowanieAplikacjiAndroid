package com.example.programowanieaplikacjiandroid.Data.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "phone")
public class Phone {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "producer")
    private String producer;
    @ColumnInfo(name = "model")
    private String model;
    @ColumnInfo(name = "version")
    private String version;
    @ColumnInfo(name = "website")
    private String website;

    public Phone(String producer, String model, String version, String website) {
        this.producer = producer;
        this.model = model;
        this.version = version;
        this.website = website;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", producer='" + producer + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    public String toStringAllArgs() {
        return "Phone{" +
                "id=" + id +
                ", producer='" + producer + '\'' +
                ", model='" + model + '\'' +
                ", version='" + version + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
