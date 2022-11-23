package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditAddressArea.java 1.00 20100818
* Copyright (c) 2010-2011 by Richard T. Salamone, Jr. All rights reserved.
*
* EditAddressArea: Extends EditString to handle an Address.
*
* @version 1.00 08/18/10
* @author Rick Salamone
* @version 1.00 20100818, rts created
* @version 2.00 20110222, rts major revision to support multiple lines
* @version 2.01 20110304, rts supports ONE action listener
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.Address;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public final class EditAddressArea
	extends JPanel
	implements FieldEditor
	{
	private static int NUM_ROWS = 5;

	private final JTextArea fTextArea = new JTextArea(NUM_ROWS, 40);
	private ActionListener fActionListener;
	private ActionEvent    fActionEvent;

	public EditAddressArea()
		{
		super ( new BorderLayout());
		fTextArea.setLineWrap(true);
		fTextArea.setWrapStyleWord(true);
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

	public void clear() { fTextArea.setText(""); }

	public void set(DataField field)
		{
		fTextArea.setText(
			((field != null)? field.toString().replace('|', '\n') : "")); 
		}

	public boolean isEmpty() { return getText().isEmpty(); }

	public void addActionListener(ActionListener al)
		{
	//	@TODO: support multiple listeners!
		fActionListener = al;
		fActionEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "");
		}

	public Address get() throws DataFieldException
		{
		Address address = Address.parse(getText());
		if ( address.length() > Address.MAX_CHARS )
			throw new DataFieldException( "Address is limited to "
			                            +  Address.MAX_CHARS + " characters" );
		if ( !address.isEmpty()
		&&    address.toString().indexOf('|') == -1 )
			throw new DataFieldException( "Address must have at least two lines!" );
		return address;
		}

	private String getText()
		{
		return fTextArea.getText().trim().replace('\n', '|');
		}
	}
