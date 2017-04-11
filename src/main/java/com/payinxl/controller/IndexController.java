package com.payinxl.controller;

import com.payinxl.common.constants.Constants;
import com.payinxl.common.controller.BaseController;
import com.payinxl.common.exception.BusinessException;
import com.payinxl.common.http.ResultObj;
import com.payinxl.common.persistence.PageContent;
import com.payinxl.common.util.MyStringUtil;
import com.payinxl.common.util.ValidUtil;
import com.payinxl.model.*;
import com.payinxl.service.MemUserService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by barry
 * Date:2017/2/24
 */
@Controller
public class IndexController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private MemUserService memUserService;
    @RequestMapping("/")
    public String home(Model model) {
        return "index";
    }

}
