package com.shanebow.dao.table;
/********************************************************************
* @(#)SortableTableModel.java 1.00 20110513
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* SortableTableModel: Interface to mark a table model as sortable for
* use with DTable. When a DFTable is instantiated with a SortableTableModel,
* it sets up a mouse listener on the table column headers to invoke the
* sort method.
*
* @author Rick Salamone
* @version 1.00, 20110513 rts initial version
*******************************************************/

public interface SortableTableModel
	{
	public void sort(final int aSortColumn, final boolean aIsAscending );
	}
