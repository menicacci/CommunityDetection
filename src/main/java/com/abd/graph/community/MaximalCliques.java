package main.java.com.abd.graph.community;

import main.java.com.abd.graph.Graph;

import java.util.*;

import static main.java.com.abd.utils.Utils.*;

public class MaximalCliques {

    private Graph graph;

    private List<Set<Integer>> maximalCliques;

    public MaximalCliques(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    public List<Set<Integer>> getMaximalCliques() {
        return maximalCliques;
    }

    public void setMaximalCliques(List<Set<Integer>> maximalCliques) {
        this.maximalCliques = maximalCliques;
    }

    public List<Set<Integer>> find() {
        return find(true);
    }

    // This function finds all the maximal cliques of the input graph
    private List<Set<Integer>> find(boolean useTomita) {
        this.resetMaximalCliques();

        Runnable algorithm = useTomita ? this::findMaximalCliquesUsingTomita : this::findMaximalCliquesUsingBronKerbosch;
        algorithm.run();

        return this.getMaximalCliques();
    }

    private void findMaximalCliquesUsingBronKerbosch() {
        this.resetMaximalCliques();

        this.BronKerbosch(new HashSet<>(), this.getGraph().getNodes().keySet(), new HashSet<>());
    }

    private void findMaximalCliquesUsingTomita() {
        this.resetMaximalCliques();

        this.BronKerbosch(new HashSet<>(), this.getGraph().getNodes().keySet(), new HashSet<>());
    }

    private void BronKerbosch(Set<Integer> R, Set<Integer> P, Set<Integer> X) {
        if (P.isEmpty() && X.isEmpty()) {
            this.getMaximalCliques().add(R);
        }

        // For each node n ∈ P
        Iterator<Integer> iterator = P.iterator();
        while (iterator.hasNext()) {
            Integer nodeID = iterator.next();
            List<Integer> neighbours = this.getGraph().getNode(nodeID).getNeighboursIDList();

            // BronKerbosch(R ∪ {n}, P ∩ N(n), X ∩ N(n))
            BronKerbosch(singleElementSetUnion(R, nodeID), intersection(P, neighbours), intersection(X, neighbours));

            // Move n from P to X
            iterator.remove();
            X.add(nodeID);
        }
    }

    private void Tomita(Set<Integer> R, Set<Integer> P, Set<Integer> X) {
        if (P.isEmpty() && X.isEmpty()) {
            this.getMaximalCliques().add(R);
        }

        // node in P ∪ X with highest |N(u) ∩ P|
        Integer pivotNode = this.getPivotNode(P, X);

        // For each node n ∈ P \ N(u)
        Set<Integer> nonAdjacentNodes = difference(P, this.getGraph().getNode(pivotNode).getNeighboursIDList());
        nonAdjacentNodes.forEach(nodeID -> {
            List<Integer> neighbours = this.getGraph().getNode(nodeID).getNeighboursIDList();
            // Tomita(R ∪ {n}, P ∩ N(n), X ∩ N(n))
            Tomita(singleElementSetUnion(R, nodeID), intersection(P, neighbours), intersection(X, neighbours));

            // Move n from P to X
            P.remove(nodeID);
            X.add(nodeID);
        });
    }

    private void resetMaximalCliques() {
        this.setMaximalCliques(new ArrayList<>());
    }

    private int getPivotNode(Set<Integer> P, Set<Integer> X) {
        return union(P, X).stream()
                .max(Comparator.comparingInt(n -> compareNodeIntersection(n, P)))
                .orElse(0);
    }

    private int compareNodeIntersection(Integer n, Set<Integer> P) {
        return intersection(P, this.getGraph().getNode(n).getNeighboursIDList()).size();
    }
}
