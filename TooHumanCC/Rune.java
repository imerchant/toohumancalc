class Rune {
	public String name;
	public int val;
	public Rune.Effect effect;
	public int tier;
	enum Effect {
		AGGRESSION ("Aggression","Increases enemy \"aggro\" in co-op games.",1000);
		
		final String desc;
		final int cap;
		final String name;
		Effect(String n, String description, int c) {
			name = n;
			desc = description;
			cap = c;
		}
	}
}