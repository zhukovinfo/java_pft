package ru.stqa.pft.addressbook.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.json.TypeToken;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class ContactCreationTests extends TestBase {

  @BeforeMethod
  private void ensureGroups(){
    Groups groups = app.db().groups();
    if (groups.size() == 0) {
      app.group().createGroup(new GroupData()
          .withName("test group 1")
          .withFooter("test header")
          .withFooter("test footer"));
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromCSV() throws IOException {
    List<Object[]> list = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.csv"))) {
      String line = reader.readLine();
      while (line != null) {
        String[] split = line.split(";");
        list.add(new Object[]{new ContactData()
            .withFirstName(split[0])
            .withMiddleName(split[1])
            .withLastName(split[2])
            .withNickName(split[3])
            .withTitle(split[4])
            .withCompanyName(split[5])
            .withAddress(split[6])
            .withHomePhone(split[7])
            .withMobilePhone(split[8])
            .withWorkPhone(split[9])
            .withFax(split[10])
            .withEmail(split[11])
            .withEmail2(split[12])
            .withEmail3(split[13])
            .withHomePage(split[14])
            .withBirthDay(split[15])
            .withBirthMonth(split[16])
            .withBirthYear(split[17])
            .withAddress2(split[18])
            .withHomePhone2(split[19])
            .withNotes(split[20])
            .withPhoto(new File(split[21]))});
        line = reader.readLine();
      }
      return list.iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromXML() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.xml"))) {
      StringBuilder xml = new StringBuilder();
      String line = reader.readLine();
      while (line != null) {
        xml.append(line);
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.allowTypes(new Class[]{ContactData.class});
      xstream.processAnnotations(Contacts.class);
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml.toString());
      return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.json"))) {
      StringBuilder json = new StringBuilder();
      String line = reader.readLine();
      while (line != null) {
        json.append(line);
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(String.valueOf(json), new TypeToken<List<ContactData>>(){}.getType());
      return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contact) {
    Contacts before = app.db().contacts();
    Groups groups = app.db().groups();
    app.goTo().addContractPage();
    app.contacts().add(contact);
    Contacts after = app.db().contacts();

    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(before
        .withAdded(contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
  }

  @Test(enabled = false)
  public void testCurrentDIr() {
    File currentDir = new File(".");
    File photo = new File("src/test/resources/photo_2022.jpg");
    System.out.println(photo.getAbsoluteFile());
    System.out.println(photo.exists());
  }
}
