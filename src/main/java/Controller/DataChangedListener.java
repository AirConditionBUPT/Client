package Controller;

public interface DataChangedListener {   //��Slave�� set���������
	
	public void TemperatureChanged(float temp);
	public void PowerChanged(int power);
	public void TempModeChanged(int mode);
	public void PaymentChanged(float payment);
}
