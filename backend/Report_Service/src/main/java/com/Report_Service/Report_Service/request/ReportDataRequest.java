package com.Report_Service.Report_Service.request;

import java.util.ArrayList;

public class ReportDataRequest {
    ArrayList<ArrayList> trendlist;
    ArrayList<ArrayList> Realationshiplist;
    ArrayList<ArrayList> Patternlist;
    ArrayList<String> Anomalylist;
    ArrayList<ArrayList> wordlist;

    public ReportDataRequest(ArrayList<ArrayList> trendlist,
            ArrayList<ArrayList> Realationshiplist,
            ArrayList<ArrayList> Patternlist,
            ArrayList<String> Anomalylist,
            ArrayList<ArrayList> wordlist){

        this.trendlist = trendlist;
        this.Realationshiplist= Realationshiplist;
        this.Patternlist= Patternlist;
        this.Anomalylist= Anomalylist;
        this.wordlist = wordlist;

    }
}
