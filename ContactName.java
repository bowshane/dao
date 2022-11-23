package com.shanebow.dao;
/********************************************************************
* @(#)ContactName.java	1.00 10/06/21
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* ContactName: The contact's name field.
*
* @author Rick Salamone
* @version 1.00, 20100621 rts created
* @version 1.01, 20100819 rts added support for first & last name
* @version 1.01, 20101023 rts added capacity support
*******************************************************/
import com.shanebow.util.CSV;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactName
	extends VarChar
	{
	public static final int MAX_CHARS = 30;

	public static final byte FIRST=0;
	public static final byte LAST=1;

	public static ContactName read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new ContactName(rs.getString(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e.toString()); }
		}

	public static ContactName parse(String input)
		throws DataFieldException
		{
		return new ContactName(input);
		}

	public ContactName ( String input )
		throws DataFieldException
		{
		super(MAX_CHARS, input);
		m_value = CSV.properNoun(m_value);
		}

	public final String[] split()
		{
		String name = m_value;
		String[] it = { name, "" };
		int commaAt = name.indexOf(",");
		int spaceAt = -1; // haven't looked yet
		if ( commaAt != -1 )
			{
			it[LAST] = name.substring(0,commaAt);
			it[FIRST] = name.substring(++commaAt).trim();
			}
		else if ((spaceAt = name.lastIndexOf(" ")) > 0 )
			{
			it[FIRST] = name.substring(0,spaceAt).trim();
			it[LAST] = name.substring(spaceAt).trim();
			}
		return it;
		}
	}
