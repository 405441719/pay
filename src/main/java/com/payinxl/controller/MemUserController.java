package com.payinxl.controller;

import com.payinxl.common.controller.BaseController;
import com.payinxl.service.MemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Created by barry
 * Date:2017/2/24
 */
@Controller
@RequestMapping("/user")
public class MemUserController extends BaseController {
    @Autowired
    private MemUserService memUserService;
    @RequestMapping("/user_info")
    public String user_info(Model model){
        return "user/user_info";
    }

}
