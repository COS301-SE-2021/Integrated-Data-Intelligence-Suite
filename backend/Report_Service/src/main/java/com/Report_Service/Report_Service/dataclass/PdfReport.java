package com.Report_Service.Report_Service.dataclass;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name = "Reports")
@Table(name = "Reports")
public class PdfReport {

    @Id
    @GeneratedValue(
            generator = "report_sequence"
    )
    @GenericGenerator(
            name = "report_sequence",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    UUID ID;

    byte[] pdf;

    public PdfReport(){

    }

    public PdfReport(byte[] pdf){
        this.pdf = pdf;
    }

    void setPdf(byte[] pdf){
        this.pdf = pdf;
    }


}
