package graph;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import Exception.ExceptionofInput;
import Exception.ExceptionofUnproperEdge;
import edge.Edge;
import edge.NetworkConnection;
import factory.SocialNetworkFactory;
import vertex.Computer;
//import vertex.Router;
import vertex.Server;
import vertex.Vertex;


public class NetworkTopology extends ConcreteGraph{
	 Logger loggersc = Logger.getLogger(SocialNetworkFactory.class);
	@Override
	public boolean addVertex(Vertex net) throws ExceptionofInput {
		if(!confirmclass(net)) {
			logger.error("type of vertex is wrong");
			throw new ExceptionofInput("type of vertex is wrong");
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
	public boolean addEdge(Edge e) throws ExceptionofInput, ExceptionofUnproperEdge {
		Vertex person1 = e.getsource();
		Vertex person2 = e.gettarget();
		
		if (person1.getlabel().equals(person2.getlabel())) {
			loggersc.error("the source and target vertex cannot be the same!");
			throw new ExceptionofUnproperEdge("the source and target vertex cannot be the same!");
		}
		if(!isingraph(person1)) {
			loggersc.error("the name of one person1 is not in the graph!");
			throw new ExceptionofUnproperEdge("the name of one person1 is not in the graph!");
		} 
		if(!isingraph(person2)) {
			loggersc.error("the name of one person2 is not in the graph!");
			throw new ExceptionofUnproperEdge("the name of one person2 is not in the graph!");
		}
		
		super.addEdge(e);
		return true;
	}
	public String vertexbyclass(String str) {
		for(Vertex v:super.vertices()) {
			if(v.getlabel().equals(str)) {
				return v.tellclass();
			}
		}
		return null;
	}
	@Override
	public Vertex findvertexbylabel(String str) {
		for(Vertex v:super.vertices()) {
			if(v.getlabel().equals(str)) {
				return v;
			}
		}
		return null;
	}
}
