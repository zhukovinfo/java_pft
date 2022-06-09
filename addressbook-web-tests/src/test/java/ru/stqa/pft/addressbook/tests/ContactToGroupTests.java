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
    Groups allGroups = app.db().groups();
    if (allGroups.size() == 0) {
      app.group().createGroup(new GroupData()
          .withName("test group 1")
          .withFooter("test header")
          .withFooter("test footer"));
    }

    Contacts contacts = app.db().contacts();
    contact = contacts.iterator().next();
    if (contact.getGroups().size() == allGroups.size()) {
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
