package fr.univamu.helpers;

public record Point(double x, double y) {

  public static final Point ZERO = new Point(0,0);

  public static Point cartesian(double x, double y) {
    return new Point(x,y);
  }

  public static Point polar(double radian, double radius) {
    return new Point(radius * Math.cos(radian), radius * Math.sin(radian));
  }

  public Point add(Point p) {
    return new Point(this.x + p.x, this.y + p.y);
  }

  public Point subtract(Point p) {
    return new Point(this.x - p.x, this.y - p.y);
  }

  public Point scale(double factor) {
    return new Point(factor * this.x, factor * this.y);
  }

  public Point rotate(double radian) {
    double cosine = Math.cos(radian);
    double sine = Math.sin(radian);
    return new Point(x * cosine - y * sine, x * sine + y * cosine);
  }

  public Point rotate(double radian, Point center) {
    return this.subtract(center).rotate(radian).add(center);
  }
}
