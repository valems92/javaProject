package Bonus;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

public class FormGUI extends BasicWindow {
	Form formCtrl;
	ArrayList<Text> textFields = new ArrayList<Text>();
	private boolean firstInteraction=true;
	
	public FormGUI(int width, int height, Form formCntrl) {
		super(width, height);
		this.formCtrl = formCntrl;
	}

	@Override
	public void initWidgets() {
		shell.setLayout(new GridLayout(2, false));

		initGroups(shell, formCtrl.myfirstClass);
		initButton();
	}

	public void initGroups(Composite parent, Class<?> myClass) {
		if (myClass.equals(formCtrl.myfirstClass) && firstInteraction) {
			Group myGroup = new Group(parent, SWT.BORDER);
			myGroup.setLayout(new GridLayout(2, false));
			myGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
			myGroup.setText(myClass.getName());

			Field[] fields = myClass.getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				Class<?> cls = fields[i].getType();
				if (formCtrl.primitiveClasses.contains(cls)) {
					createFieldLabel(myGroup, fields[i].getName(), cls);
				} else {
					firstInteraction=false;
					initGroups(myGroup, cls);
				}
			}
		}
	}

	private void createFieldLabel(Composite parent, String dataName, Class<?> cls) {
		Label label = new Label(parent, SWT.NONE);

		String clsTypeName = cls.getTypeName();
		if (clsTypeName.indexOf(".") >= 0)
			clsTypeName = clsTypeName.substring(clsTypeName.lastIndexOf(".") + 1);

		label.setText(dataName + " ( " + clsTypeName + " )" + " : ");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, false, false, 1, 1));

		Text text = new Text(parent, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		textFields.add(text);
	}

	private void initButton() {
		Button button = new Button(this.shell, SWT.PUSH);
		button.setText("Create object");
		button.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, false, false, 1, 1));

		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				createObject();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}

	private void createObject() {
		String allContentFields = "";
		String content;

		for (int i = 0; i < textFields.size(); i++) {
			content = textFields.get(i).getText();
			if (content.length() == 0) {
				displayMassage();
				return;
			}

			content = content.replace(" ", "-");
			allContentFields += content + " ";
		}

		formCtrl.createObjects(allContentFields);
	}

	private void displayMassage() {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		messageBox.setMessage("You need to fill all the fields.");

		messageBox.open();
	}
}
