package ru.stqa.pft.addressbook.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
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
          .withGroup("test1"));
    }
  }

  @Test()
  public void testModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
        .withId(modifiedContact.getId())
        .withFirstName("Vladimir")
        .withMiddleName("Alexeyevich")
        .withLastName("Sokol")
        .withNickName("sokoban_info")
        .withTitle("QC")
        .withCompanyName("SomeCompany1")
        .withAddress("test address 1")
        .withHomePhone("home")
        .withMobilePhone("89878658490")
        .withWorkPhone("12222")
        .withFax("12345")
        .withEmail("sokolovinfo@gmail.com")
        .withEmail2("mail5@email.com")
        .withEmail3("email6@email.com")
        .withHomePage("www.home.com")
        .withBirthDay("15")
        .withBirthMonth("March")
        .withBirthYear("1988")
        .withAddress2("qqq")
        .withHomePhone2("89878658491")
        .withNotes("some notes more");

    app.contacts().modify(contact);
    Contacts after = app.db().contacts();

    assertThat(after.size(), equalTo(before.size()));
    assertThat(after, equalTo(before.withOut(modifiedContact).withAdded(contact)));
  }
}
