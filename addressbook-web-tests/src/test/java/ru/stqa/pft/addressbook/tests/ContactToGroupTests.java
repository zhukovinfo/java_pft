package ru.stqa.pft.addressbook.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class ContactToGroupTests extends TestBase {

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
    if (contact.getGroups().size() == app.db().groups().size()) {
      app.goTo().addContractPage();
      String firstName = "User without a group";
      app.contacts().add(new ContactData()
          .withFirstName(firstName)
          .withMiddleName("Nikolaevich")
          .withLastName("Petrov")
          .withBirthDay("14")
          .withBirthMonth("March")
          .withBirthYear("1987"));
      contact = app.db().contactByfirstName(firstName);
    }

  }

  @Test
  public void testContactToGroup() {
    Groups before = contact.getGroups();

    GroupData group = app.db().groups().iterator().next();
    app.contacts().addToGroup(contact, group);
    Groups after = app.db().contactById(contact.getId()).getGroups();

    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(before
        .withAdded(group.withId(after.stream().mapToInt(GroupData::getId).max().getAsInt()))));
  }

}
