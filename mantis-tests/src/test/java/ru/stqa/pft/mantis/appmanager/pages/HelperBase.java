package ru.stqa.pft.mantis.appmanager.pages;

import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

public class HelperBase {

  protected final ApplicationManager app;
  WebDriver wd;

  public HelperBase(ApplicationManager app) {
    this.app = app;
    this.wd = app.getDriver();
  }

  protected void selectFromDropDownBox(By locator, String value) {
    Select dropDown = new Select(wd.findElement(locator));
    try {
      dropDown.selectByVisibleText(value);
    }
    catch (NoSuchElementException e){
      System.out.println(e.getMessage());
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

  protected void attach(By locator, File file) {
    if (file != null){
        wd.findElement(locator).sendKeys(file.getAbsolutePath());
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

}
