package com.payinxl.dao;
import com.payinxl.common.persistence.annotation.MyBatisDao;
import com.payinxl.model.*;

import java.util.List;

/**
 * Created by barry
 * Date:2017/1/10
 */
@MyBatisDao
public interface MemUserDao{
    MemUser selectMemUserByUserName(String username);

    List<MemRole> selectMemRoleByUserid(String userid);

    List<FrontMenu> selectAllfrontMenuList();
}
