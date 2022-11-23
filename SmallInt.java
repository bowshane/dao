package com.shanebow.dao;
/********************************************************************
* @(#)SmallInt.java	1.00 200110707
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* SmallInt: MySQL SMALLINT === java short === 16 bits.
*
* @version 1.00 200110707
* @author Rick Salamone
*******************************************************/
import java.sql.ResultSet;
import java.sql.SQLException;

public final class SmallInt
	implements DataField, Comparable<SmallInt>
	{
	public static SmallInt NEW_ID = new SmallInt(0);

	public static SmallInt read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new SmallInt(rs.getShort(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e); }

		}

	public static SmallInt parse(String input)
		throws DataFieldException
		{
		return new SmallInt(input);
		}

	protected short m_value;
	public SmallInt ( int value )
		{
		m_value = (short)value;
		}

	private SmallInt ( String input )
		throws DataFieldException
		{
		String trimmed = input.trim();
		if (trimmed.isEmpty())
			m_value = -1;
		else try { m_value = Short.parseShort(trimmed);}
		     catch (Exception e)
		     	{
          	throw new DataFieldException("ID " + NUMBER_REQD + input);
		     	}
		}

	@Override public boolean equals(Object other)
		{
		if ( other == null ) return false;
		if ( other instanceof SmallInt )
			return ((SmallInt)other).m_value == this.m_value;
		else if ( other instanceof Short )
			return ((Short)other).longValue() == this.m_value;
		else
			return false;
		}

	@Override public int compareTo(SmallInt other)
		{
		int diff = m_value - other.m_value;
		return (diff == 0)? 0 : (diff > 0)? 1 : -1;
		}

	public short    shortValue() { return m_value; }
	public short    toShort() { return m_value; }
	@Override public String  toString() { return (m_value <= (short)0) ? "" : "" + m_value; }
	@Override public boolean isEmpty() { return m_value <= 0; }
	@Override public String  csvRepresentation() { return "" + m_value; }
	@Override public String  dbRepresentation()  { return "" + m_value; }
	}
