package am.android.manager;

public class MobileNumberManager 
{
	
    public static boolean isMobileNO(String mobiles) 
    {
    	
    	//使用弱验证
    	boolean result = false;
    	int     length = mobiles.length();
    	int     count  = 0;
    	
    	if((length>2)&&(length<18))
    	{
    		//电话号码长度：3-17位
    		for(int i = 0;i<length;i++)
    		{
    			if(Character.isDigit(mobiles.charAt(i)))
    			{
    				count ++;
    			}
    			if(count == length)
    			{
    				result = true;
    			}
    		}
    		
    	}
    	
    	return result;   	
    	
    }     
}
