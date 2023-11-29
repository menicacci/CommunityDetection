package main.java.com.abd.graph;

import main.java.com.abd.graph.community.DFSTree;
import main.java.com.abd.graph.community.MaximalCliques;

import java.util.*;

public class Graph {

    private int nextId;

    private HashMap<Integer,Node> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
        this.nextId = 1;
    }

    public Graph(int nodesNumber) {
        this();

        for (int i = 0; i < nodesNumber; i++)
            this.addNode();
    }

    public HashMap<Integer,Node> getNodes() {
        return nodes;
    }

    public Node getNode(int nodeId) {
        return this.getNodes().get(nodeId);
    }

    public Node addNode() {
        Node newNode = new Node(this.nextId++);
        this.getNodes().put(newNode.getId(), newNode);

        return newNode;
    }

    public boolean addEdge(int id1, int id2) {
        if (id1 == id2) {
            return false;
        }

        Node n1 = this.getNodes().get(id1);
        Node n2 = this.getNodes().get(id2);

        return n1.addEdge(n2) && n2.addEdge(n1);
    }

    public Integer size() {
        return this.getNodes().size();
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public List<Node> getNodeNeighbours(int nodeId) {;
        return this.getNode(nodeId).getNeighboursList();
    }

    public void resetVisit() {
        this.getNodes().forEach((id, node) -> {
            node.reset();
        });
    }

    public boolean hasBeenVisited(Integer nodeID) {
        return this.getNodes().containsKey(nodeID) && this.getNodes().get(nodeID).isVisited();
    }

    public void setVisit(Integer nodeID) {
        if (this.getNodes().containsKey(nodeID)) {
            this.getNodes().get(nodeID).visit();
        }
    }

    public List<List<Integer>> findBiconnectedComponents(Integer treeRoot) {
        if (this.size() > 0) {
            this.resetVisit();
            DFSTree tree = new DFSTree(this, treeRoot);
            return tree.getGraphBiconnectedComponents();
        }

        return null;
    }

    public int countConnectedComponents() {
        return this.size() != 0 ? Arrays.stream(this.detectComponents()).max().getAsInt() : 0;
    }

    public int[] detectComponents() {
        this.resetVisit();

        int graphSize = this.size();
        int[] nodes = new int[graphSize];

        if (graphSize > 0) {
            int color = 1;
            for (Map.Entry<Integer, Node> entry : this.getNodes().entrySet()) {
                Node node = entry.getValue();
                if (!node.isVisited()) {
                    node.colorComponent(nodes, color);
                    color++;
                }
            }
        }

        return nodes;
    }

    public List<Set<Integer>> findMaximalCliques() {
        return new MaximalCliques(this).find();
    }

    public void printGraph() {

        this.getNodes().forEach((id, node) -> {
            System.out.print(node.getId() + ": ");
            node.printEdges();
        });
        System.out.println();
    }
}
