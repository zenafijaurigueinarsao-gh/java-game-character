public class Mage extends GameCharacter {
    public Mage(String name, int level, int health)
    { super(name, level, health);
    }
    
    @Override
    public String attack() {
        return getName() + " casts a magical bolt!";
    }
    
    @Override
    public String specialMove() {
        return getName() + " unleashes Fireball! ✦";
    }
    
    @Override
    public String getType() {
        return "Mage";
    }
}
