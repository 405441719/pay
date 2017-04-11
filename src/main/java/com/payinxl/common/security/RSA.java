package com.payinxl.common.security;

public class RSA
{

    private static RSA instance=null;
    public static final String pwd="e34efaah84";
    public static final String sign="AZSD1DFG";
    private  RSA() {
	}
    
    public  static  RSA getInstance(){
    	if(instance!=null){
    		return instance;
    	}
    	instance=new RSA();
    	return instance;
    }
    
    /**
     * 函数功能：利用公钥进行加密
     * 
     * @author Panda 2013-2-26 下午10:18:06
     * @param srcString
     * @param publicKey
     * @param encode
     * @return
     * @throws Exception
     */
    public String encrypt(String srcString)
    {
        byte[] input = srcString.getBytes();  
        byte[] salt = sign.getBytes();
		try {
			byte[] data = PBECoder.encrypt(input, pwd, salt);
			return Coder.encryptBASE64(data).trim();
		} catch (Exception e) {
			e.printStackTrace();
			return "decrypt error";
		}  
 
        
    }

    /**
     * 函数功能：利用私钥进行解密
     * 
     * @author Panda 2013-2-26 下午10:18:12
     * @param srcString
     * @param privateKey
     * @param encode
     * @return
     * @throws Exception
     */
    public String decrypt(String srcString)
    {	
    	byte[] salt = sign.getBytes();
		try {
			byte[] output = PBECoder.decrypt(Coder.decryptBASE64(srcString), pwd, salt);
			String outputStr = new String(output);
		    return outputStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "encrypt error";
		} 
    }
    public static void main(String args[])
    {
        try
        {
        	RSA rsa=RSA.getInstance();
        	String enstr=rsa.encrypt("432222233");
        	System.out.println(enstr);
        	String destr=rsa.decrypt("Mrzgfl5togxP4/IaQgPShw==");
        	System.out.println(destr);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static boolean validBalance(String balance, String valcode){
    		RSA rsa= RSA.getInstance();
    		try {
    			String en = rsa.encrypt(balance);
    			if(en.equals(valcode)){
    				return true;
    			}else{
    				return false;
    			}
    		} catch (Exception e) {
    			return false;
    		}
    }
}
