package com.shanebow.dao.edit;
/********************************************************************
* @(#)DlgAddress.java 1.0 20101021
* Copyright 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* DlgAddress: Dialog to prompt user for a address.
*
* @author Rick Salamone
* @version 1.00 20101021 rts created
* @version 1.01 20101023 rts uses Address datafield instead of String
*******************************************************/
import com.shanebow.dao.Address;
import com.shanebow.dao.DataFieldException;
import com.shanebow.ui.SBDialog;
import java.awt.*;
import javax.swing.*;

public final class DlgAddress
	{
	private static final String TITLE = "Address";
	private static final String CMD_SAVE="Save";
	private static final String[] CHOICES = { CMD_SAVE };

	public static Address get(Address aAddress)
		{
		JPanel m_panel = new JPanel(new BorderLayout());
		JLabel lblInstructions = new JLabel(
		 "<HTML>Please verify that the address is formatted<BR>"
		 + "<I>correctly for a <B>mailing label</B></I><UL>"
		 + "<LI>the <I>number and street</I> should be on the first line</LI>"
		 + "<LI>the <I>PO Box</I> or <I>c/o</I> should be on the second line</LI>"
		 + "<LI><B><I>Do not include the country here,</I></B>"
		 + " That goes in it's own field</LI>" );
		EditAddress editor = new EditAddress();
		editor.set(aAddress);
		m_panel.add( lblInstructions, BorderLayout.NORTH );
		m_panel.add( editor, BorderLayout.CENTER );

		while ( JOptionPane.showOptionDialog(null, m_panel, TITLE,
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, CHOICES, CHOICES[0]) == 0 )
			{
			try
				{
				Address address = editor.get();
				if ( isValid(address))
					return address;
				}
			catch (DataFieldException e)
				{
				SBDialog.inputError( e.getMessage());
				}
			}
		return null;
		}

	private static boolean isValid(Address address)
		{
		if ( address.isEmpty())
			return SBDialog.inputError( "Address cannot be blank" );
		return true;
		}
	}
