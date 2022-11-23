package com.shanebow.dao.table;
/********************************************************************
* @(#)DFTable.java	1.00 10/05/27
* Copyright © 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* DFTable: Extends JTable to use renders and editors for DataField
* objects where possible. This is sufficient for most tables displaying
* DataField items, but may have to provide some additional renderers.
* This class also provides support for sorting and persistent configuration.
*
* Sorting: If the the model implements the SortableTableModel interface,
* DFTable will add a mouse listener to the column headers that calls the
* model's sort method.
*
* @author Rick Salamone
* @version 1.00 20101104 rts created from various apo apps
* @version 1.01 20110212 rts added Qty cell type
* @version 1.02 20110212 rts added makeConfigurable()
* @version 1.03 20110507 rts added setEditor()
* @version 1.04 20110513 rts added support for sorting
*******************************************************/
import com.shanebow.dao.*;
import com.shanebow.dao.edit.*;
import com.shanebow.util.SBLog;
import com.shanebow.ui.LAF;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.*;

public class DFTable
	extends JTable
	{
	public  static final Font FONT = new Font("SansSerif", Font.BOLD, 14);
	private static final CellMeta[] cellTypes =
		{
		new CellMeta(PhoneNumber.class, EditPhone.class, PhoneCellRenderer.class, 10 ),
		new CellMeta(When.class,        WhenCellEditor.class, null, 10 ),
		new CellMeta(Country.class,     EditCountry.class, null, 10 ),
		new CellMeta(ContactID.class,   EditContactID.class, AlignRightRenderer.class, 10 ),
		new CellMeta(ContactName.class, EditName.class, null, 10 ),
		new CellMeta(Position.class,    EditPosition.class, null, 10 ),
		new CellMeta(Company.class,     EditCompany.class, null, 10 ),
		new CellMeta(EMailAddress.class,EditEMailAddress.class, null, 10 ),
		new CellMeta(Address.class,     EditAddress.class, null, 10 ),
		new CellMeta(ContactType.class, EditType.class, null, 10 ),
		new CellMeta(OrderID.class,     EditOrderID.class, AlignRightRenderer.class, 10 ),
		new CellMeta(CheckOutID.class,  EditCheckOutID.class, null, 10 ),
		new CellMeta(WebAddress.class,  EditWebAddress.class, null, 10 ),
		new CellMeta(Qty.class,         EditQty.class, QtyRenderer.class, 10 ),
		new CellMeta(PNL.class,         EditQty.class, PNLRenderer.class, 10 ),
		new CellMeta(USD.class,         EditQty.class, AlignRightRenderer.class, 10 ),
		};

//common contructor code
		{
		setFillsViewportHeight( true );

		// Set up to edit phone numbers
		JTextField tfPhone = new EditPhone();
		tfPhone.setBackground( java.awt.Color.YELLOW );
		tfPhone.setHorizontalAlignment(JTextField.RIGHT);
		setDefaultEditor(PhoneNumber.class, new DefaultCellEditor(tfPhone));

		// Set up to edit callback dates
		JTextField tfWhen = new WhenCellEditor(); // new EditWhen();
		tfWhen.setBackground( java.awt.Color.YELLOW );
		setDefaultEditor(When.class, new DefaultCellEditor(tfWhen));

		// Set up to edit countries
		JComboBox cbCountry = new EditCountry(); // new EditWhen();
		setDefaultEditor(Country.class, new DefaultCellEditor(cbCountry));

		// Set up renderers
		for ( CellMeta meta : cellTypes )
			{
			TableCellRenderer renderer = meta.getRenderer();
			if ( renderer != null )
				setDefaultRenderer( meta.getFieldClass(), renderer );
			}

		// Set up sorting on column headers
		if ( getModel() instanceof SortableTableModel )
			{
			System.out.println("DFTable: " + getClass().getSimpleName()
				+ "'s model implements SortableTableModel" );
			JTableHeader header = getTableHeader();
			header.setUpdateTableInRealTime(true);
			header.setReorderingAllowed(true);
			header.addMouseListener(new MouseAdapter() // to handle sorts
				{
				public void mouseClicked(MouseEvent e)
					{
					TableColumnModel colModel = getColumnModel();
					int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
					int sortColumn = colModel.getColumn(columnModelIndex).getModelIndex();
					int shiftPressed = e.getModifiers()&InputEvent.SHIFT_MASK;
					boolean ascending = (shiftPressed == 0);
	//				Raw wasSelected = fSelected;
					SortableTableModel model = (SortableTableModel)getModel();
					model.sort(sortColumn, ascending);
/*********
					@TODO: it would be nice if the selection were restored after the sort
					if ( wasSelected != null )
						{
						int index = model.indexOf(wasSelected);
						if ( index >= 0 )
							{
							setRowSelectionInterval(index,index);
							scrollRectToVisible( getCellRect(index, 0, true));
							}
						}
*********/
					} 
				});
			}
		}

	public DFTable(TableModel tm)
		{
		super(tm);
		}

	public final void setEditor( Class<? extends DataField> aClass,
		TableCellRenderer aRenderer, TableCellEditor aEditor )
		{
		if ( aRenderer instanceof Component )
			{
			int height = ((Component)aRenderer).getPreferredSize().height;
			if ( height > getRowHeight())
				setRowHeight(height);
			}
		setDefaultRenderer(aClass, aRenderer );
		setDefaultEditor(aClass, aEditor );
		}

	@Override public final void setFont(Font f)
		{
		super.setFont(f);
		if ( getRowHeight() != f.getSize() + 4 )
			setRowHeight( f.getSize() + 4 );
		TableCellEditor editor = getDefaultEditor(PhoneNumber.class);
		if ( editor instanceof DefaultCellEditor )
			((DefaultCellEditor)editor).getComponent().setFont(f);

		editor = getDefaultEditor(When.class);
		if ( editor instanceof DefaultCellEditor )
			((DefaultCellEditor)editor).getComponent().setFont(f);
		}

	protected final void log( String fmt, Object... args )
		{
		SBLog.write( toString(), String.format(fmt,args));
		}

	@Override public String toString() { return getClass().getSimpleName() + ": Unnamed"; }

	public void makeConfigurable()
		{
		if ( this instanceof ConfigurableTable )
			LAF.addPreferencesEditor(new TablePreferencesEditor((ConfigurableTable)this));
		else throw new IllegalStateException("Table doesn't implement Configurable");
		}

/*************
	public void setColumnWidths( int[] width )
		{
		TableColumnModel colModel = getColumnModel();
		int nCols = m_pageModel.getColumnCount();
		for ( int c = 0; c < nCols; c++ )
			{
			TableColumn tc = colModel.getColumn(c);
			tc.setPreferredWidth( width[c] );
			}
		}
*************/
	}
