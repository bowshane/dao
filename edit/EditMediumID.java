package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditMediumID.java 1.00 10/06/22
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* EditMediumID: A component for editing the MediumID field. Simply extends
* JLabel to implement FieldEditor, since this field is read only!
*
* @version 1.00 06/22/10
* @author Rick Salamone
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.MediumID;
import javax.swing.JLabel;

public class EditMediumID
	extends JLabel
	implements FieldEditor
	{
	public EditMediumID () { super(); }

	@Override public void addActionListener(java.awt.event.ActionListener al) {}

	public void clear() {}

	public void set(String text) { setText(text); }

	public void set(DataField f) { setText((f==null)?"":f.toString()); }

	public DataField get()
		throws DataFieldException
		{
		return MediumID.parse( getText());
		}

	public boolean isEmpty() { return getText().trim().isEmpty(); }
	}
