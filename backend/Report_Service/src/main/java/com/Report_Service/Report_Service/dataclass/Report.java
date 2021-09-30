package com.Report_Service.Report_Service.dataclass;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.UUID;


public class Report {

    byte[] pdf;
    String DateTime;
    String time;

    String TrendSummary = "";
    ArrayList<ArrayList> TrendData;

    String AnomalySummary = "";
    ArrayList<ArrayList> AnomalyData;

    String PatternandRelationshipSummary = "";
    ArrayList<ArrayList> PatternandRelationshipData;

    String TextualAnalysisSummary = "";
    ArrayList<ArrayList> TextualAnalysisData;

    public Report(){

    }

    /**get**/

    public byte[] getPdf(){
        return pdf;
    }

    public String getDateTime(){
        return this.DateTime;
    }

    public String getTime(){
        return this.time;
    }

    public String getTrendSummary(){
        return this.TrendSummary;
    }

    public ArrayList<ArrayList> getTrendData(){
        return this.TrendData;
    }

    public String getAnomalySummary(){
        return this.AnomalySummary;
    }

    public ArrayList<ArrayList> getAnomalyData(){
        return this.AnomalyData;
    }

    public String getPatternandRelationshipSummary(){
        return this.PatternandRelationshipSummary;
    }

    public ArrayList<ArrayList> getPatternandRelationshipData(){
        return this.PatternandRelationshipData;
    }

    public String getTextualAnalysisSummary(){
        return this.TextualAnalysisSummary;
    }

    public ArrayList<ArrayList> getTextualAnalysisData(){
        return this.TextualAnalysisData;
    }

    /**set**/

    public void setPdf(byte[] pdf){
        this.pdf = pdf;
    }

    public void setDateTime(String DateTime){
        this.DateTime = DateTime;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setTrendSummary(String TrendSummary){
        this.TrendSummary = TrendSummary;
    }

    public void setTrendData(ArrayList<ArrayList> TrendData){
        this.TrendData = TrendData;
    }

    public void setAnomalySummary(String AnomalySummar){
        this.AnomalySummary = AnomalySummar;
    }

    public void setAnomalyData(ArrayList<ArrayList> AnomalyData){
        this.AnomalyData = AnomalyData;
    }

    public void setPatternandRelationshipSummary(String PatternandRelationshipSummary){
        this.PatternandRelationshipSummary = PatternandRelationshipSummary;
    }

    public void setPatternandRelationshipData(ArrayList<ArrayList> PatternandRelationshipData){
        this.PatternandRelationshipData = PatternandRelationshipData;
    }

    public void setTextualAnalysisSummary(String TextualAnalysisSummary){
        this.TextualAnalysisSummary = TextualAnalysisSummary;
    }

    public void setTextualAnalysisData(ArrayList<ArrayList> TextualAnalysisData){
        this.TextualAnalysisData = TextualAnalysisData;
    }



}
