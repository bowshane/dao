package com.shanebow.dao.edit;
/********************************************************************
* @(#)DFChkPanel.java	1.0 20150816
* Copyright © 2015 by Richard T. Salamone, Jr. All rights reserved.
*
* DFChkPanel: Check box selector for a bunch of DataField DataField.
*
* @author Rick Salamone
* @version 1.00
* 20150816 rts created
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.ui.SBChkPanel;
import javax.swing.JCheckBox;

public abstract class DFChkPanel
	extends SBChkPanel
	{
	public DFChkPanel( int rows, int cols, DataField[] aChoices )
		{
		super ( rows, cols, 0, 0, aChoices);
		}

	public DFChkPanel( int rows, int cols, int hgap, int vgap, DataField[] aChoices )
		{
		super ( rows, cols, hgap, vgap, aChoices);
		}

	abstract public DataField parse(String value) throws Exception;

	public static String getCriteria(DataField[] selected) {
		if (selected == null)
			return "";
		String csv = "";
		for ( DataField df : selected )
			csv += (csv.isEmpty()? "" : ",") + df.csvRepresentation();
		return (selected.length > 1)? ("IN (" + csv + ")") : ("=" + csv);
		}

	/**
	* @returns null IF ALL BOXES ARE SELECTED!!
	* otherwise returns an array selected DataField values
	*/
	public DataField[] getSelected()
		{
		int count = numSelected();
		if (count == fChkBoxes.length)
			return null;
		DataField[] selected = new DataField[count];
		int i = 0;
		for ( JCheckBox chk : fChkBoxes )
			if ( chk.isSelected())
				try { selected[i++] = parse( chk.getText()); }
				catch(Exception e) {} // should not be possible
		return selected;
		}
	}
