# Character Forge — Java Game Character Manager

Character Forge upgrades the original console-based game-character example into a desktop application using Java Swing. It keeps the original abstraction idea and expands it with an interactive roster manager.

## Features
- **Abstraction:** `GameCharacter` is an abstract class with abstract `attack()`, `specialMove()`, and `getType()` methods.
- **Three character classes:** `Warrior` (Power Strike), `Mage` (Fireball), and `Archer` (Piercing Shot), each with its own attack behavior.
- **ADT:** `CharacterRoster` encapsulates an `ArrayList` and exposes add, list, search, update, remove, and size operations.
- **GUI:** Java Swing interface with a dark theme, roster table, editor fields, search, and action buttons.
- **User input / CRUD:** Create, view, search, update, and remove characters.
- **Validation:** Name is required, length is limited, characters must use allowed characters, levels are 1–100, health is 1–1000, and duplicate names are rejected.
- **Move demo:** Select a character and try its basic attack or special move.
- Starts with three sample characters so the interface is ready to demonstrate immediately.

## Requirements
- JDK 8 or later
- No external libraries required

## How to run
1. Download or clone this repository.
2. Open a terminal in the folder containing the `.java` files.
3. Compile:
   ```bash
   javac *.java
   ```
4. Run:
   ```bash
   java Main
   ```

## How the requirements are demonstrated

| Requirement | Where to look | Explanation |
|---|---|---|
| Abstraction | `GameCharacter.java` | Defines shared fields and abstract behaviors. |
| Different behaviors | `Warrior.java`, `Mage.java`, `Archer.java` | Each subclass implements its own attack and special move. |
| ADT | `CharacterRoster.java` | Hides the internal list and provides roster operations. |
| GUI | `Main.java` | Builds the Swing window, table, input controls, and buttons. |
| User input and validation | `Main.java`, `GameCharacter.java`, `CharacterRoster.java` | Reads form values and rejects invalid or duplicate entries. |

## Suggested class demonstration
1. Launch the application and point out the sample roster.
2. Add a character; try leaving the name blank or reusing a name to demonstrate validation.
3. Select a row, change the level or class, and click **Update**.
4. Search by a name or character class, then click **Show all**.
5. Select a character and demonstrate **Basic Attack** and **Special Move**.
6. Remove a character and explain how `CharacterRoster` manages the collection.

## Note
The roster is stored in memory while the program is running. Closing the application resets it to the three sample characters; file/database saving is not part of the current requirements.
