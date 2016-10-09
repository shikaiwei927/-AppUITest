package com.d3tech.app.test;

import java.util.Scanner;

public class testnum5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i,j;
		for(i=0;i<3;i++)              //�����λ���
		{
		Scanner stdin = new Scanner(System.in); 
		String j1 = stdin.nextLine();
		if(j1.equals("123456"))
		{System.out.println("��ӭʹ�ñ����");
		break;}                       //����ѭ��
		else
		System.out.println("���벻��ȷ������������");
		}
		if(i==3){
			System.out.println("�Բ����㲻��ʹ�ñ����");
		}
		

	}

}
