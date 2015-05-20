package org.raisercostin.test.cache.replacement;

import java.util.Random;

/**
 * Random Replacement (RR) Randomly selects a candidate item and discards it to
 * make space when necessary. This algorithm does not require keeping any
 * information about the access history. For its simplicity, it has been used in
 * ARM processors.[5] It admits efficient stochastic simulation.[6]
 *
 * @author costin
 * @param <Key>
 */
public class RandomReplacementStrategy<Key> implements ReplacementStrategy<Key> {
    private Key[] keys;
    private Random random;

    public RandomReplacementStrategy(int maxKeys, Random random) {
	this.keys = (Key[]) new Object[maxKeys];
	this.random = random;
    }

    @Override
    public Key update(Key key) {
	int index = random.nextInt(keys.length);
	Key result = keys[index];
	keys[index] = key;
	return result;
    }
}
