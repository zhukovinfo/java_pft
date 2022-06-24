package ru.stqa.pft.addressbook.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class ContactFromGroupTests extends TestBase {

  private ContactData contact;

  @BeforeMethod
  private void ensurePreconditions(){
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().createGroup(new GroupData()
          .withName("test1")
          .withHeader("test2")
          .withFooter("test3"));
    }

    if (app.db().contacts().size() == 0) {
      app.goTo().addContractPage();
      app.contacts().add(new ContactData()
          .withFirstName("Andrey")
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
          .withBirthDay("14")
          .withBirthMonth("March")
          .withBirthYear("1987")
          .withAddress2("aaa")
          .withHomePhone2("89878658493")
          .withNotes("some notes")
          .inGroup(app.db().groups().iterator().next()));
    }

    contact = app.db().contacts().iterator().next();
    if (contact.getGroups().size() == 0) {
      GroupData group = app.db().groups().iterator().next();
      app.contacts().addToGroup(contact, group);
      app.goTo().homePage();
    }
  }

  @Test
  public void testContactFromGroup() {
    Groups before = app.db().contactById(contact.getId()).getGroups();
    GroupData group = before.iterator().next();
    app.contacts().removeFromGroup(contact, group);
    Groups after = app.db().contactById(contact.getId()).getGroups();

    assertThat(after.size(), equalTo(before.size() - 1));
    assertThat(after, equalTo(before.without(group)));
  }

}
