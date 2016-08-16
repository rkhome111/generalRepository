package demo;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.KeyEvent;


public class TimeUpdate extends Thread
{
//	JLabel time,hour;
	Rosfer rosfer;
	int hour,minute,second;
	String hourStr,minuteStr,secondStr,curTime;
	boolean run=true;
	public TimeUpdate(Rosfer rosfer)
	{
		this.rosfer=rosfer;
		setDaemon(true);
		start();
	}
	
	public void run()
	{
		run=true;
		
		String s=rosfer.document.getElementsByTagName("servertime").item(0).getTextContent();
			hour=Integer.parseInt(s.substring(0, s.indexOf(':')));
			minute=Integer.parseInt(s.substring(s.indexOf(':')+1,s.lastIndexOf(':')));
			second=Integer.parseInt(s.substring(s.lastIndexOf(':')+1));
			
			hourStr=hour<10?"0"+hour:""+hour;
			minuteStr=minute<10?"0"+minute:""+minute;
			secondStr=second<10?"0"+second:""+second;

		while(run)
		{
			
			if(second>=60)
			{
				
				
				minute++;
						if(minute%2==0)
							stopSleeping();
				if(minute>=60)
				{
					hour++;					
					minute=minute%60;
					
					
					if(hour>=24)
						hour=hour%24;
					
					hourStr=hour<10?"0"+hour:""+hour;

				}
				minuteStr=minute<10?"0"+minute:""+minute;
				second=second%60;

			}
			
			

			
			secondStr=second<10?"0"+second:""+second;
			
			
			try{
				curTime=hourStr+":"+minuteStr+":"+secondStr;
				
				Thread.sleep(1000);
				second++;
				
				rosfer.liw.time.setText(curTime);
			}catch(Exception e){System.out.println("Time Updater "+e);try{Thread.sleep(1000*60*60*24);}catch(Exception eee){}}
		}
	}
	
	PointerInfo a;
	Point p;
public void stopSleeping()
{
	try{
		a= MouseInfo.getPointerInfo();
		p=a.getLocation();
		Robot r=new Robot();
		r.mouseMove(p.x, p.y+5);
		r.mouseMove(p.x, p.y);
		r.keyPress(KeyEvent.VK_NUM_LOCK);
		r.keyRelease(KeyEvent.VK_NUM_LOCK);
		r.keyPress(KeyEvent.VK_NUM_LOCK);
		r.keyRelease(KeyEvent.VK_NUM_LOCK);
		}catch(Exception e){}
}

	public String getCurTime()
	{
		return curTime;
	}
	public void increaseTime(int seconds)
	{
		System.out.println(rosfer.liw.time.getText());
		minute+=seconds/60;
		second+=seconds%60;
		
		if(second>=60)
		{
			minute+=second/60;
			second=second%60;
		}
		if(minute>=60)
		{
			hour+=minute/60;
			minute=minute%60;
		}
		
		if(hour>=24)
		{
			hour=hour%24;
			rosfer.increaseDay++;
		}

			
		hourStr=hour<10?"0"+hour:""+hour;
		minuteStr=minute<10?"0"+minute:""+minute;
		secondStr=second<10?"0"+second:""+second;
		rosfer.liw.time.setText(hourStr+":"+minuteStr+":"+secondStr);
		
		System.out.println("I am updated the time "+seconds);
		System.out.println(rosfer.liw.time.getText());
		
	}
}
