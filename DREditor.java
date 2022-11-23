package com.shanebow.dao;
/********************************************************************
* @(#)DREditor.java 1.00 20100407
* Copyright © 2010-2015 by Richard T. Salamone, Jr. All rights reserved.
*
* DREditor: A panel for editing and validating the fields of a
* data record.
*
* Programming Notes: field represents the ContactField # which is the
* the field's index 
*
* @version 1.00
* @author Rick Salamone
* 20100410 rts created
* 20100525 rts Split out from DlgContact, general cleanup & debug
* 20100526 rts Added double click = paste functionality
* 20100616 rts Uses two columns if lots of fields
* 20100623 rts adapted to use FieldEditor
* 20101101 rts validInputs takes a time argument (used only by subclasses)
* 20110331 rts num cols as argument
* 20150711 rts modified for UBOW sites
* 20150806 rts uses level to check expires
* 20150810 rts added support for spannable fields
* 20151026 rts added setEnabled for all fields
* 20151120 rts added ctor for all fields
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.edit.FieldEditor;
import com.shanebow.dao.edit.Spannable;
import com.shanebow.ui.LAF;
import com.shanebow.ui.SBDialog;
import com.shanebow.ui.layout.LabeledPairLayout;
import com.shanebow.util.SBLog;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;

public class DREditor<Record extends DataRecord>
	extends JPanel
	{
	/**
	* fRFNs: an array of the record field numbers in the order that
	* they appear in the form. For example if fRFNs[0] == 7, then
	* the Record.get(7) is the first field on this form
	*/
	private final int[]        fRFNs;

	private final JComponent[] fEditors; // a field editor for each RFN
	private Record             fBlank;   // used when passed in null to edit
	private Record             fRecord;  // the original unedited record
	private final DataField[]  fEdits;   // copies of fRecord's fields for editing
	private boolean            fDirty;

	/**
	* fSettingRecord is true for the duration of the set(Record)
	* method's execution to avoid spurious errors
	*/
	private boolean      fSettingRecord;

	/**
	* ctor: create a data record editor with all fields in a single column
	*/
	public DREditor(Record blank) { this(blank, 1, null); }

	/**
	* ctor: create a data record editor with the specified fields
	* arranged in the specified number of columns
	* @param nColumns - 1 or 2 column layout - subclasses may
	* want to implement more elaoborate schemes(e.g. see RawPanel)
	*/
	public DREditor(Record blank, int nColumns, int[] fields)
		{
		super();
		fBlank = blank;
		ActionListener edListener = new ActionListener() {
			@Override public final void actionPerformed(ActionEvent e) {
				if (!fSettingRecord) setDirty(true);
				}
			};
		KeyAdapter keyListener = new KeyAdapter() {
			@Override public final void keyTyped(KeyEvent e) { setDirty(true); }
			};
		int mid = -1;
		if (fields != null) {
			fRFNs = fields;
			for ( int i = 0; i < fRFNs.length; i++ )
				if ( fields[i] == -1 ) mid = i;
			}
		else fRFNs = allKeys();
		fEdits = new DataField[blank.numFields()];
		JPanel center = this; // if no dispo & fewer than 10 fields use one panel
		int numFields = fRFNs.length;
		fEditors = new JComponent[numFields];

		for ( int ffi = 0; ffi < numFields; ffi++ )
			{
			JComponent ed;
			int cfn = fRFNs[ffi];
			if ( cfn == -1 ) continue;
			ed = blank.meta(cfn).editor(); // wish it could be Record.get...
			fEditors[ffi] = ed;
			ed.addKeyListener(keyListener);
			((FieldEditor)ed).addActionListener(edListener);
			ed.setToolTipText(blank.meta(cfn).toolTip());
			}
		if ( mid != -1 ) // 2 column layout
			{
			center.setLayout( new GridLayout( 0, 2, 10, 20 ));
			JPanel p = new JPanel();
			addFields( p, 0, mid );
			center.add( p );
			p = new JPanel();
			addFields( p, mid, numFields );
			center.add( p );
			}
		else
			addFields( center, 0, numFields );
		}

	/** returns a sequential array [0,1,2,3...,nfields-1] */
	private int[] allKeys() {
		int n = fBlank.numFields();
		int[] it = new int[n];
		for (int i = 0; i < n; i++) it[i] = i;
		return it;
		}

	private void addFields( JPanel p, int first, int last )
		{
		p.setLayout( new LabeledPairLayout());
		for ( int i = first; i < last; i++ )
			{
			int cfn = fRFNs[i];
			if ( cfn == -1 ) continue;

			String label = fBlank.meta(cfn).label();
			if (fEditors[i] instanceof Spannable)
				p.add(LAF.titled(label, fEditors[i]), "span");
			else {
				p.add(new JLabel(label + " ", JLabel.RIGHT), "label");
				p.add(fEditors[i], "field");
				}
			}
		}

	protected void setDirty(boolean on) { fDirty = on; }

	public boolean isDirty() { return fDirty; }

	private int indexOf( int aRFN )
		{
		for ( int i = 0; i < fRFNs.length; i++ )
			if ( fRFNs[i] == aRFN ) return i;
		return -1;
		}

	final public boolean includes( int aRFN )
		{
		return indexOf(aRFN) >= 0;
		}

	public final JComponent getEditorComponent(int aRFN)
		{
		int ffi = indexOf(aRFN);
		return (ffi >= 0)? fEditors[ffi] : null;
		}

	private final FieldEditor getFieldEditor(int aRFN)
		{
		JComponent entryField = getEditorComponent(aRFN);
		return (entryField instanceof FieldEditor) ?
			(FieldEditor)entryField : null;
		}

	public final void clearField( int aRFN )
		{
		FieldEditor entryField = getFieldEditor(aRFN);
		if ( entryField instanceof FieldEditor)
			entryField.clear();
		}

	public final DataField get( int aRFN )
		throws DataFieldException
		{
		try
			{
			JComponent entryField = getEditorComponent(aRFN);
			if ( entryField != null ) // && entryField instanceof FieldEditor)
				fEdits[aRFN] = ((FieldEditor)entryField).get();
			return fEdits[aRFN];
			}
		catch (DataFieldException e ) { falseBecause ( e.toString()); throw e; }
		}

	public void set( Record aRecord )
		{
		fSettingRecord = true;
		fRecord = (aRecord == null)? fBlank : aRecord;
		int nfields = fRecord.numFields();
		for ( int i = 0; i < nfields; i++ )
			_set(i, fRecord.getDefensiveCopy(i));
		fSettingRecord = false;
		fEditors[0].requestFocusInWindow();
		setDirty(false);
		}

	public void set( int aRFN, DataField aValue )
		{
		if ( fEdits[aRFN] == null || !fEdits[aRFN].equals(aValue))
			{
			_set( aRFN, aValue );
			setDirty(true);
			}
		else _set( aRFN, aValue );
		}

	private void _set( int aRFN, DataField aValue )
		{
		fEdits[aRFN] = aValue;
		FieldEditor ed = getFieldEditor(aRFN);
		if ( ed != null )
			ed.set(aValue);
		}

	public final void setEnabled( int aRFN, boolean on )
		{
		JComponent entryField = getEditorComponent(aRFN);
		if ( entryField == null ) return;
		if (entryField instanceof JTextComponent)
			((JTextComponent)entryField).setEditable(on);
		else entryField.setEnabled(on);
		}

	/** enable/disable all fields */
	public final void setEnabled(boolean on) {
		for (int rfn : fRFNs) setEnabled(rfn, on);
		}

	/** setEnabled(false) for the specified fields */
	public final void disable( int... aRFNs )
		{
		for (int rfn : aRFNs)
			setEnabled(rfn, false);
		}

	public boolean isBlank( int aRFN )
		throws DataFieldException
		{
		if ( !includes(aRFN)) // user cannot be held responsible
			return false;      // for field that's not on the form
		DataField df = get(aRFN);
		return (df == null) || df.isEmpty();
		}

	protected boolean badBlank(int aRFN)
		{
		return falseBecause( fRecord.meta(aRFN).label() + " cannot be blank" );
		}

	public boolean validInputs()
		{
		try
			{
			for ( int rfn : fRFNs )
				if ( rfn != -1 )
				get(rfn); // if invalid will throw an exception
		/******
			if ( isBlank(Record.NAME))
				return badBlank(Record.NAME);

			if ( isBlank(Record.EMAIL))
				return falseBecause( "EMAIL address required!" );
		******/
			return true;
			}
		catch (DataFieldException e) { return false; } // get reports error
		catch (Exception e) { e.printStackTrace(); return falseBecause( e.toString()); }
		}

	public boolean hasBlankFields()
		{
		try
			{
			for ( int rfn : fRFNs )
			if ( rfn == -1 ) continue;
			else if ( isBlank(rfn))
				return !falseBecause(fRecord.meta(rfn).label() + " cannot be blank" );
			}
		catch(Exception e) { return true; }
		return false;
		}

	protected final void log( String fmt, Object... args )
		{
		SBLog.write( getClass().getSimpleName(), String.format( fmt, args ));
		}

	protected final boolean falseBecause ( String msg )
		{
		return inputError( msg );
		}

	protected final boolean inputError ( String msg )
		{
		return SBDialog.inputError( msg );
		}

	public final Record getUnedited() { return (fRecord==fBlank)?null:fRecord; }

	public DataField[] getFields()
		{
		return validInputs()? fEdits : null;
		}

	public String getTitle()
		{
		Record rec = getUnedited();
		return (rec==null)? "xx" : rec.getClass().getSimpleName(); // "" causes exception on no work
		}

	/**
	* @param String US - Unit Separator; usually a comma, carot, or "\u001F"
	* @return a String that contains 
	*/
	public String getEdits(String US) {
		if (!validInputs())
			return null;
		int nFields = fRecord.numFields();
		String it = "";
		for (int rfn = 0; rfn < nFields; rfn++) {
			it += US;
			if (includes(rfn)) {
				try {
					if (!get(rfn).equals(fRecord.get(rfn)))
						it += get(rfn).csvRepresentation();
					}
				catch (Exception e) {}
				}
			}
System.out.println("Edits: " + it + " length: " + it.length());
		if (it.length() <= nFields) {
			inputError("No changes found!");
			return null;
			}
		return it.substring(1); // remove extra US at front
		}
	}
