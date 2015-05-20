a)

Implement a web server (file-based) in Java, according to RFC 2616.

For the sake of simplicity (and to keep the effort to a reasonable amount) please implement only the GET method.

It's ok to not fully implement it, however the candidate should be able to describe the remaining steps (eg concepts like partial resources, caching etc).

The proposed solution shall have meaningful test coverage.

Constraints:

Please use Maven as a build tool. 
Please use external libraries only for things like logging/string processing (utils) and testing/mocking

b)

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