package com.myapart.app.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	
private static FileUtil instance;
	private String fileName;
	public static FileUtil getInstance() {
		if(instance == null) {
			instance = new FileUtil();
		}
		return instance;
	}
	
	public boolean uploadFileToServer(MultipartFile uploadFile, String id) {
		if(uploadFile == null) {
			return false;
		}
		
		String fileName = uploadFile.getOriginalFilename();
		String newFileName = getCurrentTime() + "_" + id + "_" +fileName;

		File file = new File(Variable.upload_path + newFileName);
		
		try {
			uploadFile.transferTo(file);
		}catch (IOException e) {
			return false;
		}
		setFileName(newFileName);
		return true;
	}
	
	public boolean deleteUploadFile(String fileName) {
		String path = Variable.upload_path + fileName;
		
		File file = new File(path);
		if(file.exists()) {
			return file.delete();
		}
		return false;
	}
	
	private String getCurrentTime() {
		
		long time = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("yymmddhhmmss");
		String curTime = formatter.format(new Date(time));
		
		return curTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
