package com.payinxl.service;
import java.util.List;

import com.payinxl.authentication.MyUserDetails;
import com.payinxl.dao.MemUserDao;
import com.payinxl.model.MemAccount;
import com.payinxl.model.MemRole;
import com.payinxl.model.MemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * Created by barry
 * Date:2017/1/10
 */
@Service
public class UserLoginService implements UserDetailsService {
    @Autowired
    private MemUserDao memUserDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	MemUser user=  memUserDao.selectMemUserByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("no user found");
        } else {
            if(user.getLocked()==MemUser.LOCKED){
                throw new UsernameNotFoundException("locked");
            }
            try {
                List<MemRole> roles = memUserDao.selectMemRoleByUserid(user.getId());
                return new MyUserDetails(user, roles);
            } catch (Exception e) {
                throw new UsernameNotFoundException("user role select fail");
            }
        }
    }
}
