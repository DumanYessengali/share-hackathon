package kz.nis.share.utils;


import kz.nis.share.exceptions.FileNameException;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

public class FileNameHelper {
	
	public String generateUniqueNumber() {
		int min = 10000;
		int max = 99999;
		int random_int = (int) (Math.random() * (max - min + 1) + min);
		return "" + random_int;
	}
	
	public String generateFileName(String fileName) {


		String shortRandomAlphabet = generateUniqueNumber();


		String dateStrFormat = DateTime.now().toString("HHmmss_ddMMyyyy");


		int indexOfExtension = fileName.indexOf(".");
		String extensionName = fileName.substring(indexOfExtension);


		return dateStrFormat + "_" + shortRandomAlphabet + extensionName;

	}

	public String generateDisplayName(String orgFileName) {
		String orgCleanPath = StringUtils.cleanPath(orgFileName);


		if (orgCleanPath.contains(".."))
			throw new FileNameException("Filename contains invalid path " + orgCleanPath);


		return generateFileName(orgCleanPath);
	}


}
