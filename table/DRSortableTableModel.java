package com.shanebow.dao.table;
/********************************************************************
* @(#)DRSortableTableModel.java 1.00 20150728
* Copyright © 2015 by Richard T. Salamone, Jr. All rights reserved.
*
* DRSortableTableModel: Extends DRTableModel with support for
* sorting the model data.
*
* @author Rick Salamone
* @version 1.00
* 20150728 rts created
*******************************************************/
import com.shanebow.dao.DataRecord;
import com.shanebow.dao.table.DRTableModel;
import com.shanebow.dao.table.SortableTableModel;
import java.util.Collections;
import java.util.Comparator;

public class DRSortableTableModel<Record extends DataRecord>
	extends DRTableModel<Record>
	implements SortableTableModel
	{
	public DRSortableTableModel(Record aBlank)
		{
		super(aBlank);
		}

	public DRSortableTableModel(Record aBlank, int[] aFields)
		{
		super(aBlank, aFields);
		}

	@SuppressWarnings("unchecked") // we explicitly check "isAssignableFrom
	public void sort(final int aSortColumn, final boolean aIsAscending )
		{
		final int ascend = aIsAscending? 1 : -1;
		final int sortField = fFields[aSortColumn];
		Class sortClass = fBlank.meta(sortField).getFieldClass();
		if ( Comparable.class.isAssignableFrom(sortClass))
			Collections.sort(fRows,new Comparator<Record>()
				{
				public int compare(Record r1, Record r2)
					{
					try
						{
						Comparable c1 = (Comparable)r1.get(sortField);
						Comparable c2 = (Comparable)r2.get(sortField);
						return ascend * c1.compareTo(c2);
						}
					catch (Exception e) { return 0; }
					}
				});
		else
			Collections.sort(fRows,new Comparator<Record>()
				{
				public int compare(Record r1, Record r2)
					{
					return ascend
				       * r1.get(sortField).toString().compareTo(r2.get(sortField).toString());
					}
				});
		fireTableDataChanged();
		}
	}
