package com.company;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class TopicGraph extends HashMap<String, ArrayList<String>> {
    public static int MAX_DEPTH = 1000;
    private String start;
    private String end;

    TopicGraph() { }

    TopicGraph(final String startingUri, final String endingUri) {
        super();
        start = startingUri;
        end = endingUri;
    }

    public static void main(String [] args) {
        TopicGraph topicGraph = new TopicGraph("/wiki/gardening", "/wiki/Afghanistan");
        ArrayList<String> result = topicGraph.start(TopicGraph.MAX_DEPTH);
        for (String node : result) {
            System.out.print(node + " -> ");
        }
    }

    private ArrayList<String> start(int depth) {
        return addURIRecursive(start, depth);
    }

    private ArrayList<String> addURIRecursive(String current, int depth) {
        System.out.println("Adding: " + current);
        if (!addURI(current)) return null;
        ArrayList<String> relatedTopics = super.get(current);
        ++depth;
        for (String topic : relatedTopics) {
            ArrayList<String> foundRoute = findRoute(start, end);
            if (foundRoute != null) return foundRoute;
            else {
                addURIRecursive(topic, depth);
            }
        }
        return null;
    }

    // Add a topic to the hashmap by the uri
    private boolean addURI(final String newURI) {
        ArrayList<String> newTopics = ParseTopics.getMentionedTopics(newURI);
        System.out.println("Adding topic: " + newURI + " related topic: " + newTopics.get(0));
        if (null != newTopics) {
            super.putIfAbsent(newURI, newTopics);
            return true;
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
