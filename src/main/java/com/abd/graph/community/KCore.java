package main.java.com.abd.graph.community;

import main.java.com.abd.graph.Graph;

import java.util.Arrays;

import static main.java.com.abd.utils.Utils.*;

/*
The k-core of a graph is the maximal induced subgraph such that each node has degree at least k.
By definition, a graph coincides with its 0-core. In this class, we will compute the core number
of each node n, defined as the maximum value k such that n belongs to the k-core of the graph.
 */
public class KCore {

    /*
    Given a Graph g, this function, for each of its nodes, computes the node's coreness. This value represents the
    maximum value k for which the node belongs to g's k-Core.
     */
    public static int[] getNodesDegree(Graph g) {
        if (g.isEmpty()) {
            return new int[0];
        }

        int i, j = 0;
        int graphSize = g.size();
        int[] degree = new int[graphSize];

        // For each node, compute the degree
        g.getNodes().forEach((nodeID, node) -> {
            degree[nodeID - 1] = node.getDegree();
        });

        int maxDegree = Arrays.stream(degree).max().getAsInt();
        // Initiate the array bin, it contains the number of element with a specific value
        int[] bin = new int[maxDegree + 1];
        Arrays.stream(degree).forEach(value -> bin[value]++);

        // Modify the array bin with the number of elements that have less than the given value
        accumulateValues(bin);

        // Iterate through the elements of the array and insert elements IDs into the corresponding bin
        // The array sort will contain the node IDs ordered by degree value
        int[] sort = orderUsingValuesPosition(degree, bin);

        // Finally, we will maintain an array that stores, for each node, the index of it's position in the sort array
        int[] pos = storeArrayPositions(sort);

        // Iterate through the sort array, we will use the degree array as output
        for (i = 0; i < graphSize; i++) {
            int node = sort[i];
            int nodeDegree = degree[node];

            int[] neighbours = g.getNode(node + 1).getNeighboursArray();
            // Iterate through node's neighbours
            for (j = 0; j < neighbours.length; j++) {
                int neighbour = neighbours[j] - 1;
                int neighbourDegree = degree[neighbour];

                // If the neighbour has higher degree, decrease it and reorder the nodes.
                if (neighbourDegree > nodeDegree) {
                    degree[neighbour]--;

                    /*
                     Swap elements in sort and pos arrays: the neighbour node will now be in the bin[neighbourDegree]
                     position of the sort array.
                     */
                    swapElementsUsingIndexArray(sort, pos, bin[neighbourDegree], neighbour);

                    /*
                    By incrementing this value we are excluding the neighbour from the set of node with its previous
                    k-core value
                     */
                    bin[neighbourDegree] += 1;
                }
            }
        }
        return degree;
    }

}
