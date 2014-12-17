
public class Main_predict {
	
	
	public static void main(String[] args) throws InterruptedException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Clock c = new Clock();
		long current_time= 150;/*c.cl();*/
		int N = 5,sum_actual=0,once_in=1;
		
		Actual_values_Today_Db a = new Actual_values_Today_Db();
		a.current_time = current_time;
		a.N = N;
		a.sum_actual = sum_actual;
		a.once_in = once_in;
		int actual_values = a.Actual_values();
		System.out.println("Actual : "+actual_values); 
		
		
		
		Predict_values_Previous_DB p = new Predict_values_Previous_DB();
		p.current_time = current_time;
		p.N = N;
		p.sum_actual = sum_actual;
		p.once_in = once_in;
		int predict_values = p.predict();
		System.out.println("\n\n\n\n\n\n\n");
		System.out.println("Actual  : "+actual_values); 
		System.out.println("Predict : "+predict_values);
		
		if(actual_values > predict_values)
		{
			System.out.println("\nStatus : BUSY");
		}
		else
		{
			System.out.println("\nStatus : FREE");
		}
		
		
	}

}
