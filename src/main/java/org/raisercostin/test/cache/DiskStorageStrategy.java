package org.raisercostin.test.cache;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class DiskStorageStrategy implements Storage<String,String> {
	private File file;

	public DiskStorageStrategy(File file) {
		this.file = file;
	}

	@Override
	public void save(String key, String value) {
		try {
			FileUtils.writeStringToFile(child(key), value);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	private File child(String key) {
		return new File(file,key);
	}

	@Override
	public String load(String key) {
		return null;
	}
}
