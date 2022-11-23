package com.shanebow.dao;
/********************************************************************
* @(#)Position.java	1.00 10/06/23
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* Position: A contact's position in the company.
*
* @author Rick Salamone
* @version 1.00, 20100623 rts created
* @version 1.01, 20101023 rts added capacity support
*******************************************************/
import com.shanebow.util.CSV;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Position extends ProperStringField
	{
	public static final int MAX_CHARS = 30;

	public static Position read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new Position(rs.getString(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e.toString()); }
		}

	public static Position parse(String input)
		throws DataFieldException
		{
		return new Position(input);
		}

	private Position(String input)
		throws DataFieldException
		{
		super(MAX_CHARS, input);
		}
	}
