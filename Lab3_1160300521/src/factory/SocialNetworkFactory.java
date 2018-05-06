package factory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edge.CommentTie;
import edge.Edge;
import edge.ForwardTie;
import edge.FriendTie;
import graph.Graph;
import graph.GraphPoet;
import graph.SocialNetwork;
import vertex.Person;
import vertex.Vertex;

public class SocialNetworkFactory extends GraphFactory{
	
	
	public SocialNetworkFactory() {
		
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
		return headline;
	}
	
	public static void build(String fileName, SocialNetwork graph) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String headline;
			Pattern p = Pattern.compile("Vertex = <[“a-zA-Z_0-9”[,]\\s+]*[<“a-zA-Z_0-9”[,]\\s+>]+>");
			//Pattern p = Pattern.compile("Vertex = <[“a-zA-Z0-9”,\\s+]*[<[“a-zA-Z0-9”,\\s]+[“[0-9].[0-9]”]+>]+>");
			Pattern e = Pattern.compile("Edge = <[“a-zA-Z_0-9([0-9].[0-9])”[,]\\s+]*>");
			while((headline = br.readLine()) != null) {
				Matcher m = p.matcher(headline);
				if(m.matches() == true) {
					String replace = null;
					headline = replacev(headline);
					String str[] = null;
					str = headline.split(",");
					str[1] = str[1].trim();
					str[2] = str[2].trim();
					str[3] = str[3].trim();
					if(str[1].equals("Person")) {
						//System.out.println(str[1]);
						Person w = new Person(str[0]);
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
					if(str[1].equals("FriendTie") && str[5].equals("Yes")) {
						Person src = null;
						Person tar = null;
						Set<Vertex> words = graph.vertices();
						for(Vertex w:words) {
							if(w.getlabel().equals(str[3])) {
								src = (Person) w;
							}
							if(w.getlabel().equals(str[4]))
								tar = (Person) w;
						}
						FriendTie edge = new FriendTie(str[0],src,tar,weight);
						graph.addEdge(edge);
					}
					if(str[1].equals("ForwardTie") && str[5].equals("Yes")) {
						Person src = null;
						Person tar = null;
						Set<Vertex> words = graph.vertices();
						for(Vertex w:words) {
							if(w.getlabel().equals(str[3])) {
								src = (Person) w;
							}
							if(w.getlabel().equals(str[4]))
								tar = (Person) w;
						}
						ForwardTie edge = new ForwardTie(str[0],src,tar,weight);
						graph.addEdge(edge);
					}
					if(str[1].equals("CommentTie") && str[5].equals("Yes")) {
						Person src = null;
						Person tar = null;
						Set<Vertex> words = graph.vertices();
						for(Vertex w:words) {
							if(w.getlabel().equals(str[3])) {
								src = (Person) w;
							}
							if(w.getlabel().equals(str[4]))
								tar = (Person) w;
						}
						CommentTie edge = new CommentTie(str[0],src,tar,weight);
						graph.addEdge(edge);
					}
				}
				
			}
		} catch (IOException e) {
			System.out.println("Cannot read from the file!");
		}
		
	}
	@Override
	public Graph<Vertex, Edge> createGraph(String filePath) {
		SocialNetwork r = new SocialNetwork();
		build(filePath, r);
		return r;
	}

}
