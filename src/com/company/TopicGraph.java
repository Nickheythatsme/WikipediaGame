package com.company;

import java.util.*;

public class TopicGraph extends HashMap<String, ArrayList<String>> {
    public static int MAX_DEPTH = 1000;

    TopicGraph() { }

    TopicGraph(final String startingUri) {
        super();
        add(startingUri);
    }

    public static void main(String [] args) {
        TopicGraph topicGraph = new TopicGraph("/wiki/gardening");
        topicGraph.display();
    }

    private void display() {
        Set<String> keys = super.keySet();
        for (String key : keys) {
            System.out.print(key + " -> ");
            ArrayList<String> topics = super.get(key);
            for (String topic : topics) {
                System.out.print(topic + ",");
            }
            System.out.println();
        }
    }

    public boolean add(TopicNode newTopic) {
        super.putIfAbsent(newTopic.getTopic(), newTopic);
        return true;
    }

    // Add a topic to the hashmap by the uri
    public boolean add(final String newURI) {
        try {
            TopicNode newNode = new TopicNode(newURI);
            // TODO remove when not testing
            System.out.println("Adding topic: " + newURI + " related topic: " + newNode.get(0));
            // Return TRUE if it has successfully been added
            if (super.putIfAbsent(newNode.getTopic(), newNode) != null) {
                return false;
            }
        }catch(java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<String> findRoute(final String start, final String end) {
        return null;
    }

    private boolean findRoute(String current, final String end, Queue<String> toSearch, int currentVisit) {
        return false;
    }
}
