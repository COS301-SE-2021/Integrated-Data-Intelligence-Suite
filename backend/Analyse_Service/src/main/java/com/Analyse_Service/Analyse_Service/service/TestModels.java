package com.Analyse_Service.Analyse_Service.service;

import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.repository.AnalyseServiceParsedDataRepository;
import com.Analyse_Service.Analyse_Service.request.AnalyseDataRequest;
import org.apache.catalina.core.ApplicationContext;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Configurable
public class TestModels {

    @Autowired
    private AnalyseServiceParsedDataRepository parsedDataRepository;

    @Autowired
    private static AnalyseServiceImpl service = new AnalyseServiceImpl();

    //private ArrayList<ParsedData> dataList = new ArrayList<>();

    public static void main(String[] args) throws InvalidRequestException, IOException {

        service.TrainOverallModels();

    }


    public AnalyseDataRequest getData(){

        return null;//new AnalyseDataRequest(this.dataList);
    }

    public static void testMLflow(){
        String path = "Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/KMeansModel"; //LogisticRegressionModel
        String script = "Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/rri/LogModel.py";


        String commandPath = "python " + script + " " + path + " KMeansModel"; //KMeansModel
        CommandLine commandLine = CommandLine.parse(commandPath);

        //commandLine.addArguments(new String[] {"../models/LogisticRegressionModel","LogisticRegressionModel", "1"});
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(new PumpStreamHandler(System.out));
        //executor.execute(commandLine);

        try {
            executor.execute(commandLine);

         } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
