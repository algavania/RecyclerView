package com.apps.recyclerview;

public class Contact {
    String name;
    String image;
    String link;

    public Contact() {
    }

    public Contact (String name, String image, String link) {
        this.name = name;
        this.image = image;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
