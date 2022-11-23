package com.shanebow.dao;
/********************************************************************
* @(#)Duration.java 1.00 20100813
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* Duration: A 'human oriented' length of time, represented internally
* as a long number of seconds.
*
* @version 1.00 08/13/10
* @author Rick Salamone
*******************************************************/
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Duration
	implements DataField, Comparable<Duration>
	{
	public static int FIELD_INDEX = -2;

	public static final int SECONDS_PER_MINUTE = 60;
	public static final int MINUTES_PER_HOUR = 60;
	public static final int HOURS_PER_DAY = 24;

	public static final int SECONDS_PER_HOUR = MINUTES_PER_HOUR * SECONDS_PER_MINUTE;
	public static final int SECONDS_PER_DAY = HOURS_PER_DAY * SECONDS_PER_HOUR;
	public static final int SECONDS_PER_SPRING_AHEAD_DAY = 23 * 60 * 60;
	public static final int SECONDS_PER_FALL_BEHIND_DAY = 25 * 60 * 60;
	public static final int SECONDS_PER_5_MINUTES = 5 * SECONDS_PER_MINUTE;
	public static final int SECONDS_PER_10_MINUTES = 10 * SECONDS_PER_MINUTE;
	public static final int SECONDS_PER_15_MINUTES = 15 * SECONDS_PER_MINUTE;

	public static Duration read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new Duration(rs.getLong(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e); }
		}

	private static int[] _multiplier =
		{
		1,                  //    1 second per second
		SECONDS_PER_MINUTE, //   60 seconds per minute
		SECONDS_PER_HOUR,   //  360 seconds per hour
		SECONDS_PER_DAY,    // 8640 seconds per day
		};

	public static Duration parse(String input)
		throws DataFieldException
		{
		long seconds = 0;
		try
			{
			String[] pieces = input.split(":");
			int field = pieces.length - 1;
			for ( int unit = 0; field >= 0; --field, unit++ )
				seconds += _multiplier[unit] * Integer.parseInt(pieces[field]);
			}
	  catch (Exception e)
	    	{
       	throw new DataFieldException(FIELD_INDEX,NUMBER_REQD + input);
	    	}
		return new Duration(seconds);
		}

	protected long m_seconds;
	public Duration ( long seconds )
		{
		m_seconds = seconds;
		}

	public int compareTo( Duration other )
		{
		return (int)(this.m_seconds - other.m_seconds);
		}

	public String toString()
		{
		if ( m_seconds < SECONDS_PER_MINUTE )
			return String.format( "0:%02d", m_seconds );
		long remaining = m_seconds;
		int days = (int)(remaining / SECONDS_PER_DAY);
		remaining %= SECONDS_PER_DAY;
		int hours = (int)(remaining / SECONDS_PER_HOUR);
		remaining %= SECONDS_PER_HOUR;
		int mins = (int)(remaining / SECONDS_PER_MINUTE);
		remaining %= SECONDS_PER_MINUTE;
		int secs = (int)remaining;
		if ( days > 0 )
			return String.format( "%d:%02d:%02d:%02d", days, hours, mins, secs );
		else if ( hours > 0 )
			return String.format( "%d:%02d:%02d", hours, mins, secs );
		else
			return String.format( "%d:%02d", mins, secs );
//		return (m_seconds == 0) ? "" : "" + m_seconds;
		}

	public void    add(Duration other) { m_seconds += other.m_seconds; }
	public long    increment() { return ++m_seconds; }
	public long    get() { return m_seconds; }
	public void    set(long x) { m_seconds = x; }
	public void    set(Duration other) { m_seconds = other.m_seconds; }
	public boolean isEmpty() { return m_seconds == 0; }
	public String  csvRepresentation() { return "" + m_seconds; }
	public String  dbRepresentation()  { return "" + m_seconds; }
	}
