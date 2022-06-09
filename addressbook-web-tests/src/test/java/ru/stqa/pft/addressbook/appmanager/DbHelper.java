package ru.stqa.pft.addressbook.appmanager;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class DbHelper {
  SessionFactory sessionFactory;
  public DbHelper(){
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
        .configure() // configures settings from hibernate.cfg.xml
        .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

  }

  public Groups groups() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<GroupData> result = session.
        createQuery( "from GroupData where deprecated = '0000-00-00'" )
        .list();
    session.getTransaction().commit();
    session.close();
    return new Groups(result);
  }

  public Contacts contacts() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<ContactData> result = session
        .createQuery( "from ContactData where deprecated = '0000-00-00'" )
        .list();
    session.getTransaction().commit();
    session.close();
    return new Contacts(result);
  }

  public ContactData contactById(int id) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<ContactData> result = session
        .createQuery( "from ContactData where id = " + id)
        .list();
    session.getTransaction().commit();
    session.close();
    return result.get(0);
  }

  public ContactData contactByfirstName(String firstName) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<ContactData> result = session
        .createQuery( "from ContactData where firstname = '" + firstName + "'")
        .list();
    session.getTransaction().commit();
    session.close();
    return result.get(0);
  }

}
