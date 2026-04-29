import java.util.List;
public class TopoSortResult {
	/*is a storage space that holds the correct order when found
	 * OR the order of the list if found*/
	private List<Integer> order;
    private List<Integer> cycle;

    private TopoSortResult(List<Integer> order, List<Integer> cycle) {
        this.order = order;
        this.cycle = cycle;
    }

    public static TopoSortResult orderInt(List<Integer> order) {
    	//the sort has not found a cycle, so return the correct order
        return new TopoSortResult(order, null);
    }

    public static TopoSortResult cycleInt(List<Integer> cycle) {
    	//returns the cycle to the sort call which then gets stored in a result in main
        return new TopoSortResult(null, cycle);
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public List<Integer> getOrder() {
        return order;
    }

    public List<Integer> getCycle() {
        return cycle;
    }
}
