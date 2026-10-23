public class Archer extends GameCharacter {
    public Archer(String name, int level, int health) {
        super(name, level, health);
    }
    
    @Override
    public String attack() {
        return getName() + " fires a precise arrow!";
    }
    
    @Override
    public String specialMove() {
        return getName() + " uses Piercing Shot! ➶";
    }
    
    @Override
    public String getType() {
        return "Archer";
    }
}
