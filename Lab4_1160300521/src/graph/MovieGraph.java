package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import Exception.ExceptionofInput;
import Exception.ExceptionofUnproperEdge;
import edge.Edge;
import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import edge.SameMovieHyperEdge;
import factory.SocialNetworkFactory;
import vertex.Actor;
import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

public class MovieGraph extends ConcreteGraph{
	private List<SameMovieHyperEdge> hyperedge= new ArrayList<>();
	Logger logger = Logger.getLogger(SocialNetworkFactory.class);
	public MovieGraph() {
		
	}
	@Override 
	public boolean addVertex(Vertex net) throws ExceptionofInput {
		if(!confirmclass(net)) {
			logger.error("type of vertex is wrong");
			throw new ExceptionofInput("type of vertex is wrong");
		}
		super.addVertex(net); 
		return true; 
	}
	
	private boolean isingraph(Vertex v) {
		Set<Vertex> r = new HashSet<Vertex>();
		r = super.vertices();
		if(r.contains(v))
			return true;
		else
			return false;
	}
	
	private boolean confirmclass (Vertex v) {
		//System.out.println(v.tellclass());
		if(v.tellclass().equals("Movie"))
			return true;
		else if(v.tellclass().equals("Actor"))
			return true;
		else if(v.tellclass().equals("Director"))
			return true;
		else 
			return false;
	}
	
	@Override
	public boolean addEdge(Edge e) throws ExceptionofInput, ExceptionofUnproperEdge  {
		Vertex person1 = e.getsource();
		Vertex person2 = e.gettarget();
		if (person1.getlabel().equals(person2.getlabel())) {
			logger.error("the source and target vertex cannot be the same!");
			throw new ExceptionofUnproperEdge("the source and target vertex cannot be the same!");
		}
		if(!isingraph(person1)) {
			logger.error("the name of one person1 is not in the graph!");
			throw new ExceptionofUnproperEdge("the name of one person1 is not in the graph!");
		} 
		if(!isingraph(person2)) {
			logger.error("the name of one person2 is not in the graph!");
			throw new ExceptionofUnproperEdge("the name of one person2 is not in the graph!");
		}
		
		super.addEdge(e);
		return true;
	}
	

	
	public boolean addHyperEdge(SameMovieHyperEdge e) {
		boolean r = this.hyperedge.add(e);
		return r;
	}
	public List<SameMovieHyperEdge> getHyperEdge() {
		return new ArrayList<SameMovieHyperEdge>(hyperedge);
	}
	public void HyperEdgetoString(SameMovieHyperEdge e) {
		for(SameMovieHyperEdge h:hyperedge) {
			if(h.getlabel().equals(e.getlabel())) {
				for(Vertex v:h.getvers())
					System.out.println(v.getlabel());
			}
		}
	}
}
