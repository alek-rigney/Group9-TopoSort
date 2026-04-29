import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		
		System.out.print("Enter number of vertices: ");
		int n = scn.nextInt();
		
		Graph g = new Graph(n);
		
		System.out.println("Enter number of edges: ");
		int e = scn.nextInt();
		
		System.out.println("Enter edges A B means A->B, one per line:");
		//from assignment example I added functionality as character instead of integers
		
		for(int i = 0; i < e; i++) {
			String fromStr = scn.next();
			String toStr = scn.next();
			
			int from = labelToInt(fromStr);
			int to = labelToInt(toStr);
			
			g.addEdge(from, to);
		}
		
		TopoSorter sorter = new TopoSorter(g);
		TopoSortResult result = sorter.sort();
		
		System.out.println();
		
		if(result.hasCycle()) {
			System.out.print("Cycle detected: ");
			printResult(result.getCycle());
		}
		else {
			System.out.print("Topological order: ");
			printResult(result.getOrder());
		}
		
		scn.close();
	}
	
	private static void printResult(List<Integer> list) {
		//to print in example format
		for(int i = 0; i<list.size(); i++) {
			System.out.print(intToLabel(list.get(i)));
			
			if( i < list.size()-1) {
				System.out.print(" -> ");
			}
		}
		System.out.println();
	}
	
	private static String intToLabel(int i) {
        return String.valueOf((char) ('A' + i));
        //A + index should get other labels
    }

    private static int labelToInt(String s) {
        return s.charAt(0) - 'A';
        //e.g C-A = 2 or index 2
    }
}
