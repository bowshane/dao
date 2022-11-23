package com.shanebow.dao.table;
/********************************************************************
* @(#)AlignRightRenderer.java 1.00 20101010
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* AlignRightRenderer: JTable cell renderer that just sets the horizontal
* alignment to right justified.
*
* @author Rick Salamone
* @version 1.00 20101010 RTS created
*******************************************************/
import javax.swing.table.DefaultTableCellRenderer;

public class AlignRightRenderer extends DefaultTableCellRenderer
	{
	public AlignRightRenderer()
		{
		super();
		this.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
		}
	}
