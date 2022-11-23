package com.shanebow.dao;
/********************************************************************
* @(#)DataFieldException.java 1.00 20100524
* Copyright 2010-2011 by Richard T. Salamone, Jr. All rights reserved.
*
* DataFieldException: An exection that is associated with a particular
* field in the contacts database table.
*
* @author Rick Salamone
* @version 1.00 20100524, rts created
* @version 1.01 20100820, rts modified for package reorganization
* @version 1.02 20100830, rts no longer prints field number -1
* @version 2.00 20100830, rts wrap throwable constructor no longer takes field
*******************************************************/
public class DataFieldException
	extends Exception
	{
	/**
	* Serial id for this class.
	*/
	private static final long serialVersionUID = 1L;
	private final int m_field;

	public DataFieldException(String message)
		{
		super(message);
		m_field = -1;
		}

	/**
	* Wrap another exception as a DataFieldException.
	* @param t -  The exception that occured.
	*/
	public DataFieldException(Throwable t)
		{
		super(t);
		m_field = -1;
		}

	/**
	* Construct a DataFieldException.
	* @param message - a description of the exception.
	*/
	public DataFieldException(int field, String message)
		{
		super(message);
		m_field = field;
		}

	public String getFieldName()
		{
		return ((m_field == -1) ? ""
                             : ("Field #" + m_field));
		}

	public String toString()
		{
		return getFieldName() + " " + getMessage();
		}
	}
