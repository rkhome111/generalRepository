package demo;

import java.util.Random;

public class SnapCounter extends Thread {
	int duration;
	int snaptime;
	boolean startSnap=true;
	Rosfer rosfer;
	int random;

	public void run() {

		while (true) {
			try {
				rosfer.isgone = false;

				if (rosfer.work_status >=2 && snaptime * 2 < rosfer.interval) {
					System.out.println("time updation instruction is given "+ snaptime);
					rosfer.tu.increaseTime(snaptime - 1);
					updateTime(rosfer.liw.time.getText());
				}
				System.out.println("startSnap is :" + startSnap+ " snaptime is :" + snaptime);
				if (startSnap) 
				{
					try {
						startSnap = false;
						Thread.sleep(snaptime * 1000);
					} catch (Exception e) {
						System.out.println(e);
					}
				 }

			else {

					random = new Random().nextInt(snaptime - 40);
					random += 20;


					System.out.println(random + "  " + snaptime + " " + this);
					Thread.sleep(random * 1000);

					if ((!rosfer.isgone) && (rosfer.work_status == 2))
						takeSnap();
					Thread.sleep((snaptime - random) * 1000);
					System.out.println("I am Ended" + snaptime+ Thread.currentThread().getName());
				  }

			} catch (Exception e) {
				System.out.println("Snap Counter " + e);
			} finally {
				updateTime(rosfer.liw.time.getText());
			}
		}

	}

	public SnapCounter(String time, Rosfer rosfer) {
		this.rosfer = rosfer;
		duration = rosfer.interval;
		updateTime(time);

	}


	public void updateTime(String time) {

		int minute = Integer.parseInt(time.substring(3, 5));
		int second = Integer.parseInt(time.substring(6));
		int totalseconds = minute * 60 + second;
		snaptime = totalseconds / duration + 1;
		snaptime = duration * snaptime - totalseconds;

		rosfer.tu.stopSleeping();

	}

	public void takeSnap() {

		new Thread(rosfer.trai).start();
		rosfer.isgone = true;
	}

}
