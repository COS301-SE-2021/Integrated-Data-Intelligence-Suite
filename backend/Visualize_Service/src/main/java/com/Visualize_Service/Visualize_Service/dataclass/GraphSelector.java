package com.Visualize_Service.Visualize_Service.dataclass;

import com.Visualize_Service.Visualize_Service.exception.VisualizerException;

import java.util.*;

public class GraphSelector {

    private HashSet<String> allGraphs = new HashSet<>();
    private HashMap<String, Boolean> selectedGraphs = new HashMap<>();

    public GraphSelector(){
        allGraphs.add("totalInteraction");
        allGraphs.add("mostProminentSentiment");
        allGraphs.add("totalTrends");
        allGraphs.add("totalAnomalies");
        allGraphs.add("lineInteractions");
        allGraphs.add("pieChart");
        allGraphs.add("extraBarOne");
        allGraphs.add("map");
        allGraphs.add("bar");
        allGraphs.add("wordCloud");
        allGraphs.add("wordCloudPieChart");
        allGraphs.add("wordCloudSunBurst");
        allGraphs.add("relation");
        allGraphs.add("pattern");
        allGraphs.add("timeline");
        allGraphs.add("scatterPlot");
        allGraphs.add("extraBarTwo");
        allGraphs.add("line");
    }

    public void setAllGraphs(HashSet<String> allGraphs){
        this.allGraphs = allGraphs;
    }

    public HashSet<String> getAllGraphs(){
        return allGraphs;
    }


    public void setSelectedGraphs(HashMap<String, Boolean> selectedGraphs) throws VisualizerException{
        if (validateAllGraphs(selectedGraphs) == false){
            throw new VisualizerException("Invalid Graphs added to Graph Selector (please check one or more of your graphs)");
        }
        this.selectedGraphs = selectedGraphs;
    }

    public HashMap<String, Boolean> getSelectedGraphs(){
        return this.selectedGraphs;
    }


    public void selectGraph(String graphName,  boolean graphSelect) throws VisualizerException {

        if(allGraphs.contains(graphName) == false) {
            throw new VisualizerException("Graph Selector doesn't have graph : " + graphName);
        }

        if(selectedGraphs.containsKey(graphName)){
            boolean oldSelect = selectedGraphs.get(graphName);
            selectedGraphs.replace(graphName, oldSelect, graphSelect);
        }
    }

    public Boolean getSelectedGraph(String graphName) throws VisualizerException{

        if(allGraphs.contains(graphName) == false)  {
            throw new VisualizerException("Graph Selector doesn't have graph : " + graphName);
        }

        if(selectedGraphs.containsKey(graphName)) {
            return selectedGraphs.get(graphName);
        }
        return false;
    }

    private boolean validateAllGraphs(HashMap<String, Boolean> selectedGraphs){
        Iterator hmIterator = selectedGraphs.entrySet().iterator();

        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            if(allGraphs.contains(mapElement.getKey()) == false)
                return false;
        }
        return true;
    }










}
