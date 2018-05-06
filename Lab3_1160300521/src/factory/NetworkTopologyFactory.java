package factory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edge.Edge;
import edge.NetworkConnection;
import graph.Graph;
import graph.NetworkTopology;
import graph.SocialNetwork;
import vertex.Computer;
import vertex.Router;
import vertex.Server;
import vertex.Vertex;

public class NetworkTopologyFactory extends GraphFactory{
	
	public NetworkTopologyFactory() {
		
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
	
	public static void build(String fileName, NetworkTopology graph) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String headline;
			Pattern p = Pattern.compile("Vertex = <[“a-zA-Z_0-9”[,]\\s+]*[<“[0-9[.]+]*”[,]\\s+>]+>");
			Pattern e = Pattern.compile("Edge = <[“a-zA-Z_0-9”[,]\\s+]*>");
			while((headline = br.readLine()) != null) {
				Matcher m = p.matcher(headline);
				if(m.matches() == true) {
					String replace = null;
					headline = replacev(headline);
					String str[] = null;
					str = headline.split(",");
					str[1] = str[1].trim();
					str[2] = str[2].trim();
					if(str[1].equals("Computer")) {
						//System.out.println(str[1]);
						Computer w = new Computer(str[0]);
						w.fillVertexInfo(str);
						graph.addVertex(w);
					}
					else if(str[1].equals("Router")) {
						Router w = new Router(str[0]);
						w.fillVertexInfo(str);
						graph.addVertex(w);
					}
					else if(str[1].equals("Server")) {
						Server w = new Server(str[0]);
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
					if(str[1].equals("NetworkConnection") && str[5].equals("No")) {
						Vertex src = null;
						Vertex tar = null;
						for(Vertex w:words) {
							if(w.getlabel().equals(str[3])) {
								if(w.tellclass() == "Computer")
								src = (Computer) w;
							}
							if(w.getlabel().equals(str[3])) {
								if(w.tellclass() == "Router")
								src = (Router) w;
							}
							if(w.getlabel().equals(str[3])) {
								if(w.tellclass() == "Server")
								src = (Server) w;
							}
							if(w.getlabel().equals(str[4])) {
								if(w.tellclass() == "Computer")
								tar = (Computer) w;
							}
							if(w.getlabel().equals(str[4])) {
								if(w.tellclass() == "Router")
								tar = (Router) w;
							}
							if(w.getlabel().equals(str[4])) {
								if(w.tellclass() == "Server")
								tar = (Server) w;
							}
						}
						NetworkConnection edge = new NetworkConnection(str[0],src,tar,weight);
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
		NetworkTopology r = new NetworkTopology();
		build(filePath, r);
		return r;
	}

}
