package Controller;

import Controller.Slave;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel {
	private Slave slave;
	//private OverViewPanel view;
	
	private Timer DataTimer;
	private Thread serv; 
	
	private boolean isOn = false;
	private float tempOffset=0.5f;   //温度偏差值，当前温度与目标温度差距小于该值时，判定为达到目标温度
	
	public ControlPanel(Slave slave) {
		this.slave=slave;
		//this.view=view;
		
		/*
                   如果需要定义数据传输对象的话 就在这里定义		  
		 */
		
		setupEvent();   //点击各个按键时的行为
	}
	
	public void setupEvent() {        //定义各个按键的行为
		
		/*view.bootButton.addActionListener(new AbstractAction() {  设置电源开关键 点击该按钮时开关机
			public void actionPerformed(ActionEvent e) {
				if(!isOn) { //........}
				else if(isOn) {//......}
			}
			
		}
		}*/
		if(!isOn) {    //没开机
			/*
			 初始化 view 
			 */
			
			slave.addDataChangedListener(new DataChangedListener() {
				public void TemperatureChanged(float temp) {
					//view.setCurrentTemp((int)temp);  //设置界面上的显示的温度
					
					//若温度达到目标温度 
					if(slave.CurrentStatus==slave.WORKING_STATUS&&(slave.getTempMode()==slave.HOT_MODE||slave.getTempMode()==slave.COLD_MODE)&&
				       Math.abs(temp-slave.getTargetTemp())<tempOffset) {
						//将从控机待机
						slave.setStatus(slave.PENDING_STATUS);
						slave.setCurrentPower(slave.PENDING_POWER);
						
					}
					//若环境温度超过目标温度超过1度
					if(slave.CurrentStatus==slave.PENDING_STATUS&& Math.abs(temp-slave.getTargetTemp())>1.0f) {
						//从控机先向主机发送开启运行请求和调温请求  OR  从控机先自行开启运行 再发送调温请求 ？？？ 
					}
				}
				
				public void PowerChanged(int power) {
					//view.setPower();
				}
				
				public void TempModeChanged(int mode) {
					//view.setTempMode();/*
				}
				
				public void PaymentChanged(float payment) {
					//view.setPayment();
				}
			});
			
			isOn=true;
		}
		
		else {  //已经开机了
			/* 
			 设置关机时的行为
			 */
			isOn=false;   //关机
			
		}
		
/*------------------------------------------------------------------------------------*/
		/*view.powerButton.addActionListener(new AbstractAction() {    //定义调节风力按钮的行为
            public void actionPerformed(ActionEvent e) {*/
		
		int power=slave.getCurrentPower();
		if(power==slave.HIGH_POWER)
			power=slave.LOW_POWER;
		else
			power++;
		
		//此处power即为目标风力 将其发送给主机 

/*------------------------------------------------------------------------------------*/
		
		/*view.tempUpButton.addActionListener(new AbstractAction() {  //定义提高温度按钮的行为
            public void actionPerformed(ActionEvent e)*/
		
		int temp1=(int)slave.getTargetTemp();
		temp1++;
		if(slave.checkTempInRange(temp1)) {  //如果目标温度在指定的范围内
			//此处temp即为目标温度 将其发送给主机
		}
		else {
			//TODO report
		}
		
/*------------------------------------------------------------------------------------*/
		
		/*view.tempDownButton.addActionListener(new AbstractAction() {  //定义降低温度按钮的行为
            public void actionPerformed(ActionEvent e)*/
		
		int temp2=(int)slave.getTargetTemp();
		temp2--;
		if(slave.checkTempInRange(temp2)) {  //如果目标温度在指定的范围内
			//此处temp即为目标温度 将其发送给主机
		}
		else {
			//TODO report
		}
		
/*------------------------------------------------------------------------------------*/
		
		/*view.ChangeModeButton.addActionListener(new AbstractAction() {  //定义改变温度模式的行为
            public void actionPerformed(ActionEvent e)*/
		int mode=slave.getTempMode();
		if(mode==slave.HOT_MODE)
			mode--;
		else if(mode==slave.COLD_MODE)
			mode++;
		else {
			//说明mode既不是HOT也不是COLD 出现了错误
			//TODO report
			}
		
		////此处mode即为目标模式 将其发送给主机
	}  
	
 
}
