import math

# Node class for storing values associated with a key
class ValueNode:
    def __init__(self, value):
        self.value = value
        self.next = None

# Node class for the doubly linked list
class Node:
    def __init__(self, key, value):
        self.key = key
        self.value_head = ValueNode(value)  # Head of value list
        self.prev = None
        self.next = None

# Doubly linked list implementation
class DoublyLinkedList:
    def __init__(self):
        self.head = None
        self.tail = None
    
    # Insert a key-value pair
    def insert(self, key, value):
        node = self._find_node(key)
        if node:
            self._append_value(node, value)  # Append value if key exists
        else:
            new_node = Node(key, value)  # Create new node
            if not self.tail:
                self.head = self.tail = new_node
            else:
                self.tail.next = new_node
                new_node.prev = self.tail
                self.tail = new_node
    
    # Append value to an existing key
    def _append_value(self, node, value):
        current_value_node = node.value_head
        while current_value_node.next:
            current_value_node = current_value_node.next
        current_value_node.next = ValueNode(value)
    
    # Find a node by key
    def _find_node(self, key):
        current = self.head
        while current:
            if current.key == key:
                return current
            current = current.next
        return None
    
    # Remove a node by key
    def remove(self, key):
        node = self._find_node(key)
        if not node:
            return False
        
        if node.prev:
            node.prev.next = node.next
        else:
            self.head = node.next
        
        if node.next:
            node.next.prev = node.prev
        else:
            self.tail = node.prev
        
        return True
    
    # Search for a key and return its first value
    def search(self, key):
        node = self._find_node(key)
        return node.value_head.value if node and node.value_head else -1
    
    # Retrieve all values for a key
    def get_all_values(self, key):
        node = self._find_node(key)
        if not node:
            return None
        
        values = []
        current_value_node = node.value_head
        while current_value_node:
            values.append(str(current_value_node.value))
            current_value_node = current_value_node.next
        return " -> ".join(values)

# Hash function interface
class HashFunction:
    def hash(self, key, capacity):
        raise NotImplementedError

# Division method hash function implementation
class DivisionHashFunction(HashFunction):
    def hash(self, key, capacity):
        return key % capacity

# Multiplication method hash function implementation
class MultiplicationHashFunction(HashFunction):
    A = 0.6180339887  # Constant (1 - sqrt(5)) / 2
    
    def hash(self, key, capacity):
        return int(capacity * ((key * self.A) % 1))

# Hash table implementation with separate chaining
class HashTable:
    LOAD_FACTOR = 0.75
    SHRINK_FACTOR = 0.25
    INITIAL_CAPACITY = 16
    
    def __init__(self, hash_function):
        self.capacity = self.INITIAL_CAPACITY
        self.table = [DoublyLinkedList() for _ in range(self.capacity)]
        self.size = 0
        self.hash_function = hash_function
    
    def insert(self, key, value):
        if self.size / self.capacity >= self.LOAD_FACTOR:
            self._resize(self.capacity * 2)
        index = self.hash_function.hash(key, self.capacity)
        self.table[index].insert(key, value)
        self.size += 1
        self.display()
    
    def remove(self, key):
        index = self.hash_function.hash(key, self.capacity)
        if self.table[index].remove(key):
            self.size -= 1
        if self.size / self.capacity <= self.SHRINK_FACTOR and self.capacity > self.INITIAL_CAPACITY:
            self._resize(self.capacity // 2)
    
    def _resize(self, new_capacity):
        old_table = self.table
        self.table = [DoublyLinkedList() for _ in range(new_capacity)]
        self.capacity = new_capacity
        self.size = 0
        
        for linked_list in old_table:
            current_node = linked_list.head
            while current_node:
                value_node = current_node.value_head
                while value_node:
                    self.insert(current_node.key, value_node.value)
                    value_node = value_node.next
                current_node = current_node.next
    
    def find(self, key):
        index = self.hash_function.hash(key, self.capacity)
        return self.table[index].search(key)
    
    def display(self):
        print("Hash Table (Chaining Display):")
        print("-------------------------------")
        for linked_list in self.table:
            current_node = linked_list.head
            while current_node:
                print(f"{current_node.key}: {linked_list.get_all_values(current_node.key)}")
                current_node = current_node.next
        print("-------------------------------")

# Example usage
if __name__ == "__main__":
    hash_function = DivisionHashFunction()
    hash_table = HashTable(hash_function)
    
    hash_table.insert(10, 100)
    hash_table.insert(20, 200)
    hash_table.insert(10, 150)
    print("Value for key 10:", hash_table.find(10))
    print("All values for key 10:", hash_table.table[hash_function.hash(10, hash_table.capacity)].get_all_values(10))
    print("Removing key 20:", "Success" if hash_table.remove(20) else "Fail")
    print("Value for key 20 after removal:", hash_table.find(20))
