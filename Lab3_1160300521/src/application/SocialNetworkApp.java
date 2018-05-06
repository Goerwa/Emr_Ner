package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edge.FriendTie;
import factory.SocialNetworkFactory;
import graph.SocialNetwork;
import vertex.Person;
import vertex.Vertex;



public class SocialNetworkApp {
	
	
	public static void main(String[] args) {
		SocialNetwork graph = (SocialNetwork) new SocialNetworkFactory().createGraph("src/source/test2.txt");
		//build("src/source/test2.txt",graph);
		System.out.println(graph.vertices().size());
		System.out.println(graph.edges().size());
	}
}
