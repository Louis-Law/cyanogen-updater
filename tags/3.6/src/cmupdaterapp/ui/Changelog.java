package cmupdaterapp.ui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import cmupdaterapp.service.UpdateInfo;
import cmupdaterapp.ui.UpdateProcessInfo;
import cmupdaterapp.utils.Preferences;

import android.os.Message;
import android.util.Log;

class Changelog implements Runnable
{
	private static final String TAG = "<CM-Updater> Changelog";
	Preferences p;
	
	public Changelog(IUpdateProcessInfo upi)
	{
		p = Preferences.getPreferences(upi);
	}
	
	//Returns the RomChangelog without a Thread
	public static List<Version> getRomChangelog(UpdateInfo ui)
	{
		Version v = new Version();
		List<Version> returnValue = new LinkedList<Version>();
		v.Version = ui.displayVersion;
		for (String str : ui.description.split("\\|"))
		{
			if(str != "")
				v.ChangeLogText.add(str);
		}
		returnValue.add(v);
		return returnValue;
	}
	
	//Gets the AppChangelog in a Thread
	public void run()
	{
		URL url;
		InputSource i;
		
		Message m = null;
		try
		{
			m = new Message();
			url = new URL(p.getChangelogURL());
			i = new InputSource(url.openStream());
        	SAXParserFactory spf = SAXParserFactory.newInstance(); 
        	SAXParser sp = spf.newSAXParser();
        	XMLReader xr = sp.getXMLReader(); 
        	ChangelogHandler ch = new ChangelogHandler(); 
        	xr.setContentHandler(ch); 
        	xr.parse(i);  
        	m.obj = ch.getParsedData();
        }
        catch (MalformedURLException e)
		{
        	m.obj = e.toString();
			Log.e(TAG, "Malformed URL!", e);
		}
		catch (IOException e)
		{
			m.obj = e.toString();
			Log.e(TAG, "Exception on opening Input Stream", e);
		}
        catch (Exception e)
        {
        	m.obj = e.toString();
        	Log.e(TAG, "Exception in Reading ChangelogXMLFile", e);
        }
        UpdateProcessInfo.ChangelogProgressHandler.sendMessage(m);
	}
}