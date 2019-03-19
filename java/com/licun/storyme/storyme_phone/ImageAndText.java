package com.licun.storyme.storyme_phone;

import java.io.Serializable;

public class ImageAndText  implements Serializable {
    private String imageUrl;
    private String text;
    private String title;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setText(String text) {
        this.text = text;
    }

    private boolean hasQuestion=false;
    public ImageAndText(String imageUrl, String text,boolean hasQuestion) {
        this.imageUrl = imageUrl;
        this.text = text;
        this.hasQuestion=hasQuestion;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHasQuestion() {
        return hasQuestion;
    }

    public void setHasQuestion(boolean hasQuestion) {
        this.hasQuestion = hasQuestion;
    }


    public String getImageUrl() {
        return imageUrl;
    }
    public String getText() {
        return text;
    }
}

