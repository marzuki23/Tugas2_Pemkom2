import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Generic class untuk menyimpan data inventaris
class Inventory<T> {
    private final List<T> items = new ArrayList<>();

    // Generic method untuk menambahkan item
    public void addItem(T item) {
        items.add(item);
    }

    // Menggunakan wildcard untuk mengambil semua data
    public List<? extends T> getItems() {
        return items;
    }
}

// Class untuk merepresentasikan item inventaris
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

// GUI untuk manajemen inventaris
public class InventoryManager extends JFrame {
    private final Inventory<Item> inventory = new Inventory<>();
    private final DefaultTableModel tableModel;
    private final JTextField nameField;
    private final JTextField categoryField;
    private final JTextField quantityField;

    public InventoryManager() {
        setTitle("Manajer Inventaris - Toko Merbabu");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(230, 240, 250));

        // Judul toko
        JLabel titleLabel = new JLabel("Toko Merbabu - Manajemen Inventaris", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 150));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Panel input
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBackground(new Color(180, 200, 230));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Tambah Barang"));
        inputPanel.setPreferredSize(new Dimension(400, 150));
        
        JLabel nameLabel = new JLabel("Nama Barang:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        nameField = new JTextField();
        
        JLabel categoryLabel = new JLabel("Kategori:");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 12));
        categoryField = new JTextField();
        
        JLabel quantityLabel = new JLabel("Jumlah:");
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 12));
        quantityField = new JTextField();
        
        JButton addButton = new JButton("Tambah Barang");
        addButton.setBackground(new Color(50, 150, 50));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        addButton.setFocusPainted(false);
        
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryField);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel());
        inputPanel.add(addButton);
        
        add(inputPanel, BorderLayout.CENTER);

        // Tabel untuk menampilkan inventaris
        String[] columnNames = {"Nama Barang", "Kategori", "Jumlah"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setBackground(new Color(100, 150, 200));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        add(new JScrollPane(table), BorderLayout.SOUTH);

        // Event Listener untuk tombol tambah
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToInventory();
            }
        });
    }

    // Generic method untuk menambahkan item ke daftar inventaris dengan validasi
    private void addItemToInventory() {
        String name = nameField.getText().trim();
        String category = categoryField.getText().trim();
        String quantityText = quantityField.getText().trim();

        if (name.isEmpty() || category.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka positif!", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Item newItem = new Item(name, category, quantity);
        inventory.addItem(newItem);
        updateTable();
        clearFields();
    }

    // Menampilkan data dalam tabel menggunakan wildcard
    private void updateTable() {
        tableModel.setRowCount(0);
        for (Item item : inventory.getItems()) {
            tableModel.addRow(new Object[]{item.getName(), item.getCategory(), item.getQuantity()});
        }
    }

    // Membersihkan input field setelah penambahan data
    private void clearFields() {
        nameField.setText("");
        categoryField.setText("");
        quantityField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InventoryManager frame = new InventoryManager();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        });
    }
}