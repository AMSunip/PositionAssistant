package am.android.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import am.android.consts.Consts;

public class CSVWriter 
{
	public static BufferedWriter     writer_buffered    = null;
	public static FileWriter         writer_file        = null;
	public static OutputStreamWriter write_outputstream = null;
	public static FileOutputStream   outputstream       = null;
	
	public static void fileExist(File file)
	{
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
				writeText(Consts.TITLE_LOG);
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}	

    public static void writeText(String raw)
    {
    	try 
    	{
    		outputstream = new FileOutputStream(Consts.file_log,true);
    		write_outputstream =  new OutputStreamWriter(outputstream,Charset.forName("gbk"));
    		writer_buffered=new BufferedWriter(write_outputstream);	
    		writer_buffered.write(raw);
    		writer_buffered.newLine();
    		write_outputstream.flush();
    		writer_buffered.close();    		
		} 
    	catch (IOException e)
    	{
			e.printStackTrace();
		}
    }    
    
    public static void writeTextWithoutLine(String raw)
    {
    	try 
    	{
    		outputstream = new FileOutputStream(Consts.file_log,true);
    		write_outputstream =  new OutputStreamWriter(outputstream,Charset.forName("gbk"));
    		writer_buffered=new BufferedWriter(write_outputstream);
    		writer_buffered.newLine();
    		write_outputstream.flush();
    		writer_buffered.close();    		
		} 
    	catch (IOException e)
    	{
			e.printStackTrace();
		}
    }    
}
