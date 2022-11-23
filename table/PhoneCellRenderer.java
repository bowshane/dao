package com.shanebow.dao.table;
/********************************************************************
* @(#)PhoneCellRenderer.java 1.00 20101010
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* PhoneCellRenderer: JTable cell renderer for a PhoneNumber
*
* @author Rick Salamone
* @version 1.00 20101010 RTS created
*******************************************************/
import javax.swing.table.DefaultTableCellRenderer;

public class PhoneCellRenderer extends DefaultTableCellRenderer
	{
	public PhoneCellRenderer()
		{
		super();
		this.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
		}
	}
