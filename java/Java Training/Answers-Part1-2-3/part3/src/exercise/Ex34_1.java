package exercise;

import java.util.concurrent.StructuredTaskScope;
import java.util.stream.IntStream;

import jp.kwebs.lib.Tools;

public class Ex34_1 {

	public static void main(String[] args) {

		try {
			var scope=new StructuredTaskScope.ShutdownOnFailure();
			var subtask = scope.fork(()->{
				return  sometask(2.0);
			});

			IntStream.range(0, 100)
					 .forEach(System.out::println);
			
			scope.join();
			scope.throwIfFailed();
			
			System.out.println(subtask.get());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String sometask(double seconds) {
		Tools.time_consuming_io_task(seconds);
		return "task-done";
	}

}
