package com.shanebow.dao.edit;
/********************************************************************
* @(#)Qty.java 1.00 20110209
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* EditQty: Extends EditString to handle a Qty.
*
* @author Rick Salamone
* @version 1.00 20110209
*******************************************************/
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.Qty;

public final class EditQty
	extends EditString
	implements FieldEditor
	{
	public EditQty()
		{
		super();
		set( Qty.ZERO );
		}

	public Qty get() throws DataFieldException
		{
		return Qty.parse( getText());
		}
	}
