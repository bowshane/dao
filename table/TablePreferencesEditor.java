package com.shanebow.dao.table;
/**
* Allows editing of a set of related user preferences.
*
* <P>Implementations of this interface do not define a "stand-alone" GUI,
* but rather a component (usually a <tt>JPanel</tt>) which can be used by the 
* caller in any way they want. Typically, a set of <tt>TablePreferencesEditor</tt> 
* objects are placed in a <tt>JTabbedPane</tt>, one per pane.
*
* 20110715 rts last known modification date
*/
import com.shanebow.dao.FieldMeta;
import com.shanebow.ui.LAF;
import com.shanebow.ui.PreferencesEditor;
import com.shanebow.ui.SublistChooser;
import com.shanebow.util.SBProperties;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public final class TablePreferencesEditor
	extends JPanel
	implements PreferencesEditor
	{
//	private static final String[] SHOW = { "10", "20", "50" };
	private final SublistChooser fColumnChooser;
	private final ConfigurableTable fTable;
	private final JCheckBox chkAuto;
//	private final JComboBox     cbShow = new JComboBox(SHOW);

	public TablePreferencesEditor( ConfigurableTable aTable )
		{
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		fTable = aTable;
		SBProperties props = SBProperties.getInstance();
		String propertyPrefix = fTable.getPropertyPrefix();

		FieldMeta[] fieldChoices = fTable.getAvailableFields();
		fColumnChooser = new SublistChooser( fieldChoices, fieldChoices.length, 8, 120 );
		LAF.titled(fColumnChooser, "Select & Order Columns");

		int[] fields = props.getIntArray(propertyPrefix + "fields");
		FieldMeta[] chosen = new FieldMeta[fields.length];
		for ( int i = 0; i < fields.length; i++ )
			chosen[i] = fieldChoices[fields[i]];
		fColumnChooser.chooseItems ( chosen );
		add( fColumnChooser );

		// Column Resizing
		if ( fTable instanceof JTable )
			{
			boolean autoResize = props.getBoolean(propertyPrefix+"col.fit", false);
			((JTable)fTable).setAutoResizeMode(autoResize? JTable.AUTO_RESIZE_ALL_COLUMNS
			                                    : JTable.AUTO_RESIZE_OFF);
			chkAuto = new JCheckBox("Fit Columns", autoResize);
			JPanel p = new JPanel(); p.add(chkAuto);
			add( LAF.titled(p, "Column Auto Resize" ));
			}
		else chkAuto = null;
		add( fontPanel() );
//add( LAF.titled(cbShow,  "Max Records" ));
		}

	public int getTableFontSize()
		{
		return SBProperties.getInstance().getInt(fTable.getPropertyPrefix() + "font.size", 12);
		}

	private JSlider fontPanel()
		{
		int size = getTableFontSize();
		JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 5, 25, size);
		LAF.titled(sizeSlider, "Font Size");
		sizeSlider.setMajorTickSpacing(5); // sets numbers for big tick marks
		sizeSlider.setMinorTickSpacing(1);  // smaller tick marks
		sizeSlider.setPaintTicks(true);     // display the ticks
		sizeSlider.setPaintLabels(true);    // show the numbers
		sizeSlider.setToolTipText("Font point size");
		sizeSlider.addChangeListener(new ChangeListener()
			{
			public void stateChanged(ChangeEvent e)
				{
				int fontSize = ((JSlider)e.getSource()).getValue();
				setTableFontSize(fontSize);
				}
			});
		return sizeSlider;
		}

	private void setTableFontSize(int size)
		{
		fTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, size));
		SBProperties.set(fTable.getPropertyPrefix() + "font.size",  "" + size);
		}

	/**
	* Return a GUI component which allows the user to edit
	* this set of related preferences.
	*/  
	public JComponent getComponent() { return this; }

	/**
	* The name of the tab in which this <tt>TablePreferencesEditor</tt>
	* will be placed. 
	*/
	public String getTitle()
		{
		return SBProperties.get(fTable.getPropertyPrefix() + "title");
		}

	/**
	* The mnemonic to appear in the tab name.
	*
	* <P>Must match a letter appearing in {@link #getTitle}.
	* Use constants defined in <tt>KeyEvent</tt>, for example <tt>KeyEvent.VK_A</tt>.
	*/
	public int getMnemonic()
		{
		return (int)SBProperties.get(fTable.getPropertyPrefix() + "mnemonic")
		            .toUpperCase().charAt(0);
		}
  
	/**
	* Store the related preferences as they are currently displayed,
	* overwriting all corresponding settings.
	*/
	public void savePreferences()
		{
		String csv = "";
		Object[] chosen = fColumnChooser.getChosen();
		for ( int i = 0; i < chosen.length; i++ )
			csv += ((i!=0)?",":"") + ((FieldMeta)chosen[i]).fieldNumber();
		String propertyPrefix = fTable.getPropertyPrefix();
		SBProperties.set(propertyPrefix + "fields", csv);

		if ( fTable instanceof JTable )
			{
			boolean auto = chkAuto.isSelected();
			SBProperties.set(propertyPrefix+"col.fit", auto );
			((JTable)fTable).setAutoResizeMode(auto? JTable.AUTO_RESIZE_ALL_COLUMNS
		                             : JTable.AUTO_RESIZE_OFF);
			}
		fTable.configure();
		}

	/**
	* Reset the related preferences to their default values, but only as 
	* presented in the GUI, without affecting stored preference values.
	*
	* <P>This method may not apply in all cases. For example, if the item 
	* represents a config which has no meaningful default value (such as a
	* mail server name), the desired behavior may be to only allow a manual
	* change. In such a case, implement this method as a no-operation. 
	*/
	public void matchGuiToDefaultPreferences()
		{
		}
	}
