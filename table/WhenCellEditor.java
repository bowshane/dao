package com.shanebow.dao.table;
/********************************************************************
* @(#)WhenCellEditor.java	1.00 10/06/22
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* WhenCellEditor: Extends JTextField specific to editing the contact
* When field. Now (11/2010), only needed for table editing of dates
* due to spinner implementation of EditWhen.
* NOTE: This not truely a table cell editor, but is used as follows:
*
*	table.setDefaultEditor(When.class,
*        new DefaultCellEditor(new WhenCellEditor()));
*
* @author Rick Salamone
* @version 1.00 20100622 rts created as EditWhen
* @version 1.01 20100820 RTS modified for package reorganization
* @version 1.02 20101101 RTS renamed to WhenTableEditor
* @version 1.03 20101104 RTS repackaged and renamed to WhenCellEditor
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.edit.FieldEditor;
import com.shanebow.dao.When;
import javax.swing.JTextField;

public class WhenCellEditor extends JTextField
	implements FieldEditor
	{
	public WhenCellEditor ()
		{
		super(8);
//		setHorizontalAlignment(JTextField.RIGHT);
		}

	public void clear() { setText(""); }

	public void set(String text)
		{
		try { setText(When.parse(text).toString()); }
		catch ( Exception e )
			{
			com.shanebow.util.SBLog.write( "error setting calldate to: " + text
				+ "\n" + e.getMessage());
			}
		}

	public void set(DataField cd) { setText(cd.toString()); }

	public When get()
		throws DataFieldException
		{
		return When.parse(getText());
		}

	public boolean isEmpty() { return getText().trim().isEmpty(); }
	}
