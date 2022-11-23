package com.shanebow.dao.edit;
/********************************************************************
* @(#)EditWhenSpinner.java	1.00 10/06/22
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* EditWhenSpinner: A component for editing the contact When field.
* Extends JTextField to implement FieldEditor.
*
* @author Rick Salamone
* @version 1.00 20100622 rts created as EditCallDate
* @version 1.01 20100820 rts modified for package reorganization
* @version 2.00 20101031 rts complete rewrite to extend JSpinner
* @version 2.01 20101031 rts added constructor that accepts format string
* @version 2.02 20101101 rts renamed to EditWhenSpinner to reflect when = time + date
* @version 2.03 20110309 rts fixed bug to check for null listener
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.shanebow.dao.When;
import com.shanebow.ui.calendar.MonthCalendar;
import com.shanebow.util.SBDate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

public class EditWhenSpinner extends JSpinner
	implements FieldEditor
	{
	public static final String DEFAULT_FORMAT = "MM/dd/yyyy";
//	public static final String DEFAULT_FORMAT = "MM/dd/yyyy HH:mm:ss";
	private final SpinnerDateModel dateModel;
	private long m_time;
	private ActionListener fActionListener;

	public EditWhenSpinner ()
		{
		this( DEFAULT_FORMAT );
		}

	public EditWhenSpinner ( String dateFormat )
		{
		super();
		Calendar calendar = Calendar.getInstance();
		Date initDate = calendar.getTime();
		calendar.add(Calendar.YEAR, -1);
		Date earliestDate = calendar.getTime();
		calendar.add(Calendar.YEAR, 5);
		Date latestDate = calendar.getTime();
		dateModel = new SpinnerDateModel(initDate, earliestDate, latestDate,
                                     Calendar.HOUR);//ignored for user input
		m_time = initDate.getTime() / 1000;
		setModel(dateModel);
		setEditor(new JSpinner.DateEditor(this, dateFormat));
		dateModel.addChangeListener( new ChangeListener()
			{
			public void stateChanged(ChangeEvent e)
				{
				long time = dateModel.getDate().getTime() / 1000;
				if ( time != m_time )
					{
					long oldTime = m_time;
					m_time = time;
					firePropertyChange(
						MonthCalendar.TIMECHANGED_PROPERTY_NAME, oldTime, m_time );
					if ( fActionListener != null )
						fActionListener.actionPerformed(
							new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "" + m_time));
					}
				}
			});
		}

	@Override public void addActionListener(ActionListener al)
		{
		if ( fActionListener != null )
			com.shanebow.ui.SBDialog.fatalError("Only one ActionListener supported");
		fActionListener = al;
		}

	@Override public Dimension getPreferredSize()
		{ return new Dimension(80, super.getPreferredSize().height); }

	public long getTime()
		{
		try { commitEdit(); }
		catch (ParseException e) {}
		return m_time;
		}

	public void setTime(long time)
		{
		if ( time == m_time ) return;
		m_time = time;
		dateModel.setValue( (time==0)? 0 : new Date(time * 1000));
		}

	// implement FieldEditor
	public void clear() {}

	public void set(String text)
		{
		try
			{
System.out.println("EditWhenSpinner.setTime text: " + text);
//	com.shanebow.util.SBLog.write( "setting when to: " + text );
			long time = When._parse(text);
			setTime((time == 0) ? SBDate.timeNow() : time );
			}
		catch ( Exception e )
			{
			com.shanebow.util.SBLog.write( "error setting calldate to: " + text
				+ "\n" + e.getMessage());
			}
		}

	public void set(DataField cd)
		{
		long time = ((When)cd).getLong();
		setTime((time == 0) ? SBDate.timeNow() : time );
		}

	public When get()
		throws DataFieldException
		{
		try { commitEdit(); }
		catch (ParseException pe) { throw new DataFieldException(pe); }
		return new When(m_time);
		}

	public boolean isEmpty() { return m_time <= SBDate.timeNow(); }
	}

/*************************
    class CCW_SpinnerNumberModel extends SpinnerNumberModel
    {
        private boolean valid ;

        public CCW_SpinnerNumberModel()
        {
            super();
            valid = false;
        }

        public void setValue(Object val)
        {
            if (val != null)
            {
                try
                {
                    Long value = new Long(val.toString());
                    valid=true;
                    super.setValue(value);
                    return;
                }
                catch (Exception e){}
            }
            valid = false;
        }

        public Object getValue()
        {
            if (valid)
            {
                return super.getValue();
            }
            else
            {
                return null;
            }
        }

        public Object getNextValue()
        {
            if (!valid )
            {
                Long p =(Long) getNextSmallest(new Long(Long.MIN_VALUE));
                if (p.longValue() ==  Long.MIN_VALUE+1 )
                {
                    p = new Long(0);
                }
                return p;
            }

            Long nextValue =Long.decode( super.getNextValue().toString());
            boolean isValid = isValid(nextValue);
            if (isValid)
            {
                return nextValue;
            }
            else
            {
                Long nextSmallest =getNextSmallest(nextValue);
                if (nextSmallest == null)
                {
                   return getNextSmallest(new Long(Long.MIN_VALUE));
                }
                else
                {
                   return nextSmallest;
                }
            }
        }

        public Object getPreviousValue()
        {
            if (!valid )
            {
                Long p =(Long) getPreviousBiggest(new Long(Long.MAX_VALUE));
                if (p.longValue() ==  Long.MAX_VALUE-1)
                {
                    p = new Long(0);
                }
                return p;
            }
            Long prevValue = Long.decode(super.getPreviousValue().toString());
            boolean isValid = isValid(prevValue);
            if (isValid)
            {
                return prevValue;
            }
            else
            {
                Long prevBiggest = getPreviousBiggest(prevValue);
                if ( prevBiggest == null)
                {
                   return getPreviousBiggest(new Long(Long.MAX_VALUE));
                }
                else
                {
                   return prevBiggest;
                }
            }
        }
    }

    // you need write your own  getPreviousBiggest() getNextSmallest()
    // function this is just an example:
 
    public Long getPreviousBiggest(Long value)
    {       
        CCW_Integer_IDL integer=getTypeInf().thisType.integer();
        long current = value.longValue();
        long previousBiggest=Long.MIN_VALUE;
                    
        if (integer.valueRanges.length==0) {
            return new Long(current-1);
        }
    
        for (int i=0;i<integer.valueRanges.length;i++)
        { 
            if ( integer.valueRanges.upperSet )

{

long tmp;

if ( integer.valueRanges[i].upperOpen )

{

tmp = integer.valueRanges[i].upperEndPoint-1;

} 

else

{

tmp = integer.valueRanges[i].upperEndPoint;

}



if (tmp < current && previousBiggest < tmp)

{

previousBiggest = tmp;

} 

} 

else 

{       

return new Long(current-1);

} 

} 

// there are no other smaller value

if (previousBiggest == Long.MIN_VALUE)

{

return null;

}

return new Long(previousBiggest);

} 
*************************/