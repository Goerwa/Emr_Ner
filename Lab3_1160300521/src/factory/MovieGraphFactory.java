package factory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edge.Edge;
import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import edge.SameMovieHyperEdge;
import graph.Graph;
import graph.GraphPoet;
import graph.MovieGraph;
import vertex.Actor;
import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

public class MovieGraphFactory extends GraphFactory{
	
	public MovieGraphFactory() {
		
	}
	private static String replacev(String headline) {
		headline = headline.replace("Vertex = <", "");
		headline = headline.replace(">", "");
		headline = headline.replace("<", "");
		headline = headline.replace("“", "");
		headline = headline.replace("”", "");
		//System.out.println(headline);
		return headline;
	}
	
	private static String replacee(String headline) {
		headline = headline.replace("Edge = <", "");
		headline = headline.replace(">", "");
		headline = headline.replace("“", "");
		headline = headline.replace("”", "");
		//System.out.println(headline);
		return headline;
	}
	private static String replaceh(String headline) {
		headline = headline.replace("Edge = <", "");
		headline = headline.replace(">", "");
		headline = headline.replace("“", "");
		headline = headline.replace("”", "");
		headline = headline.replace("{", "");
		headline = headline.replace("}", "");
		//System.out.println(headline);
		return headline;
	}
	public static void build(String fileName, MovieGraph graph) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String headline;
			Pattern p = Pattern.compile("Vertex = <[“a-zA-Z0-9”,\\s+]*[<[“a-zA-Z0-9”[,\\s]+]+[“[0-9].[0-9]”]+>]+>");
			Pattern e = Pattern.compile("Edge = <[“a-zA-Z0-9”[,]\\s+]*>");
			Pattern h = Pattern.compile("HyperEdge = <[“a-zA-Z0-9”,\\s+]*[{[“a-zA-Z0-9”[,\\s]+]+}]+>");
			while((headline = br.readLine()) != null) {
				//System.out.println(headline);
				Matcher m = p.matcher(headline);
				if(m.matches() == true) {
					String replace = null;
					headline = replacev(headline);
					String str[] = null;
					str = headline.split(",");
					for(int i=0;i<str.length;i++) {
						str[i] = str[i].trim();
						//System.out.println(str[i]);
					}
					if(str[1].equals("Director")) {
						//System.out.println(str[0]);
						Director w = new Director(str[0]);
						w.fillVertexInfo(str);
						graph.addVertex(w);
					}
					else if(str[1].equals("Actor")) {
						Actor w = new Actor(str[0]);
						w.fillVertexInfo(str);
						graph.addVertex(w);
					}
					else if(str[1].equals("Movie")) {
						Movie w = new Movie(str[0]);
						w.fillVertexInfo(str);
						graph.addVertex(w);
					}
				}
				Matcher em = e.matcher(headline);
				if(em.matches() == true) {
					String replace = null;
					headline = replacee(headline);
					//System.out.println(headline);
					String str[] = null;
					str = headline.split(",");
					str[1] = str[1].trim();
					str[2] = str[2].trim();
					double weight = Double.valueOf(str[2]);
					str[3] = str[3].trim();
					str[4] = str[4].trim();
					str[5] = str[5].trim();
					Set<Vertex> words = graph.vertices();
					Vertex src = null;
					Vertex tar = null;
					/*
					for(Vertex w:words) {
						System.out.println(w.getlabel()+" is a "+w.tellclass());
					}*/
					for(Vertex w:words) {
						if(w.getlabel().equals(str[3])) {
							if(w.tellclass() == "Movie")
							src = (Movie) w;
						}
						if(w.getlabel().equals(str[3])) {
							if(w.tellclass() == "Director")
							src = (Director) w;
						}
						if(w.getlabel().equals(str[3])) {
							if(w.tellclass() == "Actor")
							src = (Actor) w;
						}
						if(w.getlabel().equals(str[4])) {
							if(w.tellclass() == "Movie")
							tar = (Movie) w;
						}
						if(w.getlabel().equals(str[4])) {
							if(w.tellclass() == "Director")
							tar = (Director) w;
						}
						if(w.getlabel().equals(str[4])) {
							if(w.tellclass() == "Actor")
							tar = (Actor) w;
						}
					}
					if(str[1].equals("MovieDirectorRelation") && str[5].equals("No")) {
						MovieDirectorRelation edge = new MovieDirectorRelation(str[0],src,tar,weight);
						graph.addEdge(edge);
					}
					else if(str[1].equals("MovieActorRelation") && str[5].equals("No")) {
						MovieActorRelation edge = new MovieActorRelation(str[0],src,tar,weight);	
						graph.addEdge(edge);
					}					
				}
				
				Matcher hm = h.matcher(headline);
				if(hm.matches() == true) {
					headline = replaceh(headline);
					String str[] = null;
					str = headline.split(",");
					for(int i=0;i<str.length;i++) {
						str[i] = str[i].trim();
						//System.out.println(str[i]);
					}
					List<Vertex> r = new ArrayList<Vertex>();
					Set<Vertex> all = new HashSet<Vertex>();
					all = graph.vertices();
					for(int i=2;i<str.length;i++) {
						for(Vertex v:all) {
							if(v.getlabel().equals(str[i])) 
								r.add(v);
						}
					}
					if(str[1].equals("SameMovieHyperEdge")) {
					SameMovieHyperEdge hye = new SameMovieHyperEdge(str[0],r,1.0);
					graph.addHyperEdge(hye);
					//graph.HyperEdgetoString(hye);
					}
				}
				
				
				
			}
		} catch (IOException e) {
			System.out.println("Cannot read from the file!");
		}
		
	}
	@Override
	public Graph<Vertex, Edge> createGraph(String filePath) {
		MovieGraph r= new MovieGraph();
		build(filePath, r);
		return r;
	}
}
