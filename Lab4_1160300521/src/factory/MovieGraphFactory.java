package factory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import Exception.ExceptionofDirectionTest;
import Exception.ExceptionofInput;
import Exception.ExceptionofUndirection;
import Exception.ExceptionofUnproperEdge;
import edge.CommentTie;
import edge.Edge;
import edge.ForwardTie;
import edge.FriendTie;
import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import edge.SameMovieHyperEdge;
import graph.Graph;
import graph.GraphPoet;
import graph.MovieGraph;
import graph.SocialNetwork;
import vertex.Actor;
import vertex.Director;
import vertex.Movie;
import vertex.Person;
import vertex.Vertex;
 
public class MovieGraphFactory extends GraphFactory{ 
	static Logger logger = Logger.getLogger(SocialNetworkFactory.class);
	private List<String> vtype = new ArrayList<>();
	private List<String> etype = new ArrayList<>();
	public MovieGraphFactory() {
		 
	}

	private static String replacev(String headline) {
		headline = headline.replace("Vertex = <", "");
		headline = headline.replace(">", "");
		headline = headline.replace("<", "");
		headline = headline.replace("\"", "");
		headline = headline.replace(" ", ""); 
		//System.out.println(headline);
		return headline; 
	}
	
	private static String replaceh(String headline) {
		headline = headline.replace("HyperEdge = <", "");
		headline = headline.replace(">", "");
		headline = headline.replace("\"", "");
		headline = headline.replace("{", "");
		headline = headline.replace("}", "");
		headline = headline.replace(" ", "");
		return headline;
	}
	private static String replacee(String headline) {
		headline = headline.replace("Edge = <", "");
		headline = headline.replace(">", "");
		headline = headline.replace("\"", "");
		headline = headline.replace(" ", "");
		return headline;
	}
	private void dealvertex(String str, Graph graph) throws ExceptionofInput {
		Pattern p = Pattern.compile("Vertex = <(\\s*\"[a-zA-Z_0-9]+\",*\\s*){2}<(\\s*\"[a-zA-Z_0-9]*([0-9]{1,}[.][0-9]*)*\",*\\s*)+>>");
		Matcher m = p.matcher(str);
		if(m.matches() == true) {
			str = replacev(str);
			String info[] = str.split(",");
			//showstr(info);
			if(info.length != 4 && info[1].equals("Actor")) {
				logger.error("The data read in miss attributes!");
				throw new ExceptionofInput("The data read in miss attributes!");
			}
			if(info.length != 4 && info[1].equals("Director")) {
				logger.error("The data read in miss attributes!");
				throw new ExceptionofInput("The data read in miss attributes!");
			}
			if(info.length != 5 && info[1].equals("Movie")) {
				logger.error("The data read in miss attributes!");
				throw new ExceptionofInput("The data read in miss attributes!");
			}
			if(!vtype.contains(info[1])) {
				logger.error("The type of vertex " + info[1] + " is not in the graph!");
				throw new ExceptionofInput("The type of vertex " + info[1] + " is not in the graph!");
			}
			if(info[1].equals("Actor")) {
				Actor w = new Actor(info[0]);
				w.fillVertexInfo(info);
				logger.info("add vertex " + info[0] + " to the graph!");
				graph.addVertex(w);
			}
			else if(info[1].equals("Director")) {
				Director w = new Director(info[0]);
				w.fillVertexInfo(info);
				logger.info("add vertex " + info[0] + " to the graph!");
				graph.addVertex(w);
			}
			else if(info[1].equals("Movie")) {
				Movie w = new Movie(info[0]);
				w.fillVertexInfo(info);
				logger.info("add vertex " + info[0] + " to the graph!");
				graph.addVertex(w);
			}else {
				logger.error("The type of vertex " + info[1] + " is not in the graph!");
				throw new ExceptionofInput("The type of vertex " + info[1] + " is not in the graph!");
			}
		}else {
			logger.error("The data read "+str+" in does not match the regular expression!");
			throw new ExceptionofInput("The data read "+str+" in does not match the regular expression!");
		}
	}
	private void dealedge(String headline, Graph graph) throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofUndirection {
		Pattern e = Pattern.compile("Edge = <(\\s*\"-*[a-zA-Z_0-9]*([0-9]{1,}[.][0-9]*)*\",*\\s*){5},*\\s*\"(Yes|No)\"\\s*>");
		Matcher em = e.matcher(headline);
		if(em.matches() == true) {
			String replace = null;
			headline = replacee(headline);
			//System.out.println(headline);
			String str[] = headline.split(",");
			//showstr(str);
			double weight = Double.valueOf(str[2]);
			Vertex src = null;
			Vertex tar = null;
			//System.out.println(str[3]);
			//System.out.println(str[4]);
			Set<Vertex> words = graph.vertices();
			for(Vertex w:words) {
				if(w.getlabel().equals(str[3])) {
					if(w.tellclass().equals("Actor"))
						src = (Actor) w;
					if(w.tellclass().equals("Director")) {
						src = (Director) w;
					}
					if(w.tellclass().equals("Movie")) {
						src = (Movie) w;
					}
				}
			}
			for(Vertex w:words) {
				if(w.getlabel().equals(str[4])) {
					if(w.tellclass().equals("Actor"))
						tar = (Actor) w;
					if(w.tellclass().equals("Director")) {
						tar = (Director) w;
					}
					if(w.tellclass().equals("Movie"))
						tar = (Movie) w;
				}
			}
			

			if(!etype.contains(str[1])) {
				logger.error("The type of edge " +str[1] +  " is not in the graph!");
				throw new ExceptionofInput("The type of edge " +str[1] +  " is not in the graph!");
			}
			
			if(str[1].equals("MovieActorRelation")) {
				try {
					if(str[5].equals("Yes")) {
						logger.error("The edge add in can not be an directededge!");
						throw new ExceptionofUndirection("The edge add in can not be an directededge!");
					}
				}catch(ExceptionofUndirection ex) {
					System.out.println(ex);
				}
				if(!(weight > 0)) {	
					logger.error("The weight is " + weight +" not more than 0!");
					throw new ExceptionofUnproperEdge("The weight is " + weight +" not more than 0!");
				}
				if(src.tellclass().equals("Movie")) {
					if(!tar.tellclass().equals("Actor")) {
						logger.error("Provided vertices cannot form edges!");
						throw new ExceptionofUnproperEdge("Provided vertices cannot form edges!");
					}
				}
				else if(tar.tellclass().equals("Movie")) {
					if(!src.tellclass().equals("Actor")) {
						logger.error("Provided vertices cannot form edges!");
						throw new ExceptionofUnproperEdge("Provided vertices cannot form edges!");
					}
				}else {
					logger.error("Provided vertices cannot form edges!");
					throw new ExceptionofUnproperEdge("Provided vertices cannot form edges!");
				}
				MovieActorRelation edge = new MovieActorRelation(str[0],src,tar,weight);
				logger.info("add edge " +  str[0] +" to the graph!");
				graph.addEdge(edge);
			}
			else if(str[1].equals("MovieDirectorRelation")) {
				try {
					if(str[5].equals("Yes")) {
						logger.error("The edge add in can not be an directededge!");
						throw new ExceptionofUndirection("The edge add in can not be an directededge!");
					}
				}catch(ExceptionofUndirection ex) {
					System.out.println(ex);
				}
				//System.out.println(src.tellclass() +"  "+ src.getlabel());
				//System.out.println(tar.tellclass() +"  "+ tar.getlabel());
				if(src.tellclass().equals("Movie")) {
					if(!tar.tellclass().equals("Director")) {
						logger.error("Provided vertices cannot form edges!");
						throw new ExceptionofUnproperEdge("Provided vertices cannot form edges!");
					}
				}
				else if(tar.tellclass().equals("Movie")) {
					if(!src.tellclass().equals("Director")) {
						logger.error("Provided vertices cannot form edges!");
						throw new ExceptionofUnproperEdge("Provided vertices cannot form edges!");
					}
				}else {
					logger.error("Provided vertices cannot form edges!");
					throw new ExceptionofUnproperEdge("Provided vertices cannot form edges!");
				} 
				MovieDirectorRelation edge = new MovieDirectorRelation(str[0],src,tar,weight);
				logger.info("add edge " +  str[0] +" to the graph!");
				graph.addEdge(edge);
			}else { 
				logger.error("The type of edge " +str[1] +  " is not in the graph!");
				throw new ExceptionofInput("The type of edge " +str[1] +  " is not in the graph!");
			}
		}else {
			logger.error("The data read "+headline+" in does not match the regular expression!");
			throw new ExceptionofInput("The data read "+headline+" in does not match the regular expression!");
		}
	}
	private void dealhyper(String headline, Graph graph) throws ExceptionofInput, ExceptionofUnproperEdge {
		Pattern h = Pattern.compile("HyperEdge = <(\\s*\"[a-zA-Z_0-9]+\",*\\s*){2}\\{(\\s*\"[a-zA-Z_0-9]+\",*\\s*)+\\}>");
		Matcher hm = h.matcher(headline);
		if(hm.matches() == true) {
			headline = replaceh(headline); 
			String str[] = headline.split(",");
			List<Vertex> r = new ArrayList<Vertex>();
			Set<Vertex> all = new HashSet<Vertex>();
			all = graph.vertices();
			if(str.length < 4) {
				logger.error("the length of hyperedge must bu more than 1!");
				throw new ExceptionofUnproperEdge("the length of hyperedge must bu more than 1!");
			}
			if(!etype.contains(str[1])) {
				logger.error("The type of edge " +str[1] +  " is not in the graph!");
				throw new ExceptionofInput("The type of edge " +str[1] +  " is not in the graph!");
			}
			for(int i=2;i<str.length;i++) {
				//System.out.println(str[i]);
				for(Vertex v:all) {
					//System.out.println(v.getlabel());
					if(v.getlabel().equals(str[i])) {
						r.add(v);
					}
				}
			}
			if(!etype.contains(str[1])) {
				logger.error("The type of edge " +str[1] +  " is not in the graph!");
				throw new ExceptionofInput("The type of edge " +str[1] +  " is not in the graph!");
			}
			if(str[1].equals("SameMovieHyperEdge")) {
			SameMovieHyperEdge hye = new SameMovieHyperEdge(str[0],1.0);
			logger.info("add a hyperedge "+ str[1] + " to the graph");
			hye.addVertices(r);
			//System.out.println(r.size());
			((MovieGraph) graph).addHyperEdge(hye);
			}else {
			    logger.error("The type of edge " +str[1] +  " is not in the graph!");
				throw new ExceptionofInput("The type of edge " +str[1] +  " is not in the graph!");
			}
		}else {
			logger.error("The data read "+headline+" in does not match the regular expression!");
			throw new ExceptionofInput("The data read "+headline+" in does not match the regular expression!");
		}
	}
	public void build(String fileName, MovieGraph graph) throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofUndirection {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String headline;
			while((headline = br.readLine()) != null) {
				//System.out.println(headline.substring(0, 1));
				String type;
				if(!headline.equals(""))
					type = headline.substring(0, 1);
				else continue;
				Pattern graphtype = Pattern.compile("GraphType = \\s*\"[a-zA-Z_0-9]+\",*\\s*");
				Matcher m1 = graphtype.matcher(headline);
				Pattern vertextype = Pattern.compile("VertexType = (\\s*\"[a-zA-Z_0-9]+\",*\\s*)+");
				Matcher m2 = vertextype.matcher(headline);
				Pattern edgetype = Pattern.compile("EdgeType = (\\s*\"[a-zA-Z_0-9]+\",*\\s*)+");
				Matcher m3 = edgetype.matcher(headline);
				Pattern graphname = Pattern.compile("GraphName = \\s*\"[a-zA-Z_0-9]+\",*\\s*");
				Matcher m4 = graphname.matcher(headline);
				if(m1.matches()) {
					headline = headline.replace("\"", "");
					headline = headline.replace("GraphType = ", "");
					headline = headline.replace(" ", "");
					if(!headline.toLowerCase().equals("moviegraph")) {
						logger.error("the Graph type " + headline + " is wrong");
						throw new ExceptionofInput("the Graph type " + headline + " is wrong");
					}
					else {
						logger.info("the Graph type is " + headline+ "!");
					}
				}
				else if(m4.matches()) {
					headline = headline.replace("\"", "");
					headline = headline.replace("GraphName = ", "");
					headline = headline.replace(" ", "");
					System.out.println("Graphname is " + headline);
					logger.info("Graphname is " + headline);
				}
				else if(m2.matches()) {
					headline = headline.replace("\"", "");
					headline = headline.replace("VertexType = ", "");
					headline = headline.replace(" ", "");
					String[] types = headline.split(",");
					for(int i = 0;i<types.length;i++) {
						vtype.add(types[i]);
						logger.info("the vertex type "+types[i] +" is added to the graph.");
						System.out.println("the vertex type "+types[i] +" is added to the graph.");
					}
				} 
				else if(m3.matches()) {
					headline = headline.replace("\"", "");
					headline = headline.replace("EdgeType = ", "");
					headline = headline.replace(" ", "");
					String[] types = headline.split(",");
					for(int i = 0;i<types.length;i++) {
						etype.add(types[i]);
						System.out.println("the edge type "+types[i] +" is added to the graph.");
						logger.info("the edge type "+types[i] +" is added to the graph.");
						System.out.println(etype.size());
					}
				}
				else if(type.equals("V")) {
					dealvertex(headline,graph);
				}
				else if(type.equals("E")){
					dealedge(headline,graph);
				}
				else if(type.equals("H")){
					dealhyper(headline,graph);
				}
				else {
					logger.error("The data read "+headline+" in does not match the regular expression!");
					throw new ExceptionofInput("The data read "+headline+" in does not match the regular expression!");
				}
			}
		} catch (IOException e) {
			System.out.println("Cannot read from the file!");
		}finally {
			System.out.println("the number of vertex in graph:"+"  "+graph.vertices().size());
			System.out.println("the number of edge in graph:"+"  "+graph.edges().size());
			System.out.println("the number of hyperedge in graph:"+"  "+graph.getHyperEdge().size());
		}
		
	}
	@Override
	public Graph<Vertex, Edge> createGraph(String filePath) throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofUndirection {
		MovieGraph r= new MovieGraph();
		Scanner sc = new Scanner(System.in);
		String path = filePath;
		boolean flag = true;
		while(flag) {
			try {
				build(path, r);
				flag = false;
			} catch(ExceptionofInput e) {
				flag = true;
				System.out.println(e);
				System.out.println("input another file path");
				path = sc.nextLine();
			} catch(ExceptionofUnproperEdge e) {
				flag = true;
				System.out.println(e);
				System.out.println("input another file path");
				path = sc.nextLine();
			} 
		}
		return r;
	}
	/*
	public static void main(String[] args) throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofUndirection  {
		MovieGraph graph = new MovieGraph();
		try{
		build("src/source/test4.txt",graph);
		}catch(ExceptionofInput x) {
			System.out.println(x);
			//build("src/source/test2right.txt",graph);
		}
	}
	*/
}
