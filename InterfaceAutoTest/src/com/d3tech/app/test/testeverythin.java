package com.d3tech.app.test;
import java.util.Scanner;

public class testeverythin {
	
	
	public static void caltime(){
		System.out.println("input total ");
		Scanner s=new Scanner(System.in);
		float total=Float.parseFloat(s.nextLine())*1024;
		System.out.println("input your speed");
		float speed=Float.parseFloat(s.nextLine());
		
		System.out.println(total/speed/60.0);
	}
	
	public  static void main(String args[]){
		
		caltime();
		
	}

}
