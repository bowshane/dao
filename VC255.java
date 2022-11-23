package com.shanebow.dao;
/********************************************************************
* @(#)VC255.java	1.00 20110712
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* VC255: The maximum sized VarChar field.
*
* @author Rick Salamone
* @version 1.00, 20110712 rts created
*******************************************************/
import com.shanebow.util.CSV;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VC255
	extends VarChar
	{
	public static final int MAX_CHARS = 255;

	public static VC255 read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new VC255(rs.getString(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e.toString()); }
		}

	public static VC255 parse(String input)
		throws DataFieldException
		{
		return new VC255(input);
		}

	public VC255 ( String input )
		throws DataFieldException
		{
		super(MAX_CHARS, input);
		}
	}
