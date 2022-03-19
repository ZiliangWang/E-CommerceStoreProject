package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        //
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);


        if(result != null) {
            throw new UserNameDuplicatedException("Username has been taken");
        }


        //password encryption -- MD5
        String pw = user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase(Locale.ROOT);
        String md5PW = getMD5Password(pw, salt);

        user.setSalt(salt);
        user.setPassword(md5PW);

        // 补全数据：isDelete(0)
        user.setIsDelete(0);

        // 补全数据：4项日志属性
        user.setCreatedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedUser(user.getUsername());
        user.setModifiedTime(date);

        //rows == 1 -- succeed
        Integer rows = userMapper.insert(user);

        if(rows != 1) {
            throw new InsertException("User encounters problem while registering");
        }
    }

    @Override
    public User login(String username, String password) {
        User result = userMapper.findByUsername(username);
        if (result == null) {
            throw new UserNotFoundException("User is not exist in the system");
        }

        String salt = result.getSalt();
        String newMD5Password = getMD5Password(password, salt);

        if(!newMD5Password.equals(result.getPassword())) {
            throw new PasswordNotMatchException("Password is not match!");
        }
        //check if use is deleted
        if (result.getIsDelete() == 1) {
            throw new UserNotFoundException("User data does not exist!");
        }

        User user = new User();
        user.setUsername(result.getUsername());
        user.setUid(result.getUid());
        user.setAvatar(result.getAvatar());

        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPW, String newPW) {
        User result = userMapper.findByUid(uid);

        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("User data is not found");
        }

        String oldMD5pw = getMD5Password(oldPW, result.getSalt());
        if(!result.getPassword().equals(oldMD5pw)) {
            throw new PasswordNotMatchException();
        }

        String newMD5PW = getMD5Password(newPW, result.getSalt());

        Integer rows = userMapper.updatePasswordByUid(uid, newMD5PW, username, new Date());

        if(rows != 1) {
            throw new UpdateException("Unknown error when updating password!");
        }

    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);

        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("User data is not exist!");
        }

        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        return user;
    }

    @Override
    public void changeUserInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);

        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("User data is not exist!");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateUserInfo(user);
        if (rows != 1) {
            throw new UpdateException("User info update failed!");
        }

    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);

        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("User data is not exist!");
        }

        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (rows != 1) {
            throw new UpdateException("User avatar update failed!");
        }
    }


    private String getMD5Password(String password, String salt) {
        for(int i =0 ; i<3; i++) {
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }


        return password;
    }
}
