package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.getNavigationHelper().goToAddContractPage();
    app.getContactHelper().fillContactEntry(
        new ContactData("Andrey", "Nikolaevich", "Zhukov", "zhukov_info",
            "QA", "SomeCompany", "test address", "home","89878658490", "12222",
            "12345", "zhukovinfo@gmail.com", "mail2@email.com", "email3@email.com",
            "www.home.com", "14", "March", "1987", "aaa",
            "89878658493", "some notes", "test1"), true);
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().goToHomePage();
  }

}
