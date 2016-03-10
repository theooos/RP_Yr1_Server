package routePlanning.dataStructures;

/**
 * Data structure used for route planning to build a linked list of nodes (representing a path)
 * @author Szymon
 *
 */
public class CameFrom {
	public Node fromNode;
	public Node toNode;
	
	public CameFrom(Node fromNode, Node toNode) {
		this.fromNode = fromNode;
		this.toNode = toNode;
	}
}
