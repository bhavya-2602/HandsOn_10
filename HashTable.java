

public class HashTable {
    private static final double LOAD_FACTOR = 0.75;
    private static final double SHRINK_FACTOR = 0.25;
    private static final int INITIAL_CAPACITY = 16;

    private DoublyLinkedList[] table;
    private int size;
    private int capacity;
    private HashFunction hashFunction;

    
    public HashTable(HashFunction hashFunction) {
        this.capacity = INITIAL_CAPACITY;
        this.table = new DoublyLinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new DoublyLinkedList();
        }
        this.size = 0;
        this.hashFunction = hashFunction;
    }

    
    public void insert(int key, int value) {
        if ((double) size / capacity >= LOAD_FACTOR) {
            resize(capacity * 2);
        }
        int index = hashFunction.hash(key, capacity);
        table[index].insert(key, value);
        size++;
        display();  
    }

    // Remove key-value pair
    public void remove(int key) {
        int index = hashFunction.hash(key, capacity);
        table[index].delete(key);
        size--;
        if ((double) size / capacity <= SHRINK_FACTOR && capacity > INITIAL_CAPACITY) {
            resize(capacity / 2);
        }
    }

    // Resize the hash table
    private void resize(int newCapacity) {
        DoublyLinkedList[] oldTable = table;
        table = new DoublyLinkedList[newCapacity];
        capacity = newCapacity;
        size = 0;  // Reset size and rehash
        for (int i = 0; i < newCapacity; i++) {
            table[i] = new DoublyLinkedList();
        }
        for (DoublyLinkedList list : oldTable) {
            DoublyLinkedList.Node node = list.head;
            while (node != null) {
                DoublyLinkedList.ValueNode valueNode = node.valueHead;
                while (valueNode != null) {
                    insert(node.key, valueNode.value);  
                    valueNode = valueNode.next;
                }
                node = node.next;
            }
        }
    }

    
    public void display() {
        System.out.println("Hash Table (Chaining Display):");
        System.out.println("-------------------------------");
        for (DoublyLinkedList list : table) {
            DoublyLinkedList.Node node = list.head;
            while (node != null) {
                System.out.print(node.key + ": ");
                System.out.println(list.getAllValues(node.key));
                node = node.next;
            }
        }
        System.out.println("-------------------------------");
    }

    
    public int find(int key) {
        int index = hashFunction.hash(key, capacity);
        return table[index].find(key);
    }

    
    public interface HashFunction {
        int hash(int key, int capacity);
    }

    // Division method hash function implementation
    public static class DivisionHashFunction implements HashFunction {
        public int hash(int key, int capacity) {
            return key % capacity;
        }
    }

    // Multiplication method hash function implementation
    public static class MultiplicationHashFunction implements HashFunction {
        private static final double A = 0.6180339887;  // Constant (1 - sqrt(5)) / 2

        public int hash(int key, int capacity) {
            return (int) (capacity * ((key * A) % 1));
        }
    }
}
