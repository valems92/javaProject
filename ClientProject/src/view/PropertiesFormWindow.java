package view;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Text;

/**
 * <h1>PropertiesFormWindow</h1> This class is responsible for the display
 * screen settings, settings include all parameters in the properties file In
 * addition, also responsible for update the Presenter that changed settings
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */

public class PropertiesFormWindow extends DialogWindow {
    private final int FONT_SIZE = 14;

    private Maze3dGameWindow gameView;
    private ArrayList<Class<?>> primitiveClasses = new ArrayList<Class<?>>();
    private ArrayList<Text> textFields = new ArrayList<Text>();
    private boolean firstInteraction = true;

    private Class<?> topClass;
    private int width;
    private int height;

    /**
     * <h1>PropertiesFormWindow</h1> The constructor is responsible for
     * initialization all his data members, also, initialization the
     * PrimitiveClasses.
     * <p>
     * 
     * @param width
     *            - The width of setting window
     * @param height
     *            - The height of setting window
     * @param topClass
     *            - The required class for display his fields
     * @param gameView
     *            - Maze3dGameWindow class for update the Presenter that the
     *            setting is changed
     */
    public PropertiesFormWindow(int width, int height, Class<?> topClass, Maze3dGameWindow gameView) {
	this.gameView = gameView;
	this.topClass = topClass;

	this.width = width;
	this.height = height;

	initPrimitiveClasses();
    }

    /**
     * <h1>initPrimitiveClasses</h1> This method is responsible for add all
     * Primitive Classes in the Java doc into ArrayList
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
     * <h1>initWidgets</h1> The method is responsible for initialization
     * settings window. In addition, activate the some methods that are
     * responsible for creating the class fields by recourse
     * <p>
     */
    @Override
    public void initWidgets() {
	shell.setLayout(new GridLayout(2, false));
	shell.setSize(this.width, this.height);
	shell.setText("Settings Page");
	shell.setBackground(new Color(null, 139, 207, 130));
	shell.setAlpha(500);

	Monitor primary = shell.getDisplay().getPrimaryMonitor();
	Rectangle bounds = primary.getBounds();
	Rectangle rect = shell.getBounds();
	int x = bounds.x + (bounds.width - rect.width) / 2;
	int y = bounds.y + (bounds.height - rect.height) / 2;
	shell.setLocation(x, y);

	initGroups(shell, topClass);
	initButton();
    }

    /**
     * <h1>initGroups</h1> This method is responsible for part of the window
     * groups. Each group defines a class is contained in the first class
     * <p>
     * 
     * @param parent
     *            - The parent window of group
     * @param myClass
     *            - The required class
     */
    public void initGroups(Composite parent, Class<?> myClass) {
	if (myClass.equals(topClass) && firstInteraction) {
	    Group myGroup = new Group(parent, SWT.BORDER);
	    myGroup.setBackground(new Color(null, 212, 169, 127));

	    myGroup.setLayout(new GridLayout(2, false));
	    myGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
	    myGroup.setText(myClass.getName());

	    Field[] fields = myClass.getDeclaredFields();

	    for (int i = 0; i < fields.length; i++) {
		Class<?> cls = fields[i].getType();
		if (primitiveClasses.contains(cls)) {
		    createFieldLabel(myGroup, fields[i].getName(), cls);
		} else {
		    firstInteraction = false;
		    initGroups(myGroup, cls);
		}
	    }
	}
    }

    /**
     * <h1>createFieldLabel</h1> A method is responsible for recursive creation
     * of label and text boxes for all fields of the relevant class
     * <p>
     */
    private void createFieldLabel(Composite parent, String dataName, Class<?> cls) {
	Label label = new Label(parent, SWT.NONE);

	String clsTypeName = cls.getTypeName();
	if (clsTypeName.indexOf(".") >= 0)
	    clsTypeName = clsTypeName.substring(clsTypeName.lastIndexOf(".") + 1);
	label.setBackground(new Color(null, 212, 169, 127));
	label.setText(dataName + " ( " + clsTypeName + " )" + " : ");
	label.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, false, false, 1, 1));

	FontData[] fontData = label.getFont().getFontData();
	fontData[0].setHeight(FONT_SIZE);
	label.setFont(new Font(this.shell.getDisplay(), fontData[0]));

	Text text = new Text(parent, SWT.BORDER);
	text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

	fontData = text.getFont().getFontData();
	fontData[0].setHeight(FONT_SIZE);
	text.setFont(new Font(this.shell.getDisplay(), fontData[0]));

	textFields.add(text);
    }

    /**
     * <h1>initButton</h1> This method is responsible for initialization the
     * "Create Object" and "Cancel" setting window's button
     * <p>
     */
    private void initButton() {
	Button button = new Button(this.shell, SWT.PUSH);
	button.setText("Create object");
	button.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, false, false, 1, 1));
	button.setBackground(new Color(null, 139, 207, 130));

	button.addSelectionListener(new SelectionListener() {
	    @Override
	    public void widgetSelected(SelectionEvent arg0) {
		createObject();
	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent arg0) {
	    }
	});

	Button cancelButton = new Button(this.shell, SWT.PUSH);
	cancelButton.setText("Cancel");
	cancelButton.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, false, false, 1, 1));
	cancelButton.setBackground(new Color(null, 139, 207, 130));

	cancelButton.addSelectionListener(new SelectionListener() {
	    @Override
	    public void widgetSelected(SelectionEvent arg0) {
		shell.close();
	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent arg0) {
	    }
	});
    }

    /**
     * <h1>createObject</h1> Responsible for checking the input. In addition,
     * responsible for preparing the record, update and sending it to Presenter
     * <p>
     */
	private void createObject() {
	String allContentFields = "";
	String content;

	for (int i = 0; i < textFields.size(); i++) {
	    content = textFields.get(i).getText();
	    if (content.length() == 0) {
		gameView.displayMessage("You need to fill all the fields.", SWT.ICON_INFORMATION | SWT.OK, null, "");
		return;
	    }

	    content = content.replace(" ", "-");
	    allContentFields += content + " ";
	}

	gameView.view.update("change_settings " + allContentFields);
	shell.close();
    }
}