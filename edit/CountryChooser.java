package com.shanebow.dao.edit;
/********************************************************************
* @(#)CountryChooser.java 1.0 20110121
* Copyright 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* CountryChooser: Extends JList to display all known CountryID's, allowing
* multiple discontiguous selection. The get/setSelectedCountries methods
* conveniently work with arrays of Country.
*
* @author Rick Salamone
* @version 1.00 20110121 rts created based on old caller criteria code
* @version 1.01 20110503 rts added methods to set and get as csv
*******************************************************/
import com.shanebow.dao.Country;
import com.shanebow.ui.SublistChooser;
import com.shanebow.util.SBLog;
import java.awt.BorderLayout;
import java.util.Vector;
import javax.swing.*;

public final class CountryChooser
	extends JPanel
	{
	private static final int MAX_COUNTRIES = 5;
	private SublistChooser fChooser;

	private static Country[] buildCountryArray()
		{
		Country[] allCountries = new Country[Country.countAll()-1];
		int i = 0;
		for ( Country cid : Country.getAll())
			if ( cid != Country.XX )
				allCountries[i++] = cid;
		return allCountries;
		}

	public CountryChooser()
		{
		super(new BorderLayout());
		fChooser = new SublistChooser( buildCountryArray(), MAX_COUNTRIES, 8, 120 );
		add(fChooser, BorderLayout.CENTER);
		}

	public String getCSV()
		{
		Object[] selected = fChooser.getChosen();
		String csv = "";
		for ( int i = 0; i < selected.length; i++ )
			csv += ((i==0)?"":",") + ((Country)selected[i]).dbRepresentation();
		return csv;
		}

	public void setCSV(String aCSV)
		{
		clearSelection();
		if (aCSV == null || aCSV.isEmpty())
			return;

		String[] pieces = aCSV.split(",");
		Country[] ids = new Country[pieces.length];
		try
			{
			for (int i = 0; i < pieces.length; i++)
				ids[i] = Country.parse(pieces[i]);
			fChooser.chooseItems(ids);
			}
		catch (Exception e)
			{
			SBLog.write("Error initializing Country selector from csv:\n\t"
			      + aCSV + "\n\t" + e);
			}
		}

	public void clearSelection() { fChooser.removeAll(); }
	}
