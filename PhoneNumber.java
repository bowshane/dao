package com.shanebow.dao;
/********************************************************************
* @(#)PhoneNumber.java 1.00 20100607
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* PhoneNumber: A phone number stored as a String of digits.
*
* @author Rick Salamone
* @version 1.00, 20100607 rts created
* @version 1.01, 20101003 rts removed index constant
* @version 1.02, 20101023 rts moved comparable into VarChar superclass
* @version 1.01, 20101023 rts added capacity support
*******************************************************/
import com.shanebow.util.CSV;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PhoneNumber extends VarChar
	{
	public static final int MIN_DIGITS = 5;
	public static final int MAX_DIGITS=16;
	static final String BAD_PHONE   = "Phone number must contain at least "
	                                  + MIN_DIGITS + " found: ";

	public PhoneNumber( String digits )
		throws DataFieldException
		{
		super(MAX_DIGITS, digits);
		m_value = CSV.digits(m_value);
		if ( !isEmpty() && (m_value.length() < MIN_DIGITS))
			throw new DataFieldException( BAD_PHONE + digits );
		}

	public void update(String digits )
		{
		m_value = CSV.digits(digits);
		}

	public final boolean equals( PhoneNumber other )
		{
		return this.m_value.equals(other.m_value);
		}

	public String toString()
		{
		int len = m_value.length();
		return (len > 2)? m_value.substring(0,2) + " "
		                   + ((len > 5)? m_value.substring(2,5) + " " + m_value.substring(5)
		                              : m_value.substring(2))
		                : m_value;
		}

	public static PhoneNumber read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new PhoneNumber(rs.getString(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e.toString()); }
		}

	public static PhoneNumber parse(String d)
		throws DataFieldException
		{
		return new PhoneNumber(d);
		}
	}
