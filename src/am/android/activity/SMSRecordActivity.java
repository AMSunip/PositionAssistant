package am.android.activity;

import am.android.adapter.SMSInforAdapter;
import am.android.consts.Consts;
import am.android.core.Launch;
import am.android.dialog.MyDialogDeleteSMS;
import am.android.manager.AnimationManagerSystem;
import am.android.manager.DatabaseManager;
import am.android.manager.InforManager;
import am.android.positionassistant.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

public class SMSRecordActivity extends Activity implements OnClickListener
{
	
	private ClipboardManager  myClipboard               = null;
	private ClipData          myClip                    = null;

	private ImageButton       imgbtn_comeback_smsrecord = null;
	
	private ListView          listview_sms_record       = null;
	
	private MyDialogDeleteSMS mydialog_dsms             = null;
	
	private SMSInforAdapter   smsinforAdapter           = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms_record);
		
		init();
	}
	
	
	@Override
	protected void onRestart() 
	{
		super.onRestart();		
		smsinforAdapter.notifyDataSetChanged();
		
	}
	
	@SuppressLint("NewApi")
	private void copy(String text)
	{
		myClip = ClipData.newPlainText("text", text);
		myClipboard.setPrimaryClip(myClip);   
	}
	
	
	private void init()
	{
		initData();
		initView();
	}
	
	
	private void initData()
	{
		smsinforAdapter = new SMSInforAdapter(SMSRecordActivity.this, Consts.listdata_smsinfor_show);
	}
	
	private void initView()
	{
		myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
		
		imgbtn_comeback_smsrecord = (ImageButton)findViewById(R.id.imgbtn_comeback_smsrecord);
		imgbtn_comeback_smsrecord.setOnClickListener(this);
		listview_sms_record = (ListView)findViewById(R.id.listview_sms_record);
			
		listview_sms_record.setAdapter(smsinforAdapter);
		
		
		listview_sms_record.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				int pos = Consts.listdata_smsinfor_show.size() - position -1 ;
				Consts.DATA_SHOW_DETAIL = smsinforAdapter.getTimeString(pos);
				
				Intent intent = new Intent();
				intent.setClass(SMSRecordActivity.this, SMSRecordDetailActivity.class);
				startActivity(intent);
				AnimationManagerSystem.fromFade(SMSRecordActivity.this);
				
			}
		});
		
		
		
		listview_sms_record.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) 
			{
				mydialog_dsms = new MyDialogDeleteSMS(SMSRecordActivity.this, new OnClickListener()
				{

					@Override
					public void onClick(View v) 
					{
						
						if(v.getId() == R.id.tv_sms_copy)
						{
							int pos = Consts.listdata_smsinfor_show.size() - position -1;
							copy(Consts.listdata_smsinfor_show.get(pos).getInfor().trim());
							InforManager.showInfor(SMSRecordActivity.this, "已复制当前坐标");
						}
						
						
						if(v.getId() == R.id.tv_sms_delete)
						{
							
							int pos = Consts.listdata_smsinfor_show.size() - position -1;
							
							DatabaseManager.deleteItemSMSByDate(Consts.listdata_smsinfor_show, pos);
								
							DatabaseManager.getMySMSInfor();;
							Launch.sortSMSInfor();;
							Launch.combineSMSInfor();
								
							smsinforAdapter.notifyDataSetChanged();
							InforManager.showInfor(SMSRecordActivity.this, "已删除该条记录");
							
						}
						else
						{
							//取消
						}
						
						mydialog_dsms.dismiss();						
					}
					
				});
				
				mydialog_dsms.show();
				return true;
				
			}
		});
		
		
		
	}


	@Override
	public void onClick(View view)
	{
		if(view.getId() == R.id.imgbtn_comeback_smsrecord)
		{
			finish();
		}
		
	}
	
	
    
}
