package com.cy.store.service;

import com.cy.store.entity.User;

public interface IUserService {

    void reg(User user);

    User login(String username, String password);

    /**
     *
     * @param uid
     * @param username
     * @param oldPW
     * @param newPW
     */
    void changePassword(Integer uid,
                        String username,
                        String oldPW,
                        String newPW);

    /**
     *
     * @param uid
     * @return
     */
    User getByUid(Integer uid);

    /**
     *
     * @param uid
     * @param username
     * @param user
     */
     void changeUserInfo(Integer uid, String username, User user);

    /**
     *  Change user avatar
     * @param uid user id
     * @param avatar user's avatar location
     * @param username
     */
     void changeAvatar(Integer uid,
                       String avatar,
                       String username);
}
