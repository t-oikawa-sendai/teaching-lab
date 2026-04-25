package exercise;

import jp.kwebs.lib.Input;

public class Ex16_2 {
    public static void main(String[] args) {
        String buffer = "";
        while(true) {
        	String s = Input.getString();
        	if(s==null) {
        		break;
        	}
            buffer += s; 
        }
        System.out.println(buffer);
    }
}
