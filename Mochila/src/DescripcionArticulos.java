
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    /**
     * Crea la interfaz que permite al usuario ingresar los datos de loa artículos
     */
    private void createGUI() {
        this.setTitle("Descripción de artículos");
        setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane();
        table = new JTable();
        pane.setViewportView(table);
        JPanel eastPanel = new JPanel();
        btnAdd = new JButton("Agregar");
        btnNext = new JButton("Siguiente");
        btnNext.setEnabled(false);
        eastPanel.add(btnAdd);
        eastPanel.add(btnNext);
        JPanel northPanel = new JPanel();
        txtField1 = new JTextField();
        txtField2 = new JTextField();
        txtField3 = new JTextField();
        JLabel lblField1 = new JLabel("Nombre   "); //tres datos que el usuario
        JLabel lblField2 = new JLabel("Peso   ");   //debe ingresar
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
        table.setModel(tableModel); //Tabla en la que se van prentando los datos de los articulos agregados
        btnAdd.addActionListener(new ActionListener(){ //Boton que confirma la agregación de un nuevo articulo a la tabla de articulos
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(Double.parseDouble(txtField2.getText()) < 0 || Double.parseDouble(txtField3.getText()) < 0 || txtField3.getText() == null){
                        JOptionPane.showMessageDialog(null, "Uno o varios campos contienen información no válida","Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        int count = tableModel.getRowCount()+1;
                        tableModel.addRow(new Object[]{txtField1.getText(),txtField2.getText(),txtField3.getText()});
                        txtField1.setText("");
                        txtField2.setText("");
                        txtField3.setText("");
                        btnNext.setEnabled(true);
                    }
                }catch(NumberFormatException ex){ //Digitó algo que no era un número
                    JOptionPane.showMessageDialog(null, "Uno o varios campos contienen información no válida", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnNext.addActionListener(new ActionListener(){ //Boton que permite indicar que se terminó la adicion de articulos a la tabla
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Articulo> articulos = new ArrayList<Articulo>(); //estructura con los articulos de la tabla
                for(int i=0;i<table.getRowCount();++i){
                    Articulo articulo = new Articulo(table.getValueAt(i, 0).toString(),Double.parseDouble(table.getValueAt(i, 1).toString()),Double.parseDouble(table.getValueAt(i, 2).toString()));
                    articulos.add(articulo); //Se crea un objeto articulo y se agrega
                }
                controlador = new Controlador(articulos,pesoMochila,tipoOptimizacion,probabilidadCruce,probabilidadMutacion);
                controlador.run(); // Se llama al metodo del controlador que ejecuta el algoritmo que busca la solucion
                
                ArrayList<Cromosoma> resultados = controlador.getMejoresCromosomas();
                int pesoTotal = 0;
                int utilidadTotal = 0;
                dispose();
                if(resultados.isEmpty()){
                    JOptionPane.showMessageDialog(null, "No se encontró una solución factible", "Resultado Final", JOptionPane.INFORMATION_MESSAGE);
                }
                for(int i=0;i<resultados.size();++i){
                     String resultado = "";
                     String cromosoma = resultados.get(i).getCromosoma();
                     pesoTotal = 0;
                     utilidadTotal = 0;
                     for(int j=0;j<cromosoma.length();++j){
                         if(cromosoma.charAt(j)=='1'){
                             resultado += "Articulo: "+articulos.get(j).getNombre()+",\tPeso: "+articulos.get(j).getPeso()+",\tUtilidad: "+articulos.get(j).getUtilidad()+"\n";
                             pesoTotal+= articulos.get(j).getPeso();
                             utilidadTotal += articulos.get(j).getUtilidad();
                         }  
                     } //Se muestran los datos de los mejores cromosomas de cada generacion
                     resultado += "Peso total: "+pesoTotal +"\nUtilidad total: "+utilidadTotal;
                     if(i== resultados.size()-1){
                         String mensaje = "Se le aconseja llevar los siguientes artículos:\n\n"+resultado;
                        JOptionPane.showMessageDialog(null, mensaje, "Resultado Final", JOptionPane.INFORMATION_MESSAGE);
                     }else{
                        String mensaje = "El mejor cromosoma es:\n\n"+resultado;
                        JOptionPane.showMessageDialog(null, mensaje, "Resultado Generación "+i, JOptionPane.INFORMATION_MESSAGE);
                     }
                }
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
