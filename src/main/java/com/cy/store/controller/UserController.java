package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;


    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<Void>(OK);
    }

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User data = userService.login(username, password);

        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());

        //get session data
//        System.out.println(getUidFromSession(session));
//        System.out.println(getUsernameFromSession(session));

        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword (String oldPassword, String newPassword, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);

        return new JsonResult<Void>(OK);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        User data = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeUserInfo(User user, HttpSession session) {
        //user data is autofilled by springboot
        //uid is the only data missing
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeUserInfo(uid, username, user);
        return new JsonResult<Void>(OK);
    }

    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/jpg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    //MultipartFile offered by SpringMVC, can provide any type of file
    //Spring boot auto-inject file value to the param
    //RequestParam forcefully inject param accordingly
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                         @RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            throw new FileEmptyException("Avatar file is empty!");
        }
        if(file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("Avatar size is too large!");
        }

        String contentType = file.getContentType();
        if(!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("File type is incorrect!");
        }
        //file path .../upload/file.png
        String parentPath = session.getServletContext().getRealPath("upload");
        //File object point to this path, check if it exists.
        File dir = new File("I:/VueAdmin/store/src/main/resources/static/upload/");
        if(!dir.exists()) {
            dir.mkdir();
        }
        String originalFName = file.getOriginalFilename();

        System.out.println("File path: " + parentPath);

        String[] nameParts = originalFName.split("[.]", 0);

        String suffix = nameParts[1];

        String fileName = UUID.randomUUID().toString().toUpperCase() + "." +suffix;

        File dest = new File(dir, fileName);

        try {
            file.transferTo(dest);
        } catch (FileStateException e) {
            throw new FileStateException("File state exception!");
        }
        catch (IOException e) {
            throw new FileUploadException("File I/O exception!");
        }
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        String avatar = "/upload/" + fileName;

        userService.changeAvatar(uid, avatar, username);

        return new JsonResult<>(OK, avatar);
    }
}
