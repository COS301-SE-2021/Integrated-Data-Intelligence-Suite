package com.Parse_Service.Parse_Service.request;

public class AddNewsPropertiesRequest {

    private String name;

    private String titleProp;

    private String descriptionProp;

    private String contentProp;

    private String dateProp;

    private String collectionProp;

    public AddNewsPropertiesRequest() {

    }

    public AddNewsPropertiesRequest(String name, String titleProp, String descriptionProp, String contentProp, String dateProp, String collectionProp) {
        this.name = name;
        this.titleProp = titleProp;
        this.descriptionProp = descriptionProp;
        this.contentProp = contentProp;
        this.dateProp = dateProp;
        this.collectionProp = collectionProp;

    }

    public String getTitleProp() {
        return titleProp;
    }

    public void setTitleProp(String titleProp) {
        this.titleProp = titleProp;
    }

    public String getDescriptionProp() {
        return descriptionProp;
    }

    public void setDescriptionProp(String descriptionProp) {
        this.descriptionProp = descriptionProp;
    }

    public String getContentProp() {
        return contentProp;
    }

    public void setContentProp(String contentProp) {
        this.contentProp = contentProp;
    }

    public String getDateProp() {
        return dateProp;
    }

    public void setDateProp(String dateProp) {
        this.dateProp = dateProp;
    }

    public String getCollectionProp() {
        return collectionProp;
    }

    public void setCollectionProp(String collectionProp) {
        this.collectionProp = collectionProp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
