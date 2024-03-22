import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class RegistroVacunas extends JFrame {
    private HashMap<String, ArrayList<String>> registroVacunas;

    public RegistroVacunas() {
        registroVacunas = new HashMap<>();

        // Cargar registros desde el archivo
        cargarRegistros();

        setTitle("Registro de Vacunas");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextField cuiField = new JTextField(10);
        JButton buscarButton = new JButton("Buscar");
        JTextArea resultadoArea = new JTextArea(10, 20);
        resultadoArea.setEditable(false);

        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cui = cuiField.getText();
                if (registroVacunas.containsKey(cui)) {
                    resultadoArea.setText(obtenerRegistroVacunas(cui));
                } else {
                    resultadoArea.setText("No existe la persona que se registra.");
                }
            }
        });

        panel.add(cuiField, BorderLayout.NORTH);
        panel.add(buscarButton, BorderLayout.CENTER);
        panel.add(new JScrollPane(resultadoArea), BorderLayout.SOUTH);

        add(panel);
    }

    private String obtenerRegistroVacunas(String cui) {
        StringBuilder registro = new StringBuilder();
        ArrayList<String> vacunas = registroVacunas.get(cui);
        registro.append("Registro de vacunas para CUI ").append(cui).append(":\n");
        for (String vacuna : vacunas) {
            registro.append(vacuna).append("\n");
        }
        return registro.toString();
    }

    private void cargarRegistros() {
        try (Scanner scanner = new Scanner(new File("C:\\Users\\leslie\\Desktop\\Vacunas1.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":");
                String cui = parts[0];
                String[] vacunas = parts[1].substring(1, parts[1].length() - 1).split(",");
                ArrayList<String> vacunasList = new ArrayList<>(Arrays.asList(vacunas));
                registroVacunas.put(cui, vacunasList);
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No se pudo cargar el archivo de vacunas.");
        }
    }

    void guardarRegistros() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("\u202AC:\\Users\\leslie\\Desktop\\Vacunas1.txt"))) {
            for (Map.Entry<String, ArrayList<String>> entry : registroVacunas.entrySet()) {
                writer.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo guardar el archivo de vacunas.");
        }
    }

    public static void main(String[] args) {
        RegistroVacunas ventana = new RegistroVacunas();
        ventana.setVisible(true);

        // Guardar registros al cerrar la ventana
        ventana.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ventana.guardarRegistros();
            }
        });
    }
};


