package scanner;

import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;

public class Scanner {

	public static void main(String[] args) {

		String A = "test", B = "test";
		int C = 5;
		Triplet<String, String, Integer> testT = new Triplet<>(A,B,C);
		List<Triplet<String, String, Integer>> testL = new ArrayList<>();
	}
}