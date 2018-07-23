package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class TopicGraph extends HashMap<String, ArrayList<String>> {

    TopicGraph() { }

    TopicGraph(final String startingUri) {
        addURI(startingUri);
    }

    public static void main(String [] args) {
        TopicGraph topicGraph = new TopicGraph("/wiki/gardening");
        ArrayList<String> list = topicGraph.get("/wiki/gardening");
        for (String l : list) {
            System.out.println(l);
        }
    }

    // Add a topic to the hashmap by the uri
    public void addURI(final String newURI) {
        ArrayList<String> newTopics = ParseTopics.getMentionedTopics(newURI);
        if (null != newTopics)
            super.putIfAbsent(newURI, newTopics);
    }

    public ArrayList<String> findRoute(final String start, final String end) {

    }

    public boolean findRoute(String current, final String end, Queue<String> toSearch, ArrayList<String> visited) {
        if (visited.contains(current)) return false;
        ArrayList<String> connectedTopics = super.get(current);

        for (String connectedTopic : connectedTopics) {
            if (toSearch.offer(connectedTopic) ) {
                return false;
            }
            if (findRoute(connectedTopic, end, toSearch, visited)) {
                visited.add(current);
                return true;
            }
        }

        if (end.equals(current)) {
            return true;
        }
        // Doesn't work here;
        return findRoute(toSearch.poll(), end, toSearch, visited);
    }
}
