package am.android.adapter;

import java.util.ArrayList;

import am.android.cell.SMSInfor;
import am.android.consts.Consts;
import am.android.positionassistant.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class SMSInforAdapter extends BaseAdapter 
{
	
	private ArrayList<SMSInfor> listdata_smsinfor = null;
	private Context             context           = null;
	
	private ImageButton         imgbtn_icon_sms   = null;
	
	private TextView            tv_sms_count      = null;
	private TextView            tv_sms_datetime   = null; 
	private TextView            tv_sms_infor      = null;
	private TextView            tv_sms_name       = null;
	private TextView            tv_sms_number     = null;
	

	public SMSInforAdapter(Context context,ArrayList<SMSInfor> listdata_smsinfor)
	{
		 this.context           = context;
		 this.listdata_smsinfor = listdata_smsinfor;
	}
	
	private String getDateTime(String date,String time)
	{
		String result;
		result = date.substring(0,4) + "/" + date.substring(4,6) + "/" + date.substring(6,8) + "  " +
		         time.substring(0,2) + ":" + time.substring(2,4) + ":" + time.substring(4,6);
		
		return result;
		
	}
	
	
	public String getTimeString(int position)
	{
		String result = Consts.listdata_smsinfor_show.get(position).getDate();
		return result;
	}
	

	@Override
	public int getCount()
	{
		return listdata_smsinfor.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return listdata_smsinfor.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
 
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.list_item_sms_record, null);
        
		imgbtn_icon_sms = (ImageButton)convertView.findViewById(R.id.imgbtn_icon_sms);
        tv_sms_count    = (TextView)convertView.findViewById(R.id.tv_sms_count);
		tv_sms_datetime = (TextView)convertView.findViewById(R.id.tv_sms_datetime);
		tv_sms_name     = (TextView)convertView.findViewById(R.id.tv_sms_name);
		tv_sms_number   = (TextView)convertView.findViewById(R.id.tv_sms_number);
		tv_sms_infor    = (TextView)convertView.findViewById(R.id.tv_sms_infor);
        	
		imgbtn_icon_sms.setBackgroundResource(R.drawable.icon_sms);
		tv_sms_count.setText(String.valueOf(position+1));
		
		int pos = listdata_smsinfor.size() - position - 1;
		
		tv_sms_datetime.setText(getDateTime(listdata_smsinfor.get(pos).getDate(),listdata_smsinfor.get(pos).getTime()));
		tv_sms_name.setText(listdata_smsinfor.get(pos).getName());
		tv_sms_number.setText(listdata_smsinfor.get(pos).getNumber());
		tv_sms_infor.setText(listdata_smsinfor.get(pos).getInfor());		
		
		return convertView;
	}

}
