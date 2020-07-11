package am.android.cell;

public class SMSInfor
{
	private String _id;
	private String date;
	private String time;
	private String name;
	private String number;
	private String infor;
	
	
    public SMSInfor()
    {
    	this._id    = "";
    	this.date   = "";
    	this.time   = "";
    	this.name   = "";
    	this.number = "";
    	this.infor  = "";	
    }
    
    
    public SMSInfor(String date,String time,String name,String number,String infor)
    {
    	 this.date   = date;
    	 this.time   = time;
    	 this.name   = name;
    	 this.number = number;
    	 this.infor  = infor;
    }
    
    
    public SMSInfor(String _id,String date,String time,String name,String number,String infor)
    {
    	 this._id    = _id;
    	 this.date   = date;
    	 this.time   = time;
    	 this.name   = name;
    	 this.number = number;
    	 this.infor  = infor;
    }
    
    
    public String getID()
    {
    	return this._id;
    }
    
    
    public String getDate()
    {
    	return this.date;
    }
    
    
    public String getTime()
    {
    	return this.time;
    }
    
    
    public String getName()
    {
    	return this.name;
    }
    
    
    public String getNumber()
    {
    	return this.number;
    }
    
    public String getInfor()
    {
    	return this.infor;
    }
    
    
    public void setData(String date,String time,String name,String number,String infor)
    {
    	 this.date   = date;
    	 this.time   = time;
    	 this.name   = name;
    	 this.number = number;
    	 this.infor  = infor;
    }
    
    public void setData(String _id,String date,String time,String name,String number,String infor)
    {
    	 this._id    = _id;
    	 this.date   = date;
    	 this.time   = time;
    	 this.name   = name;
    	 this.number = number;
    	 this.infor  = infor;
    }
    
    
    public void setID(String _id)
    {
    	this._id = _id;
    }
    
    
    public void setDate(String date)
    {
    	this.date = date;
    }
    
    public void setTime(String time)
    {
    	this.time = time;
    }
    
    public void setName(String name)
    {
    	this.name = name;
    }
    
    public void setNumber(String number)
    {
    	this.number = number;
    }
    
    public void setInfor(String infor)
    {
    	this.infor = infor;
    }
     
}
