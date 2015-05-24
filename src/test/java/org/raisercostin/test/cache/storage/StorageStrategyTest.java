package org.raisercostin.test.cache.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;
import org.raisercostin.test.cache.storage.*;

public class StorageStrategyTest {
	@Test
	public void testDiskStorageBasicOperations() {
		StorageStrategy<String, String> storage = new DiskStorageStrategy<String,String>(new File("target/storage"));
		doBasicOperations(storage);
	}

	@Test
	public void testMemoryStorageBasicOperations() {
		StorageStrategy<String, String> storage = new MemoryStorageStrategy<String, String>();
		doBasicOperations(storage);
	}

	private void doBasicOperations(StorageStrategy<String, String> storage) {
		storage.clear();
		assertEquals(0, storage.size());
		assertEquals(false, storage.containsKey("1"));
		assertEquals(null, storage.loadOr("1", null));

		storage.save("1", "value1");
		assertEquals(1, storage.size());
		assertEquals(true, storage.containsKey("1"));
		assertEquals("value1", storage.loadOr("1", null));
		assertEquals(null, storage.loadOr("2", null));

		storage.remove("1");
		assertEquals(0, storage.size());
		assertEquals(false, storage.containsKey("1"));
		assertEquals(null, storage.loadOr("1", null));

		storage.save("1", "value1");
		storage.save("1", "value1");
		storage.save("2", "value1");
		storage.save("3", "value3");
		assertEquals(3, storage.size());
		storage.clear();
		assertEquals(0, storage.size());
	}
}
