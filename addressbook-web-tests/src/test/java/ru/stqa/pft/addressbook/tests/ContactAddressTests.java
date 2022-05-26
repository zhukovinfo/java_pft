package ru.stqa.pft.addressbook.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactAddressTests extends TestBase {

  @Test
  public void testContactAddress(){
    app.goTo().homePage();
    ContactData contact = app.contacts().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contacts().infoFromEditForm(contact);

    assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
  }

}
