package ru.stqa.pft.addressbook.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    Contacts before = app.contacts().all();
    app.goTo().addContractPage();

    File photo = new File("src/test/resources/photo_2022.jpg");
    ContactData contact = new ContactData()
        .withFirstName("Andrey1")
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
        .withPhoto(photo);

    app.contacts().add(contact);
    Contacts after = app.contacts().all();

    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(before
        .withAdded(contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
  }

  @Test
  public void testCurrentDIr() {
    File currentDir = new File(".");
    File photo = new File("src/test/resources/photo_2022.jpg");
    System.out.println(photo.getAbsoluteFile());
    System.out.println(photo.exists());
  }
}
