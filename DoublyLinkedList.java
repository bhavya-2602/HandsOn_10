
public class DoublyLinkedList {
    class ValueNode {
        int value;
        ValueNode next;

        public ValueNode(int value) {
            this.value = value;
            this.next = null;
        }
    }

    class Node {
        int key;
        Node prev, next;
        ValueNode valueHead;  // Head of the value list for this key

        public Node(int key, int value) {
            this.key = key;
            this.valueHead = new ValueNode(value);
        }
    }

    Node head, tail;

    
    public void insert(int key, int value) {
        Node node = findNode(key);
        if (node != null) {
            appendValue(node, value);  
        } else {
            Node newNode = new Node(key, value);
            if (tail == null) {
                head = tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
        }
    }

    
    private void appendValue(Node node, int value) {
        ValueNode currentValueNode = node.valueHead;
        while (currentValueNode.next != null) {
            currentValueNode = currentValueNode.next;
        }
        currentValueNode.next = new ValueNode(value);
    }

   
    private Node findNode(int key) {
        Node current = head;
        while (current != null) {
            if (current.key == key) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    // Delete a node by key
    public void delete(int key) {
        Node node = findNode(key);
        if (node == null) return;

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    // Find the first value for a given key
    public int find(int key) {
        Node node = findNode(key);
        return (node != null && node.valueHead != null) ? node.valueHead.value : -1;
    }

    // Print all the values for a given key
    public String getAllValues(int key) {
        Node node = findNode(key);
        if (node == null) return null;

        StringBuilder values = new StringBuilder();
        ValueNode currentValueNode = node.valueHead;
        while (currentValueNode != null) {
            values.append(currentValueNode.value);
            if (currentValueNode.next != null) {
                values.append(" -> ");
            }
            currentValueNode = currentValueNode.next;
        }
        return values.toString();
    }
}
