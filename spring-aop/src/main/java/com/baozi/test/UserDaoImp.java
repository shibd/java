package com.baozi.test;

/**
 * Created by baozi on 2018/3/1.
 */
public class UserDaoImp implements UserDao {

    @Override
    public int addUser(int num) {
        System.out.println("add user ......" + num);
        return 6666;
    }

    @Override
    public void updateUser() {
        System.out.println("update user ......");
    }

    @Override
    public void deleteUser() {
        System.out.println("delete user ......");
    }

    @Override
    public void findUser() {
        System.out.println("find user ......");
    }

}
