import java.util.*;

public class TopoSorter {
	private Graph graph;
	private State[] state;
	private int[] parent;
	private Stack<Integer> stack;
	enum State{
		UNVISITED,
		VISITING,
		VISITED
	}
	
	public TopoSorter(Graph graph) {
		this.graph = graph;//look at this graph
		state = new State[graph.size()];
		Arrays.fill(state, State.UNVISITED);
		parent = new int[graph.size()];//store how we got to a node
		Arrays.fill(parent, -1);
		stack = new Stack<>();
	}
	
	public TopoSortResult sort() {
		for(int i = 0; i < graph.size(); i++) {
			//the entire loop works for cycle checking
			if(state[i] == State.UNVISITED) {
				//if you haven't checked this node yet, do a d.f.s on node
				List<Integer> cycle = dfs(i);
				if (cycle != null) {
					//looking for a cycle, if we find one, return the cycle
					return TopoSortResult.cycleInt(cycle);
				}
			}
		}
		
		List<Integer> order = new ArrayList<>();
		while(!stack.isEmpty()) {
			order.add(stack.pop());
		}
		
		return TopoSortResult.orderInt(order);
	}
	
	private List<Integer> dfs(int node){
		state[node] = State.VISITING;
		
		for(int neighbor : graph.neighborsOf(node)) {
			if (state[neighbor] == State.UNVISITED) {
				//haven't been here yet, record how you got here, keep searching deeper
				parent[neighbor] = node;
				List<Integer> cycle = dfs(neighbor);
				if(cycle != null) return cycle;
			}
			else if (state[neighbor] == State.VISITING) {
				/*IMPORTANT this is where you see if you have reached a node in the stack
				 *if you did, that means there is a cycle and you want to return the cycle*/
				return buildCycle(node, neighbor);
			}
		}
		
		state[node] = State.VISITED;
		//we set this node as fully visited, so when you call d.f.s. it doesn't re-check
		stack.push(node);
		//put current node at bottom of stack /first in order
		return null;
	}
	
	private List<Integer> buildCycle(int start, int end) {
		//this will return the nodes and order of the cycle if one is detected
        List<Integer> cycle = new ArrayList<>();
        cycle.add(end);

        int current = start;
        while (current != end) {
            cycle.add(current);
            current = parent[current];
            //construct cycle in order of last to first
        }

        cycle.add(end);
        Collections.reverse(cycle);//reverse the cycle to be in correct order
        return cycle;
    }
}
