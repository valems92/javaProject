package model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class ClassGenerator {
	private Model model;
	
	private Class<?> topClass;
	private int setFieldsCounter = 0;
	private boolean firstInteraction = true;

	ArrayList<Class<?>> primitiveClasses = new ArrayList<Class<?>>();

	public ClassGenerator(Model model) {
		this.model = model;
		initPrimitiveClasses();
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

	public void createObjects(String string, Class<?> myClass) {
		try {
			topClass = myClass;
			String[] allValues = string.split(" ");
			Object o = setFieldsValue(allValues, myClass);

			model.setGeneratedObject(o);
			model.update("save_settings");
		} catch (Exception e) {
			model.update("display_message Some values are invalid. Please fix it.");
		}
	}

	private Object setFieldsValue(String[] allValues, Class<?> myClass) throws Exception {
		if (myClass.equals(topClass) && firstInteraction) {
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
					firstInteraction = false;
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