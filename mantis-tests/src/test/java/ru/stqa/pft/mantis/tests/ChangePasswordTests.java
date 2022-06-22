package ru.stqa.pft.mantis.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

public class ChangePasswordTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void resetPassword() throws IOException {
    app.login().asAdmin();

    UserData user = app.db().users().stream().iterator().next();
    String username = user.getUsername();
    String password = "new_password";

    app.manageUsers().selectUser(username);
    app.editUser().resetPassword();

    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, user.getEmail());

    app.registration().finish(confirmationLink, username, password);

    assertTrue(app.newSession().login(username, password));
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter(m -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex()
        .find("http://").nonSpace().oneOrMore()
        .build();

    return regex.getText(mailMessage.text);
  }
}
