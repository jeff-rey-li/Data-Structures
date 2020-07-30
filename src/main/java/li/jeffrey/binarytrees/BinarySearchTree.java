package li.jeffrey.binarytrees;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/**
 * This class implements a Binary Search Tree (BST) of any comparable data type. It supports add(), remove(),
 * contains(), size(), isEmpty(), and height() as well as iterators for each of the following traversals: Inorder traversal,
 * Preorder traversal, Postorder traversal, and Levelorder traversal.
 *
 * @author Jeffrey Li
 */
public class BinarySearchTree<T extends Comparable<T>> {

    private Node root;
    private int size;

    private class Node {
        T data;
        Node left;
        Node right;

        private Node(T data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    public boolean add(T data) {
        if (contains(data)) {
            return false;
        } else {
            root = addHelper(root, data);
            size++;
            return true;
        }

    }

    private Node addHelper(Node node, T data) {
        if (node == null) {
            return new Node(data);
        }
        if (data.compareTo(node.data) < 0) {
            node.left = addHelper(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = addHelper(node.right, data);
        }
        return node;
    }

    public boolean remove(T data) {
        if (!contains(data)) {
            return false;
        } else {
            root = removeHelper(root, data);
            size--;
            return true;
        }
    }

    private Node removeHelper(Node node, T data) {
        if (node == null) {
            throw new RuntimeException();
        }
        if (data.compareTo(node.data) < 0) {
            node.left = removeHelper(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = removeHelper(node.right, data);
        } else {
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node toReplace = node.right;
                while (toReplace.left != null) {
                    toReplace = toReplace.left;
                }
                node.right = removeHelper(node.right, toReplace.data);
                node.data = toReplace.data;
                return node;
            }
        }
        return node;
    }

    public boolean contains(T data) {
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

    // Wrapper over print2DUtil()
    public void print(Node root) {
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
