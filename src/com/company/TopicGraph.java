package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class TopicGraph extends HashMap<TopicNode, ArrayList<String>> {
    ArrayList<TopicNode> topics = new ArrayList<>();

    public static void main(String [] args) {
        TopicNode n = new TopicNode("/wiki/gardening");
        String [] relatedTopics = n.getMentionedTopics();
        for (String related : relatedTopics) {
            System.out.println(related);
        }
    }
}
