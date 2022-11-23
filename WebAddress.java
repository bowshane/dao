package com.shanebow.dao;
/********************************************************************
* @(#)WebAddress.java	1.00 10/06/21
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* WebAddress: A data field for a web site address.
*
* @author Rick Salamone
* @version 1.00, 20100621 rts created
* @version 1.01, 20101023 rts added capacity support
*******************************************************/
import java.sql.ResultSet;
import java.sql.SQLException;

public final class WebAddress extends VarChar
	{
	public static final int MAX_CHARS = 100;
	public static WebAddress BLANK;
	static
		{
		try { BLANK = new WebAddress(""); }
		catch (Exception e)
			{
			System.err.println("BLANK WebAddress failed");
			System.exit(1);
			}
		}

	public static WebAddress read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new WebAddress(rs.getString(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e.toString()); }
		}

	public static WebAddress parse(String input)
		throws DataFieldException
		{
		return new WebAddress(input);
		}

	private WebAddress ( String input )
		throws DataFieldException
		{
		super(MAX_CHARS, input);
		if ( !isEmpty() && (m_value.indexOf('.') < 1))
			throw new DataFieldException("Malformed web address - No dot ('.') found: " + input );
		}
	}
