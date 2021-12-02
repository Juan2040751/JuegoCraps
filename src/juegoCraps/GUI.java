package juegoCraps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is used for as View Crps class
 * @autor Juan Jose Viafara 2040751 viafara.juan@correounivalle.edu.co
 * @version v.1.0.0 date:2/12/2021
 */
public class GUI extends JFrame {
    private static final String MENSAJE_INICIO="Bienvenido a Craps \n"
            +"Oprime el boton para Lanzar para iniciar el juego"
            +"\n Si tu tiro de salida es 7 u 11 ganas con Natural"
            +"\n Si tu tiro de salida es 2, 3 u 12 pierdes con Craps"
            +"\n Si sacas cualquier otro valor estableceras el Punto"
            +"\n Estando en Punto podras seguir lanzando los dados"
            +"\n pero ahora ganaras si sacas nuevamente el valor del Punto "
            +"\n sin que previamente hayas sacado 7";

    private Header headerProject;
    private JLabel dado1, dado2;
    private JButton lanzar;
    private JPanel panelDados, panelResultado;
    private ImageIcon imageDado;
    private JTextArea mensajeSalida, resultadosDados;
    private JSeparator separator;
    private Escucha escucha;
    private ModelCraps modelCraps;
    private int flag;

    /**
     * Constructor of GUI class
     */
    public GUI(){
        initGUI();

        //Default JFrame configuration
        this.setTitle("Juego Craps");
        //this.setSize(200,100);
        this.pack();
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This method is used to set up the default JComponent Configuration,
     * create Listener and control Objects used for the GUI class
     */
    private void initGUI() {
        //Set up JFrame Container's Layout
        //Create Listener Object or Control Object
        escucha = new Escucha();
        modelCraps = new ModelCraps();
        //Set up JComponents
        headerProject = new Header("Mesa Juego Craps", Color.BLACK);
        this.add(headerProject, BorderLayout.NORTH);

        imageDado = new ImageIcon(getClass().getResource("/resources/dado.png"));
        dado1 = new JLabel(imageDado);
        dado2 = new JLabel(imageDado);
        dado1.setSize(130,108);
        dado2.setSize(130,108);

        lanzar = new JButton("lanzar");
        lanzar.addActionListener(escucha);

        panelDados = new JPanel();
        panelDados.setPreferredSize(new Dimension(300, 180));
        panelDados.setBorder(BorderFactory.createTitledBorder("Tus Dados "));
        panelDados.add(dado1);
        panelDados.add(dado2);
        panelDados.add(lanzar);

        this.add(panelDados, BorderLayout.CENTER);

        mensajeSalida = new JTextArea(7, 31);
        mensajeSalida.setText(MENSAJE_INICIO);
        //mensajeSalida.setBorder(BorderFactory.createTitledBorder("Que debes hacer  "));
        JScrollPane scroll = new JScrollPane(mensajeSalida);

        panelResultado = new JPanel();
        panelResultado.setBorder(BorderFactory.createTitledBorder("Que debes hacer  "));
        panelResultado.add(scroll);
        panelResultado.setPreferredSize(new Dimension(370,180));

        this.add(panelResultado, BorderLayout.EAST);

        resultadosDados = new JTextArea(4,31);
        separator = new JSeparator();
        separator.setPreferredSize(new Dimension(320,7));
        separator.setBackground(Color.BLUE);
        flag = 0;
    }
    /**
     * Main process of the Java program
     * @param args Object used in order to send input data from command line when
     *             the program is execute by console.
     */
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            GUI miProjectGUI = new GUI();
        });
    }

    /**
     * inner class that extends an Adapter Class or implements Listeners used by GUI class
     */
    private class Escucha implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            modelCraps.calcularTiro();
            int[] caras = modelCraps.getCaras();
            imageDado = new ImageIcon(getClass().getResource("/resources/dado "+caras[0]+".png"));
            dado1.setIcon(imageDado);
            imageDado = new ImageIcon(getClass().getResource("/resources/dado "+caras[1]+".png"));
            dado2.setIcon(imageDado);
            modelCraps.determinarJuego();
            if(flag==0){
                panelResultado.removeAll();
                panelResultado.setBorder(BorderFactory.createTitledBorder("Resultados  "));
                panelResultado.add(resultadosDados);
                panelResultado.add(separator);
                panelResultado.add(mensajeSalida);
                flag=1;
            }


            resultadosDados.setText(modelCraps.getEstadoToString()[0]);
            mensajeSalida.setRows(4);
            mensajeSalida.setText(modelCraps.getEstadoToString()[1]);
            revalidate();
            repaint();
        }
    }
}
