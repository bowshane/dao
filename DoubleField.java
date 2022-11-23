package com.shanebow.dao;
/********************************************************************
* @(#)DoubleField.java 1.00 20100815
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* DoubleField: A wrapper for double type data that implements the
* DataField interface.
*
* @version 1.00 08/15/10
* @author Rick Salamone
*******************************************************/
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DoubleField
	implements DataField
	{
	public static DoubleField read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new DoubleField(rs.getDouble(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e); }
		}

	public static DoubleField parse(String input)
		throws DataFieldException
		{
		double value = 0;
		try { value = Double.parseDouble(input); }
	  catch (Exception e)
	    	{
       	throw new DataFieldException("Double Field " + NUMBER_REQD + input);
	    	}
		return new DoubleField(value);
		}

	protected double m_value;
	public DoubleField ( double value )
		{
		m_value = value;
		}

	public String toString()
		{
		return "" + m_value;
		}

	public double  get() { return m_value; }
	public void    set(double x) { m_value = x; }
	public boolean isEmpty() { return m_value == 0.0; }
	public String  csvRepresentation() { return "" + m_value; }
	public String  dbRepresentation()  { return "" + m_value; }
	}
