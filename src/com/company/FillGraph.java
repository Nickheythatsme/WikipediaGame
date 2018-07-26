package com.company;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class FillGraph {
    private String start;
    private String end;
    private Queue<String> toSearch = new PriorityQueue<>();

    FillGraph(final String _start, final String _end) {
        start = _start;
        end = _end;
    }

    public static void main(String [] args) {
        FillGraph fillGraph = new FillGraph("/wiki/Family_Guy", "/wiki/Popeye");
        fillGraph.start();
    }

    public boolean start() {
        return false;
    }

    private boolean start(String currentTopic) {
        try {
            TopicNode newTopic = new TopicNode(currentTopic);
            for (String aTopic : newTopic) {
                toSearch.addAll(newTopic);
            }
        }catch(java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Unable to get page: " + currentTopic);
        }
        return false;
    }
}
