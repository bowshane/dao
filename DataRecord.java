package com.shanebow.dao;
/********************************************************************
* @(#)DataRecord.java	1.00 20100407
* Copyright © 2010-2015 by Richard T. Salamone, Jr. All rights reserved.
*
* DataRecord: Essentially a set of DataField objectsalong with some
* meta data and static methods that allow for flexible handling of
* the records.
*
* @author Rick Salamone
* @version 1.00
* 20100407 rts created
* 20100522 rts finalied field names & csv order for version 1
* 20100626 rts finalied parse() method to return DataField
* 20100627 rts added combobox type editors for source, country, region
* 20100818 rts moved private class FieldMeta out to share with CallFields
* 20110315 rts added constant for BLANK Raw object
* 20150711 rts modified for UBOW sites
* 20151120 rts added set and use StringBuilder for pack
*******************************************************/
import com.shanebow.util.SBLog;
import com.shanebow.dao.*;
import com.shanebow.dao.edit.*;
import javax.swing.JComponent;

public abstract class DataRecord
	implements MetaConstants
	{
	abstract public int numFields();
	abstract public FieldMeta meta(int i);
	public long lmod() { return 0; }
	abstract public int getTableNum();

	// @TODO; maybe update individual fields rather than replace them?
	public DataRecord updateFrom(DataRecord other) {
		for (int i = 0; i < fFields.length; i++)
			fFields[i] = other.fFields[i];
		return this;
		}

	protected final DataField fFields[];
	public final DataField    get(int key){ return fFields[key]; }
	public final DataField[]  getFields(){ return fFields; }
	public void set(int key, String value)
		throws Exception
		{
		fFields[key] = meta(key).parse(value);
		}

	/**
	* Returns a defensive copy of all fields
	*/
	public final DataField[] getFieldsDefensive()
		{
		DataField[] copy = new DataField[fFields.length];
		for ( int i = 0; i < fFields.length; i++ )
			copy[i] = getDefensiveCopy(i);
		return copy;
		}

	/**
	* Returns a defensive copy of a field or null if a problem
	*/
	public final DataField getDefensiveCopy(int key)
		{
		try { return meta(key).parse(fFields[key].csvRepresentation()); }
		catch ( Exception e ) { return null; }
		}

	protected DataRecord() {
		fFields = new DataField[numFields()];
		}

	/**
	* ctor required by the RecordEditorPanel
	*/
	public DataRecord(DataField[] aFields)
		{
		this();
		for ( int i = 0; i < fFields.length; i++ )
			fFields[i] = aFields[i];
		}

	/**
	* ctor generally called to create a record from data returned
	* from the server
	*/ 
	public DataRecord(String[] value )
		throws Exception
		{
		this();
		int num = value.length;
		if (num > fFields.length) num = fFields.length;
		for (int i = 0 ; i < num; i++ )
			fFields[i] = meta(i).parse(value[i]);
		}

	public final String pack(String sep)
		{
		StringBuilder out = new StringBuilder();
		for ( int i = 0; i < fFields.length; i++ )
			out.append(sep)
			   .append((fFields[i]==null)?"":fFields[i].csvRepresentation());
		return out.substring(sep.length());
		}

	@Override public String toString() {
		String it = getClass().getSimpleName();
		for ( int i = 0; i < fFields.length; i++ )
			it += " " + meta(i).label() + ": " + fFields[i];
		return it;
		}

	/**
	* For development only: Pops up a dialog showing all the field values.
	*/
	public void dump( String title )
		{
		int num = numFields();
		String msg = "<HTML>";
		for ( int i = 0; i < num; i++ )
			msg += "<B>" + meta(i).label() + ":</B> " + fFields[i] + "<BR>";
		com.shanebow.ui.SBDialog.error(title, msg);
		}

	public final void log(String msg)
		{
		String m = getClass().getSimpleName() + "> " + msg;
		SBLog.write(m);
		System.out.println(m);
		}
	}
