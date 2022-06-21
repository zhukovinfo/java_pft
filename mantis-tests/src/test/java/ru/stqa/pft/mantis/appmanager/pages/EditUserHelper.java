package ru.stqa.pft.mantis.appmanager.pages;

import org.openqa.selenium.By;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

public class EditUserHelper extends HelperBase {

  public EditUserHelper(ApplicationManager app) {
    super(app);
  }

  public void selectUser(String userName) {
    click(By.linkText(userName));
  }

  public void resetPassword() {
    click(By.cssSelector("input[value='Reset Password']"));
  }
}
