package demo;


public class CheckBreak extends Thread
{
	int time=0;
	Rosfer rosfer;
	public CheckBreak(Rosfer rosfer)
	{
		this.rosfer=rosfer;
		setDaemon(true);
		start();
	}
	public void run() 
	{
		
		while(true)
		{
			
			try{
				if(rosfer.projectId.equals("5536") && (rosfer.task_id==95||rosfer.task_id==97) && time>=900 && rosfer.liw.leftStop.isVisible())
				{
					rosfer.daw.message1.setText("Your Break time is exceeded..");
												
					rosfer.daw.message2.setText("Please change it.");
					rosfer.daw.setVisible(true);
					time=0;
				}
				else if(rosfer.projectId.equals("5536") && (rosfer.task_id==96) && time>=1800 && rosfer.liw.leftStop.isVisible())
				{
					rosfer.daw.message1.setText("Your Break time is exceeded..");
												
					rosfer.daw.message2.setText("Please change it.");
					if(!rosfer.daw.isVisible())
					rosfer.daw.setVisible(true);
					time=0;
				}
				
				Thread.sleep(1000*10);
				time+=10;
			}catch(Exception e){}
		}
		
	}

}
