package ru.stqa.pft.sandbox;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

import java.util.Arrays;
import java.util.List;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger
public class Collections {

  public static void main(String[] args){
    String[] langs = {"Java", "C#", "Python", "PHP"};

    List languages = Arrays.asList("Java", "C#", "Python", "PHP");

    for (Object language : languages) {
      log.println("Я хочу выучить " + language);
    }
  }
}

