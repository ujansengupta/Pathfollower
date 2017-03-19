package environment;

import java.util.*;

import environment.Graph.*;

@SuppressWarnings("WeakerAccess")



/**
 * Created by ujansengupta on 3/14/17.
 */
public class GraphSearch {


    Map<Integer, Float> costSoFar;
    Map<Integer, Integer> prevNode;

    public int fill = 0;

    public ArrayList<Node> aStarSearch (Node startNode, Node endNode, Map<Integer, ArrayList>)
    {
        int currentNode, currentNeighbour;
        float newCost;

        PriorityQueue<Node> queue = new PriorityQueue<>();


    }

    public ArrayList<Integer> dijkstraSearch(int startNode, int endNode, Map<Integer, ArrayList<Edge>> graph)
    {
        int currentNode, currentNeighbor;
        float newCost;

        costSoFar = new HashMap<>();
        prevNode = new HashMap<>();
        ArrayList<Edge> edges;

        compareCost comparator = new compareCost();
        PriorityQueue<Integer> openList = new PriorityQueue<>(comparator);
        Set<Integer> closedList = new HashSet<>();

        openList.add(startNode);
        costSoFar.put(startNode, 0f);

        if (startNode == endNode)
            return getPath(startNode, endNode);

        while (!openList.isEmpty())
        {
            currentNode = openList.poll();

            if (currentNode == endNode)
                break;

            edges = graph.get(currentNode);

            for (Edge edge : edges)
            {
                currentNeighbor = edge.dst;

                if (closedList.contains(currentNeighbor))
                    continue;

                newCost = costSoFar.get(currentNode) + edge.weight;

                if (!costSoFar.containsKey(currentNeighbor) || newCost < costSoFar.get(currentNeighbor))
                {
                    if (openList.contains(currentNeighbor))
                        openList.remove(currentNeighbor);

                    costSoFar.put(currentNeighbor, newCost);
                    openList.add(currentNeighbor);
                    prevNode.put(currentNeighbor, currentNode);
                }
            }

            closedList.add(currentNode);
            fill = closedList.size();

        }

        return getPath(startNode, endNode);
    }


    ArrayList<Integer> getPath(int startNode, int endNode)
    {
        ArrayList<Integer> path = new ArrayList<>();

        path.add(endNode);

        if (startNode != endNode)
        {
            int previous = prevNode.get(endNode);

            while (previous != startNode)
            {
                path.add(previous);
                previous = prevNode.get(previous);
            }
        }

        path.add(startNode);

        return path;
    }




    public class compareCost implements Comparator<Integer>
    {
        @Override
        public int compare(Integer o1, Integer o2) {
            return Float.compare(costSoFar.get(o1), costSoFar.get(o2));
        }
    }



}


