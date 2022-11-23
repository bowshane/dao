package com.shanebow.dao;
/********************************************************************
* @(#)ContactType.java	1.00 10/06/22
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* ContactType: A contact type. Stored in the contact data table
* as a single character code, but displayed to users as a string.
* Since there are only a couple of types, they are stored in this
* object rather than in a database lookup table.
*
* @version 1.00 06/22/10
* @author Rick Salamone
*******************************************************/
import com.shanebow.util.SBArray;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ContactType // extends DataField
	implements DataField
	{
	private static final SBArray<ContactType> _all = new SBArray<ContactType>(1);

	public static final ContactType BIZ = new ContactType( 'B', "Business");
	public static final ContactType RES = new ContactType( 'R', "Residential");

	public  static final int countAll() { return _all.capacity(); }
	public  static final Iterable<ContactType> getAll()  { return _all; }

	public static ContactType parse( String text )
		throws DataFieldException
		{
		if ((text == null) || text.trim().isEmpty())
			return BIZ; // default value;
		switch ( text.trim().charAt(0))
			{
			case 'B':
			case 'b': return BIZ;
			case 'R':
			case 'r': return RES;

			default:  throw new DataFieldException("Contact Type " + BAD_LOOKUP + text);
			}
		}

	public static ContactType read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return parse(rs.getString(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e.toString()); }
		}

	char m_code; // the single character acronym
	String m_name; // the full name
	private ContactType( char code, String name )
		{
		m_code = code;
		m_name = name;
		_all.add( this );
		}

	public String toString() { return m_name; }
	public boolean isEmpty() { return false; }
	public String csvRepresentation() { return "" + m_code; }
	public String dbRepresentation()  { return "'" + m_code + "'"; }
	}
