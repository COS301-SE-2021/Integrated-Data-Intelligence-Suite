package com.Parse_Service.Parse_Service.request;

public class ParseUploadedNewsDataRequest {
    private String filename;
    private String contentCol;
    private String titleCol;
    private String descCol;
    private String dateCol;

    public ParseUploadedNewsDataRequest() {

    }

    public ParseUploadedNewsDataRequest(String filename, String contentCol, String titleCol, String descCol, String dateCol) {
        this.filename = filename;
        this.contentCol = contentCol;
        this.titleCol = titleCol;
        this.descCol = descCol;
        this.dateCol = dateCol;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentCol() {
        return contentCol;
    }

    public void setContentCol(String contentCol) {
        this.contentCol = contentCol;
    }

    public String getTitleCol() {
        return titleCol;
    }

    public void setTitleCol(String titleCol) {
        this.titleCol = titleCol;
    }

    public String getDescCol() {
        return descCol;
    }

    public void setDescCol(String descCol) {
        this.descCol = descCol;
    }

    public String getDateCol() {
        return dateCol;
    }

    public void setDateCol(String dateCol) {
        this.dateCol = dateCol;
    }
}
