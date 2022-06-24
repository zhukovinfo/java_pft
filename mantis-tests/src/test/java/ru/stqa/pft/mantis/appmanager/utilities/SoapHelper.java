package ru.stqa.pft.mantis.appmanager.utilities;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import biz.futureware.mantis.rpc.soap.client.ObjectRef;
import biz.futureware.mantis.rpc.soap.client.ProjectData;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.rpc.ServiceException;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;
import ru.stqa.pft.mantis.model.Status;

public class SoapHelper {

  private final ApplicationManager app;

  public SoapHelper(ApplicationManager app) {
    this.app = app;
  }

  public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    String user = app.getProperty("web.adminLogin");
    String password = app.getProperty("web.adminPassword");
    ProjectData[] projects = mc
        .mc_projects_get_user_accessible(user, password);

    return Arrays.stream(projects)
        .map(p -> new Project().withId(p.getId()).withName(p.getName()))
        .collect(Collectors.toSet());
  }

  private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
    String apiUrl = app.getProperty("web.baseUrlOld") + app.getProperty("web.apiUrl");
    return new MantisConnectLocator()
        .getMantisConnectPort(new URL(apiUrl));
  }

  public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
    IssueData issueData = new IssueData();
    issueData.setSummary(issue.getSummary());
    issueData.setDescription(issue.getDescription());
    issueData.setProject(new ObjectRef(issue.getProject().getId(), issue.getProject().getName()));

    String user = app.getProperty("web.adminLogin");
    String password = app.getProperty("web.adminPassword");
    MantisConnectPortType mc = getMantisConnect();
    String[] categories = mc.mc_project_get_categories(user, password, issue.getProject().getId());
    issueData.setCategory(categories[0]);
    BigInteger issueId = mc.mc_issue_add(user, password, issueData);
    IssueData createdIssuedData = mc.mc_issue_get(user, password, issueId);

    return new Issue().withId(createdIssuedData.getId().intValue())
        .withSummary(createdIssuedData.getSummary())
        .withDescription(createdIssuedData.getDescription())
        .withProject(new Project()
            .withId(createdIssuedData.getProject().getId())
            .withName(createdIssuedData.getProject().getName()));
  }

  public Issue getIssue(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    String user = app.getProperty("web.adminLogin");
    String password = app.getProperty("web.adminPassword");
    IssueData given = mc.mc_issue_get(user, password, BigInteger.valueOf(issueId));
    return new Issue()
        .withId(given.getId().intValue())
        .withDescription(given.getDescription())
        .withSummary(given.getSummary())
        .withStatus(new Status()
            .withId(given.getStatus().getId())
            .withName(given.getStatus().getName()))
        .withProject(new Project()
            .withId(given.getProject().getId())
            .withName(given.getProject().getName()));
  }
}
