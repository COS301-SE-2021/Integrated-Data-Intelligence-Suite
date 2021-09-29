package com.Report_Service.Report_Service.service;

import com.Report_Service.Report_Service.exception.InvalidRequestException;
import com.Report_Service.Report_Service.exception.ReporterException;
import com.Report_Service.Report_Service.request.*;
import com.Report_Service.Report_Service.response.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReportServiceImpl {

    public ReportDataResponse reportData(ReportDataRequest request) throws ReporterException {

        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }
        //does this work

        return new ReportDataResponse();
    }

    public GetTrendAnalysisDataResponse getTrendAnalysisData(GetTrendAnalysisDataRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Data list is null");
        }

        ArrayList<ArrayList> output = new ArrayList<>();
        ArrayList<ArrayList> reqData = request.getDataList();

        for (ArrayList l: reqData) {
            String Entity = l.get(0).toString();
            String EntityType = l.get(2).toString();
            String AverageInteraction = l.get(3).toString();
            String Frequency = l.get(6).toString();


            //GetMostProminentSentimentRequest SentReq = new GetMostProminentSentimentRequest((ArrayList<ArrayList>) l.get(4));
            //GetMostProminentSentimentResponse SentResp = this.getMostProminentSentiment(SentReq);
            String mostProminentSentiment = "";//SentResp.getSentiment();

            GetMostProminentLocationRequest LocReq = new GetMostProminentLocationRequest((ArrayList<String>) l.get(4));
            GetMostProminentLocationResponse LocResp = this.getMostProminentLocation(LocReq);
            String mostProminentLocation = LocResp.getLocation();

            ArrayList<Object> row = new ArrayList<>();
            row.add(Entity);
            row.add(EntityType);
            row.add(AverageInteraction);
            row.add(Frequency);
            row.add(mostProminentSentiment);
            row.add(mostProminentSentiment);


            System.out.println(row);
            output.add(row);

        }

        int numTrends = reqData.size();
        System.out.println("");

        String summary = "The Integrated Data intelligence suite found "+ String.valueOf(numTrends) + " trends in the data collected by the search";
        System.out.println(summary);
        return new GetTrendAnalysisDataResponse(output,summary);
    }

    public GetMostProminentLocationResponse getMostProminentLocation(GetMostProminentLocationRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("CreateTimelineGraphRequest Object is null");
        }

        return new GetMostProminentLocationResponse("null");
    }

    public GetMostProminentSentimentResponse getMostProminentSentiment(GetMostProminentSentimentRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("CreateTimelineGraphRequest Object is null");
        }
        if (request.getDataList() == null) {
            throw new InvalidRequestException("Arraylist is null");
        }
        String outputs = "";
        ArrayList<ArrayList> reqData = request.getDataList();

        int k = 0;
        ArrayList<String> listSent = new ArrayList<>();
        ArrayList<ArrayList> out = new ArrayList<>();
        for (int i = 0; i < reqData.size(); i++) {
            ArrayList<String> sents = (ArrayList<String>) reqData.get(i).get(4);
            //System.out.println(locs.toString());

            for (int j = 0; j < sents.size(); j++) {
                if (listSent.isEmpty()){
                    listSent.add(sents.get(j));
                    ArrayList<Object> r = new ArrayList<>();
                    r.add(sents.get(j));
                    r.add(1);
                    out.add(r);
                }else {
                    if (listSent.contains(sents.get(j))){
                        ArrayList<Object>r =  out.get(listSent.indexOf(sents.get(j)));
                        int val=Integer.parseInt(r.get(1).toString());
                        val++;
                        r.set(1,val);
                        out.set(listSent.indexOf(sents.get(j)),r);
                    }else {
                        listSent.add(sents.get(j));
                        ArrayList<Object> r = new ArrayList<>();
                        r.add(sents.get(j));
                        r.add(1);
                        out.add(r);
                    }
                }
            }
        }


        outputs = out.get(0).get(0).toString();
        int temp = Integer.parseInt(out.get(0).get(1).toString());
        for (ArrayList o : out) {
            //System.out.println(o);
            if (Integer.parseInt(o.get(1).toString()) > temp){
                outputs = o.get(0).toString();
                temp = Integer.parseInt(o.get(1).toString());
            }
        }
        return new GetMostProminentSentimentResponse("outputs");
    }

    public GetPatternAndRelationshipDataResponse getPatternAndRelationshipData(GetPatternAndRelationshipDataRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }
        if (request.getDataListP() == null){
            throw new InvalidRequestException("Data list P is null");
        }
        if (request.getDataListR() == null){
            throw new InvalidRequestException("Data list R is null");
        }

        return new GetPatternAndRelationshipDataResponse(null,null);
    }

    public GetAnomalyDataResponse getAnomalyData(GetAnomalyDataRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Data list is null");
        }

        return new GetAnomalyDataResponse(null,null);
    }

    public GetTextualAnalysisDataResponse getTextualAnalysisData(GetTextualAnalysisDataRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Data list is null");
        }

        return new GetTextualAnalysisDataResponse(null,null);
    }

    public GenerateReportPDFResponse generateReportPDF(GenerateReportPDFRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }

        return new GenerateReportPDFResponse();
    }
}
