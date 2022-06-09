package ru.stqa.pft.addressbook.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class ContactFromGroupTests extends TestBase {

  private ContactData contact;

  @BeforeMethod
  private void ensurePreconditions(){
    if (app.db().groups().size() == 0) {
      app.group().createGroup(new GroupData()
          .withName("test group 1")
          .withFooter("test header")
          .withFooter("test footer"));
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
