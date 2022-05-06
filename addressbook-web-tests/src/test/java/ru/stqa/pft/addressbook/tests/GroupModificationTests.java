package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupModificationTests extends TestBase {

  @Test
  public void testModificationGroup(){
    app.getNavigationHelper().goToGroupPage();
    app.getGroupHelper().selectFirstGroup();
    app.getGroupHelper().initGroupModification();
    app.getGroupHelper().fillGroupForm(new GroupData("test2", "test3", "test4"));
    app.getGroupHelper().updateSelectedGroup();
    app.getGroupHelper().returnToGroupPage();
  }

}
