package ru.stqa.pft.mantis.appmanager.pages;

import org.openqa.selenium.By;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

public class LoginHelper extends HelperBase {
  public LoginHelper(ApplicationManager app) {
    super(app);
  }

  public void asAdmin() {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    enterUsername(app.getProperty("web.adminLogin"));
    enterPassword(app.getProperty("web.adminPassword"));
  }
  private void enterUsername(String username) {
    type(By.name("username"), username);
    click(By.cssSelector("input[value='Login']"));
  }

  private void enterPassword(String password) {
    type(By.name("password"), password);
    click(By.cssSelector("input[value='Login']"));
  }

}
