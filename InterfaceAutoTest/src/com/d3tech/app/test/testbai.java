package com.d3tech.app.test;

public class testbai {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		long x,a,b,c,A,d,e,f,g,E,F,G;
		for(x=100;x<999;x++)
		{
			d=0;
			E=F=G=1;
			//System.out.println(x);
			a=x/100;
			//System.out.println(a);
			c=x/10%10;
			//System.out.println(c);
			A=x%10;
			//System.out.println(A);
			
			for(e=1;e<=a;e++){
				//System.out.println(e);
				E=E*e;
				//System.out.println(E);
			}
	        for(f=1;f<=c;f++)
				F=F*f;
	        for(g=1;g<=A;g++)
				G=G*g;

	    	d=E+F+G;
	    	//System.out.println(d);
			if(x==d){
				System.out.println(x);
			}
		}

	}

}
