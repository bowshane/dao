package com.shanebow.dao;
/********************************************************************
* @(#)DataField.java	1.00 10/06/21
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* DataField: The abstact base class for all contact database fields.
*
* @version 1.00 06/21/10
* @author Rick Salamone
*******************************************************/
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ContactID
	implements DataField, Comparable<ContactID>
	{
	public static ContactID NEW_CONTACT = new ContactID(0);

	public static ContactID read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new ContactID(rs.getLong(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e); }

		}

	public static ContactID parse(String input)
		throws DataFieldException
		{
		return new ContactID(input);
		}

	protected long m_value;
	public ContactID ( long value )
		{
		m_value = value;
		}

	private ContactID ( String input )
		throws DataFieldException
		{
		String trimmed = input.trim();
		if (trimmed.isEmpty())
			m_value = -1;
		else try { m_value = Long.parseLong(trimmed);}
		     catch (Exception e)
		     	{
          	throw new DataFieldException("Contact ID " + NUMBER_REQD + input);
		     	}
		}

	@Override public boolean equals(Object other)
		{
		if ( other == null ) return false;
		if ( other instanceof ContactID )
			return ((ContactID)other).m_value == this.m_value;
		else if ( other instanceof Long )
			return ((Long)other).longValue() == this.m_value;
		else
			return false;
		}

	@Override public int compareTo(ContactID other)
		{
		long diff = m_value - other.m_value;
		return (diff == 0)? 0 : (diff > 0)? 1 : -1;
		}

	public long    toLong() { return m_value; }
	@Override public String  toString() { return (m_value == -1) ? "" : "" + m_value; }
	@Override public boolean isEmpty() { return m_value <= 0; }
	@Override public String  csvRepresentation() { return "" + m_value; }
	@Override public String  dbRepresentation()  { return "" + m_value; }
	}
