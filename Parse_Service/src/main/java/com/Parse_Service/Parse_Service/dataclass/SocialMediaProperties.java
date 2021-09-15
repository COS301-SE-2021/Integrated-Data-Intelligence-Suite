package com.Parse_Service.Parse_Service.dataclass;

import javax.persistence.*;

@Entity(name = "social_media_properties")
@Table
public class SocialMediaProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * This attribute is the name of the json property to extract
     * the interactions for each social media post.
     */
    private String interactionsProp;

    /**
     * This attribute is the name of the json property to extract
     * the location of each social media post.
     */
    private String locationProp;

    /**
     * This attribute is the name of the json property to extract
     * the date of the social media post i.e when the post was
     * published.
     */
    private String dateProp;

    /**
     * This attribute is the name of the json property to extract
     * the text for each social media post.
     */
    private String textProp;

    /**
     * This attribute is the name of the json property to extract
     * each post from the retrieved json response.
     */
    private String collectionProp;

    public SocialMediaProperties() {

    }

    public SocialMediaProperties(String interactionsProp, String locationProp, String dateProp, String textProp, String collectionProp) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
