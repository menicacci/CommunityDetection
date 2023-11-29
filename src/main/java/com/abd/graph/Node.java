package main.java.com.abd.graph;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Node {

    private final int id;

    private HashSet<Node> neighbours;

    private boolean visited;

    private int originalId;

    public Node(int id) {
        this.id = id;
        this.neighbours = new HashSet<>();
        this.visited = false;
    }

    public int getId() {
        return id;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getOriginalId() {
        return originalId;
    }

    public void setOriginalId(int originalId) {
        this.originalId = originalId;
    }

    public void visit() {
        this.setVisited(true);
    }

    public void reset() {
        this.setVisited(false);
    }

    public HashSet<Node> getNeighbours() {
        return neighbours;
    }

    public List<Node> getNeighboursList() {
        return this.getNeighbours().stream().toList();
    }

    public List<Integer> getNeighboursIDList() {
        return this.getNeighboursList().stream()
                .map(Node::getId)
                .collect(Collectors.toList());
    }

    public int[] getNeighboursArray() {
        return this.getNeighboursList().stream()
                .mapToInt(Node::getId)
                .toArray();
    }

    public boolean addEdge(Node n) {
        return this.getNeighbours().add(n);
    }

    public int getDegree() {
        return this.getNeighbours().size();
    }

    public void colorComponent(int[] nodes, int color) {
        if (!this.isVisited()) {
            this.visit();
            nodes[this.getId() - 1] = color;

            this.getNeighboursList().forEach(neighbour -> neighbour.colorComponent(nodes, color));
        }
    }

    public void printEdges() {
        this.getNeighbours().forEach(node -> {
            System.out.print(node.getId() + " ");
        });
        System.out.println();
    }

    @Override
    public int hashCode() {
        return this.getId();
    }

    @Override
    public boolean equals(Object obj) {
        Node n = (Node) obj;
        return this.getId() == n.getId();
    }

}
