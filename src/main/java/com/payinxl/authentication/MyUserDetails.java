package com.payinxl.authentication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.payinxl.common.util.ValidUtil;
import com.payinxl.model.FrontMenu;
import com.payinxl.model.MemRole;
import com.payinxl.model.MemUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;



public class MyUserDetails extends MemUser implements UserDetails,Serializable{
	private static final long serialVersionUID = 1L;
	private List<MemRole> roles;

	public MyUserDetails(MemUser memUser,List<MemRole> roles) {
		super(memUser);
		this.roles=roles;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(roles == null || roles.size() <1){
			return AuthorityUtils.commaSeparatedStringToAuthorityList("");
		}
		StringBuilder commaBuilder = new StringBuilder();
		List<FrontMenu> frontMenuList=new ArrayList<>();
		for(MemRole role : roles){
			List list=role.getFrontMenuList();
			if(ValidUtil.isUnEmpty(list))
			frontMenuList.addAll(list);
		}
		Stream stream= frontMenuList.stream().distinct();
		frontMenuList=(List<FrontMenu>)stream.collect(Collectors.toList());
		for(FrontMenu frontMenu:frontMenuList) {
			commaBuilder.append(frontMenu.getPermission()).append(",");
		}
		String authorities = commaBuilder.substring(0,commaBuilder.length()-1);
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getUsername();
	}

	@Override
	public int hashCode() {
		return this.getUsername().hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		return this.getUsername().equals(obj.toString());
	}
	@Override
	public String toString() {
		return this.getUsername();
	}
}
