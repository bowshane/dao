package com.shanebow.dao;
/********************************************************************
* @(#)When.java  1.00 20100607
* Copyright © 2010-2015 by Richard T. Salamone, Jr. All rights reserved.
*
* When: A contact When: Stored as a the contact data table
* as a long, but displayed and edited as a MM/DD/YY String.
*
* @author Rick Salamone
* @version 1.00
* 20100607 rts now extends DataField
* 20100627 rts parse & update share same code in _parse
* 20100830 rts added parse support for d-MMM-yy ala Excel
* 20101024 rts added parse support for long in SBDate style
* 20101101 rts csvRepresentation uses long value
* 20150816 rts added satisfies(range)
*******************************************************/
import com.shanebow.util.SBDate;
import java.sql.ResultSet;
import java.sql.SQLException;

public class When
	implements DataField, Comparable<When>
	{
	public static final String ERR_DATE_FORMAT="Enter date in MM/DD/YY format";
	public static final When BLANK = new When(0);
	public static final When TODAY
		= new When(SBDate.toTime(SBDate.yyyymmdd() + "  00:00"));

	public static When read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new When(rs.getLong(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e); }
		}

	public static When parse(String mdyy )
		throws DataFieldException
		{
		return new When( _parse(mdyy));
		}

	protected static final void _log(String fmt, Object... args)
		{
		com.shanebow.util.SBLog.write( "When", String.format( fmt, args ));
		}

	public static long _parseFull(String full )
		throws DataFieldException
		{
		int h = 0, m = 0, s = 0;
		String[] pieces = full.split(" ");
		String[] ymd = pieces[0].split("/");
		int month = Integer.parseInt(ymd[0]);
		int dom = Integer.parseInt(ymd[1]);
		int year = Integer.parseInt(ymd[2]);
		if (pieces.length > 1) {
			String[] hms = pieces[1].split(":");
			h = Integer.parseInt(hms[0]);
			m = Integer.parseInt(hms[1]);
			try {s = Integer.parseInt(hms[2]);}
			catch (Exception e) {}
			}
// long time = SBDate.toTime ( month, dom, year, h, m, s );
//System.out.println("parse when: " + month + "/" + dom + "/" + year + " " + h + ":" + m + ":" + s
//+ " = " + time + " = " + SBDate.yyyymmdd(time));

		return SBDate.toTime ( month, dom, year, h, m, s );
		}

	public static long _parse(String mdyy )
		throws DataFieldException
		{
		if ( mdyy.trim().isEmpty() || mdyy.equals("0"))
			return 0;
		if ( mdyy.length() >= 8 )
			{
			if (mdyy.indexOf('/') > 0)
				return _parseFull(mdyy);
			else try // see if it's a long SBDate style time
				{
				long time = Long.parseLong(mdyy);
				return time;
				}
			catch ( Exception e ) {}
			}
		int month = 0; // 2008-01-23
		int dom   = 0; // 0123456789A
		try
			{
			String[] piece = mdyy.trim().split( "/", 4 );
			if ( piece.length < 2 ) // no "/" so try "15-Sep-10"
				{
				piece = mdyy.trim().split( "-", 4 );
				month = SBDate.getMonth(piece[1]);
				dom = Integer.parseInt(piece[0]);
				}
			else // slash format, e.g. 9/15/2010
				{
				month = Integer.parseInt(piece[0]);  // 2008-01-23
				dom   = Integer.parseInt(piece[1]); // 0123456789A
				}
			if ( piece.length > 3 )
				throw new DataFieldException( BAD_DATE + mdyy + " (too many pieces)");
			if (( month < 1) || (month > 12) || (dom < 1) || (dom > 31))
				throw new DataFieldException( "Invalid month or day: " + mdyy );
			int year;
			if ( piece.length == 3 )
				{
				year = Integer.parseInt(piece[2]);
				if ( year < 100 ) year += 2000;
				}
			else // if no year entered, use this year or next
				{  // as appropriate
				SBDate now = new SBDate();
				year = now.getYear();
				if ((month < now.getMonth())
				||  ((month == now.getMonth()) && (dom <= now.getDOM())))
					++year;
				}
			return SBDate.toTime ( month, dom, year, 0, 0, 0 );
			}
		catch (Exception e)
			{
			throw new DataFieldException( BAD_DATE + mdyy );
			}
		}

	long m_time = 0;

	public When() { this(0); }

	public When( long time )
		{
		m_time = time;
		}

	@Override public int compareTo(When other)
		{
		long diff = (m_time - other.m_time)/100;
		return (diff == 0)? 0 : (diff > 0)? 1 : -1;
		}

	@Override public boolean equals(Object other)
		{
		if ( other == null ) return false;
		if ( other instanceof When )
			return ((When)other).m_time == this.m_time;
		else if ( other instanceof Long )
			return ((Long)other).longValue() == this.m_time;
		else if ( other instanceof java.util.Date )
			return ((java.util.Date)other).getTime() / 1000 == this.m_time;
		else if ( other instanceof java.util.Calendar )
			return ((java.util.Calendar)other).getTimeInMillis() / 1000 == this.m_time;
		else return false;
		}

	public void update(String mdyy )
		throws DataFieldException
		{
		m_time = _parse( mdyy );
		}
	public void setLong(long time) { m_time = time; }

	public long getLong()    { return m_time; }
	public boolean isEmpty() { return (m_time == 0); }
//	public String toString() { return (m_time == 0) ? "" : SBDate.mmddyyyy_hhmmss(m_time); }
	public String toString() { return (m_time == 0) ? "" : SBDate.mmddyy_hhmm(m_time); }
	public String csvRepresentation()
		{
		return (isEmpty()? "" : "" + m_time );
		}
	public String dbRepresentation() { return "" + m_time; }
	public boolean satisfies(long[] range) {
		return range == null || (m_time > range[0] && m_time <= range[1]);
		}
	}
