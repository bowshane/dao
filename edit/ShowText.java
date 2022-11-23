package com.shanebow.dao.edit;
/********************************************************************
* @(#)ShowText.java 1.00 20110410
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* ShowText: A component for display only character data: Extends
* JLabel to implement FieldEditor as well as TableCellRenederer.
*
* @version 1.00 20110410
* @author Rick Salamone
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.VC255;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;

public class ShowText
	extends JLabel
	implements FieldEditor // , TableCellRenderer
	{
	public ShowText ()
		{
		super();
		setOpaque(true);
		setFont( new javax.swing.JList().getFont());
		}

	@Override public void addActionListener(java.awt.event.ActionListener al) {}

	public void clear() { setText(""); }

	public void set(DataField f)
		{
		if ( f == null ) clear();
		else setText(f.toString());
		}

	public DataField get()
		throws DataFieldException
		{
		return VC255.parse(getText());
		}

	public boolean isEmpty() { return getText().trim().isEmpty(); }
	}
