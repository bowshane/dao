package com.shanebow.dao;
/********************************************************************
* @(#)DataField.java 1.00 20110209
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* OrderID: The order number of a security purchase.
*
* @author Rick Salamone
* @version 1.00 20110209
*******************************************************/
import java.sql.ResultSet;
import java.sql.SQLException;

public final class OrderID
	implements DataField, Comparable<OrderID>
	{
	public static final OrderID NEW_ORDER = new OrderID(-1);

	public static OrderID read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new OrderID(rs.getLong(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e); }
		}

	public static OrderID parse(String input)
		throws DataFieldException
		{
		return new OrderID(input);
		}

	protected long m_value;
	public OrderID ( long value )
		{
		m_value = value;
		}

	private OrderID ( String input )
		throws DataFieldException
		{
		String trimmed = input.trim();
		if (trimmed.isEmpty())
			m_value = -1;
		else try { m_value = Long.parseLong(trimmed);}
		     catch (Exception e)
		     	{
          	throw new DataFieldException("Order ID " + NUMBER_REQD + input);
		     	}
		}

	@Override public boolean equals(Object other)
		{
		if ( other == null ) return false;
		if ( other instanceof OrderID )
			return ((OrderID)other).m_value == this.m_value;
		else if ( other instanceof Long )
			return ((Long)other).longValue() == this.m_value;
		else
			return false;
		}

	public int compareTo(OrderID other)
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
