package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class ParseTopics {

    public static void main(String [] args) {
        try {
            ArrayList<String> list = ParseTopics.getMentionedTopics("/wiki/gardening");
            for (String l : list) {
                System.out.println(l);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getMentionedTopics(final String pageUri) throws java.io.IOException {
        ArrayList<String> topicList;
        String [] toReturn = null;
        WikiPage page = new WikiPage(pageUri);
        topicList = parsePayload(page.getPayload());
        return topicList;
    }

    private static ArrayList<String> parsePayload(final String payload) {
        ArrayList<String> topic_list = new ArrayList<>();
        String [] rough_topics = payload.split("\"");

        for (String rough_topic : rough_topics) {
            if (rough_topic.matches("/wiki/\\w+") && !topic_list.contains(rough_topic))
                topic_list.add(rough_topic);
        }

        return topic_list;
    }
}
