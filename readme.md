Implement a two level cache in Java: the complete requirements are:

Write a thread safe cache library, implementing the following interface:

public interface Cache<K, V> {
    void put(K key, V value);
    V get(K key);
    boolean containsKey(K key);
    V remove(K key);
    boolean isEmpty();
    int size();
    void clear();
}

The library should support multiple cache strategies (eg LRU, LFU etc). It should also support 2 levels: memory and disk.

The proposed solution shall have meaningful test coverage.

Constraints:

Please use Maven as a build tool.
Please use external libraries only for things like logging/string processing (utils) and testing/mocking

Evaluation criteria for both homeworks(in ascending order):

0)correctness - does the solution work?
1)code clarity/structure
2)performance


DONE
- independent cache replacement strategy: LRU, Random & test
- independent cache storage: memory, disk & test
- multithreading support & test
- 2 levels cache (multi levels by combining 2 CascadedCache) & test
- performance

Would be nice to have hits contests between caches strategies :D:D
