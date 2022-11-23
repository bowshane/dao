package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditComment.java 1.00 20100818
* Copyright © 2010-2015 by Richard T. Salamone, Jr. All rights reserved.
*
* EditComment: UI widget for creating/editing a Comment.
*
* @author Rick Salamone
* @version 2.00 20110311
* 20100818 rts created
* 20110311 rts major revision to support multiple lines
* 20150810 rts made Spannable and added margins
* 20151026 rts added setEnabled
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.Comment;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public final class EditComment
	extends JPanel
	implements Spannable, FieldEditor
	{
	private static int NUM_ROWS = 5;

	private final JTextArea fTextArea = new JTextArea(NUM_ROWS, 30);
	private ActionListener fActionListener;
	private ActionEvent    fActionEvent;

	public EditComment()
		{
		super ( new BorderLayout());
		fTextArea.setLineWrap(true);
		fTextArea.setWrapStyleWord(true);
		fTextArea.setMargin(new Insets(5,5,5,5));
		fTextArea.addKeyListener(	new KeyAdapter()
			{
			@Override public final void keyTyped(KeyEvent e)
				{
				if ( fActionListener != null )
					fActionListener.actionPerformed(fActionEvent);
				}
			});

		JScrollPane scroller = new JScrollPane(fTextArea,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		add(scroller, BorderLayout.CENTER);
		}

	@Override public final void setEnabled(boolean on) { fTextArea.setEditable(on); }
	public void clear() { fTextArea.setText(""); }

	public void set(DataField field)
		{
		fTextArea.setText(((field != null)? field.toString() : ""));
		}

	public boolean isEmpty() { return getText().isEmpty(); }

	public void addActionListener(ActionListener al)
		{
	//	@TODO: support multiple listeners!
		fActionListener = al;
		fActionEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "");
		}

	public Comment get() throws DataFieldException
		{
		return Comment.parse(getText());
		}

	private String getText()
		{
		return fTextArea.getText().trim();
		}
	}
