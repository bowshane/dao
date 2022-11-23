package com.shanebow.dao;
/********************************************************************
* @(#)PNL.java 1.00 20110209
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* PNL: Represents US dollars and cents. Stored internally as an
* integer to avoid rounding errors.
*
* @author Rick Salamone
* @version 1.00 20110209
* 20120809 rts split out parsing code to SBFormat to share with other apps
*******************************************************/
import com.shanebow.util.SBFormat;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class PNL
	implements DataField, Comparable<PNL>
	{
	public static final PNL ZERO = new PNL(0);

	public static PNL parse(String input)
		throws DataFieldException
		{
		try
			{
			if ( input == null )
				return ZERO;
			String trimmed = input.trim();
			if ( trimmed.isEmpty())
				return ZERO;
			return new PNL(SBFormat.parseDollarString(trimmed));
			}
	  catch (Exception e)
	    	{
    		throw new DataFieldException("Malformed US dollars - " + input);
	    	}
		}

	public static PNL read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new PNL(rs.getInt(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e); }
		}

	private int m_cents;
	public PNL() { this(0); }
	public PNL(int cents) { m_cents = cents; }
	public PNL(int dollars, int cents) { this(cents + 100*dollars); }
	public PNL( USD aNow, USD aCost, Qty aQty )
		{
		if ( aCost == null || aNow == null || aQty == null )
			m_cents = 0;
		else
			m_cents = (aNow.intValue() - aCost.intValue()) * aQty.intValue() / 100;
		}

	@Override public int compareTo(PNL other)
		{
		return m_cents - other.m_cents;
		}

	@Override public boolean equals(Object other)
		{
		if ( other == null ) return false;
		if ( other instanceof PNL )
			return ((PNL)other).m_cents == this.m_cents;
		else if ( other instanceof Number )
			return ((Number)other).intValue() == this.m_cents;
		else return false;
		}

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
