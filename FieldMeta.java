package com.shanebow.dao;
/********************************************************************
* @(#)CallFields.java	1.00 10/04/07
* Copyright © 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* FieldMeta: Meta data for a data field
*
* @version 1.00 04/07/10
* @author Rick Salamone
* 20100522 rts finalied field names & csv order for version 1
* 20100525 rts added CSV_HEADER and csvSplit to handle csv files
* 20100622 rts added read method to handle field ResultSet data
* 20100626 rts finalied parse() method to return DataField
* 20100627 rts added combobox type editors for source, country, region
* 20101108 rts added added accessor methods, member data private final
* 20110212 rts added field number for use in preferences editing
*******************************************************/
import javax.swing.JComponent;
import javax.swing.JLabel;

public final class FieldMeta
	implements MetaConstants
	{
	private final int m_fieldNumber;
	private final int m_flags;
	private final String m_label;
	private final String m_dbFieldName;
	private final Class<? extends DataField> m_class;
	private final Class<? extends JComponent> m_editorClass;
	private final String m_toolTip;

	public FieldMeta( int fieldNumber, String label, String dbName,
		Class<? extends DataField> aClass,
		Class<? extends JComponent> editor, String toolTip )
		{
		this(fieldNumber, label, dbName, aClass, 0, editor, toolTip );
		}

	public FieldMeta( int fieldNumber, String label, String dbName,
		Class<? extends DataField> aClass, int flags,
		Class<? extends JComponent> editor, String toolTip )
		{
		m_fieldNumber = fieldNumber;
		m_label = label;
		m_dbFieldName = dbName;
		m_class = aClass;
		m_flags = flags;
		m_editorClass = editor;
		m_toolTip = toolTip;
		}

	public final DataField parse(String value)
		throws Exception
		{
		return (DataField)m_class.getMethod("parse", String.class).invoke(null, value);
		}

	public final Class<? extends DataField> getFieldClass() { return m_class; }
	public final Class<? extends JComponent> getEditorClass() { return m_editorClass; }

	public final JComponent editor()
		{
		JComponent it = null;
		try { it = m_editorClass.newInstance(); }
		catch(Throwable e) { System.err.println(m_label + " " + e + " cause: " + e.getCause()); }
		it.setToolTipText(m_toolTip);
		it.setName(m_label);
		return it;
		}

	public final int fieldNumber(){ return m_fieldNumber; }
	public final String label()   { return m_label; }
	public final String toolTip() { return m_toolTip; }
	public final String dbFieldName() { return m_dbFieldName; }
	@Override public final String toString() { return m_label; }

	public final String flagNames() {
		String it = "";
		for (int b = 0, f = m_flags; f != 0; b++, f >>>= 1 )
			if ((f & 1) == 1) it += " " + META_FLAG_NAMES[b];
		return it;
		}

	public final boolean isSet(int flag) { return (m_flags & flag) == flag; }
	}
