import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Character collection ADT. Encapsulates storage and exposes safe roster operations. */
public class CharacterRoster {
    private final List<GameCharacter> characters = new ArrayList<>();

    public void add(GameCharacter character) {
        if (character == null) throw new IllegalArgumentException("Character is required.");
        if (findByName(character.getName()) != null) throw new IllegalArgumentException("A character with this name already exists.");
        characters.add(character);
    }
    public List<GameCharacter> getAll() { return Collections.unmodifiableList(new ArrayList<>(characters)); }
    public GameCharacter findByName(String name) {
        if (name == null) return null;
        for (GameCharacter c : characters) if (c.getName().equalsIgnoreCase(name.trim())) return c;
        return null;
    }
    public GameCharacter findByIndex(int index) { return index >= 0 && index < characters.size() ? characters.get(index) : null; }
    public int indexOfName(String name) {
        if (name == null) return -1;
        for (int i = 0; i < characters.size(); i++) if (characters.get(i).getName().equalsIgnoreCase(name.trim())) return i;
        return -1;
    }
    public void update(int index, GameCharacter replacement) {
        if (index < 0 || index >= characters.size()) throw new IllegalArgumentException("Select a character to update.");
        if (replacement == null) throw new IllegalArgumentException("Character is required.");
        for (int i = 0; i < characters.size(); i++) {
            if (i != index && characters.get(i).getName().equalsIgnoreCase(replacement.getName()))
                throw new IllegalArgumentException("Another character already uses this name.");
        }
        characters.set(index, replacement);
    }
    public void remove(int index) {
        if (index < 0 || index >= characters.size()) throw new IllegalArgumentException("Select a character to remove.");
        characters.remove(index);
    }
    public int size() { return characters.size(); }
}
