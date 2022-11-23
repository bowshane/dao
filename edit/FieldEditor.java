package com.shanebow.dao.edit;
/********************************************************************
* @(#)FieldEditor.java	1.00 10/06/22
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* FieldEditor: Interface implemented by JCompoment subclasses used to
* edit contact fields. Two set() methods must be provided, the first
* initalizes the component given  a string representation of the field
* data. The second initalies the editor to the value of a DataField
* object. The get() method returns the editor contents as a DataField,
* which may be saved to the DB or a CSV as required.
*
* @version 1.00 06/22/10
* @author Rick Salamone
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;

public interface FieldEditor
	{
	public void clear();
	public DataField get() throws DataFieldException;
	public void set(DataField field);
	public boolean isEmpty();
	public void addActionListener(java.awt.event.ActionListener al);
	}
