package ru.stqa.pft.mantis.appmanager.pages;

import org.openqa.selenium.By;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

public class ManageUsersHelper extends HelperBase {
  public ManageUsersHelper(ApplicationManager app) {
    super(app);
  }

  public void selectUser(String userName) {
    wd.navigate().to(app.getProperty("web.baseUrl") + "/manage_user_page.php");
    click(By.linkText(userName));
  }
}
