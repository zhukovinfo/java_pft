package ru.stqa.pft.addressbook.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("test2");
    app.group().createGroup(group);
    assertThat(app.group().count(), equalTo(before.size() + 1));

    Groups after = app.group().all();
    assertThat(after, equalTo(
        before.withAdded(group.withId(after.stream().mapToInt(GroupData::getId).max().getAsInt()))));
  }

  @Test
  public void testBadGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("test'");
    app.group().createGroup(group);
    assertThat(app.group().count(), equalTo(before.size()));

    Groups after = app.group().all();
    assertThat(after, equalTo(before));
  }
}
