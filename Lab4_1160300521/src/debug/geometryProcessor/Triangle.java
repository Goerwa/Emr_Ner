package debug.geometryProcessor;

public class Triangle implements Shape {

	double length;
	double area;
	String colour;
	String name;

	public Triangle(double l, String c, String n) {
		length = l;
		colour = c;
		name = n;
	}

	public String getShape() {
		return "Triangle";
	}

	public String getName() {
		return name;
	}

	public String getColour() {
		return colour;
	}

	public double getArea() {
		return Math.PI * length * length;
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

}
