package solver;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Problem {
	public String filename;
	public int literalNumber;
	public int constraintsNumber;
	public ArrayList<Constraint> constraints = new ArrayList<Constraint>();
	public boolean result;

	public Problem(String file) {
		this.filename = file;
		if (file.substring(file.length() - 5, file.length() - 4).equals("s"))
			this.result = true;
		else
			this.result = false;
	}

	public boolean loadData() {
		File f = new File(this.filename);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
			String oneline = null;

			if ((oneline = reader.readLine()) != null)
				if (!this.checkHeader(oneline)) {
					System.out.println("File input is illegal.");
					return false;
				}

			int constraints = 0;

			try {
				while ((oneline = reader.readLine()) != null) {
					this.constraints.add(new Constraint(oneline, this.literalNumber));
					++constraints;
				}
			} catch (ConstraintError e) {
				System.out.println(e.getMessage());
				return false;
			} catch (Exception e) {
				System.out.println("File input is illegal.");
				return false;
			}

			if (constraints != this.constraintsNumber) {
				System.out.println("Constraint Error");
				return false;
			}

			Collections.sort(this.constraints);

			reader.close();

		} catch (IOException e) {
			System.out.println("Cannot open the file " + this.filename);
			return false;
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (Exception e1) {
			}
		}
		return true;
	}

	private boolean checkHeader(String s) {
		String[] keywords = s.trim().split("\\s+");
		if (keywords.length == 4) {
			if (keywords[0].equals("p") && keywords[1].equals("cnf")) {
				this.literalNumber = Integer.valueOf(keywords[2]).intValue();
				this.constraintsNumber = Integer.valueOf(keywords[3]).intValue();
				if (this.literalNumber > 0 && this.constraintsNumber > 0)
					return true;
			}
		}
		return false;
	}

	public Problem clone() {
		Problem p = new Problem(this.filename);
		p.constraintsNumber = this.constraintsNumber;
		p.literalNumber = this.literalNumber;
		p.constraints = new ArrayList<Constraint>();
		for (Constraint c : this.constraints) {
			p.constraints.add(c.clone());
		}
		return p;
	}

	public boolean force(int l) {
		Constraint c;
		for (int i = 0; i < this.constraints.size(); ++i) {
			c = this.constraints.get(i);
			if (c.contains(l)) {
				this.constraints.remove(c);
				i--;
			}
			if (c.contains(-l)) {
				if (!c.remove(-l))
					return false;
			}
		}
		return true;
	}

	public String toString() {
		return "Problem@" + "cnf:" + this.literalNumber + " " + this.constraintsNumber + " "
				+ (this.result ? "SAT" : "UN-SAT");
	}

	public void traverse() {
		for (Constraint c : this.constraints) {
			System.out.println(c);
		}
	}

	public boolean solve(Solver s) {
		return s.solve();
	}
}
