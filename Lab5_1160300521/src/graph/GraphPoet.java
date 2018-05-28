package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import Exception.ExceptionofInput;
import Exception.ExceptionofUnproperEdge;
import edge.Edge;
import edge.WordNeighborhood;
import vertex.Vertex;
import vertex.Word;
/**
 * GraphPoet.
 * @author goerwa
 *
 */
public class GraphPoet extends ConcreteGraph {
  /**
   * to construct.
   */
  public GraphPoet() {

  }
  /**
   * read file and construct graphpoet.
   * @param corpus
   * filename
   * @throws IOException IOException
   * @throws ExceptionofInput ExceptionofInput
   * @throws ExceptionofUnproperEdge ExceptionofUnproperEdge
   */
  public GraphPoet(final File corpus) throws
  IOException, ExceptionofInput, ExceptionofUnproperEdge {
    // throw new RuntimeException("not implemented");
    List<String> wordList = new ArrayList<>();
    List<Word> words = new ArrayList<>();
    Scanner s = new Scanner(new BufferedReader(new FileReader(corpus)));
    while (s.hasNext()) {
      wordList.add(s.next());
    }
    s.close();
    // convert to lower case
    for (int i = 0; i < wordList.size(); i++) {
      wordList.set(i, wordList.get(i).toLowerCase());
      Word w = new Word(wordList.get(i));
      // System.out.println(wordList.get(i));
      words.add(w);
    }
    // add words to graph
    for (Word word : words) {
      if (!super.vertices().contains(word)) {
        super.addVertex(word);
        // System.out.println(super.vertices().size());
      }
    }
    // set edges
    for (int i = 0; i < wordList.size() - 1; i++) {
      String strsrc = words.get(i).getlabel().toLowerCase();
      String strtrg = words.get(i + 1).getlabel().toLowerCase();
      Word src = null;
      Word trg = null;
      Map<Vertex, Double> targets = new HashMap<>();
      for (Vertex v : super.vertices()) {
        if (v.getlabel().toLowerCase().equals(strtrg.toLowerCase())) {
          trg = (Word) v;
        }
      }
      for (Vertex v : super.vertices()) {
        if (v.getlabel().toLowerCase().equals(strsrc.toLowerCase())) {
          src = (Word) v;
          targets = super.targetsone(src);
          if (targets.size() == 0) {
            WordNeighborhood edge = new WordNeighborhood(src.getlabel()
                    + trg.getlabel(), src, trg, 1.0);
            super.addEdge(edge);
            trg = new Word("");
            if (!super.vertices().contains(trg)) {
              super.addVertex(trg);
            }
          }
        }
      }
      boolean flag = false;
      for (Vertex v1 : targets.keySet()) {
        if (v1.equals(trg)) {
          double oldWeight = 1.0;
          WordNeighborhood edge = null;
          if (trg.getlabel().equals("")) {
            edge = new WordNeighborhood(src.getlabel()
                + trg.getlabel(), src, trg, 0.0);
          }  else {
            edge = new WordNeighborhood(src.getlabel()
                + trg.getlabel(), src, trg, 1.0);
          }
          for (Edge e : super.edges()) {
            if (e.equals(edge)) {
              oldWeight = e.getweight();
              super.removeEdge(e);
            }
          }
          oldWeight++;
          edge = new WordNeighborhood(src.getlabel()
              + trg.getlabel(), src, trg, oldWeight);
          System.out.println(edge.getsource().getlabel()
              + "  " + edge.gettarget().getlabel() + "  "
              + edge.getweight());

          super.addEdge(edge);
          flag = true;
        }
      }
      if (!flag) {
        // System.out.println(trg.equals(null));
        WordNeighborhood edge = null;
        if (trg.getlabel().equals("")) {
          edge = new WordNeighborhood(src.getlabel()
              + trg.getlabel(), src, trg, 0.0);
        }  else {
          edge = new WordNeighborhood(src.getlabel()
              + trg.getlabel(), src, trg, 1.0);
        }
        super.addEdge(edge);
      }
    }
    checkRep();
  }
 /**
  * remove edge and set a new weight.
  * @param n new weight
  * @throws ExceptionofInput ExceptionofInput
  * @throws ExceptionofUnproperEdge ExceptionofUnproperEdge
 * @throws IOException 
  */
 public final void removeedge(final int n) throws ExceptionofInput
  , ExceptionofUnproperEdge, IOException {
    Set<Edge> edges = new HashSet<>();
    edges = super.edges();
    for (Edge e : edges) {
      int weight = e.getweight().intValue();
      if (weight < n) {
        super.removeEdge(e);
      }
    }
  }


  /**
   * checkrep to  assert word is not null.
   */
  private void checkRep() {
    for (Vertex vertex : super.vertices()) {
      String copy = vertex.getlabel().toLowerCase().trim()
          .replaceAll("\\s+", "");
      assert vertex.getlabel().equals(copy);
      // assert !vertex.getlabel().equals("");
    }
  }


  /**
   * Generate a poem.
   * @param input string from which to create the poem
   * @return poem (as described above)
   */
  public final String poem(final String input) {
    // throw new RuntimeException("not implemented");
    List<String> inputList = Arrays.asList(input.trim().split("\\s+"));
    List<String> result = new ArrayList<>();
    // System.out.println("start");
    for (int i = 0; i < inputList.size() - 1; i++) {
      int oldweight = 0;
      String oldstr = "";
      Set<Edge> edges = super.edges();
      // System.out.println(inputList.get(i));
      for (Edge e : edges) {
        if (e.getsource().getlabel().equals(inputList.get(i).toLowerCase())) {
          String newstr = e.gettarget().getlabel();
          int newweight = e.getweight().intValue();
          if (newweight > oldweight) {
            oldweight = newweight;
            oldstr = newstr;
            // System.out.println(oldstr);
          }
        }
      }

      result.add(inputList.get(i));
      if (!oldstr.equals("") && !oldstr.equals(inputList.get(i + 1)
          .toLowerCase().replaceAll("\\pP", ""))) {
        result.add(oldstr);
      }
    }
    result.add(inputList.get(inputList.size() - 1));
    checkRep();
    return String.join(" ", result);
  }

  @Override
  public final String toString() {
    return super.toString();
  }


}
