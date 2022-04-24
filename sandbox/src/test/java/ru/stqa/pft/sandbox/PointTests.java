package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void distancePointMinusTest(){
    Point p1 = new Point(-1,-1);
    Point p2 = new Point(6,2);
    Assert.assertEquals(p1.distance(p2),7.615773105863909);
  }

  @Test
  public void distancePointZeroTest(){
    Point p1 = new Point(0,0);
    Point p2 = new Point(6,2);
    Assert.assertEquals(p1.distance(p2),6.324555320336759);
  }

  @Test
  public void distancePointPlusTest(){
    Point p1 = new Point(1,1);
    Point p2 = new Point(6,2);
    Assert.assertEquals(p1.distance(p2),5.0990195135927845);
  }
}
