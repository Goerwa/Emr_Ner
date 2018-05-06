package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import vertex.Vertex;
import edge.Edge;

public class ConcreteGraph implements Graph<Vertex,Edge>{
	
	private final Set<Vertex> vertices = new HashSet<>();
    private  Set<Edge> edges = new HashSet<>();
    
    // Abstraction function:
    // AF(vertices,Edge) = {vertices,edge|vertices != null && edges'weight >0}.
    //
    // Representation invariant:
    // all vertices are not null and all edges'weight are greater than 0.
    //
    // Safety from rep exposure:
    // All fileds are private
 	// All return value types are mutable, create new variables to prevent sharing with clients.
    //
    
    private void checkRep() {
		assert vertices != null;
		for(Edge edge:edges) {
			assert edge.getweight() >= 0;
		}
    }
    
    public ConcreteGraph() {
		checkRep();
    }
    
	@Override
	public boolean addVertex(Vertex v) {
		boolean r;
		for(Vertex v1:vertices) {
			if(v1.equals(v))
				return true;
		}
		if(v != null)
			r = vertices.add(v);
		else
			return false;
		checkRep();
		return r;
	}

	@Override
	public boolean removeVertex(Vertex v) {
		if (!vertices.contains(v)) {
            checkRep();
            return false;
        }
    		vertices.remove(v);
        for (Iterator<Edge> iterator = edges.iterator(); iterator.hasNext();) {
        		Edge edge = iterator.next();
        		if (edge.getsource().equals(v) || edge.gettarget().equals(v)) {
        			iterator.remove();
        		}
        }
        checkRep();
        return true;	
	}

	@Override
	public Set<Vertex> vertices() {
		return new HashSet<Vertex>(vertices);
	}

	@Override
	public Map<Vertex, List<Double>> sources(Vertex target) {
		Map<Vertex, List<Double>> result = new HashMap<>();
		for(Edge edge:edges) {
			if(target.equals(edge.gettarget())) {
				List<Double> l = new ArrayList<>();
				l.add(edge.getweight());
				result.put(edge.getsource(), l);
			}
		}
		
		checkRep();
		return result;
	}
	
	@Override
	public Map<Vertex, Double> sourcesone(Vertex target) {
		Map<Vertex, Double> result = new HashMap<>();
		for(Edge edge:edges) {
			if(target.equals(edge.gettarget())) {
				Double l = new Double(edge.getweight());
				result.put(edge.getsource(), l);
			}
		}
		
		checkRep();
		return result;
	}

	@Override
	public Map<Vertex, List<Double>> targets(Vertex source) {
		Map<Vertex, List<Double>> result = new HashMap<>();
		for(Edge edge:edges) {
			if(source.equals(edge.getsource())) {
				List<Double> l = new ArrayList<>();
				l.add(edge.getweight());
				result.put(edge.gettarget(), l);
			}
		}
		
		checkRep();
		return result;
	}
	
	
	@Override
	public Map<Vertex, Double> targetsone(Vertex target) {
		Map<Vertex, Double> result = new HashMap<>();
		for(Edge edge:edges) {
			if(target.equals(edge.getsource())) {
				Double l = new Double(edge.getweight());
				result.put(edge.gettarget(), l);
			}
		}
		
		checkRep();
		return result;
	}
	
	@Override
	public boolean addEdge(Edge edge) {
		boolean r;
		if(!vertices.contains(edge.getsource()) || !vertices.contains(edge.gettarget())) {
			return false;
		}
		for(Edge e:edges) {
			if(e.equals(edge)) {
				if(edge.getweight() < 0.000001) {
					edges.remove(e);
					//System.out.println("remove");
					return true;
				}else if(edge.getweight() >0 ) {
					edges.remove(e);
					edges.add(edge);
					return true;
				}
			}
		}/*
		if((edge != null) && (vertices.contains(edge.gettarget())) && (vertices.contains(edge.getsource()))) {
			r = edges.add(edge);
		}
		*/
		if((edge != null)) {
			r = edges.add(edge);
		}else {
			return false;
		}	
		checkRep();
		return r;
	}

	@Override
	public boolean removeEdge(Edge edge) {
		boolean r = false;
		Edge redge = null;
		for(Edge e:edges) {
			if(edge.equals(e)) {
			r = true;
			redge = e;
			}
		}
		edges.remove(redge);
		if(!r) {
			//System.out.println("not cotain");
			checkRep();
			return false;
		}else {
			checkRep();
			return r;
		}	
	}
	
	@Override public boolean containedge(Edge edge) {
		for(Edge e:edges) {
			if(e.equals(edge)) 
				return true;
		}
		return false;
	}
	
	@Override
	public int getdistance(Vertex v1, Vertex v2) {
		if(v1.equals(v2)) 
			return 1;
		if(!vertices.contains(v1) || !vertices.contains(v2))
			return 0;
		Queue<Vertex> queue = new LinkedList<Vertex>();
		Map<String,Integer> visit = new HashMap<>();
		Set<Vertex> marked = new HashSet<>();
		queue.add(v1);
		visit.put(v1.getlabel(), 0);
		marked.add(v1);
		while(!queue.isEmpty()) {
			//Set edges1
			Vertex old = queue.poll();
			for(Edge e:edges) {
				//System.out.println(old.getlabel()+ "  "+e.getsource().getlabel()+"  "+e.gettarget().getlabel() );
				if(e.gettarget().equals(v2) && e.getsource().equals(old)) {
					int oldv = visit.get(old.getlabel());
					//System.out.println(oldv);
					return ++oldv; 
				}
			}
			for(Edge e:edges) {
				//System.out.println(old.getlabel()+ "  "+e.getsource().getlabel()+"  "+e.gettarget().getlabel() );
				if(e.getsource().equals(old) && !marked.contains(e.gettarget())) {
					
					int oldv = visit.get(old.getlabel());
					visit.remove(old.getlabel());
					oldv++;
					visit.put(e.gettarget().getlabel(), oldv);
					marked.add(e.gettarget());
					queue.add(e.gettarget());

				}
			}
			}
		return 0;
		
	}
	
	
	@Override
	public Set<Edge> edges() {
		return new HashSet<Edge>(edges);
	}

}
