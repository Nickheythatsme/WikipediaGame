package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class TopicNode {
    private static String TEST_STRING = "<a href=\"/wiki/Retail\" title=\"Retail\">Retailing</a></td></tr><tr><th";
    private ArrayList<String> mentionedTopics = new ArrayList<>();
    private String topicName;

    TopicNode(final String newTopic) {
        topicName = newTopic;
        mentionedTopics = getMentionedTopics(topicName);
    }

    public static void main(String[] args) throws Exception {
    }

    @Override
    public int hashCode() {
        return topicName.hashCode();
    }

    public String getTopicName() {
        return topicName;
    }

    public String [] getMentionedTopics() {
        String [] topics = new String[mentionedTopics.size()];
        mentionedTopics.toArray(topics);
        return topics;
    }

    public boolean equals(String toCompare) {
        return topicName.equals(toCompare);
    }

    public boolean equals(TopicNode toCompare) {
        return topicName.equals(toCompare.topicName);
    }

    public boolean contains(TopicNode toCheck) {
        return mentionedTopics.contains(toCheck.getTopicName());
    }

    public boolean contains(String toCheck) {
        return mentionedTopics.contains(toCheck);
    }

    public int compareTo(TopicNode node) {
        return topicName.compareToIgnoreCase(node.topicName);
    }


    private static ArrayList<String> getMentionedTopics(final String pageUri) {
        ArrayList<String> toReturn = null;
        try {
            WikiPage page = new WikiPage(pageUri);
            toReturn = parsePayload(page.getPayload());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error pulling page from : " + pageUri);
        }
        return toReturn;
    }

    private static ArrayList<String> parsePayload(final String payload) {
        ArrayList<String> topic_list = new ArrayList<>();
        String [] rough_topics = payload.split("\"");

        for (String rough_topic : rough_topics) {
            if (rough_topic.matches("/wiki/\\w+"))
                topic_list.add(rough_topic);
        }

        return topic_list;
    }
}
