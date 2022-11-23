package com.shanebow.dao;
/********************************************************************
* @(#)MediumID.java	1.00 200110707
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* MediumID: MySQL INT (4 bytes) or MEDIUMINT UNSIGNED (3 bytes) to
* java int (4 bytes).
*
* @version 1.00 200110707
* @author Rick Salamone
*******************************************************/
import java.sql.ResultSet;
import java.sql.SQLException;

public final class MediumID
	implements DataField, Comparable<MediumID>
	{
	public static MediumID NEW_ID = new MediumID(0);

	public static MediumID read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new MediumID(rs.getInt(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e); }

		}

	public static MediumID parse(String input)
		throws DataFieldException
		{
		return new MediumID(input);
		}

	protected int m_value;
	public MediumID ( int value )
		{
		m_value = value;
		}

	private MediumID ( String input )
		throws DataFieldException
		{
		String trimmed = input.trim();
		if (trimmed.isEmpty())
			m_value = -1;
		else try { m_value = Integer.parseInt(trimmed);}
		     catch (Exception e)
		     	{
          	throw new DataFieldException("ID " + NUMBER_REQD + input);
		     	}
		}

	@Override public boolean equals(Object other)
		{
		if ( other == null ) return false;
		if ( other instanceof MediumID )
			return ((MediumID)other).m_value == this.m_value;
		else if ( other instanceof Integer )
			return ((Integer)other).intValue() == this.m_value;
		else
			return false;
		}

	@Override public int compareTo(MediumID other)
		{
		int diff = m_value - other.m_value;
		return (diff == 0)? 0 : (diff > 0)? 1 : -1;
		}

	public int    intValue() { return m_value; }
	public int    toInt() { return m_value; }
	@Override public String  toString() { return (m_value <= 0) ? "" : "" + m_value; }
	@Override public boolean isEmpty() { return m_value <= 0; }
	@Override public String  csvRepresentation() { return "" + m_value; }
	@Override public String  dbRepresentation()  { return "" + m_value; }
	}
