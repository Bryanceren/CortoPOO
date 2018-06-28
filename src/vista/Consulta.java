/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

/**
 *
 * @author LN710Q
 */
import dao.ProductoDao;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;

/**
 *
 * @author LN710Q
 */
public class Consulta extends JFrame {

    public JLabel lblCodigo,lblPrecio,lblNombre,lblCantidad,lblTipo,lblDisponibilidad;
    public JTextField codigo, precio, nombre,cantidad;
    public JComboBox tipo;

    ButtonGroup disponibilidad = new ButtonGroup();
    public JRadioButton no;
    public JRadioButton si;
    public JTable resultados;

    public JPanel table;
    public JButton buscar, eliminar, insertar, limpiar, actualizar;

    private static final int ANCHOC = 130, ALTOC = 30;

    DefaultTableModel tm;

    public Consulta() {
        super("Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        agregarLabels();
        formulario();
        llenarTabla();
        Container container = getContentPane();
        container.add(lblCodigo);
        container.add(lblPrecio);
        container.add(lblNombre);
        container.add(lblCantidad);
        container.add(lblTipo);
        container.add(lblDisponibilidad);
        container.add(codigo);
        container.add(precio);
        container.add(nombre);
        container.add(cantidad);
        container.add(si);
        container.add(no);
        container.add(tipo);
        container.add(buscar);
        container.add(insertar);
        container.add(actualizar);
        container.add(eliminar);
        container.add(limpiar);
        container.add(table);
        setSize(950, 800);
        eventos();
    }

    public final void agregarLabels() {
        lblCodigo = new JLabel("Codigo");
        lblPrecio = new JLabel("Precio");
        lblNombre = new JLabel("Nombre");
        lblCantidad = new JLabel("Cantidad");
        lblTipo = new JLabel("Tipo");
        lblDisponibilidad = new JLabel("Disponibilidad");

        lblCodigo.setBounds(10, 10, ANCHOC, ALTOC);
        lblPrecio.setBounds(10, 60, ANCHOC, ALTOC);
        lblNombre.setBounds(10, 100, ANCHOC, ALTOC);
        lblCantidad.setBounds(10, 140, ANCHOC, ALTOC);
        lblTipo.setBounds(10, 180, ANCHOC, ALTOC);
        lblDisponibilidad.setBounds(10, 230, ANCHOC, ALTOC);

    }

    public final void formulario() {
        codigo = new JTextField();
        precio = new JTextField();
        nombre = new JTextField();
        cantidad = new JTextField();
        tipo = new JComboBox();
        
        si = new JRadioButton("si", true);
        no = new JRadioButton("no",false);
        resultados = new JTable();
        buscar = new JButton("Buscar");
        insertar = new JButton("Insertar");
        eliminar = new JButton("Eliminar");
        actualizar = new JButton("Actualizar");
        limpiar = new JButton("Limpiar");

        table = new JPanel();
        tipo.addItem("Fruta");
        tipo.addItem("Verdura");
        tipo.addItem("Bebida");
        tipo.addItem("Dulce");

        disponibilidad = new ButtonGroup();
        disponibilidad.add(si);
        disponibilidad.add(no);

        codigo.setBounds(140, 10, ANCHOC, ALTOC);
        precio.setBounds(140, 60, ANCHOC, ALTOC);
        nombre.setBounds(140, 100, ANCHOC, ALTOC);
        cantidad.setBounds(140, 140, ANCHOC, ALTOC);
        tipo.setBounds(140, 180, ANCHOC, ALTOC);
        si.setBounds(140, 230, 50, ALTOC);
        no.setBounds(210, 230, 50, ALTOC);

        buscar.setBounds(290, 10, ANCHOC, ALTOC);
        insertar.setBounds(390, 230, ANCHOC, ALTOC);
        actualizar.setBounds(520, 230, ANCHOC, ALTOC);
        eliminar.setBounds(650, 230, ANCHOC, ALTOC);
        limpiar.setBounds(780, 230, ANCHOC, ALTOC);
        resultados = new JTable();
        table.setBounds(10, 270, 600, 300);
        table.add(new JScrollPane(resultados));
    }

    public void llenarTabla() {
        tm = new DefaultTableModel() {
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return Boolean.class;
                    case 4:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };
        tm.addColumn("Codigo");
        tm.addColumn("Nombre");
        tm.addColumn("Tipo");
        tm.addColumn("Disponibilidad");
        tm.addColumn("Precio");
        tm.addColumn("Cantidad");
        

        ProductoDao fd = new ProductoDao();
        ArrayList<Producto> productos = fd.readAll();

        for (Producto fi : productos) {
            tm.addRow(new Object[]{fi.getCodigo(), fi.getNombre(), fi.getTipo(), fi.getDisponibilidad(), fi.getPrecio(), fi.getCantidad()});
        }
        resultados.setModel(tm);
    }

    public void eventos() {
        insertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoDao fd = new ProductoDao();
                Producto f = new Producto(codigo.getText(), Double.parseDouble(precio.getText()),nombre.getText(),Integer.parseInt(cantidad.getText()),
                        tipo.getSelectedItem().toString(),true);

                if (no.isSelected()) {
                    f.setDisponibilidad(false);
                }

                if (fd.create(f)) {
                    JOptionPane.showMessageDialog(null, "Filtro registrado con exito");
                    limpiarCampos();
                    llenarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema el momento de crear el filtro");
                }
            }

        });
        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoDao fd = new ProductoDao();
                Producto f = new Producto(codigo.getText(), Double.parseDouble(precio.getText()),nombre.getText(),Integer.parseInt(cantidad.getText()),
                        tipo.getSelectedItem().toString(),true);
                if (no.isSelected()) {
                    f.setDisponibilidad(false);
                }

                if (fd.update(f)) {
                    JOptionPane.showMessageDialog(null, "Filtro modificado con exito");
                    limpiarCampos();
                    llenarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema el momento de modificar el filtro");
                }
            }
        
        });
        eliminar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ProductoDao fd=new ProductoDao();
                if(fd.delete(codigo.getText())){
                    JOptionPane.showMessageDialog(null, "Filtro eliminado con exito");
                    limpiarCampos();
                    llenarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema al momento de eliminar el filtro");
                }
            }
        });
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoDao fd = new ProductoDao();
                Producto f = fd.read(codigo.getText());
                
                if (f==null) {
                    JOptionPane.showMessageDialog(null, "El Filtro no se ha encontrado");
                } else {
                    codigo.setText(f.getCodigo());
                    precio.setText(Double.toString(f.getPrecio()));
                    nombre.setText(f.getNombre());
                    cantidad.setText(Integer.toString(f.getCantidad()));
                    tipo.setSelectedItem(f.getTipo());
                    
                    if(f.getDisponibilidad()){
                        si.setSelected(true);
                    } else{
                        no.setSelected(true);
                    }
                
                }
            }
       });
       limpiar.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
               limpiarCampos();
           }
       });
    }
    
    public void limpiarCampos(){
        codigo.setText("");
        tipo.setSelectedItem("Fruta");
        precio.setText("");
        nombre.setText("");
        cantidad.setText("");
        
        
    }
    
    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new Consulta().setVisible(true);
            }
        });
    }
}
        
        
        
