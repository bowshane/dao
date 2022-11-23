package com.shanebow.dao.table;
/********************************************************************
* @(#)CellMeta.java	1.00 10/04/07
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* CellMeta: Table p
*
* @author Rick Salamone
*******************************************************/
import com.shanebow.dao.*;
import com.shanebow.dao.edit.*;
import javax.swing.JComponent;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.*;

public final class CellMeta
	{
	private final Class fieldClass;
	private final Class<? extends TableCellRenderer> rendererClass;
	private final Class<? extends JComponent> editorClass;
	private final int preferredWidth;

	public CellMeta( Class fieldClass,
	                  Class<? extends JComponent> editorClass,
	                  Class<? extends TableCellRenderer> rendererClass,
	                  int preferredWidth )
		{
		this.fieldClass = fieldClass;
		this.rendererClass = rendererClass;
		this.editorClass = editorClass;
		this.preferredWidth = preferredWidth;
		}

	public Class getFieldClass() { return fieldClass; }

	public TableCellRenderer getRenderer()
		{
		try { return (rendererClass == null)? null : rendererClass.newInstance(); }
		catch (Exception e) { return null; }
		}

	public TableCellEditor getCellEditor()
		{
		JComponent comp = getEditor();
		if ( comp == null )
			return null;

		if ( comp instanceof TableCellEditor )
			return (TableCellEditor)comp;

		if ( comp instanceof JTextField )
			return new DefaultCellEditor((JTextField)comp);

		if ( comp instanceof JComboBox )
			return new DefaultCellEditor((JComboBox)comp);

		if ( comp instanceof JCheckBox )
			return new DefaultCellEditor((JCheckBox)comp);
		return null;
		}

	public JComponent getEditor()
		{
		if ( editorClass == null )
			return null;
		JComponent it = null;
		try { it = editorClass.newInstance(); }
		catch(Throwable e) { com.shanebow.util.SBLog.write("CellMeta: " + e); }
		return it;
		}
	}
