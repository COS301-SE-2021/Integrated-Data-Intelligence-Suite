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


            GetMostProminentSentimentRequest SentReq = new GetMostProminentSentimentRequest((ArrayList<String>) l.get(4));
            GetMostProminentSentimentResponse SentResp = this.getMostProminentSentiment(SentReq);
            String mostProminentSentiment = SentResp.getSentiment();

            GetMostProminentLocationRequest LocReq = new GetMostProminentLocationRequest((ArrayList<String>) l.get(1));
            GetMostProminentLocationResponse LocResp = this.getMostProminentLocation(LocReq);
            String mostProminentLocation = LocResp.getLocation();

            ArrayList<Object> row = new ArrayList<>();
            row.add(Entity);
            row.add(EntityType);
            row.add(AverageInteraction);
            row.add(Frequency);
            row.add(mostProminentSentiment);
            row.add(mostProminentLocation);


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
        ArrayList<String> sents = request.getDataList();

        int k = 0;
        ArrayList<String> listSent = new ArrayList<>();
        ArrayList<ArrayList> out = new ArrayList<>();

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



        outputs = out.get(0).get(0).toString();
        int temp = Integer.parseInt(out.get(0).get(1).toString());
        for (ArrayList o : out) {
            //System.out.println(o);
            if (Integer.parseInt(o.get(1).toString()) > temp){
                outputs = o.get(0).toString();
                temp = Integer.parseInt(o.get(1).toString());
            }
        }
        return new GetMostProminentSentimentResponse(outputs);
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

    private String getLocation(double latitude , double longitude){
        String output = "";
        ArrayList<String> provinces = new ArrayList<>();
        provinces.add("Western Cape"); //0
        provinces.add("Northern Cape"); //1
        provinces.add("North West"); //2
        provinces.add("Free State");  //3
        provinces.add("Eastern Cape");  //4
        provinces.add("KwaZulu Natal"); //5
        provinces.add("Mpumalanga"); //6
        provinces.add("Gauteng"); //7
        provinces.add("Limpopo"); //8


        System.out.println("check this right here, lat long");
        System.out.println(latitude);
        System.out.println(longitude);


        /**Western Cape**/
        double box[][] = new double[][] {
                {-32.105816, 18.325114}, //-32.105816, 18.325114 - tl
                {-31.427866, 23.514043}, //-31.427866, 23.514043 - tr
                {-34.668590, 19.536993}, //-34.668590, 19.536993 - bl
                {-33.979034, 23.514043} //-33.979034, 23.689824 - br
        };

        if( isInBox(box,latitude,longitude) ){
            output = "Western Cape";
            return output;
        }

        /**Northern Cape**/

        box = new double[][]{
                {-28.617306, 16.515919}, //-28.617306, 16.515919 - tl
                {-25.758013, 24.738063}, //-25.758013, 24.738063 - tr
                {-31.615170, 18.218633}, //-31.615170, 18.218633 - bl
                {-30.532062, 25.165362} //-30.532062, 25.165362 - br
        };

        if( isInBox(box,latitude,longitude) ){
            output = "Northern Cape";
            return output;
        }

        /**North West**/

        box = new double[][]{
                {-25.458714, 22.868166}, //-25.458714, 22.868166 - tl
                {-24.772334, 27.020998}, //-24.772334, 27.020998 - tr
                {-27.941580, 24.702883}, //-27.941580, 24.702883 - bl
                {-26.888332, 27.339602} //-26.888332, 27.339602 - br
        };

        if( isInBox(box,latitude,longitude) ){
            output = "North West";
            return output;
        }

        /**Free State**/

        box = new double[][]{
                {-28.667645, 24.106132}, //-28.667645, 24.106132 - tl
                {-26.605363, 26.665158}, //-26.605363, 26.665158 - tr
                {-28.027116, 29.557151}, //-28.027116, 29.557151 - bl
                {-30.520515, 24.934890} //-30.520515, 24.934890 - br
        };

        if( isInBox(box,latitude,longitude) ){
            output = "Free State";
            return output;
        }

        /**Eastern Cape**/

        box = new double[][]{
                {-32.029244, 24.449780}, //-32.029244, 24.449780 - tl
                {-30.050545, 28.986950}, //-30.050545, 28.986950 - tr
                {-31.427866, 23.514043}, //-31.427866, 23.514043 - bl
                {-31.382586, 29.793336} //-31.382586, 29.793336 - br
        };

        if( isInBox(box,latitude,longitude) ){
            output = "Eastern Cape";
            return output;
        }

        /**KwaZulu Natal**/

        box = new double[][]{
                {-27.487467, 29.720804}, //-27.487467, 29.720804 - tl
                {-26.861960, 32.873880}, //-26.861960, 32.873880 - tr
                {-30.485275, 29.149515}, //-30.485275, 29.149515 - bl
                {-30.768887, 30.379984} //-30.768887, 30.379984 - br
        };

        if( isInBox(box,latitude,longitude) ){
            output = "KwaZulu Natal";
            return output;
        }

        /**Mpumalanga**/

        box = new double[][]{
                {-25.133998, 29.050638}, //-25.133998, 29.050638 - tl
                {-24.505796, 30.995218}, //-24.505796, 30.995218 - tr
                {-31.427866, 23.514043}, //-31.427866, 23.514043 - bl
                {-27.224009, 31.214945} //-27.224009, 31.214945 - br
        };

        if( isInBox(box,latitude,longitude) ){
            output = "Mpumalanga";
            return output;
        }

        /**Gauteng**/

        box = new double[][]{
                {-25.600567, 27.842142}, //-25.600567, 27.842142 - tl
                {-25.153889, 28.819925}, //-25.153889, 28.819925 - tr
                {-26.705037, 27.182963}, //-26.705037, 27.182963 - bl
                {-26.773717, 28.314554} //-26.773717, 28.314554 - br
        };

        if( isInBox(box,latitude,longitude) ){
            output = "Gauteng";
            return output;
        }

        /**Limpopo**/

        box = new double[][]{
                {-22.487459, 28.725519}, //-22.487459, 28.725519 - tl
                {-22.393532, 31.275307}, //-22.393532, 31.275307 - tr
                {-24.722124, 26.471358}, //-24.722124, 26.471358 - bl
                {-24.284997, 31.737225} //-24.284997, 31.737225 - br
        };

        if( isInBox(box,latitude,longitude) ){
            output = "Limpopo";
            return output;
        }

        return output;
    }

    private boolean isInBox(double[][] box2, double latitude, double longitude) {

        ArrayList<double[]> box = new ArrayList<>();

        double[] codinateValue = new double[2];
        //-32.105816, 18.325114 - tl
        codinateValue[0] = box2[0][0];
        codinateValue[1] = box2[0][1];
        box.add(codinateValue);

        codinateValue = new double[2];
        //-31.427866, 23.514043 - tr
        codinateValue[0] = box2[1][0];
        codinateValue[1] = box2[1][1];
        box.add(codinateValue);

        codinateValue = new double[2];
        //-34.668590, 19.536993 - bl
        codinateValue[0] = box2[2][0];
        codinateValue[1] = box2[2][1];
        box.add(codinateValue);

        codinateValue = new double[2];
        //-33.979034, 23.689824 - br
        codinateValue[0] = box2[3][0];
        codinateValue[1] = box2[3][1];
        box.add(codinateValue);


        double[] topLeft = box.get(0);
        double[] topRight = box.get(1);
        double[] bottomLeft = box.get(2);
        double[] bottomRight = box.get(3);

        /*System.out.println("check box here, latitude (side) :");
        System.out.println(latitude);

        System.out.println("bottomRight : " + bottomRight[0]);
        System.out.println("topLeft : " + topLeft[0]);
        System.out.println("topRight : " + topRight[0]);
        System.out.println("bottomLeft : " + bottomLeft[0]);

        System.out.println("check box here, longitude (u-d):");
        System.out.println(longitude);

        System.out.println("bottomRight : " + bottomRight[1]);
        System.out.println("topLeft : " + topLeft[1]);
        System.out.println("topRight : " + topRight[1]);
        System.out.println("bottomLeft : " + bottomLeft[1]);*/

        //check latitude : (SIDES)
        double maxBottom =  bottomLeft[0];
        if(bottomRight[0] > maxBottom)
            maxBottom = bottomRight[0];
        if ( latitude < maxBottom)
            return false;

        double maxTop =  topLeft[0];
        if(topRight[0] > maxBottom)
            maxTop = topRight[0];
        if ( latitude > maxTop )
            return false;

        //check longitude : (UP_DOWN)
        double maxLeft =  topLeft[1]; //19 - 20 - 21
        if(bottomLeft[1] > maxBottom)
            maxLeft = bottomLeft[1];
        if ( longitude < maxLeft )
            return false;

        double maxRight =  topRight[1];
        if(bottomRight[1] > maxBottom)
            maxRight = bottomRight[1];
        if ( longitude > maxRight )
            return false;

        return true;
    }
}
