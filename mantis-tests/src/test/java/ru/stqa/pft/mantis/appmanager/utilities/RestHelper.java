package ru.stqa.pft.mantis.appmanager.utilities;

import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.given;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.util.Set;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;
import ru.stqa.pft.mantis.model.BugifyIssue;

public class RestHelper {

  private final ApplicationManager app;

  public RestHelper(ApplicationManager app) {
    this.app = app;
    authentication = basic(app.getProperty("bugify.userName"), app.getProperty("bugify.password"));
  }

  public BugifyIssue getIssue(int issueId) {
    String json = given()
        .get(app.getProperty("bugify.baseUrl")+ issueId +".json")
        .asString();

    JsonElement parsed = JsonParser
        .parseString(json)
        .getAsJsonObject().get("issues");

    Set<BugifyIssue> issues = new Gson().fromJson(parsed, new TypeToken<Set<BugifyIssue>>(){}.getType());
    return issues.stream().findFirst().get();
  }

}
