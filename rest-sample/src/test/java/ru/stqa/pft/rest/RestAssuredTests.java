package ru.stqa.pft.rest;

import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.Set;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestAssuredTests {

  @BeforeClass
  public void init() {
    authentication = basic("288f44776e7bec4bf44fdfeb1e646490", "");
  }

  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = getIssues();
    assert oldIssues != null;

    Issue newIssue = new Issue()
        .withSubject("zhukov test issue")
        .withDescription("zhukov test description");
    int issueId = createIssue(newIssue);
    Set<Issue> newIssues = getIssues();

    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }

  private int createIssue(Issue newIssue) throws IOException {
    String json = given()
        .param("subject", newIssue.getSubject())
        .param("description", newIssue.getDescription())
        .post("https://bugify.stqa.ru/api/issues.json")
        .asString();

    JsonElement parsed = JsonParser.parseString(json);

    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

  private Set<Issue> getIssues() throws IOException {
    String json =  given()
        .get("https://bugify.stqa.ru/api/issues.json")
        .asString();

    JsonElement parsed = JsonParser.parseString(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");

    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
  }

}
