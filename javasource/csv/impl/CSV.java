package csv.impl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;

import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.meta.IMetaObject;
import com.mendix.systemwideinterfaces.core.meta.IMetaPrimitive;
import com.opencsv.CSVWriter;

public class CSV {
	public static final String LOGNODE = "CSV";
	public static final String CONTEXT_READER_OBJ = "CSVReader";
	public static final String CONTEXT_WRITER_OBJ = "CSVWriter";
	private static ILogNode logger;

	public static ILogNode getLogger() {
		if (logger == null) {
			logger = Core.getLogger(LOGNODE);
		}
		return logger;
	}
	public static String[] getAttributes(IMetaObject metaObject) {
		String[] attributes = new String[metaObject.getDeclaredMetaPrimitives().size()];
		int offset = 0;
		for (IMetaPrimitive primitive : metaObject.getDeclaredMetaPrimitives()) {
			attributes[offset] = primitive.getName();
			offset++;
		}
		Arrays.sort(attributes);
		return attributes;
	}

	public static DecimalFormat getDecimalFormat(String decimalSeparator, String groupingSeparator) {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		if (decimalSeparator != null) {
			otherSymbols.setDecimalSeparator(decimalSeparator.charAt(0));
		}
		if (groupingSeparator != null) {
			otherSymbols.setGroupingSeparator(groupingSeparator.charAt(0));
		}

		DecimalFormat df = new DecimalFormat();
		df.setDecimalFormatSymbols(otherSymbols);
		df.setParseBigDecimal(true);

		return df;
	}
	public static CSVWriter getCSVWriter(IContext context) throws CoreException {
		Object contextObj = context.getData().get(CSV.CONTEXT_WRITER_OBJ);
		if (contextObj == null || !(contextObj instanceof CSVWriter)) {
			throw new CoreException("Reader not found, this action should be invoked from a Export CSV microflow.");
		}
		return (CSVWriter) contextObj;
	}
}
