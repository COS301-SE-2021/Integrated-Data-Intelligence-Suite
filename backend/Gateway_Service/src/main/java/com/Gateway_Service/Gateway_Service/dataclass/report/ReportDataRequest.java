package com.Gateway_Service.Gateway_Service.dataclass.report;

import java.util.ArrayList;

public class ReportDataRequest {
    ArrayList<ArrayList> trendlist;
    ArrayList<ArrayList> Realationshiplist;
    ArrayList<ArrayList> Patternlist;
    ArrayList<String> Anomalylist;
    ArrayList<ArrayList> wordlist;


    public ReportDataRequest(){

    }

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

    public ArrayList<String> getAnomalylist() {
        return Anomalylist;
    }

    public ArrayList<ArrayList> getWordlist() {
        return wordlist;
    }

    public ArrayList<ArrayList> getTrendlist() {
        return trendlist;
    }

    public ArrayList<ArrayList> getRealationshiplist() {
        return Realationshiplist;
    }

    public ArrayList<ArrayList> getPatternlist() {
        return Patternlist;
    }

    public void setAnomalylist(ArrayList<String> anomalylist) {
        Anomalylist = anomalylist;
    }

    public void setPatternlist(ArrayList<ArrayList> patternlist) {
        Patternlist = patternlist;
    }

    public void setRealationshiplist(ArrayList<ArrayList> realationshiplist) {
        Realationshiplist = realationshiplist;
    }

    public void setTrendlist(ArrayList<ArrayList> trendlist) {
        this.trendlist = trendlist;
    }

    public void setWordlist(ArrayList<ArrayList> wordlist) {
        this.wordlist = wordlist;
    }
}
