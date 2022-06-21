package ru.stqa.pft.mantis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mantis_user_table")
public class UserData {

  @Id
  @Column(name = "id")
  private int id;

  @Column(name = "username")
  private String username;

  public String getUsername() {
    return username;
  }

}
