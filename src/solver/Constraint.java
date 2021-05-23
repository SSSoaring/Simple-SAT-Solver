package solver;

import java.util.HashSet;

public class Constraint implements Comparable<Constraint>, Cloneable {
	public HashSet<Integer> list = new HashSet<Integer>();

	public Constraint() {
	}

	public Constraint(String s, int uplimit) throws ConstraintError {
		String[] numbers = s.trim().split("\\s+");
		for (String n : numbers) {
			int literal = Integer.valueOf(n).intValue();
			if (literal == 0)
				break;
			if (literal > uplimit || literal < -uplimit)
				throw new ConstraintError("Literal are too many.");
			this.list.add(literal);
		}
		if (this.list.size() == 0)
			throw new ConstraintError("UN-SAT");
	}

	public boolean eval(HashSet<Integer> solution) {
		for (int i : this.list) {
			if (solution.contains(i))
				return true;
			if (!solution.contains(-i))
				return true;
		}
		return false;
	}

	public int getSize() {
		return this.list.size();
	}

	public boolean contains(int l) {
		return this.list.contains(l);
	}

	public int choose() {
		return this.list.iterator().next();
	}

	public boolean remove(int l) {
		if (this.list.size() <= 1)
			return false;
		else
			this.list.remove(l);
		return true;
	}

	public String toString() {
		String s = "";
		for (int i : this.list) {
			s += i + " ";
		}
		return s;
	}

	@Override
	public int compareTo(Constraint o) {
		return this.list.size() - ((Constraint) o).list.size();
	}

	public Constraint clone() {
		Constraint c = new Constraint();
		for (int l : this.list) {
			c.list.add(l);
		}
		return c;
	}
}
