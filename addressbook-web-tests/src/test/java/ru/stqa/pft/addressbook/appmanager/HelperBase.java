package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class HelperBase {

  WebDriver wd;

  public HelperBase(WebDriver wd) {
    this.wd = wd;
  }

  protected void selectFromDropDownBox(By locator, String value) {
    click(locator);
    Select dropDown = new Select(wd.findElement(locator));
    if (isDropDownValuePresented(dropDown, value)) {
      dropDown.selectByVisibleText(value);
    }
  }

  protected void click(By locator) {
    wd.findElement(locator).click();
  }

  protected void type(By locator, String text) {
    click(locator);
    if (text != null){
      String existingText = wd.findElement(locator).getAttribute("value");
      if (! text.equals(existingText)){
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(text);
      }
    }
  }

  protected boolean isElementPresent(By locator) {
    try {
      wd.findElement(locator);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private boolean isDropDownValuePresented(Select dropDown, String value){
    try {
      dropDown.selectByVisibleText(value);
      return true;
    }
    catch (NoSuchElementException e){
      return false;
    }
  }

}
