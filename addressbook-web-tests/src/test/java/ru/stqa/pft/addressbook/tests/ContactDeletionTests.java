package ru.stqa.pft.addressbook.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    if (app.db().contacts().size() == 0) {
      app.goTo().addContractPage();
      Groups groups = app.db().groups();
      app.contacts().add(new ContactData()
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
          .withBirthDay("14")
          .withBirthMonth("March")
          .withBirthYear("1987")
          .withAddress2("aaa")
          .withHomePhone2("89878658493")
          .withNotes("some notes")
          .inGroup(groups.iterator().next()));
    }
  }

  @Test
  public void testContactDeletion() {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();
    app.contacts().delete(deletedContact);
    app.goTo().homePage();
    Contacts after = app.db().contacts();

    assertThat(app.group().count(), equalTo(before.size() - 1));
    assertThat(after, equalTo(before.withOut(deletedContact)));
  }
}
