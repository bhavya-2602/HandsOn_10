class Node:
    def __init__(self, data):
        self.data = data
        self.color = True  # True for red, False for black
        self.parent = None
        self.left = None
        self.right = None


class RedBlackTree:
    def __init__(self):
        self.TNULL = Node(0)  # Sentinel node to represent NULL leaves
        self.TNULL.color = False  # Black color
        self.root = self.TNULL  # Root of the tree

    # Helper function for pre-order traversal
    def pre_order_helper(self, node):
        if node != self.TNULL:
            print(node.data, end=" ")
            self.pre_order_helper(node.left)
            self.pre_order_helper(node.right)

    # Left rotate around a node
    def left_rotate(self, x):
        y = x.right
        x.right = y.left
        if y.left != self.TNULL:
            y.left.parent = x
        y.parent = x.parent
        if x.parent is None:
            self.root = y
        elif x == x.parent.left:
            x.parent.left = y
        else:
            x.parent.right = y
        y.left = x
        x.parent = y

    # Right rotate around a node
    def right_rotate(self, x):
        y = x.left
        x.left = y.right
        if y.right != self.TNULL:
            y.right.parent = x
        y.parent = x.parent
        if x.parent is None:
            self.root = y
        elif x == x.parent.right:
            x.parent.right = y
        else:
            x.parent.left = y
        y.right = x
        x.parent = y

    # Fix Red-Black Tree properties after insertion
    def fix_insert(self, k):
        while k.parent and k.parent.color:
            if k.parent == k.parent.parent.left:
                u = k.parent.parent.right  # Uncle node
                if u.color:  # Case 1: Uncle is red
                    u.color = False
                    k.parent.color = False
                    k.parent.parent.color = True
                    k = k.parent.parent
                else:
                    if k == k.parent.right:  # Case 2: Node is right child
                        k = k.parent
                        self.left_rotate(k)
                    # Case 3: Node is left child
                    k.parent.color = False
                    k.parent.parent.color = True
                    self.right_rotate(k.parent.parent)
            else:
                u = k.parent.parent.left  # Uncle node
                if u.color:  # Case 1: Uncle is red
                    u.color = False
                    k.parent.color = False
                    k.parent.parent.color = True
                    k = k.parent.parent
                else:
                    if k == k.parent.left:  # Case 2: Node is left child
                        k = k.parent
                        self.right_rotate(k)
                    # Case 3: Node is right child
                    k.parent.color = False
                    k.parent.parent.color = True
                    self.left_rotate(k.parent.parent)
            if k == self.root:
                break
        self.root.color = False  # Ensure root is always black

    # Insert a new node into the Red-Black Tree
    def insert(self, key):
        node = Node(key)
        node.left = self.TNULL
        node.right = self.TNULL
        
        y = None
        x = self.root
        
        while x != self.TNULL:
            y = x
            if node.data < x.data:
                x = x.left
            else:
                x = x.right
        
        node.parent = y  # Set parent pointer
        if y is None:
            self.root = node  # Tree was empty
        elif node.data < y.data:
            y.left = node
        else:
            y.right = node
        
        if node.parent is None:
            node.color = False  # Root must be black
            return
        
        if node.parent.parent is None:
            return
        
        self.fix_insert(node)  # Fix violations if any

    # Print tree structure in a readable format
    def print_helper(self, root, indent, last):
        if root != self.TNULL:
            print(indent, end="")
            if last:
                print("R----", end="")
                indent += "   "
            else:
                print("L----", end="")
                indent += "|  "
            
            s_color = "RED" if root.color else "BLACK"
            print(f"{root.data}({s_color})")
            self.print_helper(root.left, indent, False)
            self.print_helper(root.right, indent, True)

    # Public method to print the tree
    def print_tree(self):
        self.print_helper(self.root, "", True)


# Main function for user interaction
def main():
    bst = RedBlackTree()
    
    while True:
        print("1. Insert\n2. Print Tree\n3. Exit")
        choice = int(input("Enter choice: "))
        
        if choice == 1:
            insert_value = int(input("Enter integer to insert: "))
            bst.insert(insert_value)
            print(f"Inserted {insert_value}")
        elif choice == 2:
            bst.print_tree()
        elif choice == 3:
            break
        else:
            print("Invalid choice, please try again.")


# Run the program
if __name__ == "__main__":
    main()

