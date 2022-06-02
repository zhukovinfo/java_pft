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
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactsDataGenerator {

  @Parameter(names = "-c", description = "group count")
  public int count;

  @Parameter(names = "-f", description = "target file")
  public String file;

  @Parameter(names = "-d", description = "data format")
  public String dataFormat;

  public static void main(String[] args) throws IOException {
    ContactsDataGenerator generator = new ContactsDataGenerator();
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
    List<ContactData> contacts = generate(count);
    switch (dataFormat) {
      case "csv":
        saveAsCsv(contacts, new File(file));
        break;
      case "xml":
        savesAsXml(contacts, new File(file));
        break;
      case "json":
        saveAsJson(contacts, new File(file));
        break;
      default:
        System.out.println("unrecognized format " + dataFormat);
        break;
    }
  }

  private void savesAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xStream = new XStream();
    xStream.processAnnotations(ContactData.class);
    String xml = xStream.toXML(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);
    }
  }

  private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
    try (Writer writer = new FileWriter(file)) {
      for (ContactData contact : contacts) {
        writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
            contact.getFirstName(),
            contact.getMiddleName(),
            contact.getLastName(),
            contact.getNickName(),
            contact.getTitle(),
            contact.getCompanyName(),
            contact.getAddress(),
            contact.getHomePhone(),
            contact.getMobilePhone(),
            contact.getWorkPhone(),
            contact.getFax(),
            contact.getEmail(),
            contact.getEmail2(),
            contact.getEmail3(),
            contact.getHomePage(),
            contact.getBirthDay(),
            contact.getBirthMonth(),
            contact.getBirthYear(),
            contact.getAddress2(),
            contact.getHomePhone2(),
            contact.getNotes(),
            contact.getPhoto()));
      }
    }
  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(json);
    }
  }

  private static List<ContactData> generate(int count) {
    List<ContactData> contacts = new ArrayList<>();
    File photo = new File("src/test/resources/photo_2022.jpg");
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData()
          .withFirstName(String.format("Andrey %s", i))
          .withMiddleName("Nikolaevich")
          .withLastName("Zhukov")
          .withNickName("zhukov_info")
          .withTitle("QA")
          .withCompanyName("SomeCompany")
          .withAddress("test address")
          .withHomePhone("home")
          .withMobilePhone("89878658490")
          .withWorkPhone("12222")
          .withFax("12345")
          .withEmail("zhukovinfo@gmail.com")
          .withEmail2("mail2@email.com")
          .withEmail3("email3@email.com")
          .withHomePage("www.home.com")
          .withBirthDay("25")
          .withBirthMonth("March")
          .withBirthYear("1987")
          .withAddress2("aaa")
          .withHomePhone2("89878658493")
          .withNotes("some notes")
          .withPhoto(photo));
    }
    return contacts;
  }

}
