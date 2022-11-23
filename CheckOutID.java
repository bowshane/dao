package com.shanebow.dao;
/********************************************************************
* @(#)CheckOutID.java 1.00 10/06/23
* Copyright (c) 2010-2011 by Richard T. Salamone, Jr. All rights reserved.
*
* CheckOutID: ID of the entity checking out this contact - Currently
* this is the page number on the physical page which is awaiting new
* dispositioning. When the sytem goes paperless, this will be the id
* of the employee who is currently working with this contact.
*
* @version 1.00 06/23/10
* @author Rick Salamone
* 20110606 implements Comparable
*******************************************************/
import java.sql.ResultSet;
import java.sql.SQLException;

public final class CheckOutID
	implements DataField, Comparable<CheckOutID>
	{
	public static final CheckOutID CHECKED_IN = new CheckOutID(0);

	public static CheckOutID read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new CheckOutID(rs.getLong(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e); }
		}

	public static CheckOutID parse(String input)
		throws DataFieldException
		{
		long page = 0;
		try
			{
			String trimmed = input.trim();
			if ( !trimmed.isEmpty())
				page = Long.parseLong(trimmed);
			}
	  catch (Exception e)
	    	{
       	throw new DataFieldException("Checkout page - " + NUMBER_REQD + input);
	    	}
		return new CheckOutID(page);
		}

	protected long m_value;
	private CheckOutID ( long value )
		{
		m_value = value;
		}

	@Override public boolean equals(Object other)
		{
		if ( other == null ) return false;
		if ( other instanceof CheckOutID )
			return ((CheckOutID)other).m_value == this.m_value;
		else if ( other instanceof Long )
			return ((Long)other).longValue() == this.m_value;
		else
			return false;
		}

	@Override public int compareTo(CheckOutID other)
		{
		long diff = m_value - other.m_value;
		return (diff == 0)? 0 : (diff > 0)? 1 : -1;
		}

	public void    checkIn() { m_value = 0; }
	public long    getValue() { return m_value; }
	public String  toString() { return (m_value == 0) ? "" : "" + m_value; }
	public boolean isEmpty()  { return m_value == 0; }
	public String  csvRepresentation() { return toString(); }
	public String  dbRepresentation()  { return "" + m_value; }
	}
