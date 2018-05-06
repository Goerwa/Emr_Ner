package graph;

import java.util.HashSet;
import java.util.Set;

import edge.Edge;
import edge.NetworkConnection;
import vertex.Computer;
//import vertex.Router;
import vertex.Server;
import vertex.Vertex;


public class NetworkTopology extends ConcreteGraph{
	
	@Override
	public boolean addVertex(Vertex net) {
		if (net == null) {
			throw new IllegalArgumentException("the vertex is null!");
		}
		if(isingraph(net)){
			throw new IllegalArgumentException("the person is in graph!");
		}
		if(!confirmclass(net)) {
			System.out.println(net.tellclass());
			throw new IllegalArgumentException("the type of vertexis wrong!");
		}
		super.addVertex(net);
		return true;
	}
	
	private boolean isingraph(Vertex net) {
		Set<Vertex> r = new HashSet<Vertex>();
		r = super.vertices();
		if(r.contains(net))
			return true;
		else
			return false;
	}
	
	private boolean confirmclass (Vertex v) {
		//System.out.println(v.tellclass());
		if(v.tellclass().equals("Computer"))
			return true;
		else if(v.tellclass().equals("Router"))
			return true;
		else if(v.tellclass().equals("Server"))
			return true;
		else 
			return false;
	}
	@Override
	public boolean addEdge(Edge e) {
		Vertex v1 = e.getsource();
		Vertex v2 = e.gettarget();
		
		if (v1 == null || v2 == null) {
			throw new IllegalArgumentException("a vertex is not exist in this FriendGraph!");
		}
		if(!isingraph(v1) || !isingraph(v2)) {
			throw new IllegalArgumentException("one of the vertex is not in the graph!");
		}
		if(!confirmclass(v1) || !confirmclass(v2)) {
			throw new IllegalArgumentException("the type of vertex is wrong!");
		}
		
		super.addEdge(e);
		return true;
	}
}
