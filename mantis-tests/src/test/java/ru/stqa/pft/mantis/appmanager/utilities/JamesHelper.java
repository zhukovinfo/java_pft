package ru.stqa.pft.mantis.appmanager.utilities;

import static java.lang.Thread.sleep;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import org.apache.commons.net.telnet.TelnetClient;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;
import ru.stqa.pft.mantis.model.MailMessage;

public class JamesHelper {

  private final ApplicationManager app;

  private final TelnetClient telnet;
  private InputStream in;
  private PrintStream out;

  private final Session mailSession;
  private String mailserver;
  private Store store;

  public JamesHelper(ApplicationManager app) {
    this.app = app;
    this.telnet = new TelnetClient();
    mailSession = Session.getDefaultInstance(System.getProperties());
  }

  public boolean doesUserExist(String name) {
    initTelnetSession();
    write("verify " + name);
    String result = readUntil("exists");
    return Objects.requireNonNull(result).trim().equals("User "+ name + " exist");
  }

  public void drainEmail(String username, String password) throws MessagingException {
    Folder inbox = openInbox(username, password);
    for (Message message: inbox.getMessages()) {
      message.setFlag(Flag.DELETED, true);
    }
    closeFolder(inbox);
  }

  public void createUser(String name, String password) {
    initTelnetSession();
    write("adduser " + name + " " + password);
    readUntil("User " + name + " added");
    closeTelnetSession();
  }

  private void initTelnetSession() {
    mailserver = app.getProperty("mailserver.host");
    int port = Integer.parseInt(app.getProperty("mailserver.port"));
    String login = app.getProperty("mailserver.adminlogin");
    String password = app.getProperty("mailserver.adminpassword");

    try {
      telnet.connect(mailserver, port);
      in = telnet.getInputStream();
      out = new PrintStream(telnet.getOutputStream());
    } catch (Exception e) {
      e.printStackTrace();
    }

//    readUntil("Login id:");
//    write("");
//    readUntil("Password:");
//    write("");

    readUntil("Login id:");
    write(login);
    readUntil("Password:");
    write(password);

    readUntil("Welcome " + login + ". HELP for a list of commands");
  }

  private String readUntil(String pattern) {
    try {
      char lastChar = pattern.charAt(pattern.length() - 1);
      StringBuilder sb = new StringBuilder();
      char ch = (char) in.read();
      while (true) {
        System.out.println(ch);
        sb.append(ch);
        if (ch == lastChar) {
          if (sb.toString().endsWith(pattern)) {
            return sb.toString();
          }
        }
        ch = (char) in.read();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private void write(String value) {
    try {
      out.println(value);
      out.flush();
      System.out.println(value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void closeTelnetSession() {
    write("quit");
  }

  public List<MailMessage> waitForMail(String username, String password, long timeout) throws MessagingException {
    long now = System.currentTimeMillis();
    while (System.currentTimeMillis() < now + timeout) {
      List<MailMessage> allMail = getAllMail(username, password);
      assert allMail != null;
      if (allMail.size() > 0) {
        return allMail;
      }
      try {
        sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    throw new Error("No mail :(");
  }

  private List<MailMessage> getAllMail(String username, String password) throws MessagingException {
    Folder inbox = openInbox(username, password);
    List<MailMessage> messages = Arrays.stream(inbox.getMessages())
        .map(JamesHelper::toModelMail)
        .collect(Collectors.toList());
    closeFolder(inbox);
    return messages;
  }

  private static MailMessage toModelMail(Message m) {
    try {
      return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());
    } catch (MessagingException | IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private Folder openInbox(String username, String password) throws MessagingException {
    store = mailSession.getStore("pop3");
    store.connect(mailserver, username, password);
    Folder folder = store.getDefaultFolder().getFolder("Inbox");
    folder.open(Folder.READ_WRITE);
    return folder;
  }

  private void closeFolder(Folder folder) throws MessagingException {
    folder.close(true);
    store.close();
  }




}
