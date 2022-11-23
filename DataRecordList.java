package com.shanebow.dao;
/********************************************************************
* @(#)DataRecordList.java 1.00 20150831
* Copyright © 2015 by Richard T. Salamone, Jr. All rights reserved.
*
* DataRecordList: Extends Vector with routines to parse, save,
* and restore a collection of data records.
*
* @author Rick Salamone
* @version 1.00
* 20150831 rts created
* 20150911 rts added copy
* 20151027 rts added single record ctor
*******************************************************/
import com.shanebow.util.SBLog;
import java.lang.reflect.Constructor;
import java.util.Vector;

public class DataRecordList<R extends DataRecord>
	extends Vector<R>
	{
	public static final DataRecordList EMPTY = new DataRecordList(0);

	public DataRecordList() {super();}
	public DataRecordList(int initialCapacity) {super(initialCapacity);}

	/**
	* Convenient way to make a list of one
	*/
	public DataRecordList(R record) { super(1); add(record); }
		
	/**
	* Make a list from a long String of fields,
	* such as that returned from Rox server
	*/
	public DataRecordList(Class<R> klass, String data, String sep) {
		super();
		add(klass, data, sep);
		}

	public void add(Class<R> klass, String data, String sep) {
		try {
			String[] pieces = data.split(sep,3);
			int nrows = Integer.parseInt(pieces[0],10);
			int ncols = Integer.parseInt(pieces[1],10);
			// System.out.println("DRL " + klass.getSimpleName() + " loading " + nrows + " rows");
			Constructor<R> construct = klass.getConstructor(String[].class);
			String dat = pieces[2];
			while (nrows-- > 0) {
				String[] row = dat.split(sep, ncols + 1);
				if (row.length > ncols) {
					dat = row[ncols];
					row[ncols] = null;
					}
				else dat = null;
				try { add(construct.newInstance((Object)row)); }
				catch(Exception e) { SBLog.error(klass.getSimpleName(), "Parse error: " + e); }
				}
			trimToSize();
			}
		catch (Exception x) { SBLog.error("DataRecordList.parse", x.getMessage()); }
		}

	public final long lmod() {
		long lmod = 0;
		for (R record : this)
			if (record.lmod() > lmod) lmod = record.lmod();
		return lmod;
		}

	public DataRecordList<R> copy() {
		DataRecordList<R> it = new DataRecordList<R>(size());
		for (R u : this) it.add(u);
		return it;
		}
	}