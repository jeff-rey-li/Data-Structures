package li.jeffrey.binarytrees;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/**
 * This class implements a Red Black Tree (RBT) of any comparable data type. It supports add(), contains(),
 * size(), isEmpty(), and height() as well as iterators for each of the following traversals: Inorder traversal,
 * Preorder traversal, Postorder traversal, and Levelorder traversal.
 *
 * @author Jeffrey Li
 */
public class RedBlackTree<T extends Comparable<T>> {

    private Node root;
    private int size;

    private enum NodeColor {
        RED,
        BLACK
    }

    private class Node {
        private T data;
        private NodeColor color;
        private Node left;
        private Node right;

        private Node(T data) {
            this.data = data;
            color = NodeColor.RED;
            left = null;
            right = null;
        }
    }

    public RedBlackTree() {
        root = null;
        size = 0;
    }

    public boolean insert(T data) {
        if (data == null) {
            return false;
        }
        if (contains(data)) {
            return false;
        }
        root = insertHelper(root, data);
        root.color = NodeColor.BLACK;
        size++;
        return true;
    }

    private Node insertHelper(Node current, T data) {
        if (current == null) {
            return new Node(data);
        }
        // Color swap while traversing through tree
        // Black Parent with two Red Children -> Red Parent with two Black Children
        if (current.left != null && current.right != null) {
            if (current.left.color == NodeColor.RED && current.right.color == NodeColor.RED) {
                current.color = NodeColor.RED;
                current.left.color = NodeColor.BLACK;
                current.right.color = NodeColor.BLACK;
            }
        }
        if (data.compareTo(current.data) < 0) {
            current.left = insertHelper(current.left, data);
        } else if (data.compareTo(current.data) > 0) {
            current.right = insertHelper(current.right, data);
        }

        /* Restructuring after Insertion */

        // Inner Grandchild needs two rotations, first rotate from inner to outer grandchild
        // O       O
        //  \       \
        //   O  ->   O
        //  /         \
        // O           O
        if (current.right != null && current.right.color == NodeColor.RED) {
            if (current.right.left != null && current.right.left.color == NodeColor.RED) {
                current.right = rotateRight(current.right);
            }
        }
        // Outer Grandchild needs rotation
        // O
        //  \          O
        //   O   ->   / \
        //    \      O   O
        //     O
        if (current.right != null && current.right.color == NodeColor.RED) {
            if (current.right.right != null && current.right.right.color == NodeColor.RED) {
                current = rotateLeft(current);
            }
        }
        // Inner Grandchild needs two rotations, first rotate from inner to outer grandchild
        //   O          O
        //  /          /
        // O    ->    O
        //  \        /
        //   O      O
        if (current.left != null && current.left.color == NodeColor.RED) {
            if (current.left.right != null && current.left.right.color == NodeColor.RED) {
                current.left = rotateLeft(current.left);
            }
        }
        // Outer Grandchild needs rotation
        //     O
        //    /        O
        //   O   ->   / \
        //  /        O   O
        // O
        if (current.left != null && current.left.color == NodeColor.RED) {
            if (current.left.left != null && current.left.left.color == NodeColor.RED) {
                current = rotateRight(current);
            }
        }

        return current;
    }

    private Node rotateLeft(Node node) {
        Node temp = node.right;
        node.right = temp.left;
        temp.left = node;
        temp.color = temp.left.color;
        temp.left.color = NodeColor.RED;
        return temp;
    }

    private Node rotateRight(Node node) {
        Node temp = node.left;
        node.left = temp.right;
        temp.right = node;
        temp.color = temp.right.color;
        temp.right.color = NodeColor.RED;
        return temp;
    }

    public boolean remove(T data) {
        throw new UnsupportedOperationException();
    }

    public boolean contains(T data) {
        if (data == null) {
            return false;
        }
        return containsHelper(root, data);
    }

    private boolean containsHelper(Node node, T data) {
        if (node == null) {
            return false;
        }
        if (data.compareTo(node.data) < 0) {
            return containsHelper(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            return containsHelper(node.right, data);
        } else {
            return true;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int height() {
        return heightHelper(root);
    }

    private int heightHelper(Node node) {
        if (node == null) {
            return 0;
        }
        return Math.max(heightHelper(node.left), heightHelper(node.right)) + 1;
    }

    /* Print function from GeeksForGeeks */
    int COUNT = 10;

    // Function to print binary tree in 2D
    // It does reverse inorder traversal
    private void printHelper(Node root, int space) {
        // Base case
        if (root == null)
            return;

        // Increase distance between levels
        space += COUNT;

        // Process right child first
        printHelper(root.right, space);

        // Print current node after space
        // count
        System.out.print("\n");
        for (int i = COUNT; i < space; i++)
            System.out.print(" ");
        System.out.print(root.data + "\n");

        // Process left child
        printHelper(root.left, space);
    }

    // Prints the Red Black Tree
    public void print() {
        // Pass initial space count as 0
        printHelper(root, 0);
    }

    public Iterator<T> traverse(String order) {
        switch (order) {
            case "INORDER":
                return inOrderTraversal();
            case "PREORDER":
                return preOrderTraversal();
            case "POSTORDER":
                return postOrderTraversal();
            case "LEVELORDER":
                return levelOrderTraversal();
            default:
                return null;
        }
    }

    private Iterator<T> inOrderTraversal() {
        Stack<Node> stack = new Stack<Node>();
        LinkedList<Node> queue = new LinkedList<Node>();
        if (root != null) {
            stack.push(root);
        }
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node.left != null && !queue.contains(node.left)) {
                stack.push(node);
                stack.push(node.left);
                continue;
            }
            if (node.right != null && !queue.contains(node.right)) {
                stack.push(node.right);
            }

            queue.add(node);
        }

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return !(queue.size() == 0);
            }

            @Override
            public T next() {
                return queue.remove().data;
            }
        };
    }

    private Iterator<T> preOrderTraversal() {
        Stack<Node> stack = new Stack<Node>();
        LinkedList<Node> queue = new LinkedList<Node>();
        if (root != null) {
            stack.push(root);
        }
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
            queue.add(node);
        }

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return !(queue.size() == 0);
            }

            @Override
            public T next() {
                return queue.remove().data;
            }
        };
    }

    private Iterator<T> postOrderTraversal() {
        Stack<Node> stack = new Stack<Node>();
        LinkedList<Node> queue = new LinkedList<Node>();
        if (root != null) {
            stack.push(root);
        }
        while (!stack.isEmpty()) {
            Node node = stack.peek();
            if (node.left != null && !queue.contains(node.left)) {
                stack.push(node.left);
                continue;
            }
            if (node.right != null && !queue.contains(node.right)) {
                stack.push(node.right);
                continue;
            }
            stack.pop();
            queue.add(node);
        }

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return !(queue.size() == 0);
            }

            @Override
            public T next() {
                return queue.remove().data;
            }
        };
    }

    private Iterator<T> levelOrderTraversal() {
        LinkedList<Node> queue = new LinkedList<Node>();
        LinkedList<Node> queue2 = new LinkedList<Node>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            Node node = queue.remove();
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
            queue2.add(node);
        }

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return !(queue2.size() == 0);
            }

            @Override
            public T next() {
                return queue2.remove().data;
            }
        };
    }
}
