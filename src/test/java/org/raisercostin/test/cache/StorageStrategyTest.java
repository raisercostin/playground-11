package org.raisercostin.test.cache;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class StorageStrategyTest {
	@Test
	public void testDiskStorageBasicOperations() {
		Storage<String,String> storage = new DiskStorageStrategy(new File("target/storage"));
		doBasicOperations(storage);
	}
	@Test
	public void testMemoryStorageBasicOperations() {
		Storage<String,String> storage = new MemoryStorageStrategy<String,String>();
		doBasicOperations(storage);
	}

	private void doBasicOperations(Storage<String,String> storage) {
		storage.save("1","value1");
		assertEquals("value1",storage.loadOr("1",null));
	}
}
