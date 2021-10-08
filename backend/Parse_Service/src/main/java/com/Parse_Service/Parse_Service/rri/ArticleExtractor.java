package com.Parse_Service.Parse_Service.rri;

import org.json.JSONObject;

public class ArticleExtractor {
    /**
     * This method is used to extract the title of a given article.
     * @param jsonData This is the JSON string of a particular article
     * @return This is the string containing the title of a given article
     */
    public String getTitle(String jsonData) {
        JSONObject obj = new JSONObject(jsonData);
        return obj.getString("title");
    }

    /**
     * This method is used to extract the description of a given article.
     * @param jsonData This is the JSON string of a particular article
     * @return This is the string containing the description of a given article
     */
    public String getDescription(String jsonData) {
        JSONObject obj = new JSONObject(jsonData);
        return obj.getString("description");
    }

    /**
     * This method is used to extract the content of a given article.
     * @param jsonData This is the JSON string of a particular article
     * @return This is the string containing the content of a given article
     */
    public String getContent(String jsonData) {
        JSONObject obj = new JSONObject(jsonData);
        return obj.getString("content");
    }

    /**
     * This method is used to extract the date of a given article.
     * @param jsonData This is the JSON string of a particular article
     * @return This is the string containing the date of a given article
     */
    public String getDate(String jsonData) {
        JSONObject obj = new JSONObject(jsonData);
        String[] dateTimeInfo = obj.getString("publishedAt").split("T");
        return dateTimeInfo[0];
    }
}
