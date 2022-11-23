package com.shanebow.dao;
/********************************************************************
* @(#)Qty.java 1.00 20110209
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* Qty: Represesnts an integer quantity of a product or security.
*
* @author Rick Salamone
* @version 1.00 20110209
*******************************************************/
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Qty
	implements DataField, Comparable<Qty>
	{
	public static final Qty ZERO = new Qty(0);
	public static final Qty ONE_THOUSAND = new Qty(1000);

	public static Qty read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new Qty(rs.getInt(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e); }
		}

	public static Qty parse(String input)
		throws DataFieldException
		{
		int count = 0;
		try
			{
			String trimmed = input.trim();
			if ( !trimmed.isEmpty())
				count = Integer.parseInt(trimmed);
			}
	  catch (Exception e)
	    	{
       	throw new DataFieldException("Qty - " + NUMBER_REQD + input);
	    	}
		return new Qty(count);
		}

	protected int m_value;
	private Qty ( int value )
		{
		m_value = value;
		}

	@Override public int compareTo(Qty other)
		{
		return m_value - other.m_value;
		}

	@Override public boolean equals(Object other)
		{
		if ( other == null ) return false;
		if ( other instanceof Qty )
			return ((Qty)other).m_value == this.m_value;
		else if ( other instanceof Number )
			return ((Number)other).intValue() == this.m_value;
		else return false;
		}

	public int     intValue() { return m_value; }
	public int     get() { return m_value; }
	public void    set(int x) { m_value = x; }
	public String  toString() { return (m_value == 0) ? "" : "" + m_value; }
	public boolean isEmpty() { return m_value == 0; }
	public String  csvRepresentation() { return toString(); }
	public String  dbRepresentation()  { return "" + m_value; }
	}
