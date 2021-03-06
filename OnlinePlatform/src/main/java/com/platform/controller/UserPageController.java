package com.platform.controller;

import com.platform.model.Commodity;
import com.platform.util.ImageUtil;
import com.platform.model.User;
import com.platform.repository.UserRepository;
import com.platform.util.Constants;
import com.platform.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/userpage")
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserPageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private ImageUtil imageUtil;

    /**
     * 通过账号查询用户
     * @param account 账号
     * @return User
     */
    @GetMapping("/user")
    @ResponseBody
    public List<User> getUserByAccount(@RequestParam("account") String account){
        return userRepository.findAllByAccount(account);
    }

    /**
     * 登录校验
     * @param account 账号
     * @param
     * @return 校验结果
     */
    @GetMapping("/check")
    @ResponseBody
    public String loginAccount(@RequestParam("account") String account,
                               @RequestParam("password") String password){
        List<User> list = userRepository.findAllByAccount(account);
        if(list != null && list.size() > 0 && list.get(0).getPassword().equals(password)){
            return Constants.SUCCESS;
        }else{
            return Constants.FAILED;
        }
    }

    /**
     * 注册校验
     * @param account 账号
     * @param password 密码
     * @return 校验结果
     */
    @GetMapping("/register")
    @ResponseBody
    public String registerAccount(@RequestParam("account") String account,
                                  @RequestParam("password") String password,
                                  @RequestParam("name") String name,
                                  @RequestParam("role") int role){
        List<User> list = userRepository.findAllByAccount(account);
        if(list == null || list.size() == 0){
            User user = new User();
            user.setId(userUtil.newUserId());
            user.setAccount(account);
            user.setPassword(password);
            user.setName(name);
            user.setRole(role);
            try {
                userRepository.saveAndFlush(user);
                return Constants.SUCCESS;
            }catch (Exception e){
                return Constants.FAILED;
            }
        }else{
            return Constants.FAILED;
        }
    }

    /**
     * 更新用户图片
     * @param account 账号
     * @param img 图片
     * @return 更新结果
     */
    @PostMapping("/updateuserimg")
    @ResponseBody
    public String updateImgByAccount(@RequestParam("account") String account,
                                     @RequestParam("img") MultipartFile img) throws IOException {
        List<User> users = userRepository.findAllByAccount(account);
        if(users != null && users.size() > 0){
            User user = users.get(0);
            user.setPicture(imageUtil.imageToByte(img));
            userRepository.saveAndFlush(user);
            return Constants.SUCCESS;
        }else{
            return Constants.FAILED;
        }
    }

    /**
     * 获取用户图片
     * @param account 账号
     * @return 图片
     */
    @GetMapping("/getuserimg")
    @ResponseBody
    public byte[] updateImgByAccount(@RequestParam("account") String account) throws IOException {
        return userRepository.findAllByAccount(account).get(0).getPicture();
    }
}
