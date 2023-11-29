package test.java.com.abd.graph;

import main.java.com.abd.graph.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GraphTest {

    private Graph graph;

    @BeforeEach
    void setUp() {
        this.graph = new Graph();
    }

    @Test
    public void emptyGraphTest() {
        Assertions.assertTrue(this.graph.isEmpty());
    }

    @Test
    public void singleNodeTest() {
        this.graph.addNode();
        Assertions.assertFalse(this.graph.isEmpty());
    }

    @Test
    public void multipleNodeTest() {
        this.graphSetUp();

        Assertions.assertFalse(this.graph.isEmpty());
    }

    @Test
    public void edgeCreationTest() {
        this.graphSetUp();

        Assertions.assertFalse(this.graph.getNodeNeighbours(1).isEmpty());
        Assertions.assertFalse(this.graph.getNodeNeighbours(2).isEmpty());
        Assertions.assertFalse(this.graph.getNodeNeighbours(3).isEmpty());
    }

    /*
    Graph structure
    1: 2, 3
    2: 1
    3: 1
     */
    private void graphSetUp() {
        // Add three nodes
        this.graph.addNode();
        this.graph.addNode();
        this.graph.addNode();

        this.graph.addEdge(1, 2);
        this.graph.addEdge(1, 3);
    }

}
