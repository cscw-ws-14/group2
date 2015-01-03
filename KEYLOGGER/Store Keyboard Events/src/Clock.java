import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Clock {


	public long cl() throws InterruptedException {
		while(true){
			long millisSinceGMTMidnight = System.currentTimeMillis() % (24L * 60*60*1000);
		       long m = (millisSinceGMTMidnight/(60*1000))+ 60;
		       return m;
			
	}
}
}
