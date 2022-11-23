package com.shanebow.dao.edit;
/********************************************************************
* @(#)USD.java 1.00 20110209
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* ShowUSD: Extends EditUSD to display a USD (editable = false).
*
* @author Rick Salamone
* @version 1.00 20110402
*******************************************************/

public final class ShowUSD
	extends EditUSD
	implements FieldEditor
	{
	public ShowUSD()
		{
		super();
		setEditable( false );
		}
	}
