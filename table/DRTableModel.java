package com.shanebow.dao.table;
/********************************************************************
* @(#)DRTableModel.java 1.00 20100524
* Copyright © 2015 by Richard T. Salamone, Jr. All rights reserved.
*
* DRTableModel: Extends AbstractTableModel to display DataRecords
* in a JTable with save support. Since it also implements,
* SBTableModel this model can be used with SBTable.
*
* @author Rick Salamone
* @version 1.00
* 20150910 rts created
*******************************************************/
import com.shanebow.dao.*;
import com.shanebow.ui.SBTableModel;
import com.shanebow.ui.table.AbstractSavableTableModel;
import com.shanebow.util.SBProperties;

public class DRTableModel<Record extends DataRecord>
	extends AbstractSavableTableModel
	implements SBTableModel<Record>
	{
	protected Record fBlank;
	protected int[] fFields; // the Record fields displayed in the columns
	protected DataRecordList<Record> fRows;

	public DRTableModel(Record aBlank)
		{
		super();
		fBlank = aBlank;
		int nFields = aBlank.numFields();
		System.out.println("DRTableModel item class: " + aBlank.getClass().getSimpleName());
		fFields = new int[nFields];
		for (int i = 0; i < nFields; i++)
			fFields[i] = i;
		}

	public DRTableModel(Record aBlank, int[] aFields)
		{
		super();
		fBlank = aBlank;
		fFields = aFields;
		}

	public void setFields(int[] aFields)
		{
		fFields = aFields;
		fireTableStructureChanged();
		}

	public final void reset(DataRecordList<Record> aList)
		{
		fRows = aList;
		fireTableDataChanged();
		}

	public void add( int row, Record aRecord )
		{
		fRows.add(row, aRecord);
		fireTableRowsInserted(row, row);
		}

	public void add( Record aRecord )
		{
		add ( fRows.size(), aRecord );
		}

	public void update( Record aRecord)
		{
		int index = fRows.indexOf(aRecord);
		if (index >= 0) {
			fRows.set(index, aRecord);
			fireTableRowsUpdated(index,index);
			}
		else add ( fRows.size(), aRecord);
		}

	public int indexOf(Record aRecord) { return fRows.indexOf(aRecord); }

	public DataRecordList<Record> getList() { return fRows.copy(); }

	public Record get(int row) { return fRows.get(row); }

	public Record set(int row, Record aRecord)
		{
		Record it = fRows.set(row, aRecord);
		fireTableRowsUpdated(row, row);
		return it;
		}

	public Record remove(Record aRecord)
		{
		int row = indexOf(aRecord);
		return (row < 0)? null : remove(row);
		}

	public Record remove(int row)
		{
		Record aRecord = fRows.remove(row);
		fireTableRowsDeleted(row, row);
		return aRecord;
		}

	@Override public int getColumnCount() { return fFields.length; }

	@Override public String getColumnName(int c) {
		return fBlank.meta(fFields[c]).label();
		}

	@Override public Class getColumnClass(int c) {
		return fBlank.meta(fFields[c]).getFieldClass();
		}

	@Override public boolean isCellEditable(int r, int c) { return false; }
	@Override public int getRowCount() { return (fRows==null)? 0 : fRows.size(); }

	@Override public Object getValueAt(int r, int c) {
		return getValue(get(r), fFields[c]);
		}

	protected Object getValue(Record aRecord, int field) {
		return aRecord.get(field);
		}
	}
