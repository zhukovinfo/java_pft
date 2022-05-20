package ru.stqa.pft.addressbook.tests;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

  @Test(enabled = false)
  public void testContactDeletion() {
    if (!app.getContactHelper().isThereAContact()) {
      app.goTo().goToAddContractPage();
      app.getContactHelper().addContact(
          new ContactData("Andrey", "Nikolaevich", "Zhukov", "zhukov_info",
              "QA", "SomeCompany", "test address", "home", "89878658490",
              "12222", "12345", "zhukovinfo@gmail.com", "mail2@email.com",
              "email3@email.com", "www.home.com", "14", "March", "1987",
              "aaa", "89878658493", "some notes", "test1"));
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(0);
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().closeDeletionAlertWindow();
    app.goTo().goToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();

    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(before.size() - 1);

    Assert.assertEquals(after, before);
  }
}
