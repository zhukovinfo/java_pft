package ru.stqa.pft.mantis.appmanager;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import ru.stqa.pft.mantis.appmanager.pages.EditUserHelper;
import ru.stqa.pft.mantis.appmanager.pages.LoginHelper;
import ru.stqa.pft.mantis.appmanager.pages.ManageUsersHelper;
import ru.stqa.pft.mantis.appmanager.pages.RegistrationHelper;
import ru.stqa.pft.mantis.appmanager.utilities.DbHelper;
import ru.stqa.pft.mantis.appmanager.utilities.FtpHelper;
import ru.stqa.pft.mantis.appmanager.utilities.JamesHelper;
import ru.stqa.pft.mantis.appmanager.utilities.MailHelper;

public class ApplicationManager {

  private final Properties properties;
  private WebDriver wd;
  private final String browser;
  private RegistrationHelper registrationHelper;
  private FtpHelper ftp;
  private MailHelper mailHelper;
  private JamesHelper jamesHelper;
  private LoginHelper loginHelper;
  private DbHelper dbHelper;
  private ManageUsersHelper manageUsersHelper;
  private EditUserHelper editUserHelper;
  private SoapHelper soapHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));
  }

  public void stop() {
    if (wd != null) {
      wd.quit();
    }
  }

  public HttpSession newSession() {
    return new HttpSession(this);
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }


  public WebDriver getDriver() {
    if (wd == null) {
      if (Objects.equals(browser, BrowserType.FIREFOX)) {
        wd = new FirefoxDriver();
      } else if (Objects.equals(browser, BrowserType.CHROME)) {
        wd = new ChromeDriver();
      } else if (Objects.equals(browser, BrowserType.IE)) {
        wd = new InternetExplorerDriver();
      }
      wd.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
      wd.get(properties.getProperty("web.baseUrl"));
    }
    return wd;
  }

  public FtpHelper ftp() {
    if (ftp == null) {
      ftp = new FtpHelper(this);
    }
    return ftp;
  }

  public MailHelper mail() {
    if (mailHelper == null){
      mailHelper = new MailHelper(this);
    }
    return mailHelper;
  }

  public JamesHelper james() {
    if (jamesHelper == null) {
      jamesHelper = new JamesHelper(this);
    }
    return jamesHelper;
  }

  public DbHelper db() {
    if (dbHelper == null) {
      dbHelper = new DbHelper();
    }
    return dbHelper;
  }

  public SoapHelper soap() {
    if (soapHelper == null) {
      soapHelper = new SoapHelper(this);
    }
    return soapHelper;
  }

  public RegistrationHelper registration() {
    if (registrationHelper == null) {
      registrationHelper = new RegistrationHelper(this);
    }
    return registrationHelper;
  }

  public LoginHelper login() {
    if (loginHelper == null) {
      loginHelper = new LoginHelper(this);
    }
    return loginHelper;
  }

  public ManageUsersHelper manageUsers() {
    if (manageUsersHelper == null) {
      manageUsersHelper = new ManageUsersHelper(this);
    }
    return manageUsersHelper;
  }

  public EditUserHelper editUser() {
    if (editUserHelper == null) {
      editUserHelper = new EditUserHelper(this);
    }
    return editUserHelper;
  }
}
