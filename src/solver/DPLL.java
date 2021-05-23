package solver;

public class DPLL extends Solver {

	public DPLL(Problem p) {
		super(p);
	}

	public DPLL(Problem p, int i) {
		super(p, i);
	}

	@Override
	public boolean solve() {
		boolean flag = true;
		while (flag) {
			flag = false;

			for (int i = 0; i < this.problem.constraints.size(); ++i) {
				Constraint c = this.problem.constraints.get(i);
				if (c.getSize() == 1) {
					flag = true;
					this.problem.constraints.remove(i);
					i--;
					for (int l : c.list) {
						if (!this.problem.force(l))
							return false;
					}
				}
			}
		}

		if (this.problem.constraints.size() == 0)
			return true;

		int l = this.problem.constraints.get(0).choose();
		Problem p1 = this.problem.clone();

		if (p1.force(l) && p1.solve(new DPLL(p1, 0))) {
			return true;
		}

		Problem p2 = this.problem.clone();
		if (p2.force(-l) && p2.solve(new DPLL(p2, 0))) {
			return true;
		}
		return false;
	}

}
