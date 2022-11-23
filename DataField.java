package com.shanebow.dao;
/********************************************************************
* @(#)DataField.java	1.00 10/06/21
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* DataField: A piece of contact information that can be stored in
* a csv file or the database, or edited by the user.
*
* @author Rick Salamone
* @version 1.00 20100621 rts created
*******************************************************/
import javax.swing.JComponent;

public interface DataField
//	extends Comparable<DataField>
	{
	static final String NUMBER_REQD = "Number required, found: ";
	static final String BAD_DATE    = "Expected Date in m/d/yyyy or d-MMM-yy format, found: ";
	static final String BAD_RANGE   = "Number out of range, found: ";
	static final String FIELD_REQD  = "Cannot be blank";
	static final String BAD_LOOKUP  = "Unrecognized value: ";

	abstract public boolean isEmpty();
	abstract public String csvRepresentation();
	abstract public String dbRepresentation();
	}
