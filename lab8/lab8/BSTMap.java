package lab8;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;                             // root of this BST
    private int size;                              // size of this BST

    private class Node {
        private K key;
        private V value;
        private Node left, right;
        private Node parent;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "(" + this.key + ", " + this.value + ")";
        }
    }

    public BSTMap() {
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key entered is null.");
        }
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        Node res = find(key);
        if (res == null) {
            return null;
        }
        return res.value;
    }

    private Node find(K key) {
        Node p = root;
        while (p != null) {
            int cmp = key.compareTo(p.key);
            if (cmp < 0) {
                p = p.left;
            } else if (cmp > 0) {
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key argument is null.");
        }
        if (value == null) {
            throw new IllegalArgumentException("value argument is null.");
        }
        Node newNode = new Node(key, value);
        if (root == null) {
            root = newNode;
            size++;
            return;
        }
        Node p = root;
        while (p != null) {
            int cmp = key.compareTo(p.key);
            if (cmp < 0) {
                if (p.left != null) {
                    p = p.left;
                } else {
                    p.left = newNode;
                    newNode.parent = p;
                    size++;
                    break;
                }
            } else if (cmp > 0) {
                if (p.right != null) {
                    p = p.right;
                } else {
                    p.right = newNode;
                    newNode.parent = p;
                    size++;
                    break;
                }
            } else {
                p.value = value;
                break;
            }
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new TreeSet();
        keySet(root, keys);
        return keys;
    }

    private void keySet(Node x, Set<K> keys) {
        if (x == null) {
            return;
        }
        if (x.left != null) {
            keySet(x.left, keys);
        }
        keys.add(x.key);
        if (x.right != null) {
            keySet(x.right, keys);
        }
    }

    /** An iterator that iterates over the keys of the BSTMap. */
    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    @Override
    public V remove(K key) {
        Node remove = find(key);

        if (remove == null) {
            return null;
        }

        V res = remove.value;
        removeNode(remove);
        return res;
    }

    private void removeNode(Node remove) {
        if (remove.left == null && remove.right == null) {
            if (remove.parent != null) {
                int cmp = remove.key.compareTo(remove.parent.key);
                if (cmp < 0) {
                    remove.parent.left = null;
                } else if (cmp > 0) {
                    remove.parent.right = null;
                }
            } else {
                root = null;
            }
        } else if (remove.left == null) {
            if (remove.parent != null) {
                int cmp = remove.key.compareTo(remove.parent.key);
                if (cmp < 0) {
                    remove.parent.left = remove.right;
                } else if (cmp > 0) {
                    remove.parent.right = remove.right;
                }
                remove.right.parent = remove.parent;
            } else {
                root = remove.right;
                root.parent = null;

            }
        } else if (remove.right == null) {
            if (remove.parent != null) {
                int cmp = remove.key.compareTo(remove.parent.key);
                if (cmp < 0) {
                    remove.parent.left = remove.left;
                } else if (cmp > 0) {
                    remove.parent.right = remove.left;
                }
                remove.left.parent = remove.parent;
            } else {
                root = remove.left;
                root.parent = null;
            }
        } else {
            Node newChild;
            remove.right = newChild = swapSmallest(remove.right, remove);
            if (newChild != null) {
                newChild.parent = remove;
            }
        }
        size--;
    }

    private Node swapSmallest(Node t, Node r) {
        if (t.left == null) {
            r.key = t.key;
            r.value = t.value;
            return t.right;
        } else {
            t.left = swapSmallest(t.left, r);
            if (t.left != null) {
                t.left.parent = t;
            }
            return t;
        }
    }

    @Override
    public V remove(K key, V value) {
        Node remove = find(key);
        if (remove == null || !remove.value.equals(value)) {
            return null;
        }

        removeNode(remove);
        return value;
    }

    /** Prints out this BSTMap in order of increasing Key. */
    public void printInOrder() {
        List<Node> inOrder = new ArrayList<>();
        keysInOrder(root, inOrder);
        System.out.println(Arrays.toString(inOrder.toArray()));
    }

    /** Returns a list of nodes in order of increasing Key. */
    private void keysInOrder(Node x, List<Node> inOrderKeys) {
        if (x == null) {
            return;
        }
        keysInOrder(x.left, inOrderKeys);
        inOrderKeys.add(x);
        keysInOrder(x.right, inOrderKeys);
    }

    public Iterable<Node> levelOrder() {
        Queue<Node> queue = new LinkedList<>();
        List<Node> res = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node t = queue.poll();
            res.add(t);
            if (t.left != null) {
                queue.add(t.left);
            }
            if (t.right != null) {
                queue.add(t.right);
            }
        }
        return res;
    }

    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) {
            return -1;
        }
        return 1 + Math.max(height(x.left), height(x.right));
    }

    public static void main(String[] args) {
        BSTMap<Integer, String> bst = new BSTMap<>();
        bst.put(10, "a");
        bst.put(6, "b");
        bst.put(4, "c");
        bst.put(15, "d");
        bst.put(8, "e");
        bst.put(30, "f");
        bst.put(1, "g");
        bst.put(7, "h");
        bst.put(3, "i");
        bst.put(18, "j");
        bst.put(12, "k");
        System.out.println(bst.size());
        System.out.println(bst.levelOrder().toString());
        System.out.println(bst.containsKey(30));
        bst.printInOrder();
        bst.remove(15);
        bst.printInOrder();
        bst.remove(10);
        bst.printInOrder();
        System.out.println(bst.keySet());
        bst.remove(4);
        bst.printInOrder();
        System.out.println(bst.root.key);
        System.out.println(bst.get(18));
        System.out.println(bst.remove(1, "a"));
        bst.printInOrder();
        System.out.println(bst.remove(1, "g"));
        bst.printInOrder();
    }
}
