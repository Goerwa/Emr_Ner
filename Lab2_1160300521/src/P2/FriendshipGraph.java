package P2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import P1.graph.Graph;
import P2.Person;

public class FriendshipGraph {
	private int num = 0;
	// Abstraction function:
    // represent a FriendshipGrap that vertices and edges are reasonable. 
    //
    // Representation invariant:
    // No repeating vertices, no edges with no source or target
    //
    // Safety from rep exposure:
    // All fileds are private
	// the get methods can prevent sharing data with clients. 
    //
	
	
	Graph<Person> graph = Graph.empty();
	FriendshipGraph() {
	//Graph<Person> graph = Graph.empty();
	}
	
	
	public boolean addVertex(Person person) {
		if (person == null) {
			throw new IllegalArgumentException("the person is null!");
		}if(isingraph(person)){
			throw new IllegalArgumentException("the person is in graph!");
		}
		graph.add(person);
		this.num++;
		return true;
	}
	
	private boolean isingraph(Person person) {
		Set<Person> r = new HashSet<Person>();
		r = graph.vertices();
		if(r.contains(person))
			return true;
		else
			return false;
	}
	
	public int getnum() {
		return num;
	}
	
	public boolean addEdge(Person person1, Person person2) {
		if (person1 == null || person2 == null) {
			throw new IllegalArgumentException("a person is not exist in this FriendGraph!");
		}
		if (person1.getName().equals(person2.getName())) {
			throw new IllegalArgumentException("the names of the two persons are the same!");
		}
		if(!isingraph(person1)) {
			throw new IllegalArgumentException("the name of one person1 is not in the graph!");
		}
		if(!isingraph(person2)) {
			throw new IllegalArgumentException("the name of one person2 is not in the graph!");
		}
		graph.set(person1,person2,1);
		return true;
	}
	
	public int getDistance(Person person1, Person person2) {
		if (person1 == null || person2 == null) {
			throw new IllegalArgumentException("a person is null!");
		}
		if(!isingraph(person1) || !isingraph(person2)) {
			throw new IllegalArgumentException("the name of one person is not in the graph!");
		}
		/* use BFS to get the distance */
		return bfs(person1, person2);
	}
	
	private int bfs(Person person1,Person person2) {
		Set<String> marked = new HashSet<String>();
		int count = 0;
		int depth = Integer.MAX_VALUE;
		boolean find = false;
		if(person1.getName().equals(person2.getName()))
			return 0;
		Queue<Person> queue = new LinkedList<Person>();
		Map<Person,Integer> peoples = new HashMap<>();
		peoples.put(person1, count);
		marked.add(person1.getName());
		queue.add(person1);
		
		while(!queue.isEmpty()) {
			Person v = queue.remove();
			//System.out.println(v.getName());
			count = peoples.get(v);
			count++;
			//System.out.println(count);
			Map<Person,Integer> next = graph.targets(v);
			List<Person> nextpeople = new ArrayList<>(next.keySet());
			//for(Person people1:nextpeople)
			//System.out.println(people1.getName());
			for(Person people:nextpeople) {
				peoples.put(people, count);
				if(!marked.contains(people.getName())) {
					if(people.getName().equals(person2.getName())) {
						if(count < depth) {
							find = true;
							depth = count;
						}
					}
					marked.add(people.getName());
					queue.add(people);
				}
			}
		}
		if(find)
			return depth;
		else
			return -1;
	}
	
	public Set<String> vertices() {
		Set<Person> r = new HashSet<Person>();
		r = graph.vertices();
		Set<String> result = new HashSet<>();
		for(Person people:r) {
			result.add(people.getName());
		}
		return result;
		
	}
	
	public Map<String, Integer> sources(Person person) {
		Map<Person, Integer> r = new HashMap<>();
		r = graph.sources(person);
		Map<String, Integer> result = new HashMap<>();
		for(Person people:r.keySet()) {
			result.put(people.getName(), 1);
		}
		return result;	
	}
	
	public Map<String, Integer> targets(Person person) {
		Map<Person, Integer> r = new HashMap<>();
		r = graph.targets(person);
		Map<String, Integer> result = new HashMap<>();
		for(Person people:r.keySet()) {
			result.put(people.getName(), 1);
		}
		return result;
		
	}

}
