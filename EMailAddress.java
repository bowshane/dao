package com.shanebow.dao;
/********************************************************************
* @(#)EMailAddress.java	1.00 10/06/21
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* EMailAddress: The contact's email address.
*
* @author Rick Salamone
* @version 1.00, 20100621 rts created
* @version 1.01, 20101023 rts added capacity support
* @version 1.02, 20101125 rts increased capacity from 30 to 100 chars
*******************************************************/
import java.sql.ResultSet;
import java.sql.SQLException;

public final class EMailAddress extends VarChar
	implements DataField
	{
	public static final int MAX_CHARS = 100;
	public static final EMailAddress BLANK = new EMailAddress();

	public static EMailAddress read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new EMailAddress(rs.getString(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e.toString()); }
		}

	public static EMailAddress parse(String input)
		throws DataFieldException
		{
		if ( textHasContent(input) && !hasNameAndDomain(input))
			throw new DataFieldException( "Malformed e-mail address" );
		return new EMailAddress(input);
		}

	/**
	* Validate the form of an email address.
	* requires:
	* import javax.mail.internet.AddressException;
	* import javax.mail.internet.InternetAddress; 
	*
	* <P>Return <tt>true</tt> only if
	*<ul>
	* <li> <tt>aEmailAddress</tt> can successfully construct an
	* {@link javax.mail.internet.InternetAddress}
	* <li> when parsed with "@" as delimiter, <tt>aEmailAddress</tt> contains
	* two tokens which satisfy {@link hirondelle.web4j.util.Util#textHasContent}.
	*</ul> * *<P> The second condition arises since local email addresses, simply of the form
	* "<tt>albert</tt>", for example, are valid for
	* {@link javax.mail.internet.InternetAddress}, but almost always undesired.
	public static boolean isValidEmailAddress(String aEmailAddress)
		{
		if (aEmailAddress == null)
			return false;
		boolean result = true;
		try
			{
			InternetAddress emailAddr = new InternetAddress(aEmailAddress);
			if ( ! hasNameAndDomain(aEmailAddress) )
				{ result = false; }
			}
		catch (AddressException ex){ result = false; }
		return result;
		} 
	*/
	private static boolean hasNameAndDomain(String aEmailAddress)
		{
		String[] tokens = aEmailAddress.split("@");
		return tokens.length == 2
		&& textHasContent( tokens[0] ) && textHasContent( tokens[1] ) ;
		}

	private static boolean textHasContent(String x){ return x != null && !x.trim().isEmpty(); } 

	private EMailAddress() { super(MAX_CHARS); }

	private EMailAddress ( String input )
		throws DataFieldException
		{
		super(MAX_CHARS, input);
		if ( !isEmpty() && (m_value.indexOf('@') < 1))
			throw new DataFieldException("Malformed eMail address - '@' not present: " + input );
		}
	}
