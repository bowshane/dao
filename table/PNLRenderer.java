package com.shanebow.dao.table;
/********************************************************************
* @(#)PNL.java	1.00 07/18/10
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* PNLRenderer: Renders a PNL, which is an Integer holding the number
* of cents, using a blue background for a profit and red for a loss.
*
* @version 1.00 07/18/10
* @author Rick Salamone
* 20100718 RTS 1.00 created
*******************************************************/
import com.shanebow.dao.PNL;
import com.shanebow.util.SBFormat;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class PNLRenderer extends JLabel
	implements TableCellRenderer
	{
		{
		setOpaque(true);
		setHorizontalAlignment(RIGHT);
		setFont(new JTextField().getFont());
		setForeground( Color.WHITE );
		}

	public Component getTableCellRendererComponent(
                            JTable table, Object profit,
                            boolean isSelected, boolean hasFocus,
                            int row, int column)
		{
		String text;
		Color bg;
		int pnl = ((PNL)profit).intValue();
		if ( pnl == 0 )
			{
			text = "";
			bg = isSelected?	table.getSelectionBackground()
			               : table.getBackground();
			}
		else
			{
			text = SBFormat.toDollarString( pnl );
			bg = (pnl > 0)? Color.BLUE : Color.RED;
			if ( isSelected ) bg = bg.darker();
			}
		setText( text );
		setBackground( bg );
		return this;
		}
	}
