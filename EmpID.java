package com.shanebow.dao;
/********************************************************************
* @(#)EmpID.java	1.00 20110313
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* EmpID: An employee id number.
*
* @version 1.00 20110313
* @author Rick Salamone
*******************************************************/
import java.sql.ResultSet;
import java.sql.SQLException;

public final class EmpID
	implements DataField, Comparable<EmpID>
	{
	public static EmpID XX = new EmpID(0);
	public static EmpID NEW_EMPLOYEE = new EmpID(-1);

	public static EmpID read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return new EmpID(rs.getInt(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e); }
		}

	public static EmpID parse(String input)
		throws DataFieldException
		{
		String trimmed = input.trim();
		if (trimmed.isEmpty())
			return EmpID.XX;
		try { return new EmpID(Integer.parseInt(trimmed)); }
		catch (Exception e)
			{
			throw new DataFieldException("Employee ID " + NUMBER_REQD + input);
			}
		}

	private int fID;
	public EmpID ( int aID )
		{
		fID = aID;
		}

	@Override public boolean equals(Object other)
		{
		if ( other == null ) return false;
		if ( other instanceof EmpID )
			return ((EmpID)other).fID == this.fID;
		else if (other instanceof Integer)
			return ((Integer)other).intValue() == this.fID;
		else
			return false;
		}

	public int compareTo(EmpID other) { return fID - other.fID; }

	public int    toInt() { return fID; }
	@Override public String  toString() { return (fID <= 0) ? "" : "" + fID; }
	@Override public boolean isEmpty() { return fID <= 0; }
	@Override public String  csvRepresentation() { return "" + fID; }
	@Override public String  dbRepresentation()  { return "" + fID; }
	}
