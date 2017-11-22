package tmall.dao;

public class Teee {
	public static void main(String[] args) {
		Teee.test2();
	}
	public static void test1() {
		float P ;
		float S = 100f;
		float t=0.75f;
		float D = 7f;
		P=(2*S*t)/(D-0.8f*t);
		System.out.println(P);
	}
	public static void test2() {
		float P=45f ;
		float b = 205f;
		float t;
		float D = 7f;
		t=P*D/(1.86f*b-0.8f*45);
		System.out.println(t);
	}
}
