package Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Slave {

	//从控机状态
	int PENDING_STATUS=0; //待机
	int WORKING_STATUS=1;  //工作中

	//风力 power档位设置
	int PENDING_POWER=0;  //待机时的风力
	int LOW_POWER=1;  //低档
	int MID_POWER=2;  //中档
	int HIGH_POWER=3; //高档

	//温度 模式设置
	int COLD_MODE=0;  //cold模式
	int HOT_MODE=1;  //hot模式

	//温度变化时的最小间隔
	float TEMP_GRID=0.2f;

	//从控机的实时信息
	/*从控机的端口信息 在此处定义*/
	
	
	int CurrentStatus;   //当前状态
	int CurrentPower;  //当前风速档位
	int CurrentTempMode;   //当前温度模式 hot or cold
	float CurrentTemp;  //当前房间温度
	float TargetTemp;   //目标温度
	DataChangedListener DataListener;   //用于监听从控机数据的变化
	
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
	    		//输出信息
	    		}
	    }
	    
	    
	  public boolean checkTempInRange(float Temp) {   //检查温度是否在相应的范围内
	    	
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
	    		//输出信息
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
	    
	    void ChangingTemperature() { 	    	//改变温度函数

	    	Timer ChangingTempTimer=new Timer(1000,    //1s执行一次
	    			new ActionListener(){
	    		public void actionPerformed(ActionEvent e) {
	    			 //在调节温度前先检查温度是否在范围内
	    			  if(!checkTempInRange(TargetTemp) )
	    			  {
	    			      //相应的调整措施
	    			  }
	    			  
	    			  else
	    			  {
	    			     if(CurrentTemp>=TargetTemp)
	    			       CurrentTemp+=CurrentPower* TEMP_GRID;
	    			    else
	    			      CurrentTemp-=CurrentPower* TEMP_GRID;
	    			   }
	    			  DataListener.TemperatureChanged(CurrentTemp);  //运行有关温度的监听器
	    		}
	    	});
	    	
	    	ChangingTempTimer.start();
	    }

}
