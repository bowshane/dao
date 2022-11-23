package com.shanebow.dao;
/********************************************************************
* @(#)USD.java 1.00 20110209
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* USD: Represents US dollars and cents. Stored internally as an
* integer to avoid rounding errors.
*
* @author Rick Salamone
* @version 1.00 20110209
*******************************************************/
import com.shanebow.util.SBFormat;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class USD
	implements DataField, Comparable<USD>
	{
	public static final USD ZERO = new USD(0);
	public static final USD ONE_DOLLAR = new USD(100);

	public static USD parse(String input)
		throws DataFieldException
		{
		try
			{
			if ( input == null )
				return ZERO;
			String trimmed = input.trim();
			if ( trimmed.isEmpty())
				return ZERO;
			double d = Double.parseDouble(trimmed);
			if (d > 0) d += 0.009;
			else if ( d == 0.0) return ZERO;
			else if (d < 0) d -= 0.009;
			int cents = (int)(d * 100);
			return new USD(cents);
			}
	  catch (Exception e)
	    	{
    		throw new DataFieldException("Malformed US dollars: " + input);
	    	}
		}

	public static USD read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new USD(rs.getInt(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e); }
		}

	private int m_cents;
	public USD() { this(0); }
	public USD(int cents) { m_cents = cents; }

	@Override public int compareTo(USD other)
		{
		return m_cents - other.m_cents;
		}

	@Override public boolean equals(Object other)
		{
		if ( other == null ) return false;
		if ( other instanceof USD )
			return ((USD)other).m_cents == this.m_cents;
		else if ( other instanceof Number )
			return ((Number)other).intValue() == this.m_cents;
		else return false;
		}

	public int cents() { return m_cents; }
	public int intValue() { return m_cents; }
	public int     get() { return m_cents; }
	public void    set(int x) { m_cents = x; }

	@Override public String toString()
		{
		return (m_cents == 0) ? "" : SBFormat.toDollarString(m_cents);
		}

	public boolean isEmpty() { return m_cents == 0; }
	public String  csvRepresentation() { return toString(); }
	public String  dbRepresentation()  { return "" + m_cents; }
	}
