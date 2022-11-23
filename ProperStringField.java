package com.shanebow.dao;
/********************************************************************
* @(#)ProperStringField.java 1.00 20100607
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* ProperStringField: Extends VarChar to store values as proper (capitalized) strings.
*
* @author Rick Salamone
* @version 1.00, 20100607 rts created
* @version 1.01, 20101023 rts protected constructor requires capacity
*******************************************************/
import com.shanebow.util.CSV;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProperStringField extends VarChar
	implements DataField
	{
	protected ProperStringField( int capacity, String input )
		throws DataFieldException
		{
		super( capacity, input);
		}

	public void set(String s)
		{
		super.set( CSV.properNoun(s.trim()));
		}
	}
