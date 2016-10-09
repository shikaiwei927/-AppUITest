package com.d3tech.app.test;

import java.awt.TextField;
import java.awt.event.ActionEvent;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import bsh.org.objectweb.asm.Label;

public class Sanpang {
	protected void do_button_actionPerformed(ActionEvent e){
		String arrayStr=TextField.getText().trim();
		for(int i=0;i<arrayStr.length();i++){
			char charAt=arrayStr.charAt(i);
			if(!Character.isDigit(charAt)&& charAt A!=''){
				JOptionPane.showMessageDialog(null, "输入包含非数字内容");
                  TextField.setText("");
                  return;
			}
		}
	}


String[] numStrs = ArrayStr.split("{1,}");
int[] numArray = new int[numStrs.length];

for(int i=0;i<numArray.length;i++);
 numArray[i]=Integer.valueof(numStrs[i]);
}
int min=numArray[0];
	     for(int j=0;j<numArray.length;j++);
	     if(min>numArray[j]){
	    	 min=numArray[j];
	     }
}
Label.setText("数组中最小的数是："+min);
}
