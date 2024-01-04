package com.example.casemd3qlct.service;

import com.example.casemd3qlct.model.User;

public interface UserService extends GeneralService<User> {
    public void changeprofile(String username,String password);
  public  void deleteTransactions(String username);
    void deleteWallets(String username);
    void deleteUserRecord(String username);
}
