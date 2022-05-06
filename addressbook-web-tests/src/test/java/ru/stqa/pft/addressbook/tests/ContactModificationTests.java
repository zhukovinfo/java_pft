package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testModification() {
    app.getContactHelper().selectFirstContact();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactEntry(
        new ContactData("Vladimir", "Alexeyevich", "Sokolov", "sokolov_info",
            "QC", "SomeCompany1", "test address1", "123", "89878658491",
            "222", "3333", "sokolovinfo@gmail.com", "mail3@email.com",
            "email5@email.com","www.home1.com", "15", "March", "1988",
            "www","89878658491", "some notes more"));
    app.getContactHelper().editSelectedGroup();
    app.getContactHelper().returnToHomePage();


  }
}
