package com.company;

import java.util.*;

public class TopicGraph extends HashMap<String, ArrayList<String>> {
    private ArrayList<String> lastAdded = null;
    public static int MAX_DEPTH = 1000;

    TopicGraph() { }

    TopicGraph(final String startingUri) {
        super();
        addURI(startingUri);
    }

    public static void main(String [] args) {
        TopicGraph topicGraph = new TopicGraph("/wiki/gardening");
        topicGraph.addURI(topicGraph.getLastAdded().get(0));
        topicGraph.display();
    }

    public ArrayList<String> getLastAdded() {
        return lastAdded;
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

    // Add a topic to the hashmap by the uri
    private boolean addURI(final String newURI) {
        ArrayList<String> newTopics = ParseTopics.getMentionedTopics(newURI);
        System.out.println("Adding topic: " + newURI + " related topic: " + newTopics.get(0));
        if (null != newTopics) {
            // Return TRUE if it has successfully been added
            if (super.putIfAbsent(newURI, newTopics) == null) {
                lastAdded = newTopics;
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> findRoute(final String start, final String end) {
        Queue<String> toSearch = new ArrayDeque<>();
        ArrayList<String> visited = new ArrayList<>();
        if (findRoute(start, end, toSearch, visited)) {
            for (String v : visited) {
                System.out.print(v + " -> ");
            }
            return visited;
        }
        return null;
    }

    private boolean findRoute(String current, final String end, Queue<String> toSearch, ArrayList<String> visited) {
        if (visited.contains(current)) return false;
        ArrayList<String> connectedTopics = super.get(current);

        for (String connectedTopic : connectedTopics) {
            System.out.println("findRoute: topic of: " + current + ": " + connectedTopic);
            if (toSearch.offer(connectedTopic) ) {
                return false;
            }
            if (findRoute(connectedTopic, end, toSearch, visited)) {
                visited.add(connectedTopic);
                return true;
            }
        }

        if (end.equals(current)) {
            visited.add(current);
            return true;
        }
        return false;
    }
}
