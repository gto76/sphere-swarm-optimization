package _;

public class IntegerWrapper {
	private Integer num;
	
	public IntegerWrapper(Integer num) {
		this.num = num;
	}
	public IntegerWrapper(int num) {
		this.num = num;
	}
	public IntegerWrapper() {
		this.num = null;
	}
	
	public Integer get() {
		return num;
	}
	public void set(int num) {
		this.num = num;
	}
	public void set(Integer num) {
		this.num = num;
	}
	
}
