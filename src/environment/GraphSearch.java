package environment;
import java.util.*;
import environment.Graph.*;


/**
 * Created by ujansengupta on 3/14/17.
 */


public class GraphSearch
{

    public enum SEARCHMODE {
        DIJKSTRA, ASTAR
    }

    public int fill = 0;
    private float heuristicWeight = 0;


    private Map<Integer, Float> gMap;
    private Map<Integer, Float> fMap;
    private Map<Integer, Integer> prevNode;
    private PriorityQueue<Integer> openList;
    private Set<Integer> closedList;


    private compareCost comparator = new compareCost();


    /* Methods */

    public Set<Integer> getClosedList()
    {
        return closedList;
    }


    public ArrayList<Integer> aStarSearch (int startNode, int endNode, Graph graph)
    {
        return graphSearch(startNode, endNode, graph, SEARCHMODE.ASTAR);
    }

    public ArrayList<Integer> dijkstraSearch(int startNode, int endNode, Graph graph)
    {
        return graphSearch(startNode, endNode, graph, SEARCHMODE.DIJKSTRA);
    }


    public ArrayList<Integer> graphSearch(int startNode, int endNode, Graph graph, SEARCHMODE mode)
    {
        int currentNode, currentNeighbor;
        float newCost = 0;

        /* f(n) = g(n) + w * h(n) */
        gMap = new HashMap<>();
        fMap = new HashMap<>();
        prevNode = new HashMap<>();
        ArrayList<Edge> edges;

        openList = new PriorityQueue<>(comparator);
        closedList = new HashSet<>();

        openList.add(startNode);
        gMap.put(startNode, 0f);
        fMap.put(startNode, 1.01f * euclideanDistanceHeuristic(startNode, endNode, graph.nodeMap));

        while (!openList.isEmpty())
        {
            currentNode = openList.poll();

            if (currentNode == endNode)
                break;

            edges = graph.adjacencyList.get(currentNode);

            for (Edge edge : edges)
            {
                currentNeighbor = edge.dst;

                if (closedList.contains(currentNeighbor))
                    continue;

                newCost = gMap.get(currentNode) + edge.weight;

                if (!gMap.containsKey(currentNeighbor) || newCost < gMap.get(currentNeighbor))
                {
                    if (openList.contains(currentNeighbor))
                        openList.remove(currentNeighbor);

                    gMap.put(currentNeighbor, newCost);

                    switch (mode)
                    {
                        case DIJKSTRA:
                            heuristicWeight = 0;
                            break;
                        case ASTAR:
                            heuristicWeight = 1.05f;
                            break;
                    }

                    fMap.put(currentNeighbor, newCost + heuristicWeight * euclideanDistanceHeuristic(currentNeighbor, endNode, graph.nodeMap));

                    openList.add(currentNeighbor);
                    prevNode.put(currentNeighbor, currentNode);
                }
            }

            closedList.add(currentNode);
            fill = closedList.size();

        }

        return getPath(startNode, endNode);
    }


    public ArrayList<Integer> getPath(int startNode, int endNode)
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



    float euclideanDistanceHeuristic (int node1, int node2, Map<Integer, Node> map)
    {
        return (float) (Math.sqrt(Math.pow((map.get(node2).location.x - map.get(node1).location.x), 2) +
                Math.pow((map.get(node2).location.y - map.get(node1).location.y), 2)));
    }

    float manhattanDistanceHeuristic(int node1, int node2, Map<Integer, Node> map)
    {
        return (map.get(node2).location.x - map.get(node1).location.x) + (map.get(node2).location.y - map.get(node1).location.y);
    }




    public class compareCost implements Comparator<Integer>
    {
        @Override
        public int compare(Integer o1, Integer o2) {
            return Float.compare(fMap.get(o1), fMap.get(o2));
        }
    }



}


