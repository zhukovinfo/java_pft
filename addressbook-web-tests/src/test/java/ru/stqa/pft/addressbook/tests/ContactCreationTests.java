package ru.stqa.pft.addressbook.tests;

import java.util.Comparator;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test(enabled = false)
  public void testContactCreation() {
    List<ContactData> before = app.getContactHelper().getContactList();
    app.goTo().goToAddContractPage();
    ContactData contact = new ContactData("Andrey1", "Nikolaevich", "Zhukov", "zhukov_info",
        "QA", "SomeCompany", "test address", "home", "89878658490", "12222",
        "12345", "zhukovinfo@gmail.com", "mail2@email.com", "email3@email.com",
        "www.home.com", "25", "March", "1987", "aaa",
        "89878658493", "some notes", "test1");
    app.getContactHelper().addContact(contact);
    List<ContactData> after = app.getContactHelper().getContactList();

    Assert.assertEquals(after.size(), before.size() + 1);

    before.add(contact);
    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(after, before);
  }

}
