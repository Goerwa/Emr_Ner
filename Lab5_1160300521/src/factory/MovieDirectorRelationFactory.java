package factory;

import java.util.List;
import edge.Edge;
import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import vertex.Vertex;

public class MovieDirectorRelationFactory extends EdgeFactory {

  public MovieDirectorRelationFactory() {

  }

  @Override
  public Edge createEdge(String label, List<Vertex> vertices, double weight) {
    MovieDirectorRelation e =
        new MovieDirectorRelation(label, vertices.get(0), vertices.get(1), weight);
    return e;
  }
}
