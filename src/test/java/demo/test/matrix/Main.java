package demo.test.matrix;

import java.lang.reflect.Method;

import org.junit.Test;

public class Main {

	
	public void startMain() {
		Main main = new Main();
		main.process(new FT0001ReadTextFile());
	}

	private void process(Object theObject) {
		try {
			Method[] methods = Class.forName(theObject.getClass().getName()).getMethods();

			for (int i = 0; i < methods.length; i++) {
				Backlog backlog = methods[i].getAnnotation(Backlog.class);
				if (backlog != null) {
					System.out.println(backlog.name() + "=>" + methods[i].getName());		
				} 

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
