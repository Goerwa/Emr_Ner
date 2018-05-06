package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edge.NetworkConnection;
import factory.NetworkTopologyFactory;
import graph.NetworkTopology;
import vertex.Computer;
import vertex.Router;
import vertex.Server;
import vertex.Vertex;



public class NetworkTopologyApp {
	
	
	public static void main(String[] args) {
		NetworkTopology graph = (NetworkTopology) new NetworkTopologyFactory().createGraph("src/source/test3.txt");
		//build("src/source/test3.txt",graph);
		System.out.println(graph.vertices().size());
		System.out.println(graph.edges().size());
	}
}
