# com.shanebow.dao

The `DataField` *interface* describes the contract for information that can be stored in
 a csv file or the database, or edited by the user.

`DataRecord`: Essentially a set of `DataField` objects along with some
 meta data and static methods that allow for flexible handling of
 the records.

>>In this package the prefix `DR` stands for `DataRecord` and `DF` means `DataField`.

`DREditor`: A panel for editing and validating the fields of a
 data record.

## com.shanebow.dao.table
`DRTable`: Extends `JTable` to use renderers and editors for `DataField`
 objects where possible. This is sufficient for most tables displaying
 DataField items, but may have to provide some additional renderers.
 This class also provides support for sorting and persistent configuration.

`DRTableModel`: Extends AbstractTableModel to display `DataRecords`
 in a `JTable` with save support. Since it also implements,
 `SBTableModel` this model can be used with `SBTable`.

*Sorting*: If the the model implements the `SortableTableModel` interface,
 DFTable will add a mouse listener to the column headers that calls the
 model's sort method.

`TablePreferencesEditor`: Allows editing of a set of table preferences. It
 is a concrete `JPanel` implementation of `shaebow.ui.PreferencesEditor`. Thus
 it is a component which can be used by the caller in any way they want.
 Typically, a set of `TablePreferencesEditor` objects are placed in a
 `JTabbedPane`, one per pane.

There are several renderers for table cell data included in this package and more
 can easily be added as needed for a new data type.

## com.shanebow.dao.schema
The `DBNode` interface is a simple definition of a node in the database hierarchy.

The `DBSchema` class implements a collection of `DBTable`s (i.e. `Vector<DBTable>`)
 that make up the database of an application. More specifically, it implements
 `DBNode` and is the root of the database definition hierarchy: schema &rarr; tables &rarr; fields.

`DBTable` is a database table definition comprising a set of field definitions
 (i.e. `Vector<DBField>`), a unique id, a name and some utility routines to
 facilitate reflection. Each DBTable is created as a child of the
 database, thus the constructor is package private.

The `DBField` class, which implements `DBNode`, encapulates the meta data for a field
 in a database table definition. The meta data specifies the the type of data,
 an editor, a label, and a tooltip. Each DBField is created as a child of a
 DBTable and so, the constructor is package private.


Example:
~~~
	public
	static ArrayList<Class<? extends DataRecord>> fRegisteredClasses = new ArrayList<Class<? extends DataRecord>>();

	static public <R extends DataRecord> void register(Class<R> aClass) {
		fRegisteredClasses.add(aClass);
		}

	static private <R extends DataRecord> int idTable(Class<R> aClass) {
		for (int i=0; i < fRegisteredClasses.size(); i++)
			if (fRegisteredClasses.get(i) == aClass) return i+1;
		return 0;
		}
~~~