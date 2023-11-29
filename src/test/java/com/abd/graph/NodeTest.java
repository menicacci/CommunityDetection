package test.java.com.abd.graph;

import main.java.com.abd.graph.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NodeTest {

    private Node n1;
    private Node n2;

    @BeforeEach
    void setUp() {
        this.n1 = new Node(1);
        this.n2 = new Node(2);
    }

    @Test
    public void nodeDistinctionTest() {
        Assertions.assertNotEquals(this.n1, this.n2);
    }

    @Test
    public void differentIdTest() {
        Assertions.assertTrue(this.n1.getId() != this.n2.getId());
    }

    @Test
    public void noNeighbourhoodTest() {
        Assertions.assertTrue(this.n1.getNeighbours().isEmpty() && this.n1.getDegree() == 0);
    }

    @Test
    public void addingEdgeTest() {
        this.addEdge();

        Assertions.assertFalse(this.n1.getNeighbours().isEmpty());
        Assertions.assertFalse(this.n2.getNeighbours().isEmpty());
    }

    @Test
    public void neighbourPresentTest() {
        this.addEdge();

        Assertions.assertTrue(this.n1.getNeighbours().contains(this.n2));
    }

    private void addEdge() {
        this.n1.addEdge(this.n2);
        this.n2.addEdge(this.n1);
    }
}
