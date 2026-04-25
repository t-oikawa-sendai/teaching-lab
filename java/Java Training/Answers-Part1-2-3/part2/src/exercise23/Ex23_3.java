package exercise23;

class Root {}
class Foo extends Root {}
class Bar extends Root {}
class Baz extends Root {}

public class Ex23_3 {

	public static void main(String[] args) {
		
		Root r = new Bar();
		
		switch(r) {
			case Foo foo	-> System.out.println("Foo型です");
			case Bar bar	-> System.out.println("Bar型です");
			case Baz baz	-> System.out.println("Baz型です");
			default			-> System.out.println("Rootの派生型ではありません");
		}
		
	}
}
