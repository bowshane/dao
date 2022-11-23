package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditAddress.java 1.00 20100818
* Copyright (c) 2010-2011 by Richard T. Salamone, Jr. All rights reserved.
*
* EditAddress: Extends EditString to handle an Address.
*
* @version 1.00 08/18/10
* @author Rick Salamone
* @version 1.00 20100818, rts created
* @version 2.00 20110222, rts major revision to support multiple lines
* @version 2.01 20110304, rts supports ONE action listener
* @version 2.02 20110521, rts allow blank state or province
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.Address;
import com.shanebow.ui.layout.LabeledPairPanel;
import java.awt.event.*;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class EditAddress
	extends LabeledPairPanel
	implements FieldEditor
	{
	private static final int NUM_ROWS = 5;
	private static final String[] LABELS =
		{
		"Street 1",
		"Street 2",
		"City",
		"State/Province",
		"Post/Zip Code"
		};

	private final JTextField[] fFields = new JTextField[LABELS.length];
	private ActionListener fActionListener;
	private ActionEvent    fActionEvent;

	public EditAddress()
		{
		super();
		for ( int i = 0; i < LABELS.length; i++ )
			{
			addRow( LABELS[i], fFields[i] = new JTextField(40));
			fFields[i].addKeyListener(	fKeyAdapter );
			}
		}

	private final KeyAdapter fKeyAdapter = new KeyAdapter()
		{
		@Override public final void keyTyped(KeyEvent e)
			{
			if ( fActionListener != null )
				fActionListener.actionPerformed(fActionEvent);
			}
		};

	public void clear() { for ( JTextField tf : fFields ) tf.setText(""); }

	public void set(DataField field)
		{
		if ( field == null ) { clear(); return; }
		String[] pieces = field.toString().split("\\|");
System.out.println("Set Address: " + field + "\n " + pieces.length + " pieces" );
		for ( int i = 0; i < LABELS.length; i++ )
			fFields[i].setText( (i < pieces.length)? pieces[i] : "" );
		}

	public boolean isEmpty()
		{
		for ( JTextField tf : fFields )
			if ( !tf.getText().trim().isEmpty())
				return false;
		return true;
		}

	public void addActionListener(ActionListener al)
		{
		for ( JTextField tf : fFields )
			tf.addActionListener(al);
		}

	public Address get() throws DataFieldException
		{
		Address address = Address.parse(getText());
		if ( address.length() > Address.MAX_CHARS )
			throw new DataFieldException( "Address is limited to "
			                            +  Address.MAX_CHARS + " characters" );
		if ( !address.isEmpty())
			{
			for ( int i = 0; i < LABELS.length; i++ )
				if ( i != 1 && i != 3 // street #2, State MAY be blank
				&&    fFields[i].getText().trim().isEmpty())
					throw new DataFieldException( LABELS[i] + " cannot be blank" );
			}
		return address;
		}

	private String getText()
		{
		if ( isEmpty()) return "";
		String it = fFields[0].getText().trim();
		for ( int i = 1; i < LABELS.length; i++ )
			it += "|" + fFields[i].getText().trim();
		return it;
		}
	}
