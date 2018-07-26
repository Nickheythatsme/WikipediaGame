package com.company;

import java.util.*;

public class TopicGraph {
    public static long MAX_DEPTH = 1000;
    private HashMap<String, TopicNode> verticies = new HashMap<>();

    TopicGraph() { }

    TopicGraph(final String startingUri) {
        addNode(startingUri);
    }

    public static void main(String [] args) {
        TopicGraph topicGraph = new TopicGraph();
        topicGraph.addNodeUntil("/wiki/gardening","/wiki/102nd_Security_Forces_Squadron", 1000000);
        ArrayList<String> path = topicGraph.findRoute("/wiki/gardening","/wiki/102nd_Security_Forces_Squadron");
        System.out.println("Path from start to end: ");
        for (String p : path) {
            System.out.println(p);
        }
    }

    private void display() {
        Set<String> keys = verticies.keySet();
        for (String key : keys) {
            System.out.print(key + ": ");
            System.out.print(verticies.get(key).toString());
            System.out.println();
        }
    }

    public TopicNode addNode(String newTopic) {
        try {
            TopicNode newNode = new TopicNode(newTopic);
            if (verticies.putIfAbsent(newTopic, newNode) == null) {
                System.out.println("Adding: " + newTopic);
                return newNode;
            }
        }catch (java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Cannot create node for: " + newTopic);
        }
        return null;
    }

    public boolean addNodeUntil(final String start, final String end, long maxDepth) {
        Queue<String> toAdd = new PriorityQueue<>();
        return addNodeUntil(start, end, toAdd, maxDepth, 0);
    }

    private boolean addNodeUntil(String current, final String end, Queue<String> toAdd, long maxDepth, long currentDepth) {
        if (currentDepth >= maxDepth || current == null) {
            return false;
        }
        ArrayList<String> relatedTopics = addNode(current);
        if (relatedTopics != null) {
            // System.out.println("Depth: " + currentDepth + " Adding topic: " + current);
            toAdd.addAll(relatedTopics);
        }
        if (current.equals(end)) {
            return true;
        }
        return addNodeUntil(toAdd.poll(), end, toAdd, maxDepth, currentDepth + 1);
    }

    public ArrayList<String> findRoute(final String start, final String end) {
        ArrayList<String> path = new ArrayList<>();
        Queue<TopicNode> toSearch = new ArrayDeque<>();
        toSearch.add(verticies.get(start));
        findRoute(end, toSearch, path);

        toSearch.clear();
        return path;
    }

    private boolean findRoute(final String end, Queue<TopicNode> toSearch, ArrayList<String> path) {
        TopicNode current = toSearch.poll();
        if (current == null) {
            return false;
        }
        if (current.getTopic().equals(end)) {
            System.out.println("FOUND " + end);
            path.add(current.getTopic());
            return true;
        }
        System.out.println("Topic: " + current.getTopic());
        for (String topic : current) {
            TopicNode foundTopic = verticies.get(topic);
            if (foundTopic != null && !foundTopic.visited()) {
                foundTopic.setVisited(true);
                toSearch.add(foundTopic);
            }
        }
        return findRoute(end, toSearch, path);
    }
}
