import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Inventory<T> {
    private final List<T> items = new ArrayList<>();

    public void addItem(T item) {
        items.add(item);
    }

    public List<? extends T> getItems() {
        return items;
    }
}

class Item {
    private final String name;
    private final String category;
    private final int quantity;

    public Item(String name, String category, int quantity) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }
}

public class InventoryManager extends JFrame {
    private final Inventory<Item> inventory = new Inventory<>();
    private final DefaultTableModel tableModel;
    private final JTextField nameField;
    private final JTextField categoryField;
    private final JTextField quantityField;

    public InventoryManager() {
        setTitle("Inventory Manager");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.setBackground(new Color(200, 220, 240));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Nama Barang:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        
        inputPanel.add(new JLabel("Kategori:"));
        categoryField = new JTextField();
        inputPanel.add(categoryField);
        
        inputPanel.add(new JLabel("Jumlah:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);
        
        JButton addButton = new JButton("Tambah");
        addButton.setBackground(new Color(80, 180, 100));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        inputPanel.add(addButton);
        
        add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Nama Barang", "Kategori", "Jumlah"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setBackground(Color.white);
        table.setGridColor(Color.GRAY);
        table.setRowHeight(25);
        table.getTableHeader().setBackground(new Color(170, 200, 250));
        table.getTableHeader().setForeground(Color.BLACK);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToInventory();
            }
        });
    }

    private void addItemToInventory() {
        String name = nameField.getText();
        String category = categoryField.getText();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Item newItem = new Item(name, category, quantity);
        inventory.addItem(newItem);
        updateTable();
        clearFields();
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Item item : inventory.getItems()) {
            tableModel.addRow(new Object[]{item.getName(), item.getCategory(), item.getQuantity()});
        }
    }

    private void clearFields() {
        nameField.setText("");
        categoryField.setText("");
        quantityField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryManager().setVisible(true));
    }
}
