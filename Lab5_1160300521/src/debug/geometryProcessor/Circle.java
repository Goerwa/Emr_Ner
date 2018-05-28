package debug.geometryProcessor;

public class Circle implements Shape {

  double length;
  double area;
  String colour;
  String name;

  public Circle(double l, String c, String n) {
    length = l;
    colour = c;
    name = n;
  }

  public String getShape() {
    return "Circle";
  }

  public String getName() {
    return colour;
  }

  // Get the area of this circle
  public double getArea() {
    return Math.PI * length;
  }

  public void setLength(double l) {
    length = l;
    area = getArea();
  }

  public void setColour(String c) {
    colour = c;
  }

  public void setName(String n) {
    name = n;
  }

  @Override
  public String getColour() {
    return this.colour;
  }

}
