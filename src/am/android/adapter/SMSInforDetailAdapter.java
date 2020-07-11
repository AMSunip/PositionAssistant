package am.android.adapter;

import java.util.ArrayList;

import am.android.cell.SMSInfor;
import am.android.positionassistant.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class SMSInforDetailAdapter extends BaseAdapter 
{
    
	private ArrayList<SMSInfor> listdata_smsinfor_detail = null;
	private Context             context                  = null;
	
	private ImageButton         imgbtn_icon_smsdetail    = null;
	
	private TextView            tv_smsdetail_count       = null;
	private TextView            tv_smsdetail_datetime    = null; 
	private TextView            tv_smsdetail_infor       = null;
	private TextView            tv_smsdetail_name        = null;
	private TextView            tv_smsdetail_number      = null;
	
	
	
	public SMSInforDetailAdapter(Context context,ArrayList<SMSInfor> listdata_smsinfor_detail)
	{
		 this.context                  = context;
		 this.listdata_smsinfor_detail = listdata_smsinfor_detail;
	}
	
	
	private String getDateTime(String date,String time)
	{
		String result;
		result = date.substring(0,4) + "/" + date.substring(4,6) + "/" + date.substring(6,8) + "  " +
		         time.substring(0,2) + ":" + time.substring(2,4) + ":" + time.substring(4,6);
		
		return result;	
	}
	
	

	
	
	@Override
	public int getCount()
	{
		return listdata_smsinfor_detail.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return listdata_smsinfor_detail.get(position);
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
		convertView = inflater.inflate(R.layout.list_item_sms_record_detail, null);
        
		imgbtn_icon_smsdetail = (ImageButton)convertView.findViewById(R.id.imgbtn_icon_smsdetail);
        tv_smsdetail_count    = (TextView)convertView.findViewById(R.id.tv_smsdetail_count);
		tv_smsdetail_datetime = (TextView)convertView.findViewById(R.id.tv_smsdetail_datetime);
		tv_smsdetail_name     = (TextView)convertView.findViewById(R.id.tv_smsdetail_name);
		tv_smsdetail_number   = (TextView)convertView.findViewById(R.id.tv_smsdetail_number);
		tv_smsdetail_infor    = (TextView)convertView.findViewById(R.id.tv_smsdetail_infor);
        
		imgbtn_icon_smsdetail.setBackgroundResource(R.drawable.icon_sms);
		tv_smsdetail_count.setText(String.valueOf(position+1));
	
		int pos = listdata_smsinfor_detail.size() - position - 1;
		
		tv_smsdetail_datetime.setText(getDateTime(listdata_smsinfor_detail.get(pos).getDate(),listdata_smsinfor_detail.get(pos).getTime()));
		tv_smsdetail_name.setText(listdata_smsinfor_detail.get(pos).getName());
		tv_smsdetail_number.setText(listdata_smsinfor_detail.get(pos).getNumber());
		tv_smsdetail_infor.setText(listdata_smsinfor_detail.get(pos).getInfor());		
		
		
		
		return convertView;
	}

}
