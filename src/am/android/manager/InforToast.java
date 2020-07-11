package am.android.manager;

import android.content.Context;

public class InforToast 
{
	public static void showInfor(Context context,String infor,String flag)
	{
		if(flag.endsWith("1"))
		{
			InforManager2.showInfor(context, infor);
		}
		else
		{
			InforManager.showInfor(context, infor);
		}
	}
}
