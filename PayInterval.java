package com.shanebow.dao;
/********************************************************************
* @(#)PayInterval.java	1.00 20150805
* Copyright © 2010-2015 by Richard T. Salamone, Jr. All rights reserved.
*
* PayInterval: Only two pay intervals are supported: Monthly and
* Yearly. They are stored as 'M' and 'Y' respectively.
*
* @author Rick Salamone
* @version 1.00
* 20150805 rts created
*******************************************************/
import com.shanebow.dao.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class PayInterval
	implements DataField, Comparable<PayInterval>
	{
	public static final PayInterval MONTHLY = new PayInterval("Monthly");
	public static final PayInterval YEARLY = new PayInterval("Yearly");

	private static final PayInterval[] _all = { MONTHLY, YEARLY };

	public  static final int countAll() { return _all.length; }
	public  static final PayInterval[] all() {return _all;}
	public  static final PayInterval[] getAll() {return _all;}

	public static PayInterval parse( String text )
		throws DataFieldException
		{
		try {
			char first = text.charAt(0);
			return first=='Y' || first=='y' ? YEARLY : MONTHLY;
			}
		catch(Exception e) {
			throw new DataFieldException("PayInterval " + BAD_LOOKUP + text);
			}
		}

	public static PayInterval read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return parse(rs.getString(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e); }
		}

	String m_name; // the full name

	private PayInterval(String name)
		{
		m_name = name;
		}

	@Override public int compareTo(PayInterval other)
		{
		return m_name.charAt(0) - other.m_name.charAt(0);
		}

	@Override public boolean equals(Object other)
		{
		try {
			return (other instanceof PayInterval)?  other == this
			     : (other instanceof String) ? equals(parse((String)other))
			     : false;
			}
		catch (Exception e) { return false; }
		}
	@Override public int hashCode() { return (int)m_name.charAt(0); }
	@Override public String toString() { return m_name; }

	public boolean isEmpty() { return false; }
	public String name() { return m_name; }
	public String csvRepresentation() { return "" + m_name.charAt(0); }
	public String dbRepresentation()  { return "'" + m_name.charAt(0) + "'"; }
	}
