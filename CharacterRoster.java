public abstract class CharacterRoster {
	private String name;
    private int level;
    private int health;

    public void GameCharacter(String name, int level, int health) {
        setName(name);
        setLevel(level);
        setHealth(health);
    }

    public abstract String attack();
    public abstract String specialMove();
    public abstract String getType();

    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getHealth() { return health; }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Character name cannot be empty.");
        this.name = name.trim();
    }
    public void setLevel(int level) {
        if (level < 1 || level > 100) throw new IllegalArgumentException("Level must be between 1 and 100.");
        this.level = level;
    }
    public void setHealth(int health) {
        if (health < 1 || health > 1000) throw new IllegalArgumentException("Health must be between 1 and 1000.");
        this.health = health;
    }

    @Override public String toString() { return name + " (" + getType() + ")"; }

	protected abstract void add(Warrior warrior);

	protected abstract int indexOfName(String name2);

	protected abstract String size();
}
