package com.Gateway_Service.Gateway_Service.dataclass.parse;

public class ParseUploadedSocialDataRequest {
    private String filename;
    private String textCol;
    private String locCol;
    private String interactionsCol;
    private String dateCol;

    public ParseUploadedSocialDataRequest() {

    }

    public ParseUploadedSocialDataRequest(String filename, String textCol, String locCol, String interactionsCol, String dateCol) {
        this.filename = filename;
        this.textCol = textCol;
        this.locCol = locCol;
        this.interactionsCol = interactionsCol;
        this.dateCol = dateCol;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTextCol() {
        return textCol;
    }

    public void setTextCol(String textCol) {
        this.textCol = textCol;
    }

    public String getLocCol() {
        return locCol;
    }

    public void setLocCol(String locCol) {
        this.locCol = locCol;
    }

    public String getInteractionsCol() {
        return interactionsCol;
    }

    public void setInteractionsCol(String interactionsCol) {
        this.interactionsCol = interactionsCol;
    }

    public String getDateCol() {
        return dateCol;
    }

    public void setDateCol(String dateCol) {
        this.dateCol = dateCol;
    }
}
