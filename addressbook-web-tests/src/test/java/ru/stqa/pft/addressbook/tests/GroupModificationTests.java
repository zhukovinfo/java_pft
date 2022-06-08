package ru.stqa.pft.addressbook.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().createGroup(new GroupData()
          .withName("test1")
          .withHeader("test2")
          .withFooter("test3"));
    }
  }

  @Test
  public void testModificationGroup(){
    Groups before = app.db().groups();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
        .withId(modifiedGroup.getId())
        .withName("test1")
        .withHeader("test2")
        .withFooter("test3");
    app.goTo().groupPage();
    app.group().modify(modifiedGroup);
    assertEquals(app.group().count(), before.size());

    Groups after = app.db().groups();
    assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
    verifyGroupListInUI();
  }

}
