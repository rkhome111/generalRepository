package demo;

public class ProjectTimeUpdate extends Thread
{
	int hour,minute;	
	Rosfer rosfer;
	String initialMinuteStr,initialHourStr;
	public ProjectTimeUpdate(Rosfer rosfer)
	{
		this.rosfer=rosfer;
		
	}
	
	public void run()
	{
		
		while(!Thread.currentThread().isInterrupted() && Thread.currentThread()==rosfer.ptu)
		{
			if(minute==60)
			{
				hour++;
				minute=0;
			}
		
			initialMinuteStr=minute<10?"0"+minute:""+minute;
			initialHourStr=hour<10?"0"+hour:""+hour;
			if(rosfer.liw.leftStop.isVisible())
				rosfer.liw.hour.setText(initialHourStr+":"+initialMinuteStr);
			try{
				Thread.sleep(1000*60);
				minute++;
			}catch(Exception e){System.out.println("Project time updater "+e);try{Thread.sleep(1000*60*60*100);}catch(Exception eee){}}
		}
	}
	
	public void reset()
	{
		hour=0;
		minute=0;
		initialMinuteStr=minute<10?"0"+minute:""+minute;
		initialHourStr=hour<10?"0"+hour:""+hour;
		rosfer.liw.hour.setText(initialHourStr+":"+initialMinuteStr);
		
	}

}
