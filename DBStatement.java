package com.shanebow.dao;
/********************************************************************
* @(#)DBStatement.java	1.00 10/05/14
* Copyright © 2010-2011 by Richard T. Salamone, Jr. All rights reserved.
*
* DBStatement: This class provides an interface to a physical database.
* A single connection to the database is shared by all statements.
* 
* @version 1.00 20100514
* @author Rick Salamone
* 20100601 rts keeping the latest stuffed sql for retrieval
* 20100930 rts adding constants/error check for port to MySQL
* 20101003 rts static connect method requires usr & pwd (ignored by MSAccess)
* 20110216 rts using SBProperties to specify driver & url
* 20110709 rts cleaned up connect code and added 4 arg connect method
* 20110709 rts reads SBProperties for duplicate error codes
* 20110709 rts added method getAutoIncrementID()
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.ui.SBDialog;
import com.shanebow.util.SBLog;
import com.shanebow.util.SBProperties;
import java.sql.*;
import java.net.*;

public final class DBStatement
	{
	private static Connection		_connection = null;
	private static int[] DupErrorCodes;

	public static boolean connect( String usr, String pwd )
		{
		String dbDriver = SBProperties.get("app.db.drv");
		String dbURL = SBProperties.get("app.db.url");
		return connect( dbDriver, dbURL, usr, pwd );
		}

	public static boolean connect( String aDriver, String aURL,
	                               String aUsr, String aPwd )
		{
		if ( _connection != null )
			return true;
		log( "db connect", "Attempt connection: " + aDriver + ", " + aURL );
		DupErrorCodes = null;
		try // connect to the database
			{
			SBProperties props = SBProperties.getInstance();
			if ( props != null )
				DupErrorCodes = props.getIntArray("app.db.dupCodes");
			if ( DupErrorCodes == null || DupErrorCodes.length == 0 )
				log( "db connect", "WARNING: no error codes for dups!" );
			Class.forName( aDriver );
			_connection = DriverManager.getConnection( aURL, aUsr, aPwd );
			_connection.setAutoCommit(true);
			if ( !_connection.isClosed())
				return true;
			}
		catch (ClassNotFoundException ex)
			{
			SBDialog.fatalError( "Failed to load driver:\n" + aDriver + "\n" + ex );
			}
//		catch (SQLException ex) { logError( CANT_CONNECT + aURL); }
		catch ( Exception ex )
			{
			SBDialog.fatalError( "Failed to connect to database:\n" + aURL + "\n" + ex );
			}
		return false;
		}

	public static boolean isDuplicateCode( int aErrorCode )
		{
		if (DupErrorCodes != null)
			for (int dupCode : DupErrorCodes)
				if (aErrorCode == dupCode) return true;
		return false;
		}

	public static void disconnect() { closeConnection(); }
	public static void closeConnection()
		{
		try { _connection.close(); }
		catch (Exception e) {}
		finally { _connection = null; }
		}

	public static int update ( String stmt, Object... parameters )
		throws SQLException
		{
		DBStatement db = null;
		try
			{
			db = new DBStatement();
			return db.executeUpdate(stmt, parameters);
			}
		catch ( SQLException e )
			{
			String msg = "SQL Error: " + e.getMessage();
			log( "DB.update", msg + "\nOffending statement: " + db.getSQL());
			throw e;
			}
		finally { if (db != null) db.close(); }
		}

	private static final void log ( String caller, String msg )
		{
		SBLog.write ( caller, msg );
		}

	private static final void logSQLError ( String caller, SQLException sqlex )
		{
		SBLog.error ( caller, " SQL Error: " + sqlex.toString() );
		while ((sqlex = sqlex.getNextException()) != null)
			SBLog.error ( "&", sqlex.getMessage());
		}

	private Statement m_statement = null;
	private String m_sql = null;

	public DBStatement()
		throws SQLException
		{
		m_statement = _connection.createStatement();
		}

	public DBStatement( boolean updatable)
		throws SQLException
		{
		if ( updatable )
			m_statement = _connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
			                                          ResultSet.CONCUR_UPDATABLE);
		else
			m_statement = _connection.createStatement();
		}

	public ResultSet executeQuery ( String stmt, Object... parameters )
		throws SQLException
		{
		m_sql = stuffParameters( stmt, parameters );
//		log ( "executeQuery", m_sql );
		return m_statement.executeQuery( m_sql );
		}

	public int executeUpdate ( String stmt, Object... parameters )
		throws SQLException
		{
		if ( parameters.length > 0 )
			m_sql = stuffParameters( stmt, parameters );
		else
			m_sql = stmt;
		//	log( "executeUpdate", _connection.nativeSQL( sql ));
		return m_statement.executeUpdate( m_sql );
		}

	public final ResultSet getGeneratedKeys()
		throws SQLException
		{
		return m_statement.getGeneratedKeys();
		}

	public final long getAutoIncrementID()
		throws SQLException
		{
		long it = -1;
		ResultSet rs = m_statement.getGeneratedKeys();
		if (rs.next()) it = rs.getLong(1);
		rs.close();
		return it;
		}

	public final String getSQL() { return m_sql; }

	public void close()
		{
		try
			{
			if ( m_statement != null )
				m_statement.close();
			}
		catch ( SQLException sqlex ) { 	logSQLError ( "close Statement", sqlex ); }
		finally { m_sql = null; m_statement = null; }
		}

	public final ResultSet closeResultSet(ResultSet rs)
		{
		try
			{
			if ( rs != null )
				rs.close();
			rs = null;
			}
		catch ( SQLException sqlex ) { 	logSQLError ( "close Result Set", sqlex ); }
		return rs;
		}

	private String stuffParameters( String template, Object... parameters )
		{
		String[] pieces = template.split("\\?");
		String it = pieces[0];
		for ( int i = 0; i < parameters.length; i++ )
			{
			String field = "";
			if ( parameters[i] == null )
				field = "";
			else if (parameters[i] instanceof DataField)
				field = ((DataField)parameters[i]).dbRepresentation();
			else if (parameters[i] instanceof String)
				field = "'" + parameters[i].toString() + "'";
			else
				field = parameters[i].toString();
			it += field + pieces[i+1];
			}
// System.out.println("SQL: " + it);
		return it;
		}
	}
