package com.Report_Service.Report_Service.dataclass;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name = "reports")
@Table(name = "reports")
public class PdfReport {

    @Id
    @GeneratedValue(
            generator = "report_sequence"
    )
    @GenericGenerator(
            name = "report_sequence",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    UUID id;
    byte[] pdf;

    String name;

    String date;

    public PdfReport(){

    }

    public PdfReport(byte[] pdf, String name, String date){
        this.pdf = pdf;
        this.name = name;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public byte[] getPdf(){
        return this.pdf;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    void setPdf(byte[] pdf){
        this.pdf = pdf;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }
}
