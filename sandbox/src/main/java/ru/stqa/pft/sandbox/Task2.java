package ru.stqa.pft.sandbox;

public class Task2 {

  public static void main(String[] args) {
    Point p1 = new Point(0, 1);
    Point p2 = new Point(2, -2);

    System.out.println("расстояние между точками " + "P1(" + p1.x + "," + p1.y + ") "
        + "и P2(" + p2.x + "," + p2.y + ") = " + p1.distance(p2));
  }


}
