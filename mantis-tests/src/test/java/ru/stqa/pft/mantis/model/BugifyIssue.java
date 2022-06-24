package ru.stqa.pft.mantis.model;

public class BugifyIssue {
    private int id;
    private String subject;
    private String description;
    private String state_name;

  public String getStateName() {
    return state_name;
  }

  public BugifyIssue withStateName(String state) {
    this.state_name = state;
    return this;
  }

  public int getId() {
    return id;
  }

  public BugifyIssue withId(int id) {
    this.id = id;
    return this;
  }

  public String getSubject() {
    return subject;
  }

  public BugifyIssue withSummary(String summary) {
    this.subject = summary;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public BugifyIssue withDescription(String description) {
    this.description = description;
    return this;
  }

}
