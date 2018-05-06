package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edge.WordNeighborhood;
import factory.GraphPoetFactory;
import factory.WordVertexFactory;
import graph.GraphPoet;
import vertex.Vertex;
import vertex.Word;



public class GraphPoetApp {
	
	
	
	public static void main(String[] args) {
		GraphPoet graph = (GraphPoet) new GraphPoetFactory().createGraph("src/source/test1.txt");
		//build("src/source/test1.txt",graph);
		System.out.println(graph.vertices().size());
		System.out.println(graph.edges().size());
	}
	
}
