package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Exception.ExceptionofDirection;
import Exception.ExceptionofDirectionTest;
import Exception.ExceptionofInput;
import Exception.ExceptionofUndirection;
import Exception.ExceptionofUnproperEdge;
import edge.CommentTie;
import edge.Edge;
import edge.ForwardTie;
import edge.FriendTie;
import factory.ForwardTieFactory;
import factory.FriendTieFactory;
import factory.PersonVertexFactory;
import factory.SocialNetworkFactory;
import graph.SocialNetwork;
import vertex.Person;
import vertex.Vertex;



public class SocialNetworkApp {
	
	private void help() {
		System.out.println("vertex --add label type");
		//vertex --add “label” “word” 
		System.out.println("edge --add label type [weighted=Y|N] [weight] [directed=Y|N] v1, v2");
		//edge --add “label” “word” weight=Y 2 directed=Y “This” “is”
		System.out.println("vertex --delete regex");
		//vertex --delete ab*
		//vertex --delete a*b*
		System.out.println("edge --delete regex");
		//edge --delete cd*
		//edge --delete c*d*
		System.out.println("hyperedge --add label type vertex1, ..., vertexn");
		//hyperedge --add “ForeverYoung” “SameMovieHyperEdge” “abb” ,“abbb” ,“aabb”
		//hyperedge --add “ForeverYoung” “SameMovieHyperEdge” “abb” ,“aabb” 
		//hyperedge --add “ForeverYoung” “SameMovieHyperEdge” “abb” 
	}
	private static void printstr(String[] str) {
		for(int i=0;i<str.length;i++) {
			System.out.println(str[i]);
		}
	}
	private static void justvertex(String str, SocialNetwork graph) throws ExceptionofInput {
		System.out.println("your input string is:\t"+str);	
		str = str.toLowerCase().replace("vertex --add", "").replaceAll("”", "").replaceAll("“", "").trim();
		String[] info = str.split(" +");
		Person r = (Person) new PersonVertexFactory().createVertex(info[0],info);
		graph.addVertex(r);
	}
	
	private static void justedge(String str,SocialNetwork graph) throws ExceptionofInput, ExceptionofUnproperEdge {
		System.out.println("your input string is:\t"+str);
		str = str.toLowerCase().replace("edge --add", "").replace("”", "").replace("“", "").replace("=", " ").trim();
		String[] info = str.split(" +");
		printstr(info);
		Person src = (Person) graph.findvertexbylabel(info[7]);
		Person tar = (Person) graph.findvertexbylabel(info[8]);
		List<Vertex> vers = new ArrayList<>();
		vers.add(tar);
		vers.add(src);
		double weight = Double.parseDouble(info[4]);
		if(info[1].equals("FriendTie")) {
		FriendTie r = (FriendTie) new FriendTieFactory().createEdge(info[0], vers, weight);
		graph.addEdge(r);
		}
		else if(info[1].equals("ForwardTie")) {
			ForwardTie r = (ForwardTie) new ForwardTieFactory().createEdge(info[0], vers, weight);
			graph.addEdge(r);
		}else if(info[1].equals("CommentTie")) {
			CommentTie r = (CommentTie) new FriendTieFactory().createEdge(info[0], vers, weight);
			graph.addEdge(r);
		}
	}
	private static void justremovev(String str,SocialNetwork graph) {
		System.out.println("your input string is:\t"+str);
		System.out.println(graph.vertices().size());
		String r = str.replace("vertex --delete", "").trim();
		System.out.println(r);
		Pattern v = Pattern.compile(r);
		Set<Vertex> vertices = graph.vertices();
		for(Vertex sv:vertices) {
			Matcher vertex = v.matcher(sv.getlabel());
			if(vertex.matches()) {
				graph.removeVertex(sv);
			}	
		}
		System.out.println(graph.vertices().size());
	}
	private static void justremovee(String str,SocialNetwork graph) throws ExceptionofInput, ExceptionofUnproperEdge {
		System.out.println("your input string is:\t"+str);
		System.out.println(graph.edges().size());
		String r = str.replace("edge --delete", "").trim();
		System.out.println(r);
		Pattern v = Pattern.compile(r);
		Set<Edge> edges = graph.edges();
		for(Edge e:edges) {
			Matcher edgem = v.matcher(e.getlabel());
			if(edgem.matches()) {
				graph.removeEdge(e);
			}	
		}
		System.out.println(graph.edges().size());
	}
	
	private static void justhyper(String str,SocialNetwork graph) {
		System.out.println("your input string is:\t"+str);
		String r = str.replace("hyperedge --add", "").replace(",", "").replace("“", "").replace("”", "").trim();
		String[] info = r.split(" ");
		for(int i=0;i<info.length;i++) {
			System.out.println(info[i]);
		}
		System.out.println(r);
	}
	public static void main(String[] args) throws IOException, ExceptionofInput, ExceptionofUnproperEdge, ExceptionofUndirection, ExceptionofDirection {
		SocialNetwork graph = (SocialNetwork) new SocialNetworkFactory().createGraph("src/source/test2right.txt");
		//build("src/source/test1.txt",graph);
		//System.out.println(graph.vertices().size());
		//System.out.println(graph.edges().size());
		Pattern vertexp = Pattern.compile("vertex --add(\\s+“[A-Za-z0-9]+”\\s*){2}");
		Pattern edgep = Pattern.compile("edge --add(\\s+“[A-Za-z0-9]+”\\s*){2}(\\s*weight=(Y|N)\\s+[0-9])(\\s*directed=(Y|N))(\\s+“[A-Za-z0-9]+”\\s*){2}");
		Pattern removev = Pattern.compile("vertex --delete[\\s\\S]*");
		Pattern removee = Pattern.compile("edge --delete[\\s\\S]*");
		Pattern hyperedge = Pattern.compile("hyperedge --add(\\s*“[A-Za-z0-9]+”\\s*){2}(\\s*“[A-Za-z0-9]+”\\s*,*)+");
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String str = "run";
		while(!str.equals("exit")) {
		System.out.println("\tinpur your order(can use help to show all orders)");
			str = stdin.readLine();
			Matcher vertexm = vertexp.matcher(str);
			Matcher edgem = edgep.matcher(str);
			Matcher removevm = removev.matcher(str);
			Matcher removeem = removee.matcher(str);
			Matcher hyperm = hyperedge.matcher(str);
			if(vertexm.matches()) {
				justvertex(str,graph);
				System.out.println(graph.vertices().size());
			}
			//Matcher edgem = edgep.matcher(str);
			else if(edgem.matches()) {
				System.out.println(graph.edges().size());
				justedge(str,graph);
				System.out.println(graph.edges().size());
			}
			//Matcher removevm = removev.matcher(str);
			else if(removevm.matches()) {
				System.out.println(str);
				justremovev(str,graph);
				
			}
			//Matcher removeem = removee.matcher(str);
			else if(removeem.matches()) {
				System.out.println(str);
				justremovee(str,graph);
				
			}
			//Matcher hyperm = hyperedge.matcher(str);
			else if(hyperm.matches()) {
				System.out.println(str);
				justhyper(str,graph);				
			}else {
				System.out.println("faile");
			}
		}
		System.out.println("exit");
	}
	
}