package com.Parse_Service.Parse_Service.request;


public class AddSocialMediaPropertiesRequest {
    private String name;

    private String interactionsProp;

    private String locationProp;

    private String dateProp;

    private String textProp;

    private String collectionProp;

    public AddSocialMediaPropertiesRequest() {

    }

    public AddSocialMediaPropertiesRequest(String name, String interactionsProp, String locationProp, String dateProp, String textProp, String collectionProp) {
        this.name = name;
        this.interactionsProp = interactionsProp;
        this.locationProp = locationProp;
        this.dateProp = dateProp;
        this.textProp = textProp;
        this.collectionProp = collectionProp;
    }

    public String getInteractionsProp() {
        return interactionsProp;
    }

    public void setInteractionsProp(String interactionsProp) {
        this.interactionsProp = interactionsProp;
    }

    public String getLocationProp() {
        return locationProp;
    }

    public void setLocationProp(String locationProp) {
        this.locationProp = locationProp;
    }

    public String getDateProp() {
        return dateProp;
    }

    public void setDateProp(String dateProp) {
        this.dateProp = dateProp;
    }

    public String getTextProp() {
        return textProp;
    }

    public void setTextProp(String textProp) {
        this.textProp = textProp;
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
