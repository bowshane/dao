package com.shanebow.dao.edit;
/********************************************************************
* @(#)DlgComment.java 1.0 20101021
* Copyright 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* DlgComment: Dialog to prompt user for a comment.
*
* @author Rick Salamone
* @version 1.00 20101021 rts created
* @version 1.01 20101023 rts uses Comment datafield instead of String
*******************************************************/
import com.shanebow.dao.Comment;
import com.shanebow.dao.DataFieldException;
import com.shanebow.ui.SBDialog;
import java.awt.*;
import javax.swing.*;

public final class DlgComment
	{
	private static final String TITLE = "Comment";
	private static final String CMD_SAVE="Save";
	private static int MAX_CHARS = 255;
	private static int NUM_ROWS = 8;
	private static final String[] CHOICES = { CMD_SAVE };

	public static Comment get()
		{
		JPanel m_panel = new JPanel();
		JTextArea taComment = new JTextArea(NUM_ROWS, MAX_CHARS/NUM_ROWS);
		taComment.requestFocusInWindow();
		taComment.setLineWrap(true);
		taComment.setWrapStyleWord(true);
		JScrollPane scroller = new JScrollPane(taComment,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		scroller.setBorder(BorderFactory.createLoweredBevelBorder());
		m_panel.add( scroller );

		while ( JOptionPane.showOptionDialog(null, m_panel, TITLE,
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, CHOICES, CHOICES[0]) == 0 )
			{
			try
				{
				Comment comment = Comment.parse(taComment.getText());
				if ( isValid(comment))
					return comment;
				}
			catch (DataFieldException e)
				{
				SBDialog.inputError( e.getMessage());
				}
			}
		return null;
		}

	private static boolean isValid(Comment comment)
		{
		if ( comment.isEmpty())
			return SBDialog.inputError( "Comment cannot be blank" );
		if ( comment.length() > MAX_CHARS )
			return SBDialog.inputError( "Comments are limited to "
			                            +  MAX_CHARS + " characters" );
		return true;
		}
	}
