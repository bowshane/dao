package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditSecurity.java 1.00 20110209
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* EditSecurity: A component for selecting a security from the defined
* universe of the security code table.
*
* @version 1.00 20110209
* @author Rick Salamone
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.Security;
import java.sql.ResultSet;
import javax.swing.JComboBox;

public class EditSecurity extends JComboBox
	implements FieldEditor
	{
	public EditSecurity ()
		{
		super();
		for ( Security t : Security.getAll())
			if ( t.isEnabled())
				addItem(t);
		}

	public void clear() {}

	public void set(String text)
		{
		try { setSelectedItem(Security.parse(text)); }
		catch (Exception e) { setSelectedIndex(0); }
		}

	public void set(DataField field)
		{
		try { setSelectedItem(field); }
		catch (Exception e) { setSelectedIndex(0); }
		}

	public Security get()
		throws DataFieldException
		{
		return (Security)getSelectedItem();
		}

	public boolean isEmpty() { return false; }
	}
