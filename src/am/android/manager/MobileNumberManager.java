package am.android.manager;

public class MobileNumberManager 
{
	
    public static boolean isMobileNO(String mobiles) 
    {
    	
    	//ʹ������֤
    	boolean result = false;
    	int     length = mobiles.length();
    	int     count  = 0;
    	
    	if((length>2)&&(length<18))
    	{
    		//�绰���볤�ȣ�3-17λ
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
