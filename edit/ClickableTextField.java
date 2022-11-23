package com.shanebow.dao.edit;
/********************************************************************
* @(#)ClickableTextField.java 1.00 10/05/26
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* ClickableTextField: Extends JTextField to provide double click = paste.
* Contact record.
*
* @author Rick Salamone
* @version 1.00 20100526 rts created
* @version 1.01 20101101 rts created
*******************************************************/
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.datatransfer.*;
import javax.swing.JTextField;

public class ClickableTextField extends JTextField
	{
	public ClickableTextField()
		{
		super();
		addMouseListener ( new MouseAdapter ()
			{
			public void mouseReleased (MouseEvent e)
				{
				if (e.getClickCount() == 2)// doubleClick ();
					{
					Clipboard system;
					system = Toolkit.getDefaultToolkit().getSystemClipboard();
					try
						{
             	String trstring= (String)(system.getContents(this).getTransferData(DataFlavor.stringFlavor));

			//		System.out.println("String is:"+trstring);
						if ( trstring != null )
							setText(trstring);
						}
					catch (Exception ex) { System.out.println("Paste ex: "
					        + ex.getLocalizedMessage()); }
					}
				}
			});
		}
	}
