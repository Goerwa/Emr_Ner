package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import edge.CommentTie;
import edge.Edge;
import edge.ForwardTie;
import edge.FriendTie;
import vertex.Person;
import vertex.Vertex;;

public class SocialNetwork extends ConcreteGraph{
	
	
	public SocialNetwork() {
		
	}
	@Override
	public boolean addVertex(Vertex person) {
		if (person == null) {
			throw new IllegalArgumentException("the person is null!");
		}
		if(isingraph(person)){
			throw new IllegalArgumentException("the person is in graph!");
		}
		if(!person.tellclass().equals("Person")) {
			throw new IllegalArgumentException("the type of vertex must be person!");
		}
		super.addVertex(person);
		return true;
	}
	
	private boolean isingraph(Vertex person) {
		Set<Vertex> r = new HashSet<Vertex>();
		r = super.vertices();
		if(r.contains(person))
			return true;
		else
			return false;
	}
	
	public Edge getedge (Edge e) {
		Set<Edge> edges = super.edges();
		for(Edge edge:edges) {
			if(edge.equals(e))
				return edge;
		}
		return null;
	}
	
	public boolean addEdge(Edge e) {
		Person person1 = (Person) e.getsource();
		Person person2 = (Person) e.gettarget();
		//System.out.println("the weight =  "+e.getweight());
		if (person1 == null || person2 == null) {
			throw new IllegalArgumentException("a person is not exist in this FriendGraph!");
		}
		if (person1.getlabel().equals(person2.getlabel())) {
			throw new IllegalArgumentException("the names of the two persons are the same!");
		}
		if(!isingraph(person1)) {
			throw new IllegalArgumentException("the name of one person1 is not in the graph!");
		}
		if(!isingraph(person2)) {
			throw new IllegalArgumentException("the name of one person2 is not in the graph!");
		}
		if(e.getweight() > 1.0 || e.getweight() <= 0){
			//System.out.println("the weight =  "+e.getweight());
			//return false;
			throw new IllegalArgumentException("the weight of edge must be (0,1]!");
		}

		if(super.edges().size() == 0) {
			if(e.tellclass().endsWith("FriendTie")) {
				FriendTie ne = new FriendTie(e.tellclass(),e.getsource(),e.gettarget(),1.0);
			super.addEdge(ne);
			}
			if(e.tellclass().endsWith("CommentTie")) {
				CommentTie ne = new CommentTie(e.tellclass(),e.getsource(),e.gettarget(),1.0);
				
			super.addEdge(ne);
			}
			if(e.tellclass().endsWith("ForwardTie")) {
				ForwardTie ne = new ForwardTie(e.tellclass(),e.getsource(),e.gettarget(),1.0);
				
			super.addEdge(ne);
			}
			return true;
		}
		boolean contain = super.containedge(e);
		double eweight = e.getweight();
		double old = 0;
		Set<Edge> edges = super.edges();
		for(Edge edge:edges) {
			if(edge.equals(e))
				old = edge.getweight();
		}
		if(super.addEdge(e)) {
			
			for(Edge edge:edges) {
				if(edge.equals(e)) continue;
				if(edge.tellclass().equals("FriendTie")) {
					if(contain) {
						//equals new
						double wei = edge.getweight() * (1.0 - eweight)/ (1.0 - old);
						FriendTie newedge = new FriendTie(edge.getsource(),edge.gettarget(),wei);
						super.removeEdge(edge);
						super.addEdge(newedge);
					}else {
						//equals other
						double wei = edge.getweight() * (1.0 - eweight);
						if(super.edges().isEmpty()) wei = 1.0;
						FriendTie newedge = new FriendTie(edge.getsource(),edge.gettarget(),wei);
						super.removeEdge(edge);
						super.addEdge(newedge);
					}
				}else if(edge.tellclass().equals("CommentTie")) {
					if(contain) {
						double wei = edge.getweight() * (1.0 - eweight)/ (1.0 - old);
						CommentTie newedge = new CommentTie(edge.getsource(),edge.gettarget(),wei);
						super.removeEdge(edge);
						super.addEdge(newedge);
					}else {
						double wei = edge.getweight() * (1.0 - eweight);
						if(super.edges().isEmpty()) wei = 1.0;
						CommentTie newedge = new CommentTie(edge.getsource(),edge.gettarget(),wei);
						super.removeEdge(edge);
						super.addEdge(newedge);
					}
				}else if(edge.tellclass().equals("ForwardTie")) {
					if(contain) {
						double wei = edge.getweight() * (1.0 - eweight)/ (1.0 - old);
						ForwardTie newedge = new ForwardTie(edge.getsource(),edge.gettarget(),wei);
						super.removeEdge(edge);
						super.addEdge(newedge);
					}else {
						double wei = edge.getweight() * (1.0 - eweight);
						if(super.edges().isEmpty()) wei = 1.0;
						ForwardTie newedge = new ForwardTie(edge.getsource(),edge.gettarget(),wei);
						super.removeEdge(edge);
						super.addEdge(newedge);
					}
				}else {
					return false;
				}
			}
		}else {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean removeEdge(Edge e) {
		Set<Edge> edges = super.edges();
		boolean r = false;
		boolean in = false;
		for(Edge edge:edges)
		if(edge.equals(e)) {
			in = true;
		}
		if(!in) {
			//System.out.println("not cotain");
			return false;
		}
		for(Edge edge:edges) {
			if(!edge.equals(e)) {
				double wa = e.getweight();
				double wei = edge.getweight()/ (1.0 - wa);
				//System.out.println(wei);
				FriendTie newedge = new FriendTie(edge.getsource(),edge.gettarget(),wei);
				super.removeEdge(edge);
				super.addEdge(newedge);
			}else {
				r = super.removeEdge(e);
			}
		}
		//System.out.println(r);
		return r;
		
	}
	/*
	public int getDistance(Person person1, Person person2) {
		if (person1 == null || person2 == null) {
			throw new IllegalArgumentException("a person is null!");
		}
		if(!isingraph(person1) || !isingraph(person2)) {
			throw new IllegalArgumentException("the name of one person is not in the graph!");
		}
		return bfs(person1, person2);
	}
	
	private int bfs(Person person1,Person person2) {
		Set<String> marked = new HashSet<String>();
		int count = 0;
		int depth = Integer.MAX_VALUE;
		boolean find = false;
		if(person1.getlabel().equals(person2.getlabel()))
			return 0;
		Queue<Person> queue = new LinkedList<Person>();
		Map<Person,Integer> peoples = new HashMap<>();
		peoples.put(person1, count);
		marked.add(person1.getlabel());
		queue.add(person1);
		
		while(!queue.isEmpty()) {
			Person v = queue.remove();
			//System.out.println(v.getName());
			count = peoples.get(v);
			count++;
			//System.out.println(count);
			Map<Person,Double> next = graph.targetsone(v);
			List<Person> nextpeople = new ArrayList<>(next.keySet());
			//for(Person people1:nextpeople)
			//System.out.println(people1.getName());
			for(Person people:nextpeople) {
				peoples.put(people, count);
				if(!marked.contains(people.getlabel())) {
					if(people.getlabel().equals(person2.getlabel())) {
						if(count < depth) {
							find = true;
							depth = count;
						}
					}
					marked.add(people.getlabel());
					queue.add(people);
				}
			}
		}
		if(find)
			return depth;
		else
			return -1;
	}
	*/
	public Set<String> verticesp() {
		Set<Vertex> r = new HashSet<>();
		r = super.vertices();
		Set<String> result = new HashSet<>();
		for(Vertex people:r) {
			result.add(people.getlabel());
		}
		return result;
		
	}
	
	public Map<String, Double> sources(Person person) {
		Map<Vertex, Double> r = new HashMap<>();
		r = super.sourcesone(person);
		Map<String, Double> result = new HashMap<>();
		for(Vertex people:r.keySet()) {
			result.put(people.getlabel(), 1.0);
		}
		return result;	
	}
	
	public Map<String, Double> targets(Person person) {
		Map<Vertex, Double> r = new HashMap<>();
		r = super.targetsone(person);
		Map<String, Double> result = new HashMap<>();
		for(Vertex people:r.keySet()) {
			result.put(people.getlabel(), 1.0);
		}
		return result;
		
	}
}
