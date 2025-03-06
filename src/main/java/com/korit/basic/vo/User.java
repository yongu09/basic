package com.korit.basic.vo;

import java.util.ArrayList;
import java.util.List;

import com.korit.basic.entity.UserEntity;

import lombok.Getter;

@Getter
public class User {
  private String userId;
  private String userName;
  private String userAddress;

  private User(UserEntity userEntity) {
    this.userId = userEntity.getUserId();
    this.userName = userEntity.getUserName();
    this.userAddress = userEntity.getUserAddress();
  }

  public static List<User> getList(List<UserEntity> userEntities) {
    List<User> users = new ArrayList<>();

    for (UserEntity userEntity: userEntities) {
      User user = new User(userEntity);
      user.add(user);
    }

    return users;
  }
}
