package demo;

public class UpdateGraphics extends Thread
{
	Rosfer rosfer;
	UpdateGraphics(Rosfer r)
	{
		rosfer=r;
	}
	
	public void run()
	{
		try{
			Thread.sleep(5000);
			rosfer.update(rosfer.getGraphics());
		}catch(Exception e){}
	}

}
