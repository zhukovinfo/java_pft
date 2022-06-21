package ru.stqa.pft.mantis.appmanager.pages;

import org.openqa.selenium.By;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

public class RegistrationHelper extends HelperBase {
  public RegistrationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"),username);
    type(By.name("email"), email);
    click(By.cssSelector("input[value='Signup']"));
  }

  public void finish(String confirmationLink, String username, String password) {
    wd.get(confirmationLink);
    type(By.name("realname"), username);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.xpath("//form[@id='account-update-form']/fieldset/span/button/span"));
  }
}
