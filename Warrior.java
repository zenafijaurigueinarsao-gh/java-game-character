public class Warrior extends GameCharacter {
    public Warrior(String name, int level, int health) {
        super(name, level, health);
    }
    
    @Override
    public String attack() {
        return getName() + " swings a heavy sword!";
    }
    
    @Override
    public String specialMove() {
        return getName() + " uses Power Strike! ⚔";
    }
    
    @Override
    public String getType() {
        return "Warrior";
    }
}
