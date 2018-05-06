package factory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edge.Edge;
import edge.WordNeighborhood;
import graph.Graph;
import graph.GraphPoet;
import vertex.Vertex;
import vertex.Word;

public class GraphPoetFactory extends GraphFactory{
	public GraphPoetFactory() {
		
	}
	
	private static String replacev(String headline) {
		headline = headline.replace("Vertex = <", "");
		headline = headline.replace(">", "");
		headline = headline.replace("“", "");
		headline = headline.replace("”", "");
		return headline;
	}
	
	private static String replacee(String headline) {
		headline = headline.replace("Edge = <", "");
		headline = headline.replace(">", "");
		headline = headline.replace("“", "");
		headline = headline.replace("”", "");
		return headline;
	}
	
	public static void build(String fileName, GraphPoet graph) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String headline;
			Pattern p = Pattern.compile("Vertex = <[“a-zA-Z_0-9”[,]\\s+]*>");
			Pattern e = Pattern.compile("Edge = <[“a-zA-Z_0-9”[,]\\s+]*>");
			while((headline = br.readLine()) != null) {
				//System.out.println(headline);
				Matcher m = p.matcher(headline);
				if(m.matches() == true) {
					String replace = null;
					headline = replacev(headline);
					//System.out.println(headline);
					String str[] = null;
					str = headline.split(",");
					str[1] = str[1].trim();
					if(str[1].equals("Word")) {
						Word w = (Word) new WordVertexFactory().createVertex(str[0],str);
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
					if(str[1].equals("WordNeighborhood") && str[5].equals("No")) {
						Word src = null;
						Word tar = null;
						Set<Vertex> words = graph.vertices();
						for(Vertex w:words) {
							if(w.getlabel().equals(str[3])) {
								src = (Word) w;
							}
							if(w.getlabel().equals(str[4]))
								tar = (Word) w;
						}
						WordNeighborhood edge = new WordNeighborhood(str[0],src,tar,weight);
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
		GraphPoet r= new GraphPoet();
		build(filePath, r);
		return r;
	}
	
}
