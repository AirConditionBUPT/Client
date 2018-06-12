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
	private float tempOffset=0.5f;   //�¶�ƫ��ֵ����ǰ�¶���Ŀ���¶Ȳ��С�ڸ�ֵʱ���ж�Ϊ�ﵽĿ���¶�
	
	public ControlPanel(Slave slave) {
		this.slave=slave;
		//this.view=view;
		
		/*
                   �����Ҫ�������ݴ������Ļ� �������ﶨ��		  
		 */
		
		setupEvent();   //�����������ʱ����Ϊ
	}
	
	public void setupEvent() {        //���������������Ϊ
		
		/*view.bootButton.addActionListener(new AbstractAction() {  ���õ�Դ���ؼ� ����ð�ťʱ���ػ�
			public void actionPerformed(ActionEvent e) {
				if(!isOn) { //........}
				else if(isOn) {//......}
			}
			
		}
		}*/
		if(!isOn) {    //û����
			/*
			 ��ʼ�� view 
			 */
			
			slave.addDataChangedListener(new DataChangedListener() {
				public void TemperatureChanged(float temp) {
					//view.setCurrentTemp((int)temp);  //���ý����ϵ���ʾ���¶�
					
					//���¶ȴﵽĿ���¶� 
					if(slave.CurrentStatus==slave.WORKING_STATUS&&(slave.getTempMode()==slave.HOT_MODE||slave.getTempMode()==slave.COLD_MODE)&&
				       Math.abs(temp-slave.getTargetTemp())<tempOffset) {
						//���ӿػ�����
						slave.setStatus(slave.PENDING_STATUS);
						slave.setCurrentPower(slave.PENDING_POWER);
						
					}
					//�������¶ȳ���Ŀ���¶ȳ���1��
					if(slave.CurrentStatus==slave.PENDING_STATUS&& Math.abs(temp-slave.getTargetTemp())>1.0f) {
						//�ӿػ������������Ϳ�����������͵�������  OR  �ӿػ������п������� �ٷ��͵������� ������ 
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
		
		else {  //�Ѿ�������
			/* 
			 ���ùػ�ʱ����Ϊ
			 */
			isOn=false;   //�ػ�
			
		}
		
/*------------------------------------------------------------------------------------*/
		/*view.powerButton.addActionListener(new AbstractAction() {    //������ڷ�����ť����Ϊ
            public void actionPerformed(ActionEvent e) {*/
		
		int power=slave.getCurrentPower();
		if(power==slave.HIGH_POWER)
			power=slave.LOW_POWER;
		else
			power++;
		
		//�˴�power��ΪĿ����� ���䷢�͸����� 

/*------------------------------------------------------------------------------------*/
		
		/*view.tempUpButton.addActionListener(new AbstractAction() {  //��������¶Ȱ�ť����Ϊ
            public void actionPerformed(ActionEvent e)*/
		
		int temp1=(int)slave.getTargetTemp();
		temp1++;
		if(slave.checkTempInRange(temp1)) {  //���Ŀ���¶���ָ���ķ�Χ��
			//�˴�temp��ΪĿ���¶� ���䷢�͸�����
		}
		else {
			//TODO report
		}
		
/*------------------------------------------------------------------------------------*/
		
		/*view.tempDownButton.addActionListener(new AbstractAction() {  //���彵���¶Ȱ�ť����Ϊ
            public void actionPerformed(ActionEvent e)*/
		
		int temp2=(int)slave.getTargetTemp();
		temp2--;
		if(slave.checkTempInRange(temp2)) {  //���Ŀ���¶���ָ���ķ�Χ��
			//�˴�temp��ΪĿ���¶� ���䷢�͸�����
		}
		else {
			//TODO report
		}
		
/*------------------------------------------------------------------------------------*/
		
		/*view.ChangeModeButton.addActionListener(new AbstractAction() {  //����ı��¶�ģʽ����Ϊ
            public void actionPerformed(ActionEvent e)*/
		int mode=slave.getTempMode();
		if(mode==slave.HOT_MODE)
			mode--;
		else if(mode==slave.COLD_MODE)
			mode++;
		else {
			//˵��mode�Ȳ���HOTҲ����COLD �����˴���
			//TODO report
			}
		
		////�˴�mode��ΪĿ��ģʽ ���䷢�͸�����
	}  
	
 
}
