package com.payinxl.common.controller;

import com.payinxl.common.constants.Constants;
import com.payinxl.common.util.ImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by barry
 * Date:2017/2/16
 */
@Controller
public class ValicodeController {
    @RequestMapping("/valicode") //对应/user/valicode.do请求
    public void valicode(HttpServletResponse response, HttpServletRequest request) throws Exception {
        ImageUtil imageUtil=new ImageUtil();
        imageUtil.createImage(request,response, Constants.VALIDCODE);
    }
}
