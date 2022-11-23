package com.shanebow.dao;
/********************************************************************
* @(#)Company.java	1.00 10/06/23
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* Company: A contact's company.
*
* @author Rick Salamone
* @version 1.00, 20100623 rts created
* @version 1.01, 20101023 rts added capacity support
*******************************************************/
import java.sql.ResultSet;
import java.sql.SQLException;

public class Company
	extends ProperStringField
	implements DataField
	{
	public static final int MAX_CHARS = 60;

	public static Company read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new Company(rs.getString(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e.toString()); }
		}

	public static Company parse(String input)
		throws DataFieldException
		{
		return new Company(input);
		}

	private Company(String input)
		throws DataFieldException
		{
		super(MAX_CHARS, input);
		}
	}
