package ru.stqa.pft.addressbook.appmanager;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactEntry(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("middlename"), contactData.getMiddleName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("nickname"), contactData.getNickName());
    attach(By.name("photo"), contactData.getPhoto());
    type(By.name("title"), contactData.getTitle());
    type(By.name("company"), contactData.getCompanyName());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"),contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("fax"), contactData.getFax());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    type(By.name("homepage"), contactData.getHomePage());
    selectFromDropDownBox(By.name("bday"), contactData.getBirthDay());
    selectFromDropDownBox(By.name("bmonth"), contactData.getBirthMonth());
    type(By.name("byear"), contactData.getBirthYear());
    type(By.name("address2"), contactData.getAddress2());
    type(By.name("phone2"), contactData.getHomePhone2());
    type(By.name("notes"), contactData.getNotes());
    if (creation) {
      if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        selectFromDropDownBox(By.name("new_group"), contactData.getGroups().iterator().next().getName());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void select(int contact_index) {
    wd.findElements(By.name("selected[]")).get(contact_index).click();
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='"+ id +"']")).click();
  }

  public void deleteSelectedContact() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void closeDeletionAlertWindow() {
    wd.switchTo().alert().accept();
  }

  public void initContactModification(int id) {
    wd.findElement(By.xpath(String.format("//a[@href='edit.php?id=%s']", id))).click();
  }

  public void editSelectedGroup(){
    click(By.xpath("//div[@id='content']/form/input[22]"));
  }

  public void returnToHomePage() {
    click(By.linkText("home page"));
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  private void addTo(String groupName) {
    selectFromDropDownBox(By.name("to_group"), groupName);
    click(By.name("add"));
  }

  private void removeFrom() {
    click(By.name("remove"));
  }

  public void selectGroup(GroupData group) {
    selectFromDropDownBox(By.name("group"), group.getName());
  }
  public void add(ContactData contactData) {
    fillContactEntry(contactData, true);
    submitContactCreation();
    returnToHomePage();
  }

  public void delete(ContactData contactData){
    selectContactById(contactData.getId());
    deleteSelectedContact();
    closeDeletionAlertWindow();
  }

  public void modify(ContactData contact) {
    initContactModification(contact.getId());
    fillContactEntry(contact, false);
    editSelectedGroup();
    returnToHomePage();
  }

  public void addToGroup(ContactData contact, GroupData group){
    selectContactById(contact.getId());
    addTo(group.getName());
  }

  public void removeFromGroup(ContactData contact, GroupData group) {
    selectGroup(group);
    selectContactById(contact.getId());
    removeFrom();
  }

  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<>();
    List<WebElement> rows = wd.findElement(By.id("maintable")).findElements(By.name("entry"));
    for (WebElement row: rows){
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("id"));
      String lastName = cells.get(1).getText();
      String firstName = cells.get(2).getText();
      contacts.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName));
    }
    return contacts;
  }
  public Contacts all() {
    Contacts contacts = new Contacts();
    List<WebElement> rows = wd.findElement(By.id("maintable")).findElements(By.name("entry"));
    for (WebElement row: rows){
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("id"));
      String lastName = cells.get(1).getText();
      String firstName = cells.get(2).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      contacts.add(new ContactData()
              .withId(id)
              .withFirstName(firstName)
              .withLastName(lastName)
              .withAddress(address)
              .withAllEMails(allEmails)
              .withAllPhones(allPhones));
    }
    return contacts;
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModification(contact.getId());
    String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getText();
    String homePhone2 = wd.findElement(By.name("phone2")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData()
        .withId(contact.getId())
        .withFirstName(firstName)
        .withLastName(lastName)
        .withHomePhone(home)
        .withHomePhone2(homePhone2)
        .withMobilePhone(mobile)
        .withWorkPhone(work)
        .withAddress(address)
        .withEmail(email)
        .withEmail2(email2)
        .withEmail3(email3);
  }
}
