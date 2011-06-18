package ritchie.backend.utilities;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ritchie.backend.exception.InvalidXLSException;
import ritchie.backend.exception.NoRecordsFoundException;
import ritchie.backend.exception.XLSExceedsMaxRecordsException;

public class POIXLSReader {
	private static Logger logger = Logger.getLogger(POIXLSReader.class);
	public List processXLS(String fileName, Map<String,String> map) throws IOException,XLSExceedsMaxRecordsException,InvalidXLSException,NoRecordsFoundException {
		List sheetData = new ArrayList();
		FileInputStream fis = null;
		List list = new ArrayList();
		boolean singleValueExist = false;
		
		try {
			fis = new FileInputStream(fileName);

			// Create an excel workbook from the file system.
			HSSFWorkbook workbook = new HSSFWorkbook(fis);

			// Get the first sheet on the workbook.
			HSSFSheet sheet = workbook.getSheetAt(0);

			/*
			 * When we have a sheet object in hand we can iterator on each
			 * sheet's rows and on each row's cells. We store the data read on
			 * an ArrayList so that we can printed the content of the excel to
			 * the console.
			 */
			Iterator rows = sheet.rowIterator();

			String separator = map.get(Constants.SEPARATOR);
			String[] fields = map.get(Constants.FIELDS).split(separator);
			boolean isHeaderValid = true;
			int i = 1;

			//this code checks whether the given xls file's header is valid
			while (rows.hasNext() && i<4) {
				logger.info("Row Count:   " + i);
				HSSFRow row = (HSSFRow) rows.next();
				if (i == 3) {
					for (int j = 0; j < fields.length; j++) {
						if (row.getCell(j) != null && !fields[j].trim().equalsIgnoreCase(
								row.getCell(j).toString().trim())) {
							isHeaderValid = false;
							logger.info("isHeaderValid::  " + isHeaderValid);
							throw new InvalidXLSException("Invalid Uploaded Template");
						}
					}
				}
				i++;
			}
			logger.info("rows.hasNext():  "+rows.hasNext());
			if(isHeaderValid && i<=3){
				throw new InvalidXLSException("Invalid Uploaded Template");
			}
			if(isHeaderValid && !rows.hasNext()){
				throw new NoRecordsFoundException("No records found in the uploaded xls");
			}
			// this code parses the reset of the rows if the xls's header is valid
			if (isHeaderValid) {
				while (rows.hasNext()) {
					if(i==5004){
						throw new XLSExceedsMaxRecordsException("Only 5000 records are allowed per file");
					}
					logger.info("Row Count:   " + i);
					//logger.info("inside while");
					HSSFRow row = (HSSFRow) rows.next();
					String mandatoryfields = map.get(Constants.MANDATORYFIELDS);
					HashMap validatedObjects = new HashMap();
					boolean add = true;
					for (int k = 0; k < fields.length; k++) {
						HSSFCell hc = row.getCell(k);
						//hc.getCellType()
						logger.info("k:  "+k);
						logger.info("fields[k]:  "+fields[k]);
						/*logger.info(hc.toString().trim());
						logger.info(hc.toString().trim().length());*/
						if (mandatoryfields.contains("\"" + (k+1) + "\"")) {
							logger.info("i am a mantatory field");
							if (hc != null && hc.toString().trim().length()>0) {
								logger.info("i am inside");
								if(hc.getCellType() == hc.CELL_TYPE_NUMERIC){
									singleValueExist = true;
									validatedObjects.put(fields[k], new Double(hc.getNumericCellValue()).longValue());
								}else if(hc.getCellType() == hc.CELL_TYPE_STRING){
									singleValueExist = true;
									validatedObjects.put(fields[k], hc.getStringCellValue().trim());
								}
							} else {
								logger.info("i am outside");
								add = false;
								break;
							}
						} else {
							logger.info("i am not a mantatory field");
							if (hc != null && hc.toString().trim().length()>0) {
								if(hc.getCellType() == hc.CELL_TYPE_NUMERIC){
									singleValueExist = true;
									validatedObjects.put(fields[k], new Double(hc.getNumericCellValue()).longValue());
								}else if(hc.getCellType() == hc.CELL_TYPE_STRING){
									singleValueExist = true;
									validatedObjects.put(fields[k], hc.getStringCellValue().trim());
								}
							} else {
								//validatedObjects.put(fields[k], "");
								validatedObjects.put(fields[k], null);
							}
						}
					}
					//logger.info("add:  " + add);
					if (add) {
						list.add(validatedObjects);
					}
					i++;
				}
			}
			
			if(singleValueExist == false && list.size() == 0){
				throw new NoRecordsFoundException("No records found in the uploaded xls");
			}
			/*for (int j = 0; j < list.size(); j++) {
				logger.info(list.get(j));
			}*/
			logger.info("list.size:::  " + list.size());
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException ioe) {
					logger.error(ioe.getMessage());
					throw ioe;
				}
			}
		}
		return list;
	}
}