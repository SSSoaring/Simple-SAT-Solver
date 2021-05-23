package solver;

public class BruteForce extends Solver {

	public BruteForce(Problem p) {
		super(p);
	}

	public BruteForce(Problem p, int i) {
		super(p, i);
	}

	@Override
	public boolean solve() {
		if (this.problem.constraintsNumber == 0)
			return true;

		int l = this.problem.constraints.get(0).choose();
		Problem p1 = this.problem.clone();
		
		if (p1.force(l) && p1.solve(new BruteForce(p1, 0))) {
			return true;
		}
		
		Problem p2 = this.problem.clone();
		if (p2.force(-l) && p2.solve(new BruteForce(p2, 0))) {
			return true;
		}
		
		return false;
	}
}
