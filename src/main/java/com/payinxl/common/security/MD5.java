package com.payinxl.common.security;

import com.payinxl.common.exception.BusinessException;
import com.payinxl.common.exception.ErrorCode;
import com.payinxl.common.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5
{
    public static String digest(String md5src) throws BusinessException
    {
        try {
			return digest(md5src, null);
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.ERROR, "MD5加密出错");
		}
    }

    public static String digest(String md5src, String encode) throws UnsupportedEncodingException,
            NoSuchAlgorithmException
    {
        byte[] digesta = null;
        MessageDigest alga = MessageDigest.getInstance("MD5");
        if (StringUtils.isEmpty(encode))
        {
            alga.update(md5src.getBytes());
        }
        else
        {
            alga.update(md5src.getBytes(encode));
        }
        digesta = alga.digest();
        return MyStringUtil.byte2hex(digesta);
    }
}
