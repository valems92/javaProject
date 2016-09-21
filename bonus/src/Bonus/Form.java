package Bonus;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class Form {
	final String firstClass = "topClass";

	FormGUI gui;
	Class<?> myfirstClass;
	int setFieldsCounter = 0;
	private int count=0;

	ArrayList<Class<?>> primitiveClasses = new ArrayList<Class<?>>(); // primitive

	public Form(Class<?> myclass) {
		initPrimitiveClasses();
		readDataMembers(myclass);
	}

	private void initPrimitiveClasses() {
		primitiveClasses.add(Void.class);
		primitiveClasses.add(void.class);

		primitiveClasses.add(Integer.class);
		primitiveClasses.add(int.class);

		primitiveClasses.add(Float.class);
		primitiveClasses.add(float.class);

		primitiveClasses.add(Byte.class);
		primitiveClasses.add(byte.class);

		primitiveClasses.add(Short.class);
		primitiveClasses.add(short.class);

		primitiveClasses.add(Character.class);
		primitiveClasses.add(char.class);

		primitiveClasses.add(Double.class);
		primitiveClasses.add(double.class);

		primitiveClasses.add(String.class);

		primitiveClasses.add(Boolean.class);
		primitiveClasses.add(boolean.class);

		primitiveClasses.add(Long.class);
		primitiveClasses.add(long.class);
	}

	private void readDataMembers(Class<?> myclass) {
		myfirstClass = myclass;

		gui = new FormGUI(500, 800, this);
		gui.run();
	}

	public void createObjects(String string) {
		try {
			String[] allValues = string.split(" ");
			Object o = setFieldsValue(allValues, myfirstClass);

			System.out.println(o);
		} catch (Exception e) {
			System.out.println("Some values are invalid. Please fix it.");
		}
	}

	private Object setFieldsValue(String[] allValues, Class<?> myClass) throws Exception {
		if (myClass.equals(myfirstClass)&& this.count==0) {
			Field[] fields = myClass.getDeclaredFields();
			Object o = myClass.newInstance();

			for (int i = 0; i < fields.length; i++) {
				Field currentField = fields[i];
				makeAccessible(currentField);
				Class<?> cls = fields[i].getType();

				if (primitiveClasses.contains(cls)) {
					currentField.set(o, createObjectByType(allValues[setFieldsCounter], cls));
					setFieldsCounter++;
				} else {
					count++;
					currentField.set(o, setFieldsValue(allValues, cls));
				}
			}

			return o;
		}
		return null;
	}

	public void makeAccessible(Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()))
			field.setAccessible(true);
	}

	public Object createObjectByType(String value, Class<?> fieldClass) throws Exception {
		value = value.replace("-", " ");
		String type = fieldClass.getTypeName();

		switch (type) {
		case "short":
		case "java.lang.Short":
			return Short.parseShort(value);

		case "float":
		case "java.lang.Float":
			return Float.parseFloat(value);

		case "byte":
		case "java.lang.Byte":
			return Byte.parseByte(value);

		case "int":
		case "java.lang.Integer":
			return Integer.parseInt(value);

		case "double":
		case "java.lang.Double":
			return Double.parseDouble(value);

		case "boolean":
		case "java.lang.Boolean":
			return Boolean.parseBoolean(value);

		case "char":
		case "java.lang.Character":
			return value.charAt(0);

		case "long":
		case "java.lang.Long":
			return Long.parseLong(value);

		default:
			return value;
		}
	}
}
