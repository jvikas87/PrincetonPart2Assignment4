
public class Trie {

	private Node root;

	public Trie() {
		this.root = new Node();
	}

	private static class Node {
		private Node[] array = new Node[26];
		private boolean hasValue;
	}

	public void insert(String value) {
		insert(value, 0, root);
	}

	public void insert(String value, int index, Node root) {
		if (index == value.length()) {
			root.hasValue = true;
			return;
		}
		int position = value.charAt(index) - 'A';
		if (root.array[position] == null)
			root.array[position] = new Node();
		insert(value, index + 1, root.array[position]);
	}

	public boolean contains(String value) {
		return contains(value, 0, root);
	}

	private boolean contains(String value, int index, Node root) {
		if (root == null) {
			return false;
		}
		if (index == value.length()) {
			return root.hasValue;
		}
		return contains(value, index + 1, root.array[value.charAt(index) - 'A']);
	}

	public boolean hasPrefix(String value) {
		Node head = root;
		int index = 0;
		while (index < value.length()) {
			int position = value.charAt(index++) - 'A';
			if (head.array[position] == null) {
				return false;
			}
			head = head.array[position];
		}
		return true;
	}

	public static void main(String[] args) {
		Trie trie = new Trie();
		trie.insert("A");
		trie.insert("AN");

		System.out.println(trie.hasPrefix("B"));
		System.out.println(trie.hasPrefix("BB"));
	}
}
