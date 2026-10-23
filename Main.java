import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {

    private static final Color BG = new Color(19, 24, 39);
    private static final Color PANEL = new Color(29, 37, 57);
    private static final Color ACCENT = new Color(102, 126, 234);
    private static final Color TEXT = new Color(235, 240, 255);
    private static final Color MUTED = new Color(164, 176, 202);

    private final CharacterRoster roster = new CharacterRoster();

    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"Name", "Class", "Level", "Health"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JTable table = new JTable(model);
    private final JTextField nameField = new JTextField();
    private final JTextField searchField = new JTextField();

    private final JSpinner levelSpinner =
            new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

    private final JSpinner healthSpinner =
            new JSpinner(new SpinnerNumberModel(100, 1, 1000, 10));

    private final JComboBox<String> typeBox =
            new JComboBox<>(new String[]{"Warrior", "Mage", "Archer"});

    private final JLabel status =
            new JLabel("Create your first character to begin.");

    public Main() {
        super("Character Forge | Game Character Manager");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(850, 570));
        setSize(1050, 700);
        setLocationRelativeTo(null);

        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(14, 14));
        ((JComponent) getContentPane()).setBorder(
                new EmptyBorder(18, 18, 14, 18));

        createHeader();
        createMainInterface();
        createFooter();

        // Sample characters
        roster.add(new Warrior("Astra", 8, 240));
        roster.add(new Mage("Merlin", 6, 130));
        roster.add(new Archer("Robin", 7, 170));

        refreshTable(roster.getAllCharacters());

        status.setText("Roster ready! Select a character to edit.");
    }

    private void createHeader() {
        JLabel title = new JLabel("CHARACTER FORGE");
        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));

        JLabel subtitle = new JLabel(
                "Build your roster. Choose your class. Unleash your power.");
        subtitle.setForeground(MUTED);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);

        add(header, BorderLayout.NORTH);
    }

    private void createMainInterface() {
        configureTable();

        JPanel editor = createEditorPanel();

        JPanel left = new JPanel(new BorderLayout(0, 8));
        left.setOpaque(false);
        left.setPreferredSize(new Dimension(280, 400));

        JLabel editorTitle = new JLabel("CHARACTER DETAILS");
        editorTitle.setForeground(TEXT);
        editorTitle.setFont(new Font("SansSerif", Font.BOLD, 13));

        left.add(editorTitle, BorderLayout.NORTH);
        left.add(editor, BorderLayout.CENTER);

        JPanel rosterPanel = new JPanel(new BorderLayout(0, 9));
        rosterPanel.setOpaque(false);

        JPanel searchPanel = new JPanel(new BorderLayout(8, 0));
        searchPanel.setOpaque(false);

        searchField.setToolTipText("Search by character name or class");

        JButton searchButton = button("Search", ACCENT);
        JButton showAll = button("Show All", new Color(73, 85, 111));

        JPanel searchButtons = new JPanel(new GridLayout(1, 2, 6, 0));
        searchButtons.setOpaque(false);
        searchButtons.add(searchButton);
        searchButtons.add(showAll);

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButtons, BorderLayout.EAST);

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(PANEL);
        scroll.setBorder(BorderFactory.createLineBorder(
                new Color(57, 67, 91)));

        rosterPanel.add(searchPanel, BorderLayout.NORTH);
        rosterPanel.add(scroll, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout(0, 10));
        center.setOpaque(false);
        center.add(rosterPanel, BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, left, center);

        split.setResizeWeight(0.32);
        split.setDividerLocation(300);
        split.setBorder(null);

        add(split, BorderLayout.CENTER);

        searchButton.addActionListener(e -> searchCharacters());
        showAll.addActionListener(e -> {
            searchField.setText("");
            refreshTable(roster.getAllCharacters());
        });

        searchField.addActionListener(e -> searchCharacters());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelected();
            }
        });
    }

    private void configureTable() {
        table.setBackground(PANEL);
        table.setForeground(TEXT);
        table.setGridColor(new Color(57, 67, 91));
        table.setSelectionBackground(new Color(65, 81, 130));
        table.setSelectionForeground(Color.WHITE);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setFillsViewportHeight(true);

        table.getTableHeader().setBackground(new Color(42, 52, 78));
        table.getTableHeader().setForeground(TEXT);
        table.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 12));
    }

    private JPanel createEditorPanel() {
        JPanel editor = new JPanel(new GridBagLayout());
        editor.setBackground(PANEL);
        editor.setBorder(new EmptyBorder(14, 14, 14, 14));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 4, 5, 4);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        addField(editor, g, 0, "Character Name", nameField);
        addField(editor, g, 2, "Character Class", typeBox);
        addField(editor, g, 4, "Level (1-100)", levelSpinner);
        addField(editor, g, 6, "Health (1-1000)", healthSpinner);

        JPanel actions = new JPanel(new GridLayout(2, 2, 7, 7));
        actions.setOpaque(false);

        JButton add = button("＋ Add", ACCENT);
        JButton update = button("✎ Update", new Color(58, 145, 126));
        JButton remove = button("− Remove", new Color(184, 76, 91));
        JButton clear = button("Clear", new Color(73, 85, 111));

        actions.add(add);
        actions.add(update);
        actions.add(remove);
        actions.add(clear);

        g.gridx = 0;
        g.gridy = 8;
        g.gridwidth = 2;
        editor.add(actions, g);

        JPanel abilities = new JPanel(new GridLayout(1, 2, 7, 7));
        abilities.setOpaque(false);

        JButton attack = button("▶ Attack", new Color(79, 91, 125));
        JButton special = button("✦ Special", new Color(112, 83, 160));

        abilities.add(attack);
        abilities.add(special);

        g.gridy = 9;
        editor.add(abilities, g);

        add.addActionListener(e -> createCharacter());
        update.addActionListener(e -> updateCharacter());
        remove.addActionListener(e -> removeCharacter());
        clear.addActionListener(e -> clearForm());

        attack.addActionListener(e -> showMove(false));
        special.addActionListener(e -> showMove(true));

        return editor;
    }

    private void createFooter() {
        status.setForeground(MUTED);
        status.setFont(new Font("SansSerif", Font.PLAIN, 12));
        add(status, BorderLayout.SOUTH);
    }

    private void addField(JPanel panel, GridBagConstraints g,
                          int y, String label, JComponent field) {
        g.gridx = 0;
        g.gridy = y;
        g.gridwidth = 2;

        JLabel l = new JLabel(label);
        l.setForeground(MUTED);
        l.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panel.add(l, g);

        g.gridy = y + 1;
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));

        if (field instanceof JTextField) {
            field.setBackground(new Color(20, 27, 43));
            field.setForeground(TEXT);
            ((JTextField) field).setCaretColor(TEXT);
        } else if (field instanceof JComboBox) {
            field.setBackground(new Color(20, 27, 43));
            field.setForeground(TEXT);
        }

        panel.add(field, g);
    }

    private JButton button(String text, Color color) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setBorder(new EmptyBorder(9, 8, 9, 8));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private GameCharacter buildCharacter() {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please enter a character name.");
        }

        if (name.length() > 30) {
            throw new IllegalArgumentException(
                    "Name must be 30 characters or fewer.");
        }

        if (!name.matches("[\\p{L}0-9 _'-]+")) {
            throw new IllegalArgumentException(
                    "Use letters, numbers, spaces, apostrophes, "
                    + "underscores, or hyphens in names.");
        }

        int level = (Integer) levelSpinner.getValue();
        int health = (Integer) healthSpinner.getValue();

        String type = (String) typeBox.getSelectedItem();

        switch (type) {
            case "Mage":
                return new Mage(name, level, health);
            case "Archer":
                return new Archer(name, level, health);
            default:
                return new Warrior(name, level, health);
        }
    }

    private void createCharacter() {
        try {
            GameCharacter c = buildCharacter();

            if (roster.findByName(c.getName()) != null) {
                error("A character with this name already exists.");
                return;
            }

            roster.add(c);
            searchField.setText("");
            refreshTable(roster.getAllCharacters());
            selectCharacter(c.getName());

            status.setText("Created " + c.getName() + " successfully.");

        } catch (IllegalArgumentException ex) {
            error(ex.getMessage());
        }
    }

    private void updateCharacter() {
        int row = table.getSelectedRow();

        if (row < 0) {
            error("Select a character before updating.");
            return;
        }

        String oldName = String.valueOf(model.getValueAt(row, 0));

        try {
            GameCharacter c = buildCharacter();

            GameCharacter existing = roster.findByName(c.getName());

            if (existing != null
                    && !oldName.equalsIgnoreCase(c.getName())) {
                error("Another character already uses this name.");
                return;
            }

            if (!roster.update(oldName, c)) {
                error("Unable to update the selected character.");
                return;
            }

            searchField.setText("");
            refreshTable(roster.getAllCharacters());
            selectCharacter(c.getName());

            status.setText("Updated " + c.getName() + " successfully.");

        } catch (IllegalArgumentException ex) {
            error(ex.getMessage());
        }
    }

    private void removeCharacter() {
        int row = table.getSelectedRow();

        if (row < 0) {
            error("Select a character before removing.");
            return;
        }

        String name = String.valueOf(model.getValueAt(row, 0));

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Remove " + name + " from the roster?",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            roster.remove(name);
            searchField.setText("");
            refreshTable(roster.getAllCharacters());
            clearForm();

            status.setText("Removed " + name + ".");
        }
    }

    private void clearForm() {
        table.clearSelection();
        nameField.setText("");
        typeBox.setSelectedIndex(0);
        levelSpinner.setValue(1);
        healthSpinner.setValue(100);
    }

    private void loadSelected() {
        int row = table.getSelectedRow();

        if (row < 0) {
            return;
        }

        String name = String.valueOf(model.getValueAt(row, 0));
        GameCharacter c = roster.findByName(name);

        if (c == null) {
            return;
        }

        nameField.setText(c.getName());
        typeBox.setSelectedItem(c.getType());
        levelSpinner.setValue(c.getLevel());
        healthSpinner.setValue(c.getHealth());
    }

    private void selectCharacter(String name) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString()
                    .equalsIgnoreCase(name)) {
                table.setRowSelectionInterval(i, i);
                return;
            }
        }
    }

    private void refreshTable(List<GameCharacter> characters) {
        model.setRowCount(0);

        for (GameCharacter c : characters) {
            model.addRow(new Object[]{
                    c.getName(),
                    c.getType(),
                    c.getLevel(),
                    c.getHealth()
            });
        }

        status.setText("Showing " + characters.size()
                + " character(s) | Total: " + roster.size());
    }

    private void searchCharacters() {
        String q = searchField.getText().trim().toLowerCase();

        if (q.isEmpty()) {
            refreshTable(roster.getAllCharacters());
            return;
        }

        List<GameCharacter> found = new ArrayList<>();

        for (GameCharacter c : roster.getAllCharacters()) {
            if (c.getName().toLowerCase().contains(q)
                    || c.getType().toLowerCase().contains(q)) {
                found.add(c);
            }
        }

        refreshTable(found);
        status.setText("Search results: " + found.size()
                + " match(es) for \"" + q + "\".");
    }

    private void showMove(boolean special) {
        int row = table.getSelectedRow();

        if (row < 0) {
            error("Select a character first.");
            return;
        }

        String name = String.valueOf(model.getValueAt(row, 0));
        GameCharacter c = roster.findByName(name);

        if (c != null) {
            String message = special
                    ? c.specialMove()
                    : c.attack();

            JOptionPane.showMessageDialog(
                    this,
                    message,
                    special ? "Special Move" : "Basic Attack",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Check Your Input",
                JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Use the default appearance if unavailable.
            }

            Main app = new Main();
            app.setVisible(true);
        });
    }

    static abstract class GameCharacter {
        protected String name;
        protected int level;
        protected int health;

        public GameCharacter(String name, int level, int health) {
            this.name = name;
            this.level = level;
            this.health = health;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }

        public int getHealth() {
            return health;
        }

        public abstract String getType();

        public abstract String attack();

        public abstract String specialMove();
    }

    static class Warrior extends GameCharacter {
        public Warrior(String name, int level, int health) {
            super(name, level, health);
        }

        @Override
        public String getType() {
            return "Warrior";
        }

        @Override
        public String attack() {
            return name + " uses Sword Strike! "
                    + "A powerful melee attack.";
        }

        @Override
        public String specialMove() {
            return name + " uses Power Strike! "
                    + "A devastating blow from a mighty warrior.";
        }
    }

    static class Mage extends GameCharacter {
        public Mage(String name, int level, int health) {
            super(name, level, health);
        }

        @Override
        public String getType() {
            return "Mage";
        }

        @Override
        public String attack() {
            return name + " casts Magic Bolt! "
                    + "A blast of magical energy.";
        }

        @Override
        public String specialMove() {
            return name + " casts Fireball! "
                    + "A fiery magical explosion.";
        }
    }

    static class Archer extends GameCharacter {
        public Archer(String name, int level, int health) {
            super(name, level, health);
        }

        @Override
        public String getType() {
            return "Archer";
        }

        @Override
        public String attack() {
            return name + " shoots an Arrow! "
                    + "A precise ranged attack.";
        }

        @Override
        public String specialMove() {
            return name + " uses Piercing Shot! "
                    + "An arrow that pierces through enemies.";
        }
    }

    static class CharacterRoster {
        private final List<GameCharacter> characters =
                new ArrayList<>();

        public void add(GameCharacter character) {
            characters.add(character);
        }

        public List<GameCharacter> getAllCharacters() {
            return new ArrayList<>(characters);
        }

        public GameCharacter findByName(String name) {
            for (GameCharacter c : characters) {
                if (c.getName().equalsIgnoreCase(name)) {
                    return c;
                }
            }
            return null;
        }

        public boolean update(String oldName, GameCharacter updated) {
            for (int i = 0; i < characters.size(); i++) {
                if (characters.get(i).getName()
                        .equalsIgnoreCase(oldName)) {
                    characters.set(i, updated);
                    return true;
                }
            }
            return false;
        }

        public boolean remove(String name) {
            GameCharacter c = findByName(name);

            if (c != null) {
                characters.remove(c);
                return true;
            }

            return false;
        }

        public int size() {
            return characters.size();
        }
    }
}
