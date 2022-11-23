package com.shanebow.dao.schema;
/********************************************************************
* @(#)DBField.java 1.00 20150916
* Copyright © 2015 by Richard T. Salamone, Jr. All rights reserved.
*
* DBField: Encapulates the meta data for a field in a database
* table definition. The meta data specifies the the type of data,
* an editor, a label, and a tooltip. Each DBField is created as
* a child of a DBTable and so, the constructor is package private.
*
* @version 1.00
* @author Rick Salamone
* 20150916 rts created
*******************************************************/
import  com.shanebow.dao.*;

class DBField
	implements DBNode
	{
	private int fIndex;
	FieldMeta fMeta;

	DBField(int index, FieldMeta aMeta) {
		fIndex = index;
		fMeta = aMeta;
		}
	public Object[] getChildren() { return null; }
	public DBNode getChild(int index) { return null; }
	public int getChildCount() {return 0;}
	public Object getColumn(int col) {
		switch(col) {
			case 0: return fMeta.dbFieldName();
			case 1: return fMeta.getFieldClass().getSimpleName();
			case 2: return fMeta.getEditorClass().getSimpleName();
			case 3: return fMeta.toolTip();
			case 4: return fMeta.flagNames();

//			case 3: return new Date(file.lastModified());
			}
		return null;
		}
	public String toString() {return "" + fIndex + " " + fMeta.label();}
	}
