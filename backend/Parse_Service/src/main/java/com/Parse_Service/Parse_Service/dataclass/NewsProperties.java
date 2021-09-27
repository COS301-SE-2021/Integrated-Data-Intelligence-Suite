package com.Parse_Service.Parse_Service.dataclass;

import javax.persistence.*;

@Entity(name = "news_properties")
@Table(name = "news_properties")
public class NewsProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * This attribute is the name of news source.
     */
    private String name;

    /**
     * This attribute is the name of the json property to extract
     * the title for each news article.
     */
    private String titleProp;

    /**
     * This attribute is the name of the json property to extract
     * the description for each news article.
     */
    private String descriptionProp;

    /**
     * This attribute is the name of the json property to extract
     * the content for each news article.
     */
    private String contentProp;

    /**
     * This attribute is the name of the json property to extract
     * the date when each article was published.
     */
    private String dateProp;

    /**
     * This attribute is the name of the json property to extract
     * each post from the retrieved json response.
     */
    private String collectionProp;

    public NewsProperties() {

    }

    public NewsProperties(String name, String titleProp, String descriptionProp, String contentProp, String dateProp, String collectionProp) {
        this.name = name;
        this.titleProp = titleProp;
        this.descriptionProp = descriptionProp;
        this.contentProp = contentProp;
        this.dateProp = dateProp;
        this.collectionProp = collectionProp;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
