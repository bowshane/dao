package com.shanebow.dao.edit;
/********************************************************************
* @(#)ClickableTextField.java 1.00 20110301
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* ClickableTextField: Extends JTextField to provide double click = paste.
* Contact record.
*
* @author Rick Salamone
* @version 1.00 20110301 rts created based upon ClickableTextField
*******************************************************/
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.datatransfer.*;
import javax.swing.JTextArea;

public class ClickableTextArea
	extends JTextArea
	{
	public ClickableTextArea()
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
