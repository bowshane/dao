package com.shanebow.dao;
/********************************************************************
* @(#)Comment.java	1.00 10/06/23
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* Comment: A user comment.
*
* @author Rick Salamone
* @version 1.00, 20101023 rts created
*******************************************************/
import com.shanebow.util.CSV;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Comment extends VarChar
	{
	public static Comment BLANK;
	static
		{
		try { BLANK = new Comment(""); }
		catch (Exception e)
			{
			System.err.println("BLANK comment failed");
			System.exit(1);
			}
		}
	public static final int MAX_CHARS = 255;

	public static Comment read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new Comment(rs.getString(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e.toString()); }
		}

	public static Comment parse(String input)
		throws DataFieldException
		{
		return (input == null || input.isEmpty())? BLANK : new Comment(input);
		}

	private Comment(String input)
		throws DataFieldException
		{
		super(MAX_CHARS, input);
		}
	}
