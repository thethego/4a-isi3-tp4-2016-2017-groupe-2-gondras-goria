package view;

import controler.Controller;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by theo on 12/04/17.
 */
public class SimpleLogo extends JFrame implements ActionListener {
    private static final Dimension VGAP = new Dimension(1,5);
    private static final Dimension HGAP = new Dimension(5,1);
    private final int width = 1024;
    private final int height = 600;

    private int nbTurtles;
    private DrawSheet sheet;
    private JTextField inputValue;
    private Controller controller;


    public SimpleLogo(int nbTurtles) {
        super("un logo tout simple");
        this.nbTurtles = nbTurtles;
        logoInit();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                super.windowClosing(arg0);
                System.exit(0);
            }
        });
    }

    public void logoInit() {
        getContentPane().setLayout(new BorderLayout(10,10));

        int mode = -1;
        //Custom button text
        Object[] options = {"quitter",
                "tortues contrôlées",
                "tortues autonomes",
                "tortues en mode flocking"
        };
        mode = JOptionPane.showOptionDialog(this,
                "Quel mode voulez-vous lancer ?",
                "choix du mode",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);

        if(mode<1){
            System.exit(0);
        }
        this.setResizable(false);

        // Boutons
        JToolBar toolBar = new JToolBar();
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(toolBar);

        getContentPane().add(buttonPanel,"North");

        addButton(toolBar,"Effacer","Nouveau dessin","/icons/index.png");

        toolBar.add(Box.createRigidArea(this.HGAP));
        this.inputValue=new JTextField("45",5);
        if(mode != 1){
            this.inputValue.setVisible(false);
        }
        toolBar.add(this.inputValue);
        if(mode==1) {
            addButton(toolBar, "Avancer", "Avancer 50", null);
            addButton(toolBar, "Droite", "Droite 45", null);
            addButton(toolBar, "Gauche", "Gauche 45", null);
        }

        addButton(toolBar, "Lever", "Lever Crayon", null);
        addButton(toolBar, "Baisser", "Baisser Crayon", null);
        addButton(toolBar, "Lever toutes", "Lever Crayon pour toutes les tortues", null);
        addButton(toolBar, "Baisser toutes", "Baisser Crayon pour toutes les tortues", null);
        addButton(toolBar,"Ajouter","Ajouter une tortue",null);

        String[] colorStrings = {"noir", "bleu", "cyan","gris fonce","rouge",
                "vert", "gris clair", "magenta", "orange",
                "gris", "rose", "jaune"};

        // Create the combo box
        toolBar.add(Box.createRigidArea(this.HGAP));
        JLabel colorLabel = new JLabel("   Couleur: ");
        toolBar.add(colorLabel);
        JComboBox colorList = new JComboBox(colorStrings);
        toolBar.add(colorList);

        colorList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                int n = cb.getSelectedIndex();
                controller.handleAction("setColor",n+"");
            }
        });


        // Menus
        JMenuBar menubar=new JMenuBar();
        setJMenuBar(menubar);	// on installe le menu bar
        JMenu menuFile=new JMenu("File"); // on installe le premier menu
        menubar.add(menuFile);

        addMenuItem(menuFile, "Effacer", "Effacer", KeyEvent.VK_N);
        addMenuItem(menuFile, "Quitter", "Quitter", KeyEvent.VK_Q);

        JMenu menuCommandes=new JMenu("Commandes"); // on installe le premier menu
        menubar.add(menuCommandes);
        if(mode == 1){
            addMenuItem(menuCommandes, "Avancer", "Avancer", -1);
            addMenuItem(menuCommandes, "Droite", "Droite", -1);
            addMenuItem(menuCommandes, "Gauche", "Gauche", -1);
        }
        addMenuItem(menuCommandes, "Lever Crayon", "Lever", -1);
        addMenuItem(menuCommandes, "Baisser Crayon", "Baisser", -1);

        JMenu menuHelp=new JMenu("Aide"); // on installe le premier menu
        menubar.add(menuHelp);
        addMenuItem(menuHelp, "Aide", "Help", -1);
        addMenuItem(menuHelp, "A propos", "About", -1);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // les boutons du bas
        if(mode == 1){
            JPanel p2 = new JPanel(new GridLayout());
            JButton b20 = new JButton("Proc1");
            p2.add(b20);
            b20.addActionListener(this);
            JButton b21 = new JButton("Proc2");
            p2.add(b21);
            b21.addActionListener(this);
            JButton b22 = new JButton("Proc3");
            p2.add(b22);
            b22.addActionListener(this);

            getContentPane().add(p2,"South");

        }

        // Création de la feuille de dessin
        this.sheet = new DrawSheet();
        this.sheet.setBackground(Color.white);
        this.sheet.setSize(new Dimension(width,height));
        this.sheet.setPreferredSize(new Dimension(width,height));
        this.sheet.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.handleAction("changeTurtle",e.getX()+","+e.getY());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                controller.handleAction("mouseMoved",e.getX()+","+e.getY());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                controller.handleAction("disableObjective",null);
            }
        });
        this.sheet.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                controller.handleAction("mouseMoved",e.getX()+","+e.getY());
            }
        });

        getContentPane().add(this.sheet,"Center");

        // Creation du model
        Dimension size = sheet.getSize();
        Model model = Model.getInstance();
        Model.Initialize(width,height,mode,nbTurtles);
        model.addObserver(this.sheet);
        this.sheet.update(model, model.getObstacles());
        this.sheet.update(model, model.getTurtles());

        // On créé le controleur des tortues
        this.controller = new Controller(model);

        pack();
        setVisible(true);
    }

    /** la gestion des actions des boutons */
    public void actionPerformed(ActionEvent e)
    {
        controller.handleAction(e.getActionCommand(),inputValue.getText());
    }


    //utilitaires pour installer des boutons et des menus
    public void addButton(JComponent p, String name, String tooltiptext, String imageName) {
        JButton b;
        if ((imageName == null) || (imageName.equals(""))) {
            b = (JButton)p.add(new JButton(name));
        }
        else {
            java.net.URL u = this.getClass().getResource(imageName);
            if (u != null) {
                ImageIcon im = new ImageIcon (u);
                b = (JButton) p.add(new JButton(im));
            }
            else
                b = (JButton) p.add(new JButton(name));
            b.setActionCommand(name);
        }

        b.setToolTipText(tooltiptext);
        b.setBorder(BorderFactory.createRaisedBevelBorder());
        b.setMargin(new Insets(0,0,0,0));
        b.addActionListener(this);
    }

    public void addMenuItem(JMenu m, String label, String command, int key) {
        JMenuItem menuItem;
        menuItem = new JMenuItem(label);
        m.add(menuItem);

        menuItem.setActionCommand(command);
        menuItem.addActionListener(this);
        if (key > 0) {
            if (key != KeyEvent.VK_DELETE)
                menuItem.setAccelerator(KeyStroke.getKeyStroke(key, Event.CTRL_MASK, false));
            else
                menuItem.setAccelerator(KeyStroke.getKeyStroke(key, 0, false));
        }
    }
}
