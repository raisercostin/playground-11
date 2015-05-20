package org.raisercostin.test.cache.storage;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class DiskStorageStrategy implements StorageStrategy<String, String> {
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
	return new File(file, key);
    }

    @Override
    public String loadOr(String key, String defaultValue) {
	File file = child(key);
	if (file.exists()) {
	    try {
		return FileUtils.readFileToString(file);
	    } catch (IOException e) {
		throw new RuntimeException(e);
	    }
	} else {
	    return defaultValue;
	}
    }

    @Override
    public void remove(String key) {
	try {
	    FileUtils.forceDelete(child(key));
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }
}
