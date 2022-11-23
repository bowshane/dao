package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditName.java 1.00 20100818
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* EditName: An abstract editor component for string/varchar
* database fields. Concrete subclasses must implement the get()
* method which calls the appropriate parser. For example:
*   return ContactName.parse(getText());
*
* @version 1.00 08/18/10
* @author Rick Salamone
* 20100818 RTS created
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.ContactName;

public final class EditName
	extends EditString // which extends ClickableTextField
	implements FieldEditor
	{
	public DataField get() throws DataFieldException
		{
		String value = getText().trim();
		if ( value.isEmpty())
			throw new DataFieldException("Contact name " + DataField.FIELD_REQD );
		return ContactName.parse( value);
		}
	}
