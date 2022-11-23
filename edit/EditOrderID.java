package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditOrderID.java 1.00 20110209
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* EditOrderID: A component for editing the order ID field. Simply extends
* JLabel to implement FieldEditor, since this field is read only!
*
* @author Rick Salamone
* @version 1.00 20110209
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.OrderID;
import javax.swing.JLabel;

public class EditOrderID
	extends JLabel
	implements FieldEditor
	{
	private int m_field;

	public EditOrderID () { this(0); }
	public EditOrderID (int field)
		{
		super();
		setFieldIndex(field);
		}

	public void setFieldIndex(int f)
		{
		m_field = f;
		}

	@Override public void addActionListener(java.awt.event.ActionListener al) {}

	public void clear() {}

	public void set(String text) { setText(text); }

	public void set(DataField f) { setText(f.toString()); }

	public DataField get()
		throws DataFieldException
		{
		return OrderID.parse( getText());
		}

	public boolean isEmpty() { return getText().trim().isEmpty(); }
	}
