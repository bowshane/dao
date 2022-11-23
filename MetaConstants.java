package com.shanebow.dao;
/********************************************************************
* @(#)DataRecord.java	1.00 20100407
* Copyright © 2010-2015 by Richard T. Salamone, Jr. All rights reserved.
*
* DataRecord: Essentially a set of DataField objectsalong with some
* meta data and static methods that allow for flexible handling of
* the records.
*
* @author Rick Salamone
* @version 1.00
* 20100407 rts created
* 20100522 rts finalied field names & csv order for version 1
* 20100626 rts finalied parse() method to return DataField
* 20100627 rts added combobox type editors for source, country, region
* 20100818 rts moved private class FieldMeta out to share with CallFields
* 20110315 rts added constant for BLANK Raw object
* 20150711 rts modified for UBOW sites
import static FieldMeta.AI; // autoincrement
import static FieldMeta.NN; // Not null
import static FieldMeta.CT; // created time, not modifiable
import static FieldMeta.LM; // last modified time
import static FieldMeta.Q; // requires quote
import static FieldMeta.PW; // password field
*******************************************************/

public interface MetaConstants
	{
	public static final int AI = 0x0001; // autoincrement
	public static final int NN = 0x0002; // Not null
	public static final int NM = 0x0004; // not modifiable
	public static final int LM = 0x0008|NN; // last modified time
	public static final int UQ = 0x0010; // unique
	public static final int PW = 0x0020; // password field
	public static final int SQ = 0x0040; // sequence # field
	public static final int IN = 0x0080; // indexed field
	public static final int PK = 0x0100|NN; // (part of the) primary key (not null)
	public static final int CT = 0x0200|NM|NN; // created time, not modifiable
	public static final int Q  = 0x0400;    // requires quote
	public static final int J  = 0x0800;    // joined (derived) field (not in table)

	public static final String[] META_FLAG_NAMES = {
		"AI", "NN", "NM", "LM", "UQ", "PW", "SQ", "IN", "PK", "CT", "Q", "J"
		};
	}
