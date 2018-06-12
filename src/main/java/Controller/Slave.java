package Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Slave {

	//�ӿػ�״̬
	int PENDING_STATUS=0; //����
	int WORKING_STATUS=1;  //������

	//���� power��λ����
	int PENDING_POWER=0;  //����ʱ�ķ���
	int LOW_POWER=1;  //�͵�
	int MID_POWER=2;  //�е�
	int HIGH_POWER=3; //�ߵ�

	//�¶� ģʽ����
	int COLD_MODE=0;  //coldģʽ
	int HOT_MODE=1;  //hotģʽ

	//�¶ȱ仯ʱ����С���
	float TEMP_GRID=0.2f;

	//�ӿػ���ʵʱ��Ϣ
	/*�ӿػ��Ķ˿���Ϣ �ڴ˴�����*/
	
	
	int CurrentStatus;   //��ǰ״̬
	int CurrentPower;  //��ǰ���ٵ�λ
	int CurrentTempMode;   //��ǰ�¶�ģʽ hot or cold
	float CurrentTemp;  //��ǰ�����¶�
	float TargetTemp;   //Ŀ���¶�
	DataChangedListener DataListener;   //���ڼ����ӿػ����ݵı仯
	
	public Slave(String id, String ipAddr) {
	        this.CurrentPower = PENDING_POWER;
	       // this.currentTemp = Configure.CURRENT_TEMP;
	    }
	
	
	
	  public void addDataChangedListener(DataChangedListener listener) {
        this.DataListener = listener;
    }
	   
	    
	  public float getCurrentTemp() {
	        return CurrentTemp;
	    }

	  public void setCurrentTemp(float currentTemp) {
	        this.CurrentTemp = currentTemp;
	        DataListener.TemperatureChanged(CurrentTemp); 
	    }

	    public float getTargetTemp() {
	        return TargetTemp;
	    }
	    
	    public void setTargetTemp(float targetTemp) {
	    	if(checkTempInRange(targetTemp)) {
	    		this.TargetTemp=targetTemp;
	    	}
	    	
	    	else {
	    		//�����Ϣ
	    		}
	    }
	    
	    
	  public boolean checkTempInRange(float Temp) {   //����¶��Ƿ�����Ӧ�ķ�Χ��
	    	
	    	if(CurrentTempMode==COLD_MODE)
	    	{
	    	if(Temp>=18f && Temp<=25f )
	    	   return true;
	    	else
	    	   return false;
	    	}

	    	else if(CurrentTempMode==HOT_MODE)
	    	{
	    	 if(Temp>=25f && Temp<=30f )
	    	   return true;
	    	else
	    	   return false;
	    	}
	    	
	    	else {
	    		//�����Ϣ
	    		return false;
	    	}
	    }
	  
	  public int getCurrentPower() {
	        return CurrentPower;
	    }


	  public void setCurrentPower(int power) {
	      if (power != this.CurrentPower && power >= PENDING_POWER && power <= HIGH_POWER) {
	            this.CurrentPower = power;
	            System.out.println("power: " + CurrentPower);
	            DataListener.PowerChanged(power);  
	       }
	    }
	  
	   public int getTempMode() {
	        return CurrentTempMode;
	    }

	   public void setWorkMode(int Mode) {
	        if(Mode != this.CurrentTempMode && (Mode == COLD_MODE || Mode == HOT_MODE)) {
	            this.CurrentTempMode = Mode;
	            DataListener.TempModeChanged(Mode);
	        } 
	    }
	   
	   public int getStatus() {
	        return CurrentStatus;
	    }

	    public void setStatus(int status) {
	        if(status == PENDING_STATUS || status == WORKING_STATUS) {
	            this.CurrentStatus = status;
	        }
	    }
	    
	    void ChangingTemperature() { 	    	//�ı��¶Ⱥ���

	    	Timer ChangingTempTimer=new Timer(1000,    //1sִ��һ��
	    			new ActionListener(){
	    		public void actionPerformed(ActionEvent e) {
	    			 //�ڵ����¶�ǰ�ȼ���¶��Ƿ��ڷ�Χ��
	    			  if(!checkTempInRange(TargetTemp) )
	    			  {
	    			      //��Ӧ�ĵ�����ʩ
	    			  }
	    			  
	    			  else
	    			  {
	    			     if(CurrentTemp>=TargetTemp)
	    			       CurrentTemp+=CurrentPower* TEMP_GRID;
	    			    else
	    			      CurrentTemp-=CurrentPower* TEMP_GRID;
	    			   }
	    			  DataListener.TemperatureChanged(CurrentTemp);  //�����й��¶ȵļ�����
	    		}
	    	});
	    	
	    	ChangingTempTimer.start();
	    }

}
