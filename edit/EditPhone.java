package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditPhone.java 1.00 20100818
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* EditPhone: Extends EditString to handle a Phone.
*
* @version 1.00 08/18/10
* @author Rick Salamone
* 20100818 RTS created
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.PhoneNumber;

public final class EditPhone
	extends EditString // which extends ClickableTextField
	implements FieldEditor
	{
	public DataField get() throws DataFieldException
		{
		return PhoneNumber.parse( getText());
		}
	}
