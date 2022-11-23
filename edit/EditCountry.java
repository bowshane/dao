package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditCountry.java	1.00 10/06/27
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* EditCountry: A component for editing the contact's country of residence
* field. It is stored as a foreign key into the country table. Due to the
* relatively small number of anticipated countries, we simply extend
* JComboBox to implement FieldEditor, and keep all the values in memory.
*
* @version 1.00 06/27/10
* @author Rick Salamone
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.Country;
import java.sql.ResultSet;
import javax.swing.JComboBox;

public class EditCountry extends JComboBox
	implements FieldEditor
	{
	public EditCountry ()
		{
		super();
		for ( Country t : Country.getAll())
			addItem(t);
		}

	public void clear() {}

	public void set(String text)
		{
		try { setSelectedItem(Country.parse(text)); }
		catch (Exception e) { setSelectedIndex(0); }
		}

	public void set(DataField field)
		{
		try { setSelectedItem((Country)field); }
		catch (Exception e) { setSelectedIndex(0); }
		}

	public Country get()
		throws DataFieldException
		{
		return (Country)getSelectedItem();
		}

	public boolean isEmpty() { return false; }
	}
