package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import edge.SameMovieHyperEdge;
import factory.MovieGraphFactory;
import graph.MovieGraph;
import vertex.Actor;
import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

public class MovieGraphApp {
	
	
	public static void main(String[] args) {
		MovieGraph graph = (MovieGraph) new MovieGraphFactory().createGraph("src/source/test4.txt");
		//build("src/source/test4.txt",graph);
		System.out.println(graph.vertices().size());
		System.out.println(graph.edges().size());
	}
}
