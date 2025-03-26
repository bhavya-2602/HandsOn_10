class Node:
    def __init__(self, data):
        self.data = data  # Node value
        self.left = None  # Left child
        self.right = None  # Right child
        self.height = 1  # Height of node

class AVLTree:
    def __init__(self):
        self.root = None  # Root of the AVL tree

    # Get height of a node
    def height(self, node):
        return node.height if node else 0

    # Get balance factor of a node
    def get_balance(self, node):
        return self.height(node.left) - self.height(node.right) if node else 0

    # Right rotate subtree rooted with y
    def rotate_right(self, y):
        x = y.left
        T2 = x.right
        x.right = y
        y.left = T2
        # Update heights
        y.height = max(self.height(y.left), self.height(y.right)) + 1
        x.height = max(self.height(x.left), self.height(x.right)) + 1
        print(f"Right rotation performed on {y.data}")
        return x

    # Left rotate subtree rooted with x
    def rotate_left(self, x):
        y = x.right
        T2 = y.left
        y.left = x
        x.right = T2
        # Update heights
        x.height = max(self.height(x.left), self.height(x.right)) + 1
        y.height = max(self.height(y.left), self.height(y.right)) + 1
        print(f"Left rotation performed on {x.data}")
        return y

    # Insert a node into the AVL tree
    def insert(self, node, data):
        if not node:
            print(f"Inserting {data}")
            return Node(data)
        
        # Insert in left or right subtree
        if data < node.data:
            node.left = self.insert(node.left, data)
        elif data > node.data:
            node.right = self.insert(node.right, data)
        else:
            return node
        
        # Update height of ancestor node
        node.height = 1 + max(self.height(node.left), self.height(node.right))
        
        # Get balance factor
        balance = self.get_balance(node)
        
        # Perform rotations if unbalanced
        if balance > 1 and data < node.left.data:  # Left Left Case
            return self.rotate_right(node)
        if balance < -1 and data > node.right.data:  # Right Right Case
            return self.rotate_left(node)
        if balance > 1 and data > node.left.data:  # Left Right Case
            node.left = self.rotate_left(node.left)
            return self.rotate_right(node)
        if balance < -1 and data < node.right.data:  # Right Left Case
            node.right = self.rotate_right(node.right)
            return self.rotate_left(node)
        
        return node

    # Find node with minimum value
    def min_value_node(self, node):
        while node.left:
            node = node.left
        return node

    # Delete a node from the AVL tree
    def delete(self, node, data):
        if not node:
            return node
        
        # Traverse the tree to find the node to delete
        if data < node.data:
            node.left = self.delete(node.left, data)
        elif data > node.data:
            node.right = self.delete(node.right, data)
        else:
            # Node with one child or no child
            if not node.left or not node.right:
                temp = node.left if node.left else node.right
                node = temp if temp else None
            else:
                # Node with two children: get the inorder successor
                temp = self.min_value_node(node.right)
                node.data = temp.data
                node.right = self.delete(node.right, temp.data)
        
        if not node:
            return node
        
        # Update height
        node.height = max(self.height(node.left), self.height(node.right)) + 1
        
        # Balance the node if needed
        balance = self.get_balance(node)
        if balance > 1 and self.get_balance(node.left) >= 0:
            return self.rotate_right(node)
        if balance > 1 and self.get_balance(node.left) < 0:
            node.left = self.rotate_left(node.left)
            return self.rotate_right(node)
        if balance < -1 and self.get_balance(node.right) <= 0:
            return self.rotate_left(node)
        if balance < -1 and self.get_balance(node.right) > 0:
            node.right = self.rotate_right(node.right)
            return self.rotate_left(node)
        
        return node

    # Search for a node in the AVL tree
    def search(self, node, data):
        if not node or node.data == data:
            return node
        return self.search(node.left, data) if data < node.data else self.search(node.right, data)

    # Perform in-order traversal of the AVL tree
    def in_order(self, node):
        if node:
            self.in_order(node.left)
            print(node.data, end=" ")
            self.in_order(node.right)

    # Helper function to print in-order traversal
    def print_in_order(self):
        self.in_order(self.root)
        print()

    # Wrapper functions for inserting, deleting, and searching
    def insert_value(self, data):
        self.root = self.insert(self.root, data)

    def delete_value(self, data):
        self.root = self.delete(self.root, data)

    def search_value(self, data):
        return self.search(self.root, data) is not None

if __name__ == "__main__":
    avl = AVLTree()
    while True:
        print("1. Insert\n2. Delete\n3. Search\n4. InOrder Traversal\n5. Exit")
        choice = int(input("Enter choice: "))
        if choice == 1:
            value = int(input("Enter integer to insert: "))
            avl.insert_value(value)
            print(f"Inserted {value}")
        elif choice == 2:
            value = int(input("Enter integer to delete: "))
            avl.delete_value(value)
            print(f"Deleted {value}")
        elif choice == 3:
            value = int(input("Enter integer to search: "))
            found = avl.search_value(value)
            print(f"Found: {found}")
        elif choice == 4:
            print("InOrder Traversal:")
            avl.print_in_order()
        elif choice == 5:
            break
        else:
            print("Invalid choice, please try again.")

