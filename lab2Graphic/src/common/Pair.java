package common;

public class Pair<L, R> {
	
	public final L left;
	public final R right;
	
	private Pair(L l, R r) {
		left = l; right = r;
	}
	
	public static <L, R> Pair<L, R> of(L l, R r) {
		return new Pair<>(l, r);
	}
	
	@Override
	public String toString() {
		return "{ " + left + ", " + right + " }";
	}
	
}
