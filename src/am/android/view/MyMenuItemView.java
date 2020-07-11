package am.android.view;

import am.android.consts.Consts;
import am.android.positionassistant.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyMenuItemView extends LinearLayout  implements OnClickListener
{
	private boolean         isExtend                 = false;
	
	private LinearLayout    layout_contract_divider1 = null;
	private LinearLayout    layout_contract_divider2 = null;
	private LinearLayout    layout_contract_divider3 = null;
	private LinearLayout    layout_contract_divider4 = null;
	private LinearLayout    layout_extend            = null;
	
	private RelativeLayout  layout_contract1         = null;
	private RelativeLayout  layout_contract2         = null;
	private RelativeLayout  layout_contract3         = null;
	
	private TextView        tv_title_menu            = null;
	private TextView        tv_item1                 = null;
	private TextView        tv_item11                = null;
	private TextView        tv_item2                 = null;
	private TextView        tv_item22                = null;
	private TextView        tv_item3                 = null;
	private TextView        tv_item33                = null;
	
	private String          ItemTitle                = null;
	private String          ItemText1                = null;
	private String          ItemText11               = null;
	private String          ItemText2                = null;
	private String          ItemText22               = null;
	private String          ItemText3                = null;
	private String          ItemText33               = null;
	
	private OnClickListener listener                 = null;

	
	public MyMenuItemView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
        
		View convertView = View.inflate(context,R.layout.item_menu,this);
		layout_contract_divider1=(LinearLayout)convertView.findViewById(R.id.layout_contract_divider1);
		layout_contract_divider2=(LinearLayout)convertView.findViewById(R.id.layout_contract_divider2);
		layout_contract_divider3=(LinearLayout)convertView.findViewById(R.id.layout_contract_divider3);
		layout_contract_divider4=(LinearLayout)convertView.findViewById(R.id.layout_contract_divider4);
			
		layout_extend=(LinearLayout)convertView.findViewById(R.id.layout_extend);
		layout_contract1=(RelativeLayout)convertView.findViewById(R.id.layout_contract1);
		layout_contract2=(RelativeLayout)convertView.findViewById(R.id.layout_contract2); 
		layout_contract3=(RelativeLayout)convertView.findViewById(R.id.layout_contract3);  
		tv_title_menu = (TextView)convertView.findViewById(R.id.tv_title_menu);
		tv_item1 = (TextView)convertView.findViewById(R.id.tv_item1);
		tv_item11 = (TextView)convertView.findViewById(R.id.tv_item11);
		tv_item2 = (TextView)convertView.findViewById(R.id.tv_item2);
		tv_item22 = (TextView)convertView.findViewById(R.id.tv_item22);
		tv_item3 = (TextView)convertView.findViewById(R.id.tv_item3);
		tv_item33 = (TextView)convertView.findViewById(R.id.tv_item33);
		
		layout_contract1.setOnClickListener(this);
		layout_contract2.setOnClickListener(this);
		layout_contract3.setOnClickListener(this);
		
		tv_title_menu.setOnClickListener(this);
		
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyMenuItemView);
		ItemTitle = typedArray.getString(0);
		ItemText1 = typedArray.getString(1);
		ItemText11 = typedArray.getString(2);
		ItemText2 = typedArray.getString(3);
		ItemText22 = typedArray.getString(4);
		ItemText3 = typedArray.getString(5);
		ItemText33 = typedArray.getString(6);		
		
		setItemTextAndTag(ItemTitle,ItemText1,ItemText11,ItemText2,ItemText22,ItemText3,ItemText33);
		
		Drawable drawable = getResources().getDrawable(R.drawable.arrextend_b);
		drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
		tv_title_menu.setCompoundDrawables(drawable, null, null, null);
		typedArray.recycle();	
	}
	
	public void setItemTextAndTag(String ItemTitle,String ItemText1,String ItemText2,String ItemText3)
	{
		setItemTitle(ItemTitle);
		setItemText1(ItemText1);
		setItemText2(ItemText2);
		setItemText3(ItemText3);
	}
	
	public void setItemTextAndTag(String ItemTitle,String ItemText1,String ItemText11,String ItemText2,String ItemText22,String ItemText3,String ItemText33)
	{
		setItemTitle(ItemTitle);
		setItemText1(ItemText1);
		setItemText11(ItemText11);
		setItemText2(ItemText2);
		setItemText22(ItemText22);
		setItemText3(ItemText3);
		setItemText33(ItemText33);
	}
		
	public void setMMIVTheme(String THEME)
	{
		if(THEME.equals("1"))
		{
			//炫酷黑
			tv_item1.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			tv_item2.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			tv_item3.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			tv_item11.setTextColor(Consts.COLOR_THEME_BLACK_TEXT2);
			tv_item22.setTextColor(Consts.COLOR_THEME_BLACK_TEXT2);
			tv_item33.setTextColor(Consts.COLOR_THEME_BLACK_TEXT2);
			
			layout_contract_divider1.setBackgroundResource(R.drawable.divider_black);
			layout_contract_divider2.setBackgroundResource(R.drawable.divider_cb);
			layout_contract_divider3.setBackgroundResource(R.drawable.divider_cb);
			layout_contract_divider4.setBackgroundResource(R.drawable.divider_black);
					
		}
		else
		{
			//简约白
			tv_item1.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			tv_item2.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			tv_item3.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			tv_item11.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT2);
			tv_item22.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT2);
			tv_item33.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT2);
			
			layout_contract_divider1.setBackgroundResource(R.drawable.divider_default);
			layout_contract_divider2.setBackgroundResource(R.drawable.divider_default);
			layout_contract_divider3.setBackgroundResource(R.drawable.divider_default);
			layout_contract_divider4.setBackgroundResource(R.drawable.divider_default);		
		}
	}
	
	public void setTextViewIcon(TextView tv_item,int drawableId)
	{
		Drawable drawable = getResources().getDrawable(drawableId);
		drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
		tv_item.setCompoundDrawables(drawable, null, null, null);
	}
	
	public void setAllTextViewIcon(int drawableId1,int drawableId2,int drawableId3)
	{
		setTextViewIcon(tv_item1,drawableId1);
		setTextViewIcon(tv_item2,drawableId2);
		setTextViewIcon(tv_item3,drawableId3);
	}
	
	public void setAllTextViewIcon(int drawableId1,int drawableId11,int drawableId2,int drawableId22,int drawableId3,int drawableId33)
	{
		setTextViewIcon(tv_item1,drawableId1);
		setTextViewIcon(tv_item11,drawableId11);
		setTextViewIcon(tv_item2,drawableId2);
		setTextViewIcon(tv_item22,drawableId22);
		setTextViewIcon(tv_item3,drawableId3);
		setTextViewIcon(tv_item33,drawableId33);
	}
	
	public void setExtend(boolean isExtend)
	{
		this.isExtend=!isExtend;
		
		if(isExtend)
		{
			//展开
			LinearLayout.LayoutParams params =(LinearLayout.LayoutParams) layout_extend.getLayoutParams();
			//params.width= 240;
			params.height=LinearLayout.LayoutParams.WRAP_CONTENT;
			//params.height=150;
			layout_extend.setLayoutParams(params);
			Drawable drawable = getResources().getDrawable(R.drawable.arrextend_b);
			drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
			tv_title_menu.setCompoundDrawables(drawable, null, null, null);
		}
		else
		{
			//折叠
			LinearLayout.LayoutParams params =(LinearLayout.LayoutParams) layout_extend.getLayoutParams();
			//params.width= 240;
			params.height=0;
			layout_extend.setLayoutParams(params);
			Drawable drawable = getResources().getDrawable(R.drawable.arrextend_a);
			drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
			tv_title_menu.setCompoundDrawables(drawable, null, null, null);
		}		
		
	}
	
	public boolean getExtend()
	{
		return this.isExtend;
	}
	
	public void setItemTitle(String ItemTitle)
	{
		this.ItemTitle = ItemTitle;
		tv_title_menu.setText(this.ItemTitle);
		tv_title_menu.setTag(this.ItemTitle);
	}
	
	public void setItemText1(String ItemText1)
	{
		if(ItemText1.equals(""))
		{
			ItemText1="联系人1";
		}
		
		this.ItemText1 = ItemText1;
		tv_item1.setText(this.ItemText1);
		tv_item1.setTag(this.ItemText1);
	}
	
	public void setItemText11(String ItemText11)
	{
		if(ItemText11.equals(""))
		{
			ItemText11="新建联系人";
		}
		this.ItemText11 = ItemText11;
		
		tv_item11.setText(this.ItemText11);
		tv_item11.setTag(this.ItemText11);
	}
	
	public void setItemText2(String ItemText2)
	{
		if(ItemText2.equals(""))
		{
			ItemText2="联系人2";
		}
		
		this.ItemText2 = ItemText2;
		tv_item2.setText(this.ItemText2);
		tv_item2.setTag(this.ItemText2);
	}
	
	public void setItemText22(String ItemText22)
	{
		if(ItemText22.equals(""))
		{
			ItemText22="新建联系人";
		}
		this.ItemText22 = ItemText22;
		tv_item22.setText(this.ItemText22);
		tv_item22.setTag(this.ItemText22);
	}
	
	public void setItemText3(String ItemText3)
	{
		if(ItemText3.equals(""))
		{
			ItemText3="联系人3";
		}
		this.ItemText3 = ItemText3;
		tv_item3.setText(this.ItemText3);
		tv_item3.setTag(this.ItemText3);
	}
	
	public void setItemText33(String ItemText33)
	{
		if(ItemText33.equals(""))
		{
			ItemText33="新建联系人";
		}
		this.ItemText33 = ItemText33;
		tv_item33.setText(this.ItemText33);
		tv_item33.setTag(this.ItemText33);
	}
	
	public String getItemTitle()
	{
		return this.ItemTitle;
	}
	
	public String getItemText1()
	{
		return this.ItemText1;
	}
	
	public String getItemText11()
	{
		return this.ItemText11;
	}
	
	public String getItemText2()
	{
		return this.ItemText2;
	}
	
	public String getItemText22()
	{
		return this.ItemText22;
	}
	
	public String getItemText3()
	{
		return this.ItemText3;
	}
	
	public String getItemText33()
	{
		return this.ItemText33;
	}
	
	public int getLayoutContract1Id()
	{
		return layout_contract1.getId();
	}
	
	public int getLayoutContract2Id()
	{
		return layout_contract2.getId();
	}
	
	
	public int getLayoutContract3Id()
	{
		return layout_contract3.getId();
	}
	
	public void setOnClickListener(OnClickListener listener)
	{
		this.listener=listener;
	}

	@Override
	public void onClick(View v)
	{
		listener.onClick(v);
	}

}
