import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
	// Initializes the data structure using the given array of strings as the
	// dictionary.
	// (You can assume each word in the dictionary contains only the uppercase
	// letters A through Z.)
	private Trie dictionary;

	private Map<Integer, Integer> points;

	private int[] neighbourX;
	private int[] neighbourY;

	public BoggleSolver(String[] dictionary) {
		this.dictionary = new Trie();
		for (String word : dictionary) {
			this.dictionary.insert(word);
		}
		points = new HashMap<>();
		points.put(0, 0);
		points.put(1, 0);
		points.put(2, 0);
		points.put(3, 1);
		points.put(4, 1);
		points.put(5, 2);
		points.put(6, 3);
		points.put(7, 5);
		points.put(8, 11);

		neighbourX = new int[] { 1, -1, -1, 1, -1, 1, 0, 0 };
		neighbourY = new int[] { -1, -1, 1, 1, 0, 0, -1, 1 };

	}

	// Returns the set of all valid words in the given Boggle board, as an Iterable.
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		int row = board.rows();
		int col = board.cols();
		Set<String> ans = new HashSet<>();
		for (int outer = 0; outer < row; outer++) {
			for (int inner = 0; inner < col; inner++) {
				boolean[][] visited = new boolean[row][col];
				List<String> temp = new ArrayList<>();
				getAllValidWords(board, outer, inner, ans, temp, 0, visited);
			}
		}
		return ans;
	}

	private void getAllValidWords(BoggleBoard board, int outer, int inner, Set<String> ans, List<String> temp,
			int index, boolean[][] visited) {
		visited[outer][inner] = true;
		if (temp.size() > index) {
			temp.set(index, String.valueOf(board.getLetter(outer, inner)));
		} else {
			temp.add(String.valueOf(board.getLetter(outer, inner)));
		}

		String word = getWord(temp, index);
		if (!dictionary.hasPrefix(word)) {
			visited[outer][inner] = false;
			return;
		}
		if (word.length() >= 3 && dictionary.contains(word)) {
			ans.add(word);
		}
		int length = neighbourX.length;
		for (int rowIndex = 0; rowIndex < length; rowIndex++) {
			int targetRowIndex = outer + neighbourX[rowIndex];
			int targetColIndex = inner + neighbourY[rowIndex];
			if (targetRowIndex >= 0 && targetRowIndex < board.rows() && targetColIndex >= 0
					&& targetColIndex < board.cols() && !visited[targetRowIndex][targetColIndex]) {
				getAllValidWords(board, targetRowIndex, targetColIndex, ans, temp, index + 1, visited);
			}
		}
		visited[outer][inner] = false;
	}

	private String getWord(List<String> temp, int index) {
		StringBuilder builder = new StringBuilder();
		for (int tempIndex = 0; tempIndex <= index; tempIndex++) {
			if (temp.get(tempIndex).equals("Q")) {
				builder.append("Qu");
			} else {
				builder.append(temp.get(tempIndex));
			}
		}
		return builder.toString().toUpperCase();
	}

	// Returns the score of the given word if it is in the dictionary, zero
	// otherwise.
	// (You can assume the word contains only the uppercase letters A through Z.)
	public int scoreOf(String word) {
		if (!dictionary.contains(word)) {
			return 0;
		}
		int length = word.length();
		if (length > 8) {
			return 11;
		}
		return points.get(length);
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		String[] dictionary = in.readAllStrings();
		BoggleSolver solver = new BoggleSolver(dictionary);
		BoggleBoard board = new BoggleBoard(args[1]);
		int score = 0;
		int count = 0;
		for (String word : solver.getAllValidWords(board)) {
			StdOut.println(word);
			score += solver.scoreOf(word);
			count++;
		}
		StdOut.println("Score = " + score);
		System.out.println(count);
	}

}