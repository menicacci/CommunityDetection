package main.java.com.abd.graph.community;

import main.java.com.abd.graph.Graph;
import main.java.com.abd.graph.Node;

import java.util.*;

import static main.java.com.abd.utils.Utils.*;

public class DFSTree {

    private Graph originalGraph;

    private Graph treeGraph;

    private Integer treeRoot;

    private HashMap<Integer, List<Integer>> edges;

    private HashMap<Integer, List<Integer>> fronds;

    HashMap<Integer, Integer> IDsCorrespondence;

    private List<Set<Integer>> biconnectedComponents = new ArrayList<>();

    public DFSTree(Graph g, Integer treeRoot) {
        this.originalGraph = g;
        this.treeGraph = new Graph();
        this.treeRoot = treeRoot;
        this.edges = new HashMap<>();
        this.fronds = new HashMap<>();
        this.IDsCorrespondence = new HashMap<>();

        this.setUp();
    }

    public Graph getOriginalGraph() {
        return originalGraph;
    }

    public Graph getTreeGraph() {
        return treeGraph;
    }

    public Integer getTreeRoot() {
        return treeRoot;
    }

    public HashMap<Integer, List<Integer>> getEdges() {
        return edges;
    }

    public void setEdges(HashMap<Integer, List<Integer>> edges) {
        this.edges = edges;
    }

    public HashMap<Integer, List<Integer>> getFronds() {
        return fronds;
    }

    public void setFronds(HashMap<Integer, List<Integer>> fronds) {
        this.fronds = fronds;
    }

    public HashMap<Integer, Integer> getIDsCorrespondence() {
        return IDsCorrespondence;
    }

    public List<Set<Integer>> getBiconnectedComponents() {
        return biconnectedComponents;
    }

    private void setUp() {
        this.generateDFSTree(null, this.getTreeRoot());
        this.clearEdges();
    }

    /*
    This function generates a tree making a Depth First Search through the original graph.
    In the process, the edges are divided into two groups:
        - edges: Edges that lead towards unexplored nodes
        - fronds: Edges that lead towards already explored nodes
    The tree graph is made just with the first group of arches. During the DFS, the nodes
    created have associated an ID that corresponds to the preorder-visit of the graph. This
    will be useful for detecting the bi-connected components (Tarjan).
     */
    public void generateDFSTree(Node parent, Integer nodeID) {
        // Check if node has been visited
        if (!this.getOriginalGraph().hasBeenVisited(nodeID)) {
            this.getOriginalGraph().setVisit(nodeID);
            // Create a new node
            Node new_node = this.createNodeForTreeGraph(parent, nodeID);

            // Check node's neighbours to detect edges and fronds
            List<Node> neighbours = this.getOriginalGraph().getNodeNeighbours(nodeID);
            neighbours.forEach(neighbour -> {
                // If the node's already been explored, then is labeled as a frond
                boolean isExplored = neighbour.isVisited();
                HashMap<Integer, List<Integer>> map = isExplored ? this.getFronds() : this.getEdges();

                // Add the arch to the correct map
                if (!map.containsKey(nodeID)) {
                    map.put(nodeID, new ArrayList<>());
                }
                map.get(nodeID).add(neighbour.getId());

                // If the neighbour has not been explored yet, explore it
                if (!isExplored) {
                    generateDFSTree(new_node, neighbour.getId());
                }
            });
        }
    }

    private Node createNodeForTreeGraph(Node parent, Integer nodeID) {
        // Add the new node to the tree graph, remember OriginalID -> NewID correspondence
        Node new_node = this.getTreeGraph().addNode();
        this.getIDsCorrespondence().put(nodeID, new_node.getId());
        new_node.setOriginalId(nodeID);

        // Add an edge between the new node and the parent, if it exists
        if (parent != null) {
            this.getTreeGraph().addEdge(new_node.getId(), parent.getId());
        }

        return new_node;
    }

    public List<List<Integer>> getGraphBiconnectedComponents() {
        this.treeGraph.resetVisit();
        // Explore the tree graph
        this.findBiconnectedComponents(this.treeGraph.getNode(1));

        /*
        The IDs of the bi-connected components found refer to the tree graph,
        not the original graph. So they need to be decoded.
         */
        List<List<Integer>> biconnectedComponentsDecoded = new ArrayList<>();
        this.getBiconnectedComponents().forEach(component -> {
            List<Integer> originalComponent = new ArrayList<>();

            component.forEach(ID -> {
                originalComponent.add(this.getTreeGraph().getNode(ID).getOriginalId());
            });
            biconnectedComponentsDecoded.add(originalComponent);
        });

        return biconnectedComponentsDecoded;
    }

    private List<Integer[]> findBiconnectedComponents(Node node) {
        node.visit();
        // List of visited edges
        List<Integer[]> biconnectedComponentsEdges = new ArrayList<>();

        // If the node has no edges, then it is a bi-connected component itself
        if (node.getNeighbours().isEmpty()) {
            this.getGraphBiconnectedComponents().add(new ArrayList<>(List.of(node.getId())));
            return null;
        }

        node.getNeighbours().forEach(neighbour -> {
            // If neighbour has been already visited, no action needed
            if (!neighbour.isVisited()) {
                // Get the list of neighbour visited edges
                List<Integer[]> neighbourEdges = findBiconnectedComponents(neighbour);

                // Calculate the lowest node ID from neighbour's edge list
                int lowpoint = getLowPoint(neighbourEdges, this.getTreeGraph().size());

                // In this case, the node is a cut-vertex, then its ancestors belong to the same component
                if (lowpoint >= node.getId()) {
                    this.cutVertexFound(node.getId(), neighbour.getId(), neighbourEdges);
                }
                else {
                    biconnectedComponentsEdges.addAll(neighbourEdges);
                }
            }
        });

        // Add node's fronds to visited edges
        List<Integer> frondsEdges = this.getFronds().get(node.getId());
        if (frondsEdges != null) {
            frondsEdges.forEach(neighbourNode -> {
                biconnectedComponentsEdges.add(new Integer[]{node.getId(), neighbourNode});
            });
        }

        return biconnectedComponentsEdges;
    }

    private void cutVertexFound(int cutVertexID, int neighbourID, List<Integer[]> componentsEdges) {
        Set<Integer> componentNodes = new HashSet<>();

        // Add all component's node to the set
        componentNodes.add(cutVertexID);
        componentNodes.add(neighbourID);
        componentsEdges.forEach(edge -> componentNodes.add(edge[0]));

        this.getBiconnectedComponents().add(componentNodes);
    }

    private void clearEdges() {
        // Clean this.fronds map removing the duplicates
        this.getEdges().forEach((nodeID, neighbouringNodeIDs) -> {
            neighbouringNodeIDs.forEach(neighbourID -> {
                if (this.getFronds().containsKey(neighbourID)) {
                    this.getFronds().get(neighbourID).remove(nodeID);
                }
            });
        });

        // Swap the IDs of the edges, now they refer to the original graph's nodes
        this.setEdges(swapEdgesIDs(this.getEdges(), this.getIDsCorrespondence()));
        this.setFronds(swapEdgesIDs(this.getFronds(), this.getIDsCorrespondence()));
    }
}
