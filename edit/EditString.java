package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditString.java	1.00 10/06/22
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* EditString: An abstract editor component for string/varchar
* database fields. Concrete subclasses must implement the get()
* method which calls an appropriate parser.
*
* @version 1.00 06/22/10
* @author Rick Salamone
* 20100818 RTS now abstract class which does not rely upon Contact
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;

public abstract class EditString
	extends ClickableTextField
	implements FieldEditor
	{
	public EditString () { super(); }

	public void clear() { setText(""); }
	public boolean isEmpty() { return getText().trim().isEmpty(); }

	public void set(String text) { setText(text); }
	public void set(DataField field) { setText((field==null)?"":field.toString()); }

	abstract public DataField get() throws DataFieldException;
	}
