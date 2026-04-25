package exercise;

import java.io.IOException;

public class Ex29_5 {

	public static void main(String[] args) {
		try {
			bar();
		}
		catch(IOException e){
			e.printStackTrace();	
		}
		System.out.println("正常終了");
	}
	public static void bar() throws IOException {	// throws IOExceptionを追加した
		baz();
		System.out.println("bar終了");
	}
	public static void baz() throws IOException {	// IOExceptionはチェック例外です
		throw new IOException();
	}	
}
