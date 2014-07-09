
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * @author Mario, Alex
 * @version 07.08.14
 */
public class DescripcionArticulos extends javax.swing.JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd;
    private JButton btnNext;
    private JTextField txtField1;
    private JTextField txtField2;
    private JTextField txtField3;
    private double pesoMochila;
    private String tipoOptimizacion;
    private double probabilidadCruce;
    private double probabilidadMutacion;
    private Controlador controlador;
    /**
     * Creates new form DescripcionArticulos
     */
    public DescripcionArticulos(double pesoMochila,String tipoOptimizacion, double probabilidadCruce, double probabilidadMutacion) {
        initComponents();
        createGUI();
        this.pesoMochila = pesoMochila;
        this.tipoOptimizacion = tipoOptimizacion;
        this.probabilidadCruce = probabilidadCruce;
        this.probabilidadMutacion = probabilidadMutacion;
    }
    
    private void createGUI() {
        setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane();
        table = new JTable();
        pane.setViewportView(table);
        JPanel eastPanel = new JPanel();
        btnAdd = new JButton("Agregar");
        btnNext = new JButton("Siguiente");
        eastPanel.add(btnAdd);
        eastPanel.add(btnNext);
        JPanel northPanel = new JPanel();
        txtField1 = new JTextField();
        txtField2 = new JTextField();
        txtField3 = new JTextField();
        JLabel lblField1 = new JLabel("Nombre   ");
        JLabel lblField2 = new JLabel("Peso   ");
        JLabel lblField3 = new JLabel("Utilidad   ");
        northPanel.add(lblField1);
        northPanel.add(txtField1);
        northPanel.add(lblField2);
        northPanel.add(txtField2);
        northPanel.add(lblField3);
        northPanel.add(txtField3);
        txtField1.setPreferredSize(new Dimension( 100, 24 ));
        txtField2.setPreferredSize(new Dimension( 50, 24 ));
        txtField3.setPreferredSize(new Dimension( 50, 24 ));
        add(northPanel, BorderLayout.NORTH);
        add(eastPanel, BorderLayout.EAST);
        add(pane,BorderLayout.CENTER);
        tableModel = new DefaultTableModel(new Object[]{"Nombre","Peso","Utilidad"},0);
        table.setModel(tableModel);
        btnAdd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int count = tableModel.getRowCount()+1;
                tableModel.addRow(new Object[]{txtField1.getText(),txtField2.getText(),txtField3.getText()});
                txtField1.setText("");
                txtField2.setText("");
                txtField3.setText("");
            }
        });
        btnNext.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               ArrayList<Articulo> articulos = new ArrayList<Articulo>();
               for(int i=0;i<table.getRowCount();++i){
                   Articulo articulo = new Articulo(table.getValueAt(i, 0).toString(),Double.parseDouble(table.getValueAt(i, 1).toString()),Double.parseDouble(table.getValueAt(i, 2).toString()));
                   articulos.add(articulo);
               }
               controlador = new Controlador(articulos,pesoMochila,tipoOptimizacion,probabilidadCruce,probabilidadMutacion);
               controlador.run();
            }
        });
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
