package net.homeip.entreprisesmd.mvconv.gui;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.gui.icons.IconLoader;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * This dialog show general information about the application.
 * 
 * @author patapouf
 * 
 */
public class AboutDialog extends Dialog {

	/**
	 * Margin.
	 */
	private static final int MARGIN = 30;

	/**
	 * Font use to diaply application name.
	 */
	private Font appNameFont;

	/**
	 * Create a new About dialog.
	 * 
	 * @param shell
	 *            the parent of this dialog.
	 */
	public AboutDialog(Shell shell) {
		super(shell);
	}

	/**
	 * This implementation close the dialog.
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 */
	protected void buttonPressed(int buttonId) {
		cancelPressed();
	}

	/**
	 * This implementation create a close button.
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {

		createButton(parent, IDialogConstants.CLOSE_ID,
				IDialogConstants.CLOSE_LABEL, true);

	}

	/**
	 * This implementation create the content of the about dialog.
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {

		// Setup bolded font
		FontData[] appNameFontData = getShell().getFont().getFontData();
		for (int fontIndex = 0; fontIndex < appNameFontData.length; fontIndex++) {
			appNameFontData[fontIndex].setStyle(SWT.BOLD);
			appNameFontData[0].setHeight(appNameFontData[0].getHeight() + 7);
		}
		appNameFont = new Font(Display.getCurrent(), appNameFontData);

		Composite comp = new Composite(parent, SWT.NONE) {
			public Point computeSize(int wHint, int hHint, boolean changed) {
				Point size = super.computeSize(wHint, hHint, changed);
				return new Point(size.x + MARGIN, size.y + MARGIN);
			}
		};

		// Configure parent layout
		GridLayout gridLayout = new GridLayout(1, false);
		comp.setLayout(gridLayout);

		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);

		// Image
		Image applicationIcon = IconLoader.loadIcon(IconLoader.ICON_APP_64)
				.createImage();
		Label imageLabel = new Label(comp, SWT.NONE);
		imageLabel.setImage(applicationIcon);
		imageLabel
				.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));

		// App name and version
		String appName = Localization.getString(Localization.APPLICATION_NAME)
				+ " " + Main.APPLICATION_VERSION;

		Text appNameText = new Text(comp, SWT.CENTER | SWT.READ_ONLY
				| SWT.MULTI | SWT.WRAP);
		appNameText.setText(appName);
		appNameText.setFont(appNameFont);
		appNameText.setLayoutData(gridData);
		appNameText.setBackground(this.getShell().getBackground());

		// App description
		String appDescription = Localization
				.getString(Localization.APPLICATION_DESCRIPTION);

		Text appDescText = new Text(comp, SWT.CENTER | SWT.READ_ONLY
				| SWT.MULTI | SWT.WRAP);
		appDescText.setText(appDescription);
		appDescText.setLayoutData(gridData);
		appDescText.setBackground(this.getShell().getBackground());

		// App copyright
		String appCopyright = Localization
				.getString(Localization.APPLICATION_COPYRIGHT);

		Text appCopyrightText = new Text(comp, SWT.CENTER | SWT.READ_ONLY
				| SWT.MULTI);
		appCopyrightText.setText(appCopyright);
		appCopyrightText.setLayoutData(gridData);
		appCopyrightText.setBackground(this.getShell().getBackground());

		// Application web site
		String webSiteAdress = Localization
				.getString(Localization.APPLICATION_WEBSITE);

		Link appWebSite = new Link(comp, SWT.NONE);
		appWebSite.setText("<a>" + webSiteAdress + "</a>");
		appWebSite
				.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		appWebSite.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Program.launch(event.text);
			}
		});

		return imageLabel;

	}

}
