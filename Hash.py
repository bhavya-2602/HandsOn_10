import math

# Node class for Doubly Linked List
class Node:
    def __init__(self, key, value):
        self.key, self.value, self.next, self.prev = key, value, None, None

# Doubly Linked List for chaining in hash table
class DoublyLinkedList:
    def __init__(self):
        self.head, self.tail = None, None
    
    # Insert a new key-value pair at the end
    def insert(self, key, value):
        new_node = Node(key, value)
        if not self.head:
            self.head = self.tail = new_node
        else:
            self.tail.next, new_node.prev, self.tail = new_node, self.tail, new_node
    
    # Remove a node with a specific key
    def remove(self, key):
        temp = self.head
        while temp:
            if temp.key == key:
                if temp.prev: temp.prev.next = temp.next
                if temp.next: temp.next.prev = temp.prev
                if temp == self.head: self.head = temp.next
                if temp == self.tail: self.tail = temp.prev
                return True
            temp = temp.next
        return False
    
    # Search for a key and return its value
    def search(self, key):
        temp = self.head
        while temp:
            if temp.key == key:
                return temp.value
            temp = temp.next
        return -1

# Hash Table Implementation
class HashTable:
    def __init__(self, capacity=8):
        self.capacity, self.size, self.A = capacity, 0, (math.sqrt(5) - 1) / 2
        self.table = [DoublyLinkedList() for _ in range(capacity)]
    
    # Hash function using multiplication method
    def _hash(self, key):
        return int(self.capacity * ((key * self.A) % 1))
    
    # Resize the hash table when it's too full or too empty
    def _resize(self, new_capacity):
        new_table = [DoublyLinkedList() for _ in range(new_capacity)]
        for dll in self.table:
            temp = dll.head
            while temp:
                new_table[temp.key % new_capacity].insert(temp.key, temp.value)
                temp = temp.next
        self.table, self.capacity = new_table, new_capacity
    
    # Insert a key-value pair into the hash table
    def insert(self, key, value):
        self.table[self._hash(key)].insert(key, value)
        self.size += 1
        if self.size >= self.capacity:
            self._resize(self.capacity * 2)
    
    # Remove a key from the hash table
    def remove(self, key):
        if self.table[self._hash(key)].remove(key):
            self.size -= 1
            if self.size <= self.capacity // 4 and self.capacity > 8:
                self._resize(self.capacity // 2)
            return True
        return False
    
    # Search for a key and return its value
    def search(self, key):
        return self.table[self._hash(key)].search(key)

# Example usage
if __name__ == "__main__":
    ht = HashTable()
    ht.insert(10, 100)
    ht.insert(20, 200)
    ht.insert(30, 300)
    print("Value for key 10:", ht.search(10))
    print("Value for key 20:", ht.search(20))
    print("Removing key 20:", "Success" if ht.remove(20) else "Fail")
    print("Value for key 20 after removal:", ht.search(20))

