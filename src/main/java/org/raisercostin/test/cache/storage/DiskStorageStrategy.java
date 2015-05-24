package org.raisercostin.test.cache.storage;

import java.io.*;
import java.util.Objects;

import org.apache.commons.io.FileUtils;

/**
 * Stores keys and values of type string in separate files under a storage folder. Could be optimized if needed by
 * storing in a single file with direct seek access if a maximum size for each value is added or using other strategies.
 *
 * It is not thread safe since the safety should be assured by the client of this class.
 *
 * @author costin
 */
public class DiskStorageStrategy<Key, Value> implements StorageStrategy<Key, Value> {
	private final static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(DiskStorageStrategy.class);
	private static final String PREFIX = "cache-";
	private static final String SUFFIX = ".val";
	private static FilenameFilter filter = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.startsWith(PREFIX) && name.endsWith(SUFFIX);
		}
	};

	private File root;

	public DiskStorageStrategy(File root) {
		Objects.requireNonNull(root);
		LOG.debug("creating CacheDiskStorage at ["+root.getAbsolutePath()+"]");
		this.root = root;
	}

	@Override
	public void save(Key key, Value value) {
		try {
			FileUtils.writeStringToFile(child(key), serializeValue(value));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/** Could be overridden in order to change the Key serialization algorithm */
	protected String serializeValue(Value value) {
		return value.toString();
	}

	private Value deserializeValue(String value) {
		return (Value) value;
	}

	/** Could be overridden in order to change the Key serialization algorithm */
	protected String toStringKey(Key key) {
		return key.toString();
	}

	private File child(Key key) {
		Objects.requireNonNull(key);
		return new File(root, PREFIX + toStringKey(key) + SUFFIX);
	}

	@Override
	public Value loadOr(Key key, Value defaultValue) {
		File file = child(key);
		if (file.exists()) {
			try {
				return deserializeValue(FileUtils.readFileToString(file));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			return defaultValue;
		}
	}

	@Override
	public void remove(Key key) {
		if (child(key).exists()) {
			try {
				FileUtils.forceDelete(child(key));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public boolean containsKey(Key key) {
		return child(key).exists();
	}

	@Override
	public int size() {
		String[] all = root.list(filter);
		if (all == null)
			return 0;
		return all.length;
	}

	@Override
	public void clear() {
		try {
			if (root.exists())
				FileUtils.forceDelete(root);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
