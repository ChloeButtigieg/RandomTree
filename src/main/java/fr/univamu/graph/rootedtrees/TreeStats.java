package fr.univamu.graph.rootedtrees;

import java.util.Arrays;

public record TreeStats(
    int graphCount,
    double wienerIndex,
    double diameter,
    double radius,
    double distanceCenterCentroid,
    double eccentricity,
    double[] degreeDistribution,
    double[] heightDistribution,
    double[] depthDistribution
) {

  public static TreeStats empty() {
    double[] array = new double[0];
    return new TreeStats(
        0,0,0,0,0,0,
        array,array,array
    );
  }

  @Override
  public String toString() {
    return
        "Wiener index = " + this.averageWienerIndex() +
            "\nDiameter = " + this.averageDiameter() +
            "\nRadius = " + this.averageRadius() +
            "\nDistance center-centroid = " + this.averageDistanceCenterCentroid() +
            "\nAverage eccentricity = " + this.averageEccentricity() +
            "\nDegree distribution = " + Arrays.toString(this.averageDegreeDistribution()) +
            "\nHeight distribution = " + Arrays.toString(this.averageHeightDistribution()) +
            "\nDepth distribution = " + Arrays.toString(this.averageDepthDistribution()) +
            "";
  }


  public TreeStats add(TreeStats stats) {
    return new TreeStats(
        this.graphCount + stats.graphCount,
        this.wienerIndex + stats.wienerIndex,
        this.diameter + stats.diameter,
        this.radius + stats.radius,
        this.distanceCenterCentroid + stats.distanceCenterCentroid,
        this.eccentricity + stats.eccentricity,
        addDistribution(this.degreeDistribution, stats.degreeDistribution),
        addDistribution(this.heightDistribution, stats.heightDistribution),
        addDistribution(this.depthDistribution, stats.depthDistribution)
    );
  }

  public double averageWienerIndex() {
    return average(wienerIndex);
  }

  public double averageDiameter() {
    return average(diameter);
  }

  public double averageRadius() {
    return average(radius);
  }

  public double averageDistanceCenterCentroid() {
    return average(distanceCenterCentroid);
  }

  public double averageEccentricity() {
    return average(eccentricity);
  }

  public double[] averageDegreeDistribution() {
    return average(degreeDistribution);
  }

  public double[] averageHeightDistribution() {
    return average(heightDistribution);
  }

  public double[] averageDepthDistribution() {
    return average(depthDistribution);
  }

  private double average(double d) {
    return d / graphCount;
  }

  private double[] average(double[] distribution) {
    return Arrays.stream(distribution).map(c -> c / graphCount).toArray();
  }

  private double[] addDistribution(double[] dist1, double[] dist2) {
    int length = Math.max(dist1.length, dist2.length);
    double[] sum = new double[length];
    for (int i = 0; i < length; i++) {
      double x1 = i < dist1.length ? dist1[i] : 0;
      double x2 = i < dist2.length ? dist2[i] : 0;
      sum[i] = x1 + x2;
    }
    return sum;
  }




}
