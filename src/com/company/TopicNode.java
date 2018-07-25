package com.company;

import java.util.ArrayList;

public class TopicNode extends ArrayList<String> {
    private String topic = null;
    private int lastVisited = 0;

    TopicNode(final String uri) throws java.io.IOException {
        super();
        topic = uri;
        super.addAll(ParseTopics.getMentionedTopics(uri));
    }

    public static void main(String [] args) {
        try {
            TopicNode node = new TopicNode("/wiki/url");
        }catch(java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Unable to connect to " + "/wiki/url");
        }
    }

    /**
     * @return topic
     */
    public String getTopic() {
        return topic;
    }

    public boolean visited(int currentVisit) {
        if (currentVisit == lastVisited) {
            return true;
        }
        else {
            lastVisited = currentVisit;
        }
        return false;
    }
}
