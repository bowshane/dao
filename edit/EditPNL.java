package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditPNL.java 1.00 10/06/22
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* EditPNL: A component for editing the contact ID field. Simply extends
* JLabel to implement FieldEditor, since this field is read only!
*
* @version 1.00 06/22/10
* @author Rick Salamone
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.PNL;
import java.awt.Color;
import javax.swing.JLabel;

public final class EditPNL extends JLabel
	implements FieldEditor
	{
	public EditPNL ()
		{
		super();
		setForeground(Color.WHITE);
		}

	@Override public void addActionListener(java.awt.event.ActionListener al) {}

	public void clear() {}

	@Override public void setText( String text )
		{
		super.setText(text);
		if ( text == null || text.isEmpty() || text.equals("0.00"))
			setBackground(Color.WHITE);
		else if ( text.indexOf('-') >= 0 )
			setBackground(Color.RED);
		else
			setBackground(Color.BLUE);
		}

	public void set(String text) { setText(text); }

	public void set(DataField f) { setText(f.toString()); }

	public DataField get()
		throws DataFieldException
		{
		return PNL.parse( getText());
		}

	public boolean isEmpty() { return getText().trim().isEmpty(); }
	}
