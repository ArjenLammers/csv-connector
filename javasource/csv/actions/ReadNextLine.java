// This file was generated by Mendix Modeler.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package csv.actions;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import com.opencsv.CSVReader;
import csv.impl.CSV;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.meta.IMetaObject;
import com.mendix.systemwideinterfaces.core.meta.IMetaPrimitive;

/**
 * Reads a next line from a CSV.
 * This action should only be invoked from the microflow used by a ImportCSV action.
 * If the action returns an empty object, the end of the file is reached.
 * 
 * Take into account that the order of attributes filled of an entity will be ordered in alphabetical manner.
 * An example usable entity layout would be:
 * C01_Column1
 * C02_Column2
 * ...
 * 
 * All attributes should be of the type String; thus specific parsing should be part of the microflow.
 * 
 * This is implemented because declared primitives are returned in a different order than declared within the model.
 */
public class ReadNextLine extends CustomJavaAction<IMendixObject>
{
	private java.lang.String entity;

	public ReadNextLine(IContext context, java.lang.String entity)
	{
		super(context);
		this.entity = entity;
	}

	@java.lang.Override
	public IMendixObject executeAction() throws Exception
	{
		// BEGIN USER CODE
		ILogNode logger = CSV.getLogger();
		Object contextObj = getContext().getData().get(CSV.CONTEXT_READER_OBJ);
		if (contextObj == null || !(contextObj instanceof CSVReader)) {
			throw new CoreException("Reader not found, this action should be invoked from a Import CSV microflow.");
		}
		
		@SuppressWarnings("resource") // reader is closed by the ImportCSV action
		CSVReader reader = (CSVReader) contextObj;

		// This is implemented because declared primitives are returned in a different order than declared within the model.
		Collection<? extends IMetaPrimitive> attributes = Core.getMetaObject(this.entity).getDeclaredMetaPrimitives();
		List<String> attributeNames = new LinkedList<String>();
		for (IMetaPrimitive attribute : attributes) {
			String name = attribute.getName();
			if (name.equalsIgnoreCase("createdBy") || name.equalsIgnoreCase("owner") ||
				name.equalsIgnoreCase("createdDate") || name.equalsIgnoreCase("changedDate")) {
				continue;
			}
			attributeNames.add(name);
		}
		Collections.sort(attributeNames);
		
		String line[] = reader.readNext();
		if (line == null) {
			logger.debug("End of file has been reached.");
			return null;
		}
		
		IMendixObject result = Core.instantiate(getContext(), this.entity);
		int counter = 0;
		for (String attributeName : attributeNames) {
			result.setValue(getContext(), attributeName, line[counter]);
			counter++;
		}
		
		return result;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "ReadNextLine";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
