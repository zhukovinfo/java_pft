package ru.stqa.pft.mantis.model;

import java.math.BigInteger;

public class Status {

  private BigInteger id;
  private String name;

  public BigInteger getId() {
    return id;
  }

  public Status withId(BigInteger id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public Status withName(String name) {
    this.name = name;
    return this;
  }

}
