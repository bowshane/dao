package com.shanebow.dao.edit;
/********************************************************************
* @(#)DateTimePanel.java	1.00 10/08/01
* Copyright (c) 2010-2011 by Richard T. Salamone, Jr. All rights reserved.
*
* DateTimePanel: Presents a calendar interface to prompt for a date
* and time. Notifies listeners of changes via property changes.
*
* @author Rick Salamone
* @version 1.00, 20100801 rts created based on some old code laying around
* @version 1.01, 20101025 rts cleaned up, added time panel, repackaged
* @version 2.00, 20101031 rts time panel uses friendly date spinner
* @version 2.01, 20101101 rts time panel uses EditWhen spinner
* @version 2.02, 20110418 rts time panel uses comboxbox & am/pm selector
*******************************************************/
import com.shanebow.ui.calendar.MonthCalendar;
import com.shanebow.ui.LAF;
import com.shanebow.ui.SBRadioPanel;
import com.shanebow.util.SBDate; 
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.*;

public final class DateTimePanel
	extends JPanel
	implements PropertyChangeListener
	{
	private final boolean USE_SPINNER = false;

	private final MonthCalendar  calendar;
	private final CBTime         fcbTime = new CBTime();

	public DateTimePanel()
		{
		super( new BorderLayout());

		calendar = new MonthCalendar();
		calendar.addPropertyChangeListener(MonthCalendar.TIMECHANGED_PROPERTY_NAME, this);
		calendar.setPreferredSize(new Dimension(200,180));

		//Add Components to this panel
		add(calendar, BorderLayout.CENTER);
		add(fcbTime, BorderLayout.PAGE_END);
		}

	@Override public void addPropertyChangeListener(String aProp, PropertyChangeListener aPCL)
		{
		super.addPropertyChangeListener(aProp, aPCL);
		if ( aProp.equals(MonthCalendar.TIMECHANGED_PROPERTY_NAME))
			{
			calendar.addPropertyChangeListener(MonthCalendar.TIMECHANGED_PROPERTY_NAME, aPCL);
			}
		}

	public void propertyChange(PropertyChangeEvent evt)
		{
		if ( !evt.getPropertyName().equals(MonthCalendar.TIMECHANGED_PROPERTY_NAME))
			return;
		Long time = (Long)evt.getNewValue();

		if ( !evt.getSource().equals(calendar))
			calendar.setTime(time);
		if ( !evt.getSource().equals(fcbTime))
			fcbTime.setTime(time);
		}

	public void setTime() { setTime(SBDate.adjust(SBDate.timeNow(), 0, 0 )); }
	public void setTime(long time)
		{
		calendar.setTime(time);
		fcbTime.setTime(time);
		}

	public long getTime()
		{
		String yyyymmdd__hhmm = calendar.toString().substring(0,8)
			                      + "  " + fcbTime.toString();
// System.out.println("getTime: '" + yyyymmdd__hhmm + "'");
		return SBDate.toTime(yyyymmdd__hhmm);
		}
	}

final class CBTime
	extends JPanel
	{
	private static final String[] AM_PM = { "am", "pm" };
	private static final String[] TIMES =
		{
		"12:00", "12:30",
		"1:00", "1:30",
		"2:00", "2:30",
		"3:00", "3:30",
		"4:00", "4:30",
		"5:00", "5:30",
		"6:00", "6:30",
		"7:00", "7:30",
		"8:00", "8:30",
		"9:00", "9:30",
		"10:00", "10:30",
		"11:00", "11:30",
		};

	private final JComboBox cbHHMM = new JComboBox(TIMES);
	private final SBRadioPanel<String>  rgAMPM;

	public CBTime()
		{
		super(new BorderLayout());
		add( cbHHMM, BorderLayout.WEST );
		rgAMPM = new SBRadioPanel<String>( 1,0, AM_PM );
		add( rgAMPM, BorderLayout.EAST );
		}

	public void setTime(long time)
		{
		String[] hhmm = SBDate.hhmm(time).split(":");
//	System.out.println( "Set to: " + SBDate.yyyymmdd__hhmmss(time) + " hhmm: " + hhmm[0] + ":" + hhmm[1] );
		int hour = Integer.parseInt(hhmm[0]);
		if ( hour > 11 )
			{
			hour -= 12;
			rgAMPM.select(AM_PM[1]);
			}
		else
			rgAMPM.select(AM_PM[0]);
		if ( hour == 0 ) hour = 12;
		int min = Integer.parseInt(hhmm[1]) % 30;
		String timeString = "" + hour + ":" + pad(min);
		cbHHMM.setSelectedItem(timeString);
		}

	private final String pad(int x) { return ((x < 10)? "0" : "") + x; }

	@Override public String toString()
		{
		boolean pm = ((String)rgAMPM.getSelected()).equals(AM_PM[1]);
		String[] hhmm = ((String)cbHHMM.getSelectedItem()).split(":");
		int hour = Integer.parseInt(hhmm[0]);
		if ( hour == 12 ) hour = 0;
		if ( pm ) hour += 12;
		int min = Integer.parseInt(hhmm[1]);
		return pad(hour) + ":" + pad(min);
		}
	}
