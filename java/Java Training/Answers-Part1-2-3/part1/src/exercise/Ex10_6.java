package exercise;

import java.util.ArrayList;

public class Ex10_6 {

	public static void main(String[] args) {
        var list = new ArrayList<String>();
        
        list.add("リンゴ");
        list.add("バナナ");
        list.add("みかん");
         
        for(String s : list) {
            System.out.println(s);
        }
	}

}
