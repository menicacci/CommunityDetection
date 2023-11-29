package main.java.com.abd.utils;

import main.java.com.abd.graph.Graph;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class GraphGeneration {

    public static Graph generateRandomGraph(int nodesNumber, float edgeNodeRatio) {
        Graph g = new Graph(nodesNumber);

        int edgeNumber = (int) (nodesNumber*edgeNodeRatio);
        Random random = new Random();
        for (int i = 0; i < edgeNumber; i++) {
            int id1, id2;
            while (((id1 = random.nextInt(nodesNumber)) == (id2 = random.nextInt(nodesNumber)))) {}

            g.addEdge(id1 + 1, id2 + 1);
        }

        return g;
    }

    public static Graph createExample(int number) {
        String functionName = "createExample" + number;

        try {
            Method method = GraphGeneration.class.getDeclaredMethod(functionName);
            return (Graph) method.invoke(null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("Invalid number. No corresponding example.");
            return new Graph();
        }
    }

    /*
    1: 2 3 4 5
    2: 1 3 4 5
    3: 1 2 4 5
    4: 1 2 3 5
    5: 1 2 3 4
    6: 7 8 9
    7: 6 8 9
    8: 6 7 9
    9: 6 7 8 10 11
    10: 9
    11: 9
     */
    private static Graph createExample1() {
        Graph g = new Graph(11);

        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(1, 5);

        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(2, 5);

        g.addEdge(3, 4);
        g.addEdge(3, 5);

        g.addEdge(4, 5);

        g.addEdge(6, 7);
        g.addEdge(6, 8);
        g.addEdge(6, 9);

        g.addEdge(7, 8);
        g.addEdge(7, 9);

        g.addEdge(8, 9);

        g.addEdge(9, 10);
        g.addEdge(9, 11);

        return g;
    }

    /*
    1: 2 3
    2: 1 3 4 10 11 14
    3: 1 2 4 5 6 7
    4: 2 3
    5: 3 6 7 9
    6: 3 5 7 8 9
    7: 17 18 3 5 6
    8: 6
    9: 16 5 6
    10: 2 11 12 13 14
    11: 2 10 12 13
    12: 10 11 15
    13: 10 11
    14: 2 10
    15: 12
    16: 20 9
    17: 18 19 7
    18: 17 19 7
    19: 17 18
    20: 16
     */
    private static Graph createExample2() {
        Graph g = new Graph(20);

        g.addEdge(1, 2);
        g.addEdge(1, 3);

        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(2, 10);
        g.addEdge(2, 11);
        g.addEdge(2, 14);

        g.addEdge(3, 4);
        g.addEdge(3, 5);
        g.addEdge(3, 6);
        g.addEdge(3, 7);

        g.addEdge(5, 6);
        g.addEdge(5, 7);
        g.addEdge(5, 9);

        g.addEdge(6, 7);
        g.addEdge(6, 8);
        g.addEdge(6, 9);

        g.addEdge(7, 17);
        g.addEdge(7, 18);

        g.addEdge(9, 16);

        g.addEdge(10, 11);
        g.addEdge(10, 12);
        g.addEdge(10, 13);
        g.addEdge(10, 14);

        g.addEdge(11, 12);
        g.addEdge(11, 13);

        g.addEdge(12, 15);
        g.addEdge(16, 20);

        g.addEdge(17, 18);
        g.addEdge(17, 19);

        g.addEdge(18, 19);

        return g;
    }

    /*
    1: 2 3 4 5
    2: 1 3 4
    3: 1 2 4
    4: 1 2 3 6 7
    5: 1
    6: 4 7 8
    7: 4 6 8
    8: 6 7
     */
    private static Graph createExample3() {
        Graph g = new Graph(8);

        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(1, 5);

        g.addEdge(2, 3);
        g.addEdge(2, 4);

        g.addEdge(3, 4);

        g.addEdge(4, 6);
        g.addEdge(4, 7);

        g.addEdge(6, 7);
        g.addEdge(6, 8);

        g.addEdge(7, 8);

        return g;
    }

    /*
    1: 2 3 4
    2: 1 3 4 5 6
    3: 1 2 4
    4: 1 2 3 5 6
    5: 2 4 6
    6: 2 4 5
    7:
    8:
    9:
    10:
     */
    private static Graph createExample4() {
        Graph g = new Graph(10);

        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);

        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(2, 5);
        g.addEdge(2, 6);

        g.addEdge(3, 4);

        g.addEdge(4, 5);
        g.addEdge(4, 6);

        g.addEdge(5, 6);

        return g;
    }

}
