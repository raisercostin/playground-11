package org.raisercostin.test.cache;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;
import org.raisercostin.test.cache.storage.*;

public class StorageStrategyTest {
    @Test
    public void testDiskStorageBasicOperations() {
	StorageStrategy<String, String> storage = new DiskStorageStrategy(new File(
		"target/storage"));
	doBasicOperations(storage);
    }

    @Test
    public void testMemoryStorageBasicOperations() {
	StorageStrategy<String, String> storage = new MemoryStorageStrategy<String, String>();
	doBasicOperations(storage);
    }

    private void doBasicOperations(StorageStrategy<String, String> storage) {
	storage.save("1", "value1");
	assertEquals("value1", storage.loadOr("1", null));
	assertEquals(null, storage.loadOr("2", null));
	storage.remove("1");
	assertEquals(null, storage.loadOr("1", null));
    }
}
