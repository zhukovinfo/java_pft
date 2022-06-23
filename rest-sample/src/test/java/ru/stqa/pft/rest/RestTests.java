package ru.stqa.pft.rest;

import static org.testng.Assert.assertEquals;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

public class RestTests {

  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = getIssues();
    assert oldIssues != null;

    Issue newIssue = new Issue().withSubject("zhukov test issue").withDescription("zhukov test description");
    int issueId = createIssue(newIssue);
    Set<Issue> newIssues = getIssues();

    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }

  private int createIssue(Issue newIssue) throws IOException {
    String json = getExecutor()
        .execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
            .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                new BasicNameValuePair("description", newIssue.getDescription())))
        .returnContent().asString();
    JsonElement parsed = JsonParser.parseString(json);

    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

  private Set<Issue> getIssues() throws IOException {
    String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json"))
        .returnContent().asString();
    JsonElement parsed = JsonParser.parseString(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
  }

  private Executor getExecutor() {
    return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }
}