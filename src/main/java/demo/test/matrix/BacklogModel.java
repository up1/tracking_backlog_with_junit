package demo.test.matrix;

public class BacklogModel {
	private String name;
	private String className;
	private String methodName;
	
	public BacklogModel() {	
	}

	public BacklogModel(String name, String className, String methodName) {
		this.name = name;
		this.className = className;
		this.methodName = methodName;
	}

	public String getName() {
		return name;
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

}
