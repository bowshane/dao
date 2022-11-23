package com.shanebow.dao.schema;
/********************************************************************
* @(#)DBTable.java 1.00 20150916
* Copyright © 2015 by Richard T. Salamone, Jr. All rights reserved.
*
* DBTable: A database table definition comprising a set of field
* definitions, a unique id, a name and some utility routines to
* facilitate reflection. Each DBTable is created as a child of the
* database, thus the constructor is package private.
*
* @version 1.00
* @author Rick Salamone
* 20150916 rts created
*******************************************************/
import  com.shanebow.dao.*;
import java.util.Vector;

public final class DBTable
	extends Vector<DBField>
	implements DBNode
	{
	private final int fID;
	public String name;
	private final DataRecord fBlankRecord;

	DBTable(int aID, String aTableName, DataRecord aBlank) {
		fID = aID;
		name = aTableName;
		fBlankRecord = aBlank;
		int nflds = aBlank.numFields();
		for (int f=0; f < nflds; f++)
			add(new DBField(f, aBlank.meta(f)));
		}

	public <R extends DataRecord>  DataRecordList<R> emptyRecordList(String data, String sep) {
		return new DataRecordList<R>(0);
		}

	@SuppressWarnings("unchecked")
	public <R extends DataRecord>  DataRecordList<R> buildRecordList(String data, String sep) {
		Class<R> klass = getRecordClass();
		return new DataRecordList<R>(klass, data, sep);
		}
//	<R extends DataRecord> Class<R> getRecordClass2() {return fBlankRecord.getClass(); }

	Class getRecordClass() {return fBlankRecord.getClass(); }
//	<R extends DataRecord> Class<R> getRecordClass() {return fBlankRecord.getClass(); }

	public int id() { return fID; }
	public DataRecord blank() { return fBlankRecord; }
	public FieldMeta meta(int field) { return fBlankRecord.meta(field); }
	public final String name() { return name; }

	public Object[] getChildren() { return this.toArray(); }
	public DBField getChild(int index) { return get(index); }
	public int getChildCount() {return size(); }
	public String getColumn(int col) { return (col==1)? fBlankRecord.getClass().getSimpleName() : null; }
	public String toString() {return "" + (fID*10) + " " + name;}
	}
