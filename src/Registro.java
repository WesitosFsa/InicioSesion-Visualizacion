import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Registro extends JFrame {
    private JTextField txtnombre;
    private JTextField txtcodigo;
    private JTextField txtcedula;
    private JTextField txtcontraseña;
    private JButton registrarseButton;
    private JPanel panelregistro;
    public Registro(Connection conexion) {
        super("Registro");
        setContentPane(panelregistro);
        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombrecito = txtnombre.getText();
                String codiguito = txtcodigo.getText();
                String cedulita = txtcedula.getText();
                String contraseñita = txtcontraseña.getText();

                insertarDatos(conexion, nombrecito, codiguito, cedulita, contraseñita);
            }
        });
    }
    public void insertarDatos(Connection conexion, String nombre, String codigo, String cedula, String contraseña) {
        String query = "INSERT INTO usuarios (nombre, codigo, cedula, contraseña) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, codigo);
            preparedStatement.setString(3, cedula);
            preparedStatement.setString(4, contraseña);

            int filasInsertadas = preparedStatement.executeUpdate();

            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "Datos correctos", "DataCorrecta", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "error", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void conexion() {
        String url = "jdbc:mysql://localhost:3306/capacitacion";
        String usuarioDB = "root";
        String contraseñaDB = "123456";

        try (Connection conexion = DriverManager.getConnection(url, usuarioDB, contraseñaDB)) {

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo en la bd", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
