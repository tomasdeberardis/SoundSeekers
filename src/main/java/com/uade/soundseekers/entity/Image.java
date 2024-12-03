package com.uade.soundseekers.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Image")
public class Image {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String imageName;


    @Getter
    private String description;

    @Setter
    @Getter
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name="event_id")
    private Event event;

    public Image() {

    }

    public Image(byte[] imageData) {
        super();
        this.imageData = imageData;
    }

    public String imageName() {
        return imageName;
    }

    public void imageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "Image [id=" + id + ", imageName=" + imageName  + ", description=" + description
                + ", Event=" + event + "]";
    }

}