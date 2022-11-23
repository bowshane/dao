package com.shanebow.dao.schema;
/********************************************************************
* @(#)DBNode.java 1.00 20150916
* Copyright © 2015 by Richard T. Salamone, Jr. All rights reserved.
*
* DBNode: A simple definition of a node in the database hierarchy.
*
* @version 1.00
* @author Rick Salamone
* 20150916 rts created
*******************************************************/

public interface DBNode {
	public DBNode getChild(int index);
	public int getChildCount();
	public Object[] getChildren();
	public Object getColumn(int col);
	}
