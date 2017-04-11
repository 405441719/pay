package com.payinxl.service;
import com.payinxl.dao.MemUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by barry
 * Date:2017/2/13
 */
@Service
@Transactional
public class MemUserService {
    private final static Logger logger = LoggerFactory.getLogger(MemUserService.class);
    @Autowired
    private MemUserDao memUserDao;

}
