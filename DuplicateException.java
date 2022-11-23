package com.shanebow.dao;
/********************************************************************
* @(#)DuplicateException.java	1.00 05/23/10
* Copyright 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* Duplicate Exception: A simple exception, thrown by the importer or
* server to indicate that it encountered a duplicate db record.
*
* @author Rick Salamone
* @version 1.00 06/01/10
* 20100728 RTS moved to admin package
*******************************************************/
public class DuplicateException extends Exception
	{
	public DuplicateException() { super(); }
	public DuplicateException(Exception ex) { super(ex); }
	public DuplicateException( String msg ) { super(msg); }
	}
