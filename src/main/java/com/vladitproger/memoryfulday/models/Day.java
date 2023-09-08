package com.vladitproger.memoryfulday.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(schema = "memoryfulday", name = "memoryful")
public class Day {

    @Id
    private Long timestamp;
    private String description, content;
    private int steps;
    private String mainImage;
//    private MultipartFile mainImage;

    private String allImages;

    public Day() {}

    public Day(Long timestamp) {
        this.timestamp = timestamp;
        this.mainImage = "NoPhoto.png";
        this.description = "No brief description";
        this.steps = 0;
        this.content = "No content";
    }

    public Day(Long timestamp, String mainImage, String description, String content, int steps) {
        this.timestamp = timestamp;
        this.mainImage = mainImage;
        this.description = description;
        this.content = content;
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Day{" +
                "timestamp=" + timestamp +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", steps=" + steps +
                ", mainImage='" + mainImage + '\'' +
                ", allImages='" + allImages + '\'' +
                '}';
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

//    public String getMainImagePath() {
//        return mainImagePath;
//    }

//    public void setMainImagePath(String mainImagePath) {
//        this.mainImagePath = mainImagePath;
//    }


    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getAllImages() {
        return allImages;
    }

    public void setAllImages(String allImages) {
        this.allImages = allImages;
    }

}
