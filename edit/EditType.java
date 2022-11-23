package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditType.java	1.00 10/06/22
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* EditType: A component for editing the contact type field. Simply
* extends JComboBox to implement FieldEditor, since this field only
* has a few allowed values.
*
* @version 1.00 06/22/10
* @author Rick Salamone
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.ContactType;
import java.sql.ResultSet;
import javax.swing.JComboBox;

public class EditType extends JComboBox
	implements FieldEditor
	{
	public EditType ()
		{
		super();
		for ( ContactType t : ContactType.getAll())
			addItem(t);
		}

	public void clear() {}

	public void set(String text)
		{
		try { setSelectedItem(ContactType.parse(text)); }
		catch (Exception e) { setSelectedIndex(0); }
		}

	public void set(DataField field)
		{
		try { setSelectedItem(field); }
		catch (Exception e) { setSelectedIndex(0); }
		}

	public ContactType get()
		throws DataFieldException
		{
		return (ContactType)getSelectedItem();
		}

	public boolean isEmpty() { return false; }
	}
