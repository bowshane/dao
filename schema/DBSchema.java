package com.shanebow.dao.schema;
/********************************************************************
* @(#)DBSchema.java 1.00 20150916
* Copyright © 2015 by Richard T. Salamone, Jr. All rights reserved.
*
* DBSchema: A collection of DBTables that make up the database of
* an application. More specifically, it is the root of the database
* definition hierarchy: schema -> tables -> fields.
*
* @version 1.00
* @author Rick Salamone
* 20150916 rts created
*******************************************************/
import  com.shanebow.dao.*;
import java.util.Vector;

public class DBSchema
	extends Vector<DBTable>
	implements DBNode
	{
	private String prefix; // = "thm";
	public DBSchema() { this(""); }
	public DBSchema(String prefix) { this.prefix = prefix; }

	public void setTblPrefix(String prefix){
		this.prefix = prefix;
		}

	public String getTblPrefix(){ return prefix; }

	public void register(int tableID, String aName, DataRecord aBlank) {
		add(new DBTable(tableID, aName,  aBlank));
		}

	public <R extends DataRecord> int idTable(Class<R> aClass) {
		for (DBTable table : this)
			if (table.getRecordClass() == aClass) return table.id();
		return 0;
		}

/***
	public <R extends DataRecord> void register(int tableID, String aName, Class<R> aClass) {
		add(new DBTable(tableID, aName,  new com.shanebow.web.users.Raw()));
		fRegisteredClasses.add(aClass);
		}
***/

	public Object[] getChildren() { return this.toArray(); }
	public DBTable getChild(int index) { return get(index); }
	public int getChildCount() {return size(); }
	public Object getColumn(int col) {return null;}
	public String toString() {return prefix;}
	}
