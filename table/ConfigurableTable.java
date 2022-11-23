package com.shanebow.dao.table;
/********************************************************************
* @(#)ConfigurableTable.java 1.00 20110212
* Copyright © 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* ConfigurableTable: Interface for a table that may be configured
* via a TablePreferencesEditor.
*
* @author Rick Salamone
* @version 1.00, 20110212 rts created
*******************************************************/
import com.shanebow.dao.FieldMeta;
import java.awt.Font;

public interface ConfigurableTable
	{
	/**
	* @return an array of FieldMeta that may be added to the table
	*/
	FieldMeta[] getAvailableFields();

	/**
	* @return a String that holds the prefix for the property names
	*         that will be configured. Should begin with "usr."
	*/
	String getPropertyPrefix();

	/**
	* Apply the configuration settings to the table. Called when
	* the user selects "Apply" from the preferences editor.
	*/
	void configure();

	void setFont(Font aFont);
	}
