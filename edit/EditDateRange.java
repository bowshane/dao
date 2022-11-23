package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditDateRange.java 1.00 20101005
* Copyright © 2010-2015 by Richard T. Salamone, Jr. All rights reserved.
*
* EditDateRange: Friendly panel to specify common date ranges.
*
* @author Rick Salamone
* @version 1.09
* 20101005 rts created
* 20101009 rts added choices anytime, yesterday, mtd, ytd
* 20101101 rts modified for new date spinner editor
* 20101031 rts added constructor that accepts format string
* 20101104 rts removed choice for anything
* 20101105 rts implemented prop change for date range change
* 20101121 rts support for vert orient and auto resize
* 20110117 rts handles setEnabled
* 20110309 rts fixed bug in display of extra date for between
* 20110403 rts added choices wtd, last month
* 20121209 rts need to call recalculate from getDateRange
* 20150816 rts added getSettings & restore
* 20151028 rts added choices before, after
*******************************************************/
import com.shanebow.ui.calendar.MonthCalendar;
import com.shanebow.ui.SBDialog;
import com.shanebow.util.SBDate;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.*;
import java.util.*;

public class EditDateRange extends JPanel
	implements ActionListener, PropertyChangeListener
	{
	public static final int HORIZONTAL=0;
	public static final int VERTICAL=1;
	private static final String DATE_FORMAT="MM/dd/yy";
	private static final String[] RANGE_CHOICES
		= { "anytime", "today", "yesterday", "on", "before", "after", "WTD", "MTD", "YTD", "last month", "between" };
//		= { "anytime", "today", "yesterday", "on", "WTD", "MTD", "YTD", "last month" };
	private static final int ANYTIME = 0;
	private static final int TODAY = 1;
	private static final int YESTERDAY = 2;
	private static final int ON_DATE = 3;
	private static final int BEFORE = 4;
	private static final int AFTER = 5;
	private static final int WTD = 6;
	private static final int MTD = 7;
	private static final int YTD = 8;
	private static final int LAST_MONTH = 9;
	private static final int BETWEEN = 10;

	private final JComboBox cbChoices = new JComboBox(RANGE_CHOICES);
	private final EditWhenSpinner[] tfDate = new EditWhenSpinner[2];
	private long[] m_range = new long[2]; // cache range for prop changes
	private int    m_orient;

	private String m_maxDate = null;

	public EditDateRange() { this( DATE_FORMAT, HORIZONTAL ); }
	public EditDateRange(int orient) { this ( DATE_FORMAT, orient ); }
	public EditDateRange(String format) { this ( format, HORIZONTAL ); }

	public EditDateRange( String dateFormat, int orient )
		{
		super();
		LayoutManager layout;
		if (orient == HORIZONTAL)
			layout = new FlowLayout(FlowLayout.RIGHT,3,0);
//			layout = new BoxLayout(this,BoxLayout.X_AXIS);
		else
			layout = new GridLayout(3,1,0,3);
		setLayout(layout);
		m_orient = orient;
		add( cbChoices );
		cbChoices.setPrototypeDisplayValue("yesterday");
		cbChoices.setPreferredSize(new Dimension(80,23));
		cbChoices.addActionListener(this);
		String yyyymmdd = SBDate.yyyymmdd();
		m_range[0] = SBDate.toTime( yyyymmdd + "  00:00:00" );
		m_range[1] = SBDate.toTime( yyyymmdd + "  23:59:59" );
		for ( int i = 0; i < 2; i++ )
			{
			add(tfDate[i] = new EditWhenSpinner(dateFormat));
			tfDate[i].setTime(m_range[i]);
			tfDate[i].addPropertyChangeListener(this);
			}
		cbChoices.setSelectedIndex(ANYTIME);
		doRangeChoice(ANYTIME);
		}

	@Override public void setEnabled(boolean enabled)
		{
		Component[] children = getComponents();
		for ( Component child : children )
			child.setEnabled(enabled);
		}

	@Override public void actionPerformed( ActionEvent e )
		{
		if ( !e.getSource().equals(cbChoices))
			return;
		doRangeChoice ( cbChoices.getSelectedIndex());
		}

	private void doRangeChoice ( int choiceIndex )
		{
		recalculate();
		switch( choiceIndex )
			{
			default:          cbChoices.setSelectedIndex(TODAY);
			case ANYTIME:     // fall thru
			case WTD:
			case MTD:
			case YTD:
			case YESTERDAY:
			case LAST_MONTH:
			case TODAY:       tfDate[0].setVisible(false);
			                  tfDate[1].setVisible(false);
sizeEm(84,84);
			                  break;

			case BEFORE:
			case AFTER:
			case ON_DATE:     tfDate[1].setVisible(false);
			                  tfDate[0].setVisible(true);
sizeEm(70,100);
			                  break;

			case BETWEEN:     tfDate[0].setVisible(true);
			                  tfDate[1].setVisible(true);
sizeEm(60,100);
			                  break;
			}
		if (m_orient == VERTICAL) return;
		try {getParent().doLayout();}
		catch (Exception e) {} // System.out.println("Bingo EDR error line: 143 " + e); }
		validate();
		repaint();
	//	System.out.println("outbound size " + getSize()
	//	 + "\n  cbox: " + cbChoices.getSize() + "\n  date: " + tfDate[0].getSize());
		}

	private void sizeEm(int cboxWidth, int dateWidth) {
		if (m_orient == VERTICAL) return;
		Dimension d = cbChoices.getPreferredSize();
		d.width = cboxWidth;
		cbChoices.setPreferredSize(d);
		}

	public void propertyChange(PropertyChangeEvent evt)
		{
		String prop = evt.getPropertyName();
		long[] old = { m_range[0], m_range[1] };
		if ( prop.equals(MonthCalendar.TIMECHANGED_PROPERTY_NAME))
			{
			Long time = (Long)evt.getNewValue();
			if ( time == m_range[0] )
				return;
			tfDate[0].setTime( m_range[0] = time );
			firePropertyChange(MonthCalendar.RANGECHANGED_PROPERTY_NAME, old, m_range );
			if ( cbChoices.getSelectedIndex() == BETWEEN )
				return;
			cbChoices.setSelectedIndex(ON_DATE);
			doRangeChoice(ON_DATE);
			}
		else if ( prop.equals(MonthCalendar.RANGECHANGED_PROPERTY_NAME))
			{
			long[] time = (long[])evt.getNewValue();
			if ( time[0] != old[0] || time[1] != old[1] )
				return;
			tfDate[0].setTime( m_range[0] = time[0] );
			tfDate[1].setTime( m_range[1] = time[1] );
			cbChoices.setSelectedIndex(BETWEEN);
			doRangeChoice(BETWEEN);
			firePropertyChange(prop, old, m_range );
			}
		}

	public void setMaxDate(String yyyymmdd)
		{
		m_maxDate = yyyymmdd;
		}

	public long[] getDateRange() {
		recalculate();
		int index = cbChoices.getSelectedIndex();
		return (index==ANYTIME)? null : m_range;
		}

	public String getCriteria() {
		recalculate();
		int index = cbChoices.getSelectedIndex();
		return (index==ANYTIME)? ""
		     : (index==BEFORE)? "<" + m_range[0]
		     : (index==AFTER)? ">" + m_range[1]
		     : "BETWEEN " + m_range[0] + " AND " + m_range[1];
		}

	public String getSettings() {
		return cbChoices.getSelectedIndex() + "," + m_range[0] + "," + m_range[1];
		}

	public void restore(String settings) {
		String[] pieces = settings.split(",");
			m_range[0] = Long.parseLong(pieces[1]);
			m_range[1] = Long.parseLong(pieces[2]);
		try
			{
			tfDate[0].setTime( m_range[0]);
			tfDate[1].setTime( m_range[1]);
			}
		catch(Exception ignore){}
		cbChoices.setSelectedIndex(Integer.parseInt(pieces[0]));
		recalculate();
		}

	private void recalculate()
		{
		long[] old = { m_range[0], m_range[1] };
		switch( cbChoices.getSelectedIndex())
			{
			case ANYTIME:     m_range[0] = m_range[1] = 0;
			                  return;

			default:
			case TODAY:       m_range[0] = m_range[1] = SBDate.timeNow();
			                  break;

			case YESTERDAY:   m_range[0] = m_range[1] = SBDate.timeNow() - (24*60*60);
			                  break;

			case ON_DATE:
			case BEFORE:
			case AFTER:    	m_range[0] = tfDate[0].getTime();
								if (m_range[0] == 0) m_range[0] = SBDate.timeNow();
								m_range[1] = m_range[0];
			                  break;

			case BETWEEN:     m_range[0] = tfDate[0].getTime();
			                  m_range[1] = tfDate[1].getTime();
			                  break;

			case WTD:         m_range[0] = SBDate.toTime(
			                             SBDate.yyyymmdd().substring(0,6) + "01  00:00:00");
			                  m_range[1] = SBDate.timeNow();
			                  int dow = SBDate.dayOfWeek(m_range[1]) - 1;
			                  m_range[0] = SBDate.adjust(m_range[1] - (dow *24*60*60), 0, 0);
			                  break;

			case MTD:         m_range[0] = SBDate.toTime(
			                             SBDate.yyyymmdd().substring(0,6) + "01  00:00:00");
			                  m_range[1] = SBDate.timeNow();
			                  break;

			case LAST_MONTH:  m_range[1] = SBDate.toTime(
			                      SBDate.yyyymmdd().substring(0,6) + "01  00:00:00") - 1;
			                  m_range[0] = SBDate.toTime(
			                      SBDate.yyyymmdd(m_range[1]).substring(0,6) + "01  00:00:00");
			                  break;

			case YTD:         m_range[0] = SBDate.toTime(
			                             SBDate.yyyymmdd().substring(0,4) + "0101  00:00:00");
			                  m_range[1] = SBDate.timeNow();
			                  break;
			}
		
		if ( m_range[1] < m_range[0] )
			{
			long temp = m_range[0];
			m_range[0] = m_range[1];
			m_range[1] = temp;
			}
		try{
			m_range[0] = SBDate.toTime(SBDate.yyyymmdd(m_range[0]) + "  00:00:00");
			m_range[1] = SBDate.toTime(SBDate.yyyymmdd(m_range[1]) + "  23:59:59");
			}
		catch (Exception e) { System.out.println("EDR SBDate failed"); }
		if ( m_range[0] != old[0] || m_range[1] != old[1] )
			firePropertyChange(MonthCalendar.RANGECHANGED_PROPERTY_NAME, old, m_range );
		}
	}
