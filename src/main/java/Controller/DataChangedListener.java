package Controller;

public interface DataChangedListener {   //在Slave中 set方法里调用
	
	public void TemperatureChanged(float temp);
	public void PowerChanged(int power);
	public void TempModeChanged(int mode);
	public void PaymentChanged(float payment);
}
