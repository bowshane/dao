package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditCompany.java 1.00 20100818
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* EditCompany: Extends EditString to handle a Company.
*
* @version 1.00 08/18/10
* @author Rick Salamone
* 20100818 RTS created
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.Company;

public final class EditCompany
	extends EditString // which extends ClickableTextField
	implements FieldEditor
	{
	public DataField get() throws DataFieldException
		{
		return Company.parse( getText());
		}
	}
