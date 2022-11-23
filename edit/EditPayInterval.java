package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditPayInterval.java 1.00 20151012
* Copyright © 2015 by Richard T. Salamone, Jr. All rights reserved.
*
* EditPayInterval: A combo box for selecting the payment interval.
*
* @version 1.00
* @author Rick Salamone
* 20151012 rts created
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.edit.FieldEditor;
import com.shanebow.dao.PayInterval;
import javax.swing.JComboBox;

public class EditPayInterval
	extends JComboBox
	implements FieldEditor
	{
	public EditPayInterval ()
		{
		super();
		for ( PayInterval t : PayInterval.getAll())
			addItem(t);
		}

	public void clear() {}

	public void set(String text)
		{
		try { setSelectedItem(PayInterval.parse(text)); }
		catch (Exception e) { setSelectedIndex(0); }
		}

	public void set(DataField field)
		{
		try { setSelectedItem((PayInterval)field); }
		catch (Exception e) { setSelectedIndex(0); }
		}

	public PayInterval get()
		throws DataFieldException
		{
		return (PayInterval)getSelectedItem();
		}

	public boolean isEmpty() { return false; }
	}
