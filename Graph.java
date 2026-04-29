import java.util.*;
//using array list and stack, 

class Graph {
    private int vertices;
    private ArrayList<ArrayList<Integer>> adjacent;
    /*I was getting some errors because of the way I tried to use linked list
     * I ended up changing to array list because I can imitate linked well enough*/

    public Graph(int v) {
        vertices = v;
        adjacent = new ArrayList<>();

        for (int i = 0; i < v; i++) {
            adjacent.add(new ArrayList<>());
        }
    }
    public int addVertex() {
    	//adding a vertex at end of list after graph is initialized, returning index
    	adjacent.add(new ArrayList<>());
    	return adjacent.size() - 1;
    }
    public void addEdge(int from, int to) {
        adjacent.get(from).add(to);
    }

    public ArrayList<Integer> neighborsOf(int v) {
        return adjacent.get(v);
    }

    public int size() {
        return vertices;
    }
}
