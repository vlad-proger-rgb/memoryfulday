package com.vladitproger.memoryfulday.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "memoryful")
public class Day {

    @Id
    private Long timestamp;
    private String brief_desc, full_desc;
    private int steps;
    private String main_image;
    private String all_images;

    public Day(Long timestamp) {
        this.timestamp = timestamp;
        this.main_image = "20230821_120812.jpg";
        this.brief_desc = "No brief description";
        this.steps = 0;
        this.full_desc = "No content";
    }

    public Day(Long timestamp, String brief_desc, String full_desc, int steps, String main_image) {
        this.timestamp = timestamp;
        this.brief_desc = brief_desc;
        this.full_desc = full_desc;
        this.steps = steps;
        this.main_image = main_image;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBrief_desc() {
        return brief_desc;
    }

    public void setBrief_desc(String brief_desc) {
        this.brief_desc = brief_desc;
    }

    public String getFull_desc() {
        return full_desc;
    }

    public void setFull_desc(String full_desc) {
        this.full_desc = full_desc;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getMain_image() {
        return main_image;
    }

    public void setMain_image(String main_image) {
        this.main_image = main_image;
    }

    public String getAll_images() {
        return all_images;
    }

    public void setAll_images(String all_images) {
        this.all_images = all_images;
    }
}
