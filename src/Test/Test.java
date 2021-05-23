package Test;

import solver.BruteForce;
import solver.DPLL;
import solver.Problem;

public class Test {
	public static void run(String file) {
		Problem p = new Problem(file);
		if (p.loadData()) {
			System.out.println(p);

			long startTime = System.currentTimeMillis();
			System.out.println("DPLL:");
			boolean result = p.solve(new DPLL(p));
			if (result == p.result)
				System.out.println(" ✔ " + (result ? "SAT" : "UN-SAT"));
			else
				System.out.println(" ✘ " + (result ? "SAT" : "UN-SAT"));

			long endTime = System.currentTimeMillis();
			double usedTime = (endTime - startTime) / 1000.0;

			System.out.println(" Running time: " + usedTime + " s");

			System.out.println("Brute-Force:");
			startTime = System.currentTimeMillis();
			result = p.solve(new BruteForce(p));
			if (result == p.result)
				System.out.println(" ✔ " + (result ? "SAT" : "UN-SAT"));
			else
				System.out.println(" ✘ " + (result ? "SAT" : "UN-SAT"));

			endTime = System.currentTimeMillis();
			usedTime = (endTime - startTime) / 1000.0;

			System.out.println(" Running time: " + usedTime + " s");
		}
	}

	public static void main(String[] args) {
		run("doc/p1s.cnf");
		run("doc/p2u.cnf");
		run("doc/p3s.cnf");
		run("doc/p4u.cnf");
		run("doc/p5u.cnf");
		run("doc/p6u.cnf");
		run("doc/p7u.cnf");
		run("doc/p8s.cnf");
	}
}

