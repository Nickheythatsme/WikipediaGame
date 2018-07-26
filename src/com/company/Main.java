package com.company;

public class Main {

    public static void main(String[] args) {
        WikiGraph wikiGraph = new WikiGraph("/wiki/Family_Guy","/wiki/SpongeBob_SquarePants");
        wikiGraph.startSearch();
        wikiGraph.displayPath();
    }
}
