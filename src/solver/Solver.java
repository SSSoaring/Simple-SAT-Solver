package solver;

public abstract class Solver {

	public Problem problem;

	public Solver(Problem p) {
		this.problem = p.clone();
	}

	public Solver(Problem p, int i) {
		this.problem = p;
		this.problem.constraintsNumber = this.problem.constraints.size();
	}

	public abstract boolean solve();
}
