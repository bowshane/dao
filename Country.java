package com.shanebow.dao;
/********************************************************************
* @(#)Country.java	1.00 2010623
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* Country: Country's are stored in the contact data table as an
* integer which is the foreign key to the country table. The
* country table stores the ID along with the country name and
* it's abbreviation.
*
* @author Rick Salamone
* @version 1.00
* 20100623 rts Created based on Dispo
* 20100827 rts 1.01 added nationality for counties
* 20100830 rts 1.02 added many new countries
* 20101019 rts 1.03 added Canada
* 20101129 rts 1.04 added parseNationality()
* 20110122 rts 1.05 added equals()
* 20110302 rts 1.06 added Guadalupe
* 20110318 rts 2.00 reads code table from jar file
* 20110319 rts 2.01 added fields for phone code, mail delay, GMT offset
* 20110604 rts 2.02 implements hashCode for binarySearch in SBArray
* 20130215 rts 2.02 move codetable into the dao directory
*******************************************************/
import com.shanebow.util.SBArray;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.shanebow.ui.SBDialog;
import com.shanebow.util.CSV;
import com.shanebow.util.SBMisc;
import java.io.*;
import java.util.Collections;
import java.util.Comparator;

// remove scotland replace with UK
public final class Country
	implements DataField, Comparable<Country>
	{
	private static final String CODE_TABLE="com/shanebow/dao/codetable/countries.csv";
	private static final SBArray<Country> _all = new SBArray<Country>(255);
	public static final Country XX = new Country(   0, "--", null,  -1, -1, -1 );
	static
		{
		BufferedReader reader = null;
System.out.println("loading countries");
		try
			{
			reader = SBMisc.resourceReader(CODE_TABLE);
			reader.readLine(); // ignore header
			String text;
			while ((text = reader.readLine()) != null )
				unmarshall(text);
			}
		catch (Exception e) { System.out.println( CODE_TABLE + ": " + e ); }
		finally { try { if (reader != null) reader.close(); } catch (Exception e){}}
//		loadPhoneCodes();
		}

	public  static final int countAll() { return _all.size(); }
	public  static final Iterable<Country> getAll()  { return _all; }

	public static Country parseNationality( String text )
		throws DataFieldException
		{
		if (text == null)
			return Country.XX;
		String trimmed = text.trim();
		if ( trimmed.isEmpty() || trimmed.equals("0"))
			return XX;
		try { return find(Integer.parseInt(trimmed)); }
		catch(Exception e) {}
		for ( Country country : _all )
			if ( trimmed.equalsIgnoreCase(country.nationality()))
				return country;
		if ( trimmed.equalsIgnoreCase("Aussie")) return parse("Australia");
		throw new DataFieldException("Country " + BAD_LOOKUP + text);
		}

	public static Country parse( String text )
		throws DataFieldException
		{
		if (text == null)
			return Country.XX;
		String trimmed = text.trim();
		if ( trimmed.isEmpty() || trimmed.equals("0"))
			return XX;
		try { return find(Integer.parseInt(trimmed)); }
		catch(Exception e) {}
		trimmed = trimmed.toLowerCase();
		for ( Country country : _all )
			if ( trimmed.startsWith(country.m_name.toLowerCase()))
				return country;
		throw new DataFieldException("Country " + BAD_LOOKUP + text);
		}

	public static Country read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return find(rs.getInt(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e); }
		}

	public static Country findSlow(int id)
		{
		for ( Country country : _all )
			if ( country.m_id == id )
				return country;
		return Country.XX;
		}

	public static Country find(int id)
		{
		int index = _all.binarySearch(id);
		return (index < 0)? Country.XX : _all.get(index);
		}

	int m_id;
	String m_name; // the full name
	String m_nationality;
	private int fPhoneCode;
	private int fMailDaysToArrive;
	private int fGMTOffset;

	private Country( int id, String name, String nationality,
		int aPhoneCode, int aMailDaysToArrive, int aGMTOffset )
		{
		m_id = id;
		m_name = name;
		m_nationality = nationality;
		fPhoneCode = aPhoneCode;
		fMailDaysToArrive = 5; // aMailDaysToArrive;
		fGMTOffset = -1; // aGMTOffset;
		_all.add( this );
		}

	@Override public int compareTo(Country other)
		{
		return m_id - other.m_id;
		}

	@Override public boolean equals(Object other)
		{
		if ( other == null ) return false;
		if ( !(other instanceof Country)) return false;
		return ((Country)other).m_id == this.m_id;
		}
	@Override public int hashCode() { return m_id; }
	@Override public String toString() { return m_name; }

	public boolean isEmpty() { return (m_id == 0); }
	public int id() { return m_id; }
	public String name() { return m_name; }
	public String nationality()
		{
		if ( m_nationality != null )
			return m_nationality;
		if ( m_name.endsWith("a")) return m_name + "n";
		if ( m_name.endsWith("n")) return m_name + "ese";
		return m_name;
		}

	public String csvRepresentation() { return "" + m_id; }
	public String dbRepresentation()  { return "" + m_id; }

	/**
	* marshall() - encodes the Country object as a String suitable
	* for network transmission. Inverse of unmarshall().
	*/
	private static final String SEP = ","; // separates parts of marshalled object
	public String marshall()
		{
		StringBuffer csv = new StringBuffer();
		csv.append(m_id);
		csv.append(SEP).append(m_name);
		csv.append(SEP);
		if ( m_nationality != null )
			csv.append(m_nationality);
		csv.append(SEP).append(fPhoneCode);
		csv.append(SEP).append(fMailDaysToArrive);
		csv.append(SEP).append(fGMTOffset);
		return csv.toString();
		}

	/**
	* unmarshall() - decodes a marshalled Country into a new Country object
	* Inverse of marshall().
	*/
	public static final Country unmarshall(String aMarshalled)
		throws DataFieldException
		{
		String[] pieces = aMarshalled.split(SEP);
		try
			{
			return new Country( Integer.parseInt(pieces[0]), // id
			                 pieces[1], // name
			                 pieces[2].isEmpty()? null : pieces[2], // nationality
			                 Integer.parseInt(pieces[3]), // fPhoneCode
			                 Integer.parseInt(pieces[4]), // fMailDaysToArrive
			                 Integer.parseInt(pieces[5])); // fGMTOffset
			}
		catch (Throwable t) { throw new DataFieldException("Country init failed: " + t);}
		}

/***************
	private static final String filespec = "c:/apps/src/com/apo/resources/codetable/countries.csv";
	static
		{
		try
			{
			PrintWriter file = new PrintWriter ( filespec );
			file.println ( "#,Name,Nationality,Calling Code,Mail Lead Time,GMT Offset");
			for ( Country country : getAll())
				if ( country == Country.XX )
					continue;
				else if ( country.m_name.IndexOf(',') >= 0 )
					throw new IOException("Country Name cannot contain a comma");
				else
					file.println ( country.marshall());
			file.close();
			}
		catch (IOException e)
			{
			SBDialog.error( "Error Saving countries", filespec + " Error: " + e.toString());
			}
		}

	static void loadPhoneCodes()
		{
		BufferedReader stream = null;
		String filename = "c:/apps/src/com/apo/_docs/globalareacodes.csv";
		try
			{
			stream = new BufferedReader(new FileReader(filename));
			stream.readLine(); // ignore header
			String text;
			String prev = "";
			while ((text = stream.readLine()) != null )
				{
				String[] pieces = CSV.split(text, 4);
				if (pieces[0].equals(prev)) continue;
				Country country = find(pieces[0]);
				if ( country == Country.XX )
					country = new Country( 0, pieces[0], null, 0 );
				country.fPhoneCode = Integer.parseInt(pieces[1]);
				prev = pieces[0];
				}
			}
		catch (Exception e) { System.out.println( filename + ": " + e ); }
		finally { try { if (stream != null) stream.close(); } catch (Exception e){}}
		}

	private static Country find(String name)
		{
		for ( Country country : _all )
			if ( country.m_name.equalsIgnoreCase(name))
				return country;
		return Country.XX;
		}
***************/
	} //288
