package com.shanebow.dao;
/********************************************************************
* @(#)Address.java	1.00 10/06/23
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* Address: A contact's address.
*
* @author Rick Salamone
* @version 1.00, 20100623 rts created
* @version 1.01, 20101023 rts added capacity support
*******************************************************/
import com.shanebow.util.CSV;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Address extends ProperStringField
	implements DataField
	{
	public static final int MAX_CHARS = 200;
	public static Address BLANK;
	static
		{
		try { BLANK = new Address(""); }
		catch (Exception e)
			{
			System.err.println("BLANK Address failed");
			System.exit(1);
			}
		}

	public static Address read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new Address(rs.getString(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e.toString()); }
		}

	public static Address parse(String input)
		throws DataFieldException
		{
		return new Address(input);
		}

	private Address(String input)
		throws DataFieldException
		{
		super(MAX_CHARS, input);
// System.out.format( "Address:\n *%s*\n *%s*\n", input, m_value );
		}
	}
