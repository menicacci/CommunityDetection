package main.java.com.abd;

import main.java.com.abd.graph.Graph;
import main.java.com.abd.utils.GraphGeneration;

import java.util.Arrays;
import java.util.List;

import static main.java.com.abd.graph.community.KCore.getNodesDegree;

public class Main {

    public static void main(String[] args) {

        Graph g = GraphGeneration.createExample(4);

        g.printGraph();

        System.out.println("Graph connected components: " + g.countConnectedComponents());

        int[] cores = getNodesDegree(g);
        System.out.println("Node's coreness:");
        System.out.println(Arrays.toString(cores));
        System.out.println();

        List<List<Integer>> bc = g.findBiconnectedComponents(1);
        System.out.println("Bi-connected Components:");
        bc.forEach(System.out::println);
    }

}
