package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditWhen.java	1.00 10/06/22
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* EditWhen: A component for editing the contact When field.
* Extends JTextField to implement FieldEditor.
*
* @author Rick Salamone
* @version 1.00 20100622 rts created as EditCallDate
* @version 1.01 20100820 rts modified for package reorganization
* @version 2.00 20101031 rts complete rewrite to extend JSpinner
* @version 2.01 20101031 rts added constructor that accepts format string
* @version 2.02 20101101 rts renamed to EditWhen to reflect when = time + date
* @version 2.03 20110309 rts fixed bug to check for null listener
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.When;
import com.shanebow.util.SBDate;
import java.awt.Dimension;
import javax.swing.JTextField;

public class EditWhen extends JTextField
	implements FieldEditor
	{
	public EditWhen ()
		{
		super(50);
		}

	@Override public Dimension getPreferredSize()
		{ return new Dimension(125, super.getPreferredSize().height); }

	public long getTime()
		{
		try { return When.parse(getText()).getLong(); }
		catch (Exception e) { return 0; }
		}

	public void setTime(long time)
		{
		setText((time == 0)? "" : SBDate.mmddyyyy_hhmmss(time));
		}

	// implement FieldEditor
	public void clear() { setTime(0); }

	public void set(String text)
		{
		long time;
		try { time = When._parse(text); }
		catch ( Exception e )
			{
			com.shanebow.util.SBLog.write( "error setting date to: " + text
				+ "\n" + e.getMessage());
			time = 0;
			}
		setTime(time);
		}

	public void set(DataField cd)
		{
		setTime(((When)cd).getLong());
		}

	public When get()
		throws DataFieldException
		{
		return When.parse(getText());
		}

	public boolean isEmpty() { return getText().trim().isEmpty(); }
	}
