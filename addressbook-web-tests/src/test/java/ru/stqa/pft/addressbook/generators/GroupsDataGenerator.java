package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupsDataGenerator {

  @Parameter(names = "-c", description = "group count")
  public int count;

  @Parameter(names = "-f", description = "target file")
  public String file;

  @Parameter(names = "-d", description = "data format")
  public static String dataFormat;

  public static void main(String[] args) throws IOException {
    GroupsDataGenerator generator = new GroupsDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<GroupData> groups = generateGroups(count);
    switch (dataFormat) {
      case "csv":
        saveAsCsv(groups, new File(file));
        break;
      case "xml":
        saveAsXml(groups, new File(file));
        break;
      case "json":
        saveAsJson(groups, new File(file));
        break;
      default:
        System.out.println("unrecognized format " + dataFormat);
        break;
    }
  }

  private void saveAsXml(List<GroupData> groups, File file) throws IOException {
    XStream xStream = new XStream();
    xStream.processAnnotations(GroupData.class);
    String xml = xStream.toXML(groups);
    try (Writer writer = new FileWriter(file)){
      writer.write(xml);

    }
  }

  private void saveAsCsv(List<GroupData> groups, File file) throws IOException {
    File wd = new File(".");
    System.out.println("working dir: " + wd.getAbsolutePath());
    try (Writer writer = new FileWriter(file)){
      for (GroupData group : groups) {
        writer.write(String.format("%s;%s;%s\n", group.getName(), group.getHeader(), group.getFooter()));
      }
    }
  }

  private void saveAsJson(List<GroupData> groups, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(groups);
    try (Writer writer = new FileWriter(file)) {
      writer.write(json);
    }
  }

  private static List<GroupData> generateGroups(int count) {
    List<GroupData> groups = new ArrayList<GroupData>();
    for (int i = 0; i < count; i++) {
      groups.add(new GroupData()
          .withName(String.format("test %s", i))
          .withHeader(String.format("header %s", i))
          .withFooter(String.format("footer %s", i)));
    }
    return groups;
  }
}
