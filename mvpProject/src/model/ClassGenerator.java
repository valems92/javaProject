package model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * <h1>ClassGenerator</h1> This class create an object of the type class
 * recieved. It get a string of all fields values, separated by a space. When
 * finish creating the object, throws a notification to presenter.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
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

	/**
	 * <h1>initPrimitiveClasses</h1> Create an arrayList of all primitive
	 * classes and variables.
	 * <p>
	 */
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

	/**
	 * <h1>createObjects</h1> Get the class type to create, and all it's fields
	 * values. When finish creating or an exeption was catched, send
	 * notification to presenter.
	 * 
	 * @param string
	 *            a string with all fileds vaules separated by a space
	 * @param myClass
	 *            class type to create an object of
	 */
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

	/**
	 * <h1>setFieldsValue</h1> Recursive method. Create an object of the
	 * received class. In a loop, set the value of each field. If the field is
	 * not of a premitive type, Call the function again with the field class
	 * type.
	 * <p>
	 * 
	 * @param allValues
	 *            class fields values
	 * @param myClass
	 *            class type to create an object of it
	 * @return the object of the class type wanted with all fields set
	 * @throws Exception
	 */
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

	/**
	 * <h1>makeAccessible</h1> If field received is not public, set the
	 * accessible flag of it to true
	 * <p>
	 * 
	 * @param field
	 *            field to make accessible
	 */
	public void makeAccessible(Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()))
			field.setAccessible(true);
	}

	/**
	 * <h1>createObjectByType</h1> Get a value and a type to convert it.
	 * <p>
	 * @param value
	 *            value of the object
	 * @param fieldClass
	 *            type to covert the value
	 * @return the object created
	 * @throws Exception
	 */
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