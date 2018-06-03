package graph;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import Exception.ExceptionofInput;
import Exception.ExceptionofUnproperEdge;
import edge.Edge;
import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import edge.SameMovieHyperEdge;
import factory.SocialNetworkFactory;
import vertex.Movie;
import vertex.Person;
import vertex.Vertex;
/**
 * MovieGraph.
 * @author goerwa
 *
 */
public class MovieGraph extends ConcreteGraph {
  /** set of SameMovieHyperEdge.*/
  private Set<SameMovieHyperEdge> hyperedge = new HashSet<>();
  Logger logger = Logger.getLogger(SocialNetworkFactory.class);
  /** used to write operation.*/
  private FileOutputStream outSTr = null;
  /** used to write operation.*/
  private BufferedOutputStream Buff = null;
  /** to construct.*/
  public MovieGraph() {
    try {
      outSTr = new FileOutputStream(new File("src/source/write.txt"));
      Buff = new BufferedOutputStream(outSTr);
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }
  }

  @Override
  public final boolean addVertex(final Vertex net) throws ExceptionofInput,
  IOException {
    /*
    if (!confirmclass(net)) {
      logger.error("type of vertex is wrong");
      throw new ExceptionofInput("type of vertex is wrong");
    }
    }*/
    super.addVertex(net);
    return true;
  }

  /**
   * confirm class of vertex is right.
   * @param v vertex
   * @return true if type is right
   */
  private boolean confirmclass(final Vertex v) {
    // System.out.println(v.tellclass());
    if (v.tellclass().equals("Movie")) {
      return true;
    } else if (v.tellclass().equals("Actor")) {
      return true;
    } else if (v.tellclass().equals("Director")) {
      return true;
    }
      return false;
  }

  @Override
  public final boolean addEdge(final Edge e) throws ExceptionofInput,
  ExceptionofUnproperEdge, IOException {
    /*
    if (e.tellclass().equals("MovieDirectorRelation")) {
      MovieDirectorRelation n = (MovieDirectorRelation) e;
      Buff.write(("Edge = <\"" + n.getlabel() + "\", \"" + n.tellclass()
         + "\", <\"" + n.getweight() + "\", \"" + n.getsource().getlabel()
          + "\", \"" + n.gettarget().getlabel() + "No" + "\">>\n").getBytes());
      Buff.flush();
    } else if (e.tellclass().equals("MovieActorRelation")) {
      MovieActorRelation n = (MovieActorRelation) e;
      Buff.write(("Edge = <\"" + n.getlabel() + "\", \"" + n.tellclass()
      + "\", <\"" + n.getweight()  + "\", \"" + n.getsource().getlabel()
      + "\", \"" + n.gettarget().getlabel() + "No" + "\">>\n").getBytes());
      Buff.flush();
    }*/
    super.addEdge(e);
    return true;
  }


  /**
   * to add hyperedge.
   * @param e hyperedge.
   * @return true if successful,otherwise return false
   * @throws IOException IOException
   */
  public final boolean addHyperEdge(final SameMovieHyperEdge e)
      throws IOException {
    if (e.tellclass().equals("SameMovieHyperEdge")) {
      SameMovieHyperEdge n = (SameMovieHyperEdge) e;
      List<Vertex> vers = n.getvers();
      String info = "HyperEdge = <\"" + n.getlabel() + "\", \""
      + n.tellclass() + "\", {\"" + vers.get(0).getlabel();
      for (int i = 1; i < vers.size(); i++) {
        info += "\",\"" + vers.get(i).getlabel();
      }
      info += "\"}>\n";
      Buff.write(info.getBytes());
      Buff.flush();
    }
    boolean r = this.hyperedge.add(e);
    return r;
  }
 /**
  * get hyperedges.
  * @return hyperedges
  */
  public final List<SameMovieHyperEdge> getHyperEdge() {
    return new ArrayList<SameMovieHyperEdge>(hyperedge);
  }
  /**
   * hyperedge to string.
   * @param edge hyperedge
   */
  public final void hyperEdgetoString(final SameMovieHyperEdge edge) {
    for (SameMovieHyperEdge h : hyperedge) {
      if (h.getlabel().equals(edge.getlabel())) {
        for (Vertex v : h.getvers()) {
          System.out.println(v.getlabel());
        }
      }
    }
  }
}
