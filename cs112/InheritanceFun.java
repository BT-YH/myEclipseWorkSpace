/*
 * Date: Sep 14 2022
 */
public class InheritanceFun {

	public static void main(String[] args) {
		
		// superclass
		System.out.println("Superclass A:");
		A a = new A();
		System.out.printf("a: i=%d j=%d\n", a.i, a.j);
		a.f();
		a.g();
		System.out.println();
		
		// subclass (B extends A)
		System.out.println("Subclass B:");
		B b = new B();
		System.out.printf("a: i=%d j=%d\n", b.i, b.j);
		b.f(); // overriding
		b.g(); // inheriting
		b.h(); // defining
		System.out.println();
		
		// subclass assigned to superclass variable (polymorphism)
		System.out.println("Subclass B assigned to A variable (polymorphism):");
		A a2 = b; // implicit casting
		System.out.printf("a2: i=%d j=%d\n", a2.i, a2.j);
		a2.f(); // overriding, dynamic binding - call the most 'specific' f for what the object is
		a2.g(); // inheriting
//		a2.h(); // error - can't call h() on an A
		System.out.println();

		// explicit downcasting: all behavior is as in the previous subclass examples
		System.out.println("Subclass B assigned to A variable and then downcasted back to B:");
		if (a2 instanceof B) {
			B b2 = (B) a2;
			System.out.printf("b2: i=%d j=%d\n", b2.i, b2.j);
			b2.f(); // overriding, dynamic binding - call the most 'specific' f for what the object is
			b2.g(); // inheriting
			b2.h(); // defining
			System.out.println();
		}
		//instanceof
		if (a instanceof A) {
			System.out.println("a instanceof A");
		} if (a instanceof B) {
			System.out.println("a instanceof B");
		} if (b instanceof A) {
			System.out.println("b instanceof A");
		} if (b instanceof B) {
			System.out.println("b instanceof B");
		} if (a2 instanceof A) {
			System.out.println("a2 instanceof A");
		} if (a2 instanceof B) {
			System.out.println("a2 instanceof B");
		}
	}
	
}

class A {
	int i = 1, j = 2;
	
	void f() {
		System.out.println("A.f");
	}
	
	void g() {
		System.out.println("A.g and from my perspective i=" + i);
	}
}

class B extends A {
	int i = 3;
	
	void f() {
		System.out.println("B.f");
		System.out.println("Calling superclass f... ");
		super.f();
	}
	
	void h() {
		System.out.println("B.h and superclass i=" + super.i);
	}
}
