package ru.stqa.pft.mantis.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import javax.xml.rpc.ServiceException;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;

public class LoginTests extends TestBase {

  @Test
  public void testLogin() throws IOException, ServiceException {
    //skipIfNotFixed(0000001); Mantis issue id, for soap example
    skipIfNotFixed(2056); // Bugify issue id, for rest example
    HttpSession session = app.newSession();
    assertTrue(session.login("administrator", "root"));
    assertTrue(session.isLoggedInAs("administrator"));
  }

}
