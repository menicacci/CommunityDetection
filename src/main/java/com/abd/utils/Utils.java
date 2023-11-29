package main.java.com.abd.utils;

import java.util.*;

public class Utils {

    public static HashMap<Integer, List<Integer>> swapEdgesIDs(HashMap<Integer, List<Integer>> edges, HashMap<Integer, Integer> correspondence) {
        HashMap<Integer, List<Integer>> newEdges = new HashMap<>();

        edges.forEach((nodeID, neighbouringNodeIDs) -> {
            newEdges.put(correspondence.get(nodeID), new ArrayList<>());
            neighbouringNodeIDs.forEach(neighbourID -> {
                newEdges.get(correspondence.get(nodeID)).add(correspondence.get(neighbourID));
            });
        });

        return newEdges;
    }

    public static void accumulateValues(int[] array) {
        int start = 0;
        for (int i = 0; i < array.length; i++) {
            int num = array[i];
            array[i] = start;
            start += num;
        }
    }

    public static int[] orderUsingValuesPosition(int[] array, int[] valuePositions) {
        int i;
        int len = array.length;
        int[] orderedArray = new int[len];

        for (i = 0; i < len; i++) {
            orderedArray[valuePositions[array[i]]] = i;
            valuePositions[array[i]]++;
        }

        // Reset the values of array valuePositions to the beginning values
        // Each element e of this array will now point to the first element such that orderedArray[e] <= e
        for (i = valuePositions.length - 1; i > 0; i--) {
            valuePositions[i] = valuePositions[i - 1];
        }
        valuePositions[0] = 0;

        return orderedArray;
    }

    public static int[] storeArrayPositions(int[] array) {
        int len = array.length;
        int[] pos = new int[len];
        for (int i = 0; i < len; i++) {
            pos[array[i]] = i;
        }

        return pos;
    }

    public static void swapElementsUsingIndexArray(int[] sort, int[] pos, int posFrom, int posTo) {
        int F_Sort = sort[posFrom];
        int F_Pos = pos[sort[posFrom]];
        int S_Pos = pos[posTo];

        sort[posFrom] = posTo;
        sort[pos[posTo]] = F_Sort;

        pos[F_Sort] = S_Pos;
        pos[posTo] = F_Pos;
    }

    public static int getLowPoint(List<Integer[]> edges, int maxSize) {
        int lowpoint = maxSize;

        if (edges != null && !edges.isEmpty()) {
            for (Integer[] edge : edges) {
                // Get the lowest id of the node's connected
                int minNode = Math.min(edge[0], edge[1]);
                // Update if lower than lowpoint
                lowpoint = Math.min(lowpoint, minNode);
            }
        }

        return lowpoint;
    }

    public static <T> Set<T> singleElementSetUnion(Set<T> set, T value) {
        Set<T> newSet = new HashSet<>(Collections.singleton(value));
        return union(set, newSet);
    }

    public static <T> Set<T> union(Set<T> set1, Set<T> set2) {
        Set<T> resultSet = new HashSet<>(set1);
        resultSet.addAll(set2);
        return resultSet;
    }

    public static <T> Set<T> intersection(Set<T> set1, List<T> set2) {
        Set<T> resultSet = new HashSet<>(set1);
        resultSet.retainAll(set2);
        return resultSet;
    }

    public static <T> Set<T> difference(Set<T> set1, List<T> set2) {
        Set<T> resultSet = new HashSet<>(set1);
        resultSet.removeAll(set2);
        return resultSet;
    }

}
