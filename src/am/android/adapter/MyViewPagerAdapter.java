package am.android.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyViewPagerAdapter extends PagerAdapter
{
	
	private ArrayList<View>   mListViews  = null;
	private ArrayList<String> mTitleList  = null;
	
	public MyViewPagerAdapter(ArrayList<View> mListViews,ArrayList<String> mTitleList) 
	{
		this.mListViews=mListViews;
		this.mTitleList=mTitleList;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		container.removeView(mListViews.get(position));
	}
	
	 @Override  
     public Object instantiateItem(ViewGroup container, int position) 
	 {
		container.addView(mListViews.get(position), 0);
		return mListViews.get(position); 
	 }
	
	@Override
	public int getCount()
	{	
		return mListViews.size();
	}
	
	 @Override  
     public int getItemPosition(Object object)
	 {  
        return super.getItemPosition(object);  
     } 
	 
	 @Override  
     public CharSequence getPageTitle(int position)
	 {  
         return mTitleList.get(position);
     }  

	@Override
	public boolean isViewFromObject(View v, Object object)
	{
		return v==object;
	}

}
