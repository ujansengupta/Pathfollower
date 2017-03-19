package environment;

import processing.core.PVector;

import java.util.*;

/**
 * Created by ujansengupta on 3/11/17.
 */

@SuppressWarnings("WeakerAccess")

public class Graph
{

    public Map<Integer, Node> nodeMap = new HashMap<>();
    public Map<Integer, ArrayList<Edge>> adjacencyList;

    public Graph()
    {
        adjacencyList = new HashMap<>();
    }

    public Map<Integer, ArrayList<Edge>> buildGraph(ArrayList<Integer> invalidNodes, PVector numTiles)
    {
        int index;
        int tilesX = (int)numTiles.x;
        int tilesY = (int)numTiles.y;

        for (int i = 0; i < tilesY; i++)
        {
            for (int j = 0; j < tilesX; j++)
            {
                index = i * tilesY + j;

                if (invalidNodes.contains(index))
                    continue;

                ArrayList<Edge> edges = new ArrayList<>();

                nodeMap.put(index, new Node(index, new PVector(j, i)));

                /* Check neighbours clockwise */

                if (j - 1 >= 0 && (!invalidNodes.contains(index - 1)))
                    edges.add(new Edge(index, index - 1, 1));

                if (j - 1 >= 0 && i + 1 < tilesY && (!invalidNodes.contains(index - 1 + tilesX)))
                    edges.add(new Edge(index, index - 1 + tilesX, (float)Math.sqrt(2)));

                if (i + 1 < tilesY && (!invalidNodes.contains(index + tilesX)))
                    edges.add(new Edge(index, index + tilesX, 1));

                if (j + 1 < tilesX && i + 1 < tilesY && (!invalidNodes.contains(index + 1 + tilesX)))
                    edges.add(new Edge(index, index + 1 + tilesX, (float)Math.sqrt(2)));

                if (j + 1 < tilesX && (!invalidNodes.contains(index + 1)))
                    edges.add(new Edge(index, index + 1, 1));

                if (j + 1 < tilesX && i - 1 >= 0 && (!invalidNodes.contains(index + 1 - tilesX)))
                    edges.add(new Edge(index, index + 1 - tilesX, (float)Math.sqrt(2)));

                if (i - 1 >= 0 && (!invalidNodes.contains(index - tilesX)))
                    edges.add(new Edge(index, index - tilesX, 1));

                if (i - 1 >= 0 && j - 1 >= 0 && (!invalidNodes.contains(index - 1 - tilesX)))
                    edges.add(new Edge(index, index - 1 - tilesX, (float)Math.sqrt(2)));

                adjacencyList.put(index, edges);

            }
        }

        return adjacencyList;
    }



    /* Helper Methods */

    public class Node
    {
        int nodeID;
        PVector location;

        public Node(int ID, PVector loc)
        {
            this.nodeID = ID;
            this.location = loc;
        }
    }

    public class Edge
    {
        int src, dst;
        float weight;

        public Edge(int src, int dst, float weight)
        {
            this.src = src;
            this.dst = dst;
            this.weight = weight;
        }

        public int getSrc()
        {
            return src;
        }

        public int getDst()
        {
            return dst;
        }

        public float getWeight()
        {
            return weight;
        }

    }
}
