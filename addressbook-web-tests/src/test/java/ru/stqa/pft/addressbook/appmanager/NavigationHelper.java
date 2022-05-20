package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

  public NavigationHelper(WebDriver wd) {
    super(wd);
  }

  public void goToHomePage() {
    if (isElementPresent(By.xpath("//*[contains(text(),'Delete record')]"))) {
      click(By.linkText("home"));
      return;
    }

    if (isElementPresent(By.id("maintable"))) {
      return;
    }

    click(By.linkText("home"));
  }

  public void groupPage() {
    if (isElementPresent(By.tagName("h1"))
        && wd.findElement(By.tagName("h1")).getText().equals("Groups")
        && isElementPresent(By.name("name"))) {
      return;
    }
    click(By.linkText("groups"));
  }

  public void goToAddContractPage() {
    click(By.linkText("add new"));
  }
}
