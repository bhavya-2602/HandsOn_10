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

# Example usage
if __name__ == "__main__":
    dll = DoublyLinkedList()
    dll.insert(10, 100)
    dll.insert(20, 200)
    dll.insert(10, 150)
    print("Value for key 10:", dll.search(10))
    print("All values for key 10:", dll.get_all_values(10))
    print("Removing key 20:", "Success" if dll.remove(20) else "Fail")
    print("Value for key 20 after removal:", dll.search(20))
