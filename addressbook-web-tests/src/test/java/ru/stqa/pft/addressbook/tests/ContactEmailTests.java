package ru.stqa.pft.addressbook.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactEmailTests extends TestBase{

  @Test
  public void testContactEMail(){
    app.goTo().homePage();
    ContactData contact = app.contacts().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contacts().infoFromEditForm(contact);

    assertThat(contact.getAllEMails(), equalTo(mergeEmails(contactInfoFromEditForm)));
  }

  private String mergeEmails(ContactData contact) {
    return Stream.of(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
        .filter(s -> !s.equals(""))
        .collect(Collectors.joining("\n"));
  }

}
