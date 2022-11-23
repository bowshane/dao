package com.shanebow.dao.edit;
/********************************************************************
* @(#)SelectRelationalOperator.java 1.00 20101128
* Copyright 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* SelectRelationalOperator: Component to select an SQL relational
* operator. Simple subclass of JComboBox 
*
* @author Rick Salamone
* @version 1.00, 20101128
*******************************************************/
import javax.swing.JComboBox;

public class SelectRelationalOperator
	extends JComboBox
	{
	public static final String[] OPERATORS = { "=", "<>", ">", "<", ">=", "<=" };
	public SelectRelationalOperator()
		{
		super(OPERATORS);
		}
	}
