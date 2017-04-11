package com.payinxl.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class MyStringUtil
{
    private static long tmpID = 0;
    private static boolean tmpIDlocked = false;
    
    private static Random ran = new Random();
    private final static int delta = 0x9fa5 - 0x4e00 + 1;
      
    public static char getRandomHan() {
        return (char)(0x4e00 + ran.nextInt(delta)); 
    }
    
    public static String getRandomNickName() {
    	int num=ran.nextInt(3)+3;
    	String nickname="";
    	for(int i=0;i<num;i++){
    		nickname+=(char)(0x4e00 + ran.nextInt(delta)); 
    	}
    	return nickname;
    }
    public static byte[] hex2byte(String strhex)
    {
        if (strhex == null)
        {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1)
        {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++)
        {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    public static String byte2hex(byte[] b)
    {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++)
        {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
            {
                hs.append("0");
                hs.append(stmp);
            }
            else
            {
                hs.append(stmp);
            }
        }
        return hs.toString();
    }

    /**
     * @函数功能:读取文件转换成字符串
     * @author 俞少坊 2012-12-20
     * @param path
     * @return
     * @throws IOException
     * @return String
     * @throws
     */
    public static String file2String(String path) throws IOException
    {
        return file2String(path, null);
    }

    /**
     * @函数功能:读取文件转换成字符串
     * @author 俞少坊 2012-12-20
     * @param path
     * @param encoding
     * @return
     * @throws IOException
     * @return String
     * @throws
     */
    public static String file2String(String path, String encoding) throws IOException
    {
        ClassPathResource resource = new ClassPathResource(path);
        File file = resource.getFile();
        Assert.notNull(file, "File is null");
        Assert.isTrue(file.exists(), "File is not exists");
        InputStreamReader reader = null;
        StringWriter writer = new StringWriter();
        try
        {
            if (StringUtils.isNotBlank(encoding))
            {
                reader = new InputStreamReader(new FileInputStream(file), encoding);
            }
            else
            {
                reader = new InputStreamReader(new FileInputStream(file));
            }
            // 将输入流写入输出流
            char[] buffer = new char[1024];
            int n = 0;
            while (-1 != (n = reader.read(buffer)))
            {
                writer.write(buffer, 0, n);
            }
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (writer != null)
            {
                try
                {
                    writer.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return writer.toString();
    }

    /**
     * 将字符串保存到文件中
     * 
     * @param res
     * @param filePath
     * @return
     */
    public boolean string2file(String res, String filePath)
    {
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try
        {
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists())
            {
                distFile.getParentFile().mkdirs();
            }
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024];
            int len;
            while ((len = bufferedReader.read(buf)) != -1)
            {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            flag = false;
            return flag;
        }
        finally
        {
            if (bufferedReader != null)
            {
                try
                {
                    bufferedReader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * @函数功能:获取字母（大小写）数字的随机组合
     * @author 俞少坊 2012-12-20
     * @param size
     * @return
     * @return String
     * @throws
     */
    public static String getRandom_ABCabc123(int size)
    {
        return RandomStringUtils.random(size, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
    }

    /**
     * @函数功能:获取字母（大小写）的随机组合
     * @author 俞少坊 2012-12-20
     * @param size
     * @return
     * @return String
     * @throws
     */
    public static String getRandom_ABCabc(int size)
    {
        return RandomStringUtils.random(size, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /**
     * @函数功能:获取数字的随机组合
     * @author 俞少坊 2012-12-20
     * @param size
     * @return
     * @return String
     * @throws
     */
    public static String getRandom_123(int size)
    {
        return RandomStringUtils.random(size, "1234567890");
    }

    public static InputStream String2InputStream(String str)
    {
        ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
        return stream;
    }

    public static String inputStream2String(InputStream is) throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = in.readLine()) != null)
        {
            buffer.append(line);
        }
        return buffer.toString();
    }

    public static String padLeftBlank(String src, int length)
    {
        return padLeft(src, " ", length);
    }

    public static String padLeftZero(String src, int length)
    {
        return padLeft(src, "0", length);
    }

    public static String padLeft(String src, String pad, int length)
    {
        if (src == null || src.equals(""))
        {
            src = "";
        }
        if (pad == null || pad.equals(""))
        {
            pad = " ";
        }
        while (src.length() < length)
        {
            src = pad + src;
        }
        return src;
    }

    public static String padRight(String src, String pad, int length)
    {
        if (src == null || src.equals(""))
        {
            src = "";
        }
        if (pad == null || pad.equals(""))
        {
            pad = " ";
        }
        while (src.length() < length)
        {
            src = src + pad;
        }
        return src;
    }

    /**
     * 判断是否为做多2个小数位
     * 
     * @param src
     * @return
     */
    public static boolean isTwoDecimalPlace(String src)
    {
        Pattern pattern = Pattern.compile("^[+]?(([1-9]\\d*[.]?)|(0.))(\\d{0,2})?$");
        return pattern.matcher(src).matches();
    }

    public static boolean isNumeric(String str)
    {
        if (str.isEmpty())
        {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static boolean isManyDuplicate(String str)
    {
        if (str.isEmpty())
        {
            return false;
        }
        Pattern pattern = Pattern.compile("^.*?([0-9\\d])\\1\\1\\1\\1\\1\\1\\1\\1\\1\\1\\1(?!\\1).*?$");
        return pattern.matcher(str).matches();
    }

    public static boolean isDouble(String src)
    {
        if (src.isEmpty())
        {
            return false;
        }
        Pattern pattern = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$");
        return pattern.matcher(src).matches();
    }

    public static boolean isABCabc123(String src)
    {
        if (src.isEmpty())
        {
            return false;
        }
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
        return pattern.matcher(src).matches();
    }

    public static JSONObject toJson(Map<String, Object> map)
    {
        Entry<String, Object> item = null;
        JSONObject json = new JSONObject();
        Iterator<Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext())
        {
            item = it.next();
            json.put(item.getKey(), item.getValue());
        }
        return json;
    }

    public static String toJsonString(Map<String, Object> map)
    {
        return toJson(map).toJSONString();
    }

    /**
     * 将字符串如param1name=param1value&param2name=param2value&param3name=param3value
     * 这样格式的字符串分析成Map<String,String>格式
     * 
     * @param queryString
     * @return
     */
    public static Map<String, String> splitHttpQueryString(String queryString)
    {
        String[] str = queryString.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0, j = str.length; i < j; i++)
        {
            String[] tempstr = str[i].split("=");
            if (tempstr.length < 2)
            {
                map.put(tempstr[0], "");
                continue;
            }
            if (tempstr.length == 3)
            {
                tempstr[1] = tempstr[1] + "=" + tempstr[2];
            }
            map.put(tempstr[0], tempstr[1]);
        }
        return map;
    }
    public static int string2int(String str, int num){
    	if(str!=null){
    		
    		try{
    			if(str.indexOf(".")>0){
    				num= Integer.valueOf(str.substring(0,str.indexOf(".")));
    			}else{
    				num= Integer.valueOf(str);
    			}
    		}catch(Exception e){
    		}
    	}
    	return num;
    }
    public static long string2long(String str, long num){
    	if(str!=null){
    		
    		try{
    			if(str.indexOf(".")>0){
    				num= Long.valueOf(str.substring(0,str.indexOf(".")));
    			}else{
    				num= Long.valueOf(str);
    			}
    		}catch(Exception e){
    		}
    	}
    	return num;
    }
    public static float string2float(String str, float num){
    	if(str!=null){
    		try{
    			num= Float.valueOf(str);
    		}catch(Exception e){
    		}
    	}
    	 str=decimal2Format(num);
    	 num= Float.valueOf(str);
    	return num;
    }
    public static double string2double(String str, double num){
    	if(str!=null){
    		try{
    			num= Double.valueOf(str);
    		}catch(Exception e){
    		}
    	}
    	str=decimal3Format(num);
   	 	num= Double.valueOf(str);
    	return num;
    }
    public static String default2string(String str, String defaultstr){
    	if(str!=null&&!"".equals(str.trim())){
    		return str.trim();
    	}else{
    		return defaultstr;
    	}
    	
    }
    /**
     * 生产唯一id
     * 
     * @return
     */
    public synchronized static String getUniqueId(String prefix) {
     long ltime = 0;
     while (true) {
      if (tmpIDlocked == false) {
       tmpIDlocked = true;
       ltime = Long.valueOf(new SimpleDateFormat("yyMMddHHmmssSSS")
         .format(new Date()).toString());
       if (tmpID < ltime) {
        tmpID = ltime;
       } else {
        tmpID = tmpID + 1;
        ltime = tmpID;
       }
       tmpIDlocked = false;
       return prefix+ltime;
      }
     }
    }
    public static String decimalFormat(double f, String format){
    	DecimalFormat df = new DecimalFormat(format);
    	try{
    		return df.format(f);
    	}catch(Exception e){
    		return "0.0";
    	}
    }

    public static String decimal3Format(double f){
        return decimalFormat(f,"######0.000");
    }
    public static String decimal2Format(double f){
        return decimalFormat(f,"######0.00");
    }
    public static String intFormat(double f){
        return decimalFormat(f,"#");
    }
    public static String intstrformat(String str){
    	int index=str.indexOf(".");
    	if(index>0){
    		return str.substring(0,index);
    	}else{
    		return str;
    	}
    }
    public static String dateTimeFormat(Date date){
    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	if(date!=null){
    		return format.format(date);
    	}else{
    		return "";
    	}
    }
    public static String dateTimeFormat(String formatTemplet, Date date){
    	SimpleDateFormat format=new SimpleDateFormat(formatTemplet);
    	if(date!=null){
    		return format.format(date);
    	}else{
    		return "";
    	}
    }
    public static String dateFormat(Date date){
    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	if(date!=null){
    		return format.format(date);
    	}else{
    		return "";
    	}
    }
    public static Date stringToTime(String datestr){
    	if(ValidUtil.isEmpty(datestr) ){
    		return null;
    	}
    	try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datestr);
		} catch (ParseException e) {
			return null;
		}
    }
    public static Date stringToDate(String datestr){
    	if(ValidUtil.isEmpty(datestr) ){
    		return null;
    	}
    	try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(datestr);
		} catch (ParseException e) {
			return null;
		}
    }   
    public static String formatPhoneNo(String phoneno){
    	if(ValidUtil.isUnEmpty(phoneno)&&phoneno.length()>=9)
    		return String.format("%s-%s-%s", phoneno.substring(0,3),phoneno.substring(3, 7),phoneno.substring(7));
    	else
    		return phoneno;
    }
    public static String formatOilNo(String oilno){
    	if(ValidUtil.isUnEmpty(oilno)&&oilno.length()>=19)
    		return String.format("%s-%s-%s-%s", oilno.substring(0,3),oilno.substring(3, 7),oilno.substring(7,11),oilno.substring(11));
    	else
    		return oilno;
    }
    public static String beginPriceHtml(String beginprice){
    	byte[] nums=beginprice.getBytes();
    	StringBuffer sb=new StringBuffer();
    	for(byte n:nums){
    		sb.append("<i>"+(char)n+"</i>");
    	}
    	return sb.toString();
    }
    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {
     
        StringBuffer unicode = new StringBuffer();
     
        for (int i = 0; i < string.length(); i++) {
     
            // 取出每一个字符
            char c = string.charAt(i);
     
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
     
        return unicode.toString();
    }
    /**
     * unicode 转字符串
     */
    public static String unicode2String(String ori) {
    	char aChar;
        int len = ori.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = ori.charAt(x++);
            if (aChar == '\\') {
                aChar = ori.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = ori.charAt(x++);
                        switch (aChar) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            value = (value << 4) + aChar - '0';
                            break;
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            value = (value << 4) + 10 + aChar - 'a';
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            value = (value << 4) + 10 + aChar - 'A';
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
 
        }
        return outBuffer.toString();
    }
}
