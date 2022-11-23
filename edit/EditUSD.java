package com.shanebow.dao.edit;
/********************************************************************
* @(#)USD.java 1.00 20110209
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* EditUSD: Extends EditString to handle a USD.
*
* @author Rick Salamone
* @version 1.00 20110209
*******************************************************/
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.USD;

public class EditUSD
	extends EditString
	implements FieldEditor
	{
	public EditUSD()
		{
		super();
		set( USD.ZERO );
		}

	public final USD get() throws DataFieldException
		{
		return USD.parse( getText());
		}
	}
