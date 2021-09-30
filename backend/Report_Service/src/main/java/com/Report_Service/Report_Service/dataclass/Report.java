package com.Report_Service.Report_Service.dataclass;

import java.util.ArrayList;

public class Report {
    public byte[] pdf;
    public String DateTime;
    public String time;

    public String TrendSummary = "";
    public ArrayList<ArrayList> TrendData;

    public String AnomalySummary = "";
    public ArrayList<ArrayList> AnomalyData;

    public String PatternandRelationshipSummary = "";
    public ArrayList<ArrayList> PatternandRelationshipData;

    public String TextualAnalysisSummary = "";
    public ArrayList<ArrayList> TextualAnalysisData;

    public Report(){

    }
}
