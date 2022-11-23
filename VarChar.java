package com.shanebow.dao;
/********************************************************************
* @(#)VarChar.java	1.00 10/06/07
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* VarChar: The base class for all varchar data fields. Subclasses
* should provide a constructor that takes a String value and calls
* super(int capacity,String value) in order to set the capacity to
* match the size allotted in the database.
*
* @author Rick Salamone
* @version 1.00, 20100607 rts created
* @version 1.01, 20101003 rts handles null input to constructor
* @version 1.02, 20101022 rts added capacity & renamed VarChar from StringField
* @version 1.03, 20101023 rts implements CharSequence
* @version 1.04, 20101023 rts implements Comparable<VarChar>
* @version 1.05, 20101023 rts protected constructor requires capacity
* @version 1.06, 20101023 rts added equals() method
* @version 1.07, 20101024 rts added final log method to aid subclass debug
*******************************************************/
import com.shanebow.util.CSV;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VarChar
	implements DataField, CharSequence, Comparable<VarChar>
	{
	protected String m_value;
	protected int m_capacity = 255;

	protected final void log(String fmt, Object... args)
		{
		com.shanebow.util.SBLog.write( getClass().getSimpleName(),
		    String.format( fmt, args ));
		}

	public static VarChar parse(String input)
		throws DataFieldException
		{
		return new VarChar(255, input);
		}

	public VarChar() { this(255); }

	protected VarChar( int capacity )
		{
		m_capacity = capacity;
		m_value = "";
		}

	protected VarChar( int capacity, String input )
		throws DataFieldException
		{
		m_capacity = capacity;
		if ( input == null )
			input = "";
		set(input);
		}

	public final int  getCapacity() { return m_capacity; }
	public final void setCapacity(int max) { m_capacity = max; }

	private void _trucateIfNecessry()
		{
		if ( m_value.length() > m_capacity )
			m_value = m_value.substring(0, m_capacity);
		}

	// implement DataField
	public void set(String input)
		{
		m_value = CSV.clean(input);
		_trucateIfNecessry();
		}

	public boolean isEmpty() { return m_value.isEmpty(); }
	public String csvRepresentation()
		{
//		return ((m_value.indexOf(',') >= 0) ? "\"" + m_value + "\"" : m_value);
		return m_value;
		}

	public String dbRepresentation()  { return "'" + m_value + "'"; }

	// implement java.lang.CharSequence
	public String toString() { return m_value; } // not final, subclasses do format
	public final char charAt(int index) { return m_value.charAt(index); }
	public final int  length() { return m_value.length(); }
	public final CharSequence subSequence(int start, int end)
		{
		return m_value.subSequence(start, end);
		}

	// implement java.lang.Comparable<VarChar>
	@Override public int compareTo(VarChar other)
		{
		return m_value.compareTo(other.m_value);
		}

	@Override public final boolean equals( Object other )
		{
		return other != null
			&& (other instanceof VarChar)
			&& other.toString().equals(m_value);
		}
	}
