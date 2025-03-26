class Node:
    def __init__(self, key_value):
        self.key = key_value  # Node key value
        self.left = None  # Left child
        self.right = None  # Right child

class BinarySearchTree:
    def __init__(self):
        self.root = None  # Root of the BST

    # Insert a key into the BST
    def insert(self, key_value):
        self.root = self._insert_recursive(self.root, key_value)

    def _insert_recursive(self, current_node, key_value):
        if current_node is None:
            return Node(key_value)  # Create a new node if spot is empty
        
        # Traverse left or right depending on key value
        if key_value < current_node.key:
            current_node.left = self._insert_recursive(current_node.left, key_value)
        elif key_value > current_node.key:
            current_node.right = self._insert_recursive(current_node.right, key_value)
        
        return current_node

    # Search for a key in the BST
    def search(self, key_value):
        return self._search_recursive(self.root, key_value)

    def _search_recursive(self, current_node, key_value):
        if current_node is None or current_node.key == key_value:
            return current_node  # Return node if found
        
        # Traverse left or right based on key value
        if key_value < current_node.key:
            return self._search_recursive(current_node.left, key_value)
        return self._search_recursive(current_node.right, key_value)

    # Delete a key from the BST
    def delete(self, key_value):
        self.root = self._delete_recursive(self.root, key_value)

    def _delete_recursive(self, current_node, key_value):
        if current_node is None:
            return current_node  # Key not found
        
        # Find the node to delete
        if key_value < current_node.key:
            current_node.left = self._delete_recursive(current_node.left, key_value)
        elif key_value > current_node.key:
            current_node.right = self._delete_recursive(current_node.right, key_value)
        else:
            # Case 1: Node with only one child or no child
            if current_node.left is None:
                return current_node.right
            elif current_node.right is None:
                return current_node.left
            
            # Case 2: Node with two children - Get inorder successor
            current_node.key = self._min_value(current_node.right)
            current_node.right = self._delete_recursive(current_node.right, current_node.key)
        
        return current_node

    # Find the minimum value node in a subtree
    def _min_value(self, current_node):
        minimum_value = current_node.key
        while current_node.left is not None:
            minimum_value = current_node.left.key
            current_node = current_node.left
        return minimum_value

    # In-order traversal (Left, Root, Right)
    def in_order(self):
        self._in_order_recursive(self.root)
        print()

    def _in_order_recursive(self, current_node):
        if current_node is not None:
            self._in_order_recursive(current_node.left)
            print(current_node.key, end=" ")
            self._in_order_recursive(current_node.right)

    # Pre-order traversal (Root, Left, Right)
    def pre_order(self):
        self._pre_order_recursive(self.root)
        print()

    def _pre_order_recursive(self, current_node):
        if current_node is not None:
            print(current_node.key, end=" ")
            self._pre_order_recursive(current_node.left)
            self._pre_order_recursive(current_node.right)

    # Post-order traversal (Left, Right, Root)
    def post_order(self):
        self._post_order_recursive(self.root)
        print()

    def _post_order_recursive(self, current_node):
        if current_node is not None:
            self._post_order_recursive(current_node.left)
            self._post_order_recursive(current_node.right)
            print(current_node.key, end=" ")

if __name__ == "__main__":
    bst = BinarySearchTree()
    choice = None

    while choice != 0:
        print("Menu:")
        print("1. Insert")
        print("2. Delete")
        print("3. Search")
        print("4. In-order Traversal")
        print("5. Pre-order Traversal")
        print("6. Post-order Traversal")
        print("0. Exit")
        choice = int(input("Enter your choice: "))

        if choice == 1:
            insert_key = int(input("Enter key to insert: "))
            bst.insert(insert_key)
        elif choice == 2:
            delete_key = int(input("Enter key to delete: "))
            bst.delete(delete_key)
        elif choice == 3:
            search_key = int(input("Enter key to search: "))
            result = bst.search(search_key)
            print("Search for key {}: {}".format(search_key, "Found" if result is not None else "Not Found"))
        elif choice == 4:
            print("In-order Traversal:")
            bst.in_order()
        elif choice == 5:
            print("Pre-order Traversal:")
            bst.pre_order()
        elif choice == 6:
            print("Post-order Traversal:")
            bst.post_order()
        elif choice == 0:
            print("Exiting...")
        else:
            print("Invalid choice. Please try again.")

