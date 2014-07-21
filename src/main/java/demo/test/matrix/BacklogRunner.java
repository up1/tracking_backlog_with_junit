package demo.test.matrix;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.TestClass;

public class BacklogRunner extends Runner {
	public static final String BACKLOG_RESULT_DIRECTORY = "backlog_result";
	private List<Field> fieldList = new ArrayList<Field>();
	private List<Method> fTestMethods = new ArrayList<Method>();
	List<BacklogModel> backlogList = new ArrayList<BacklogModel>();
	private final TestClass fTestClass;

	public BacklogRunner(Class<?> aClass) {
		fTestClass = new TestClass(aClass);

		Field[] allField = aClass.getDeclaredFields();
		for (int i = 0; i < allField.length; i++) {
			if (allField[i].getAnnotation(Rule.class) != null) {
				fieldList.add(allField[i]);
			}
		}

		Method[] classMethods = aClass.getDeclaredMethods();
		for (int i = 0; i < classMethods.length; i++) {
			Method classMethod = classMethods[i];
			int length = classMethod.getParameterTypes().length;
			int modifiers = classMethod.getModifiers();
			if (classMethod.getReturnType() == null || length != 0 || Modifier.isStatic(modifiers) || !Modifier.isPublic(modifiers) || Modifier.isInterface(modifiers)
					|| Modifier.isAbstract(modifiers)) {
				continue;
			}
			String methodName = classMethod.getName();
			if (methodName.toUpperCase().startsWith("TEST") || classMethod.getAnnotation(Test.class) != null) {
				fTestMethods.add(classMethod);
			}
			if (classMethod.getAnnotation(Ignore.class) != null) {
				fTestMethods.remove(classMethod);
			}

			Backlog backlog = classMethod.getAnnotation(Backlog.class);
			if (classMethod.getAnnotation(Test.class) != null && backlog != null) {
				BacklogModel newBacklog = new BacklogModel(backlog.name(), aClass.getName(), classMethod.getName());
				backlogList.add(newBacklog);
			}
		}
	}

	@Override
	public Description getDescription() {
		Description spec = Description.createSuiteDescription(this.fTestClass.getName(), this.fTestClass.getJavaClass().getAnnotations());
		return spec;
	}

	@Override
	public void run(RunNotifier runNotifier) {
		for (Field field : fieldList) {
			Description spec = Description.createTestDescription(fTestClass.getClass(), field.getName());
			runNotifier.fireTestStarted(spec);
		}
		for (int i = 0; i < fTestMethods.size(); i++) {
			Method method = fTestMethods.get(i);
			Description spec = Description.createTestDescription(method.getClass(), method.getName());
			runNotifier.fireTestStarted(spec);
		}

		generateResultFile();
	}

	private void generateResultFile() {
		String filename = BACKLOG_RESULT_DIRECTORY + File.separator + "result_" + fTestClass.getName() + ".json";
		new GenerateResultJson(backlogList).writeFile(filename);
		new GenerateResultHtml().writeFile();
	}
}
