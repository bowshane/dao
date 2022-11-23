package com.shanebow.dao;
/********************************************************************
* @(#)Security.java 1.00 20110209
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* Security: A code table of available securities
*
* @author Rick Salamone
* @version 1.00 20110209 rts
*******************************************************/
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class Security
	implements DataField, Comparable<Security>
	{
	private static final ArrayList<Security> _all = new ArrayList<Security>();
	public static final Security XX = new Security( "--", "", true );
	static
		{
		new Security( "AAPL", "Apple", false);
		new Security( "FCX", "Freeport McMoran", false);
		new Security( "GOOG", "Google", false);
		new Security( "HOG", "Harley Davidson Motorcycles", false);
		new Security( "IBM", "Intl Business Machines", false);
		new Security( "MCD", "McDonalds", false);

		new Security( "REN", "Resolute Energy Corp", true);
		}

	public  static final Iterable<Security> getAll()  { return _all; }

	public static Security parse( String text )
		throws DataFieldException
		{
		if (text == null)
			return Security.XX;
		String trimmed = text.trim();
		if ( trimmed.isEmpty())
			return Security.XX;
		for ( Security security : _all )
			if ( security.m_symbol.equalsIgnoreCase(trimmed))
				return security;
		throw new DataFieldException("Unrecognized Security: " + text);
		}

	public static Security read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return parse(rs.getString(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e.toString()); }
		}

	private final String m_symbol;
	private final String m_desc; // the full desc
	private final boolean m_enabled;
	private Security( String id, String desc, boolean aEnabled )
		{
		m_symbol = id;
		m_desc = desc;
		m_enabled = aEnabled;
		_all.add( this );
		}

	public boolean isEnabled() { return m_enabled; }

	// implement java.lang.Comparable<Security>
	public int compareTo(Security other)
		{
		return m_symbol.compareTo(other.m_symbol);
		}

	public boolean isEmpty() { return (this == XX); }
	public String toString() { return m_symbol + (m_desc.isEmpty()? "" : " " + m_desc); }
	public String symbol() { return m_symbol; }
	public String desc() { return m_desc; }
	public String csvRepresentation() { return (isEmpty()? "" : "" + m_symbol); }
	public String dbRepresentation()  { return "'" + m_symbol + "'"; }
	}
