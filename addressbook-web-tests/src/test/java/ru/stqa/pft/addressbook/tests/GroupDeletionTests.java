package ru.stqa.pft.addressbook.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensureConditions() {
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().createGroup(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupDeletion() {
    Groups before = app.group().all();
    GroupData deletedGroup = before.iterator().next();
    app.group().delete(deletedGroup);
    assertThat(app.group().count(), equalTo(before.size() - 1));

    Groups after = app.group().all();
    assertThat(after, equalTo(before.without(deletedGroup)));
  }

}
