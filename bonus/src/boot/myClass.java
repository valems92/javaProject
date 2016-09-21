package boot;

public class myClass {
	
	private int intDataMember;
	private innerClass inner;
	public String stringDataMember;
	public Test test1;	
	protected boolean boolDataMember;
	protected double doubleDataMember;
	public Test test2;
	private char charDataMember;
	public Test test3;

	@Override
	public String toString() {
		return "myClass [ intDataMember = " + intDataMember + ", inner = " + inner + ", stringDataMember = " + stringDataMember + ", test1 = " + test1.toString() + ", boolDataMember = " + boolDataMember
				+ ", doubleDataMember = " + doubleDataMember + ", test2 = " + test2.toString() + ", charDataMember = " + charDataMember + ", test3 = " + test3.toString() + " ]";
	}
}