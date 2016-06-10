package juego;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;
import javax.swing.*;
import modelo.*;

public class PanelAnimar extends JPanel implements Runnable {

    public static String IMAGEN_FONDO_JUEGO = "fondo.png";

    public static int ANGULO_DERECHO = 360;

    public static int ANGULO_IZQUIERDO = 180;

    private InterfazJuego juego;

    //Hilo que controla la animacion
    Thread objHilo;

    //Objeto que inicia los números aleatorios 
    Random azar = new Random();

    //Controla el hilo 
    private static boolean ejecuta = false;

    //Constante del juego
    public static boolean INACTIVO = false;
    public static boolean ACTIVO = true;

    //Instancia la base y el lanzador
    private PanelBaseLanzador objBaseLanzador;
    private PanelLanzador objLanzador;

    int angulo = ANGULO_IZQUIERDO;

    //Instancia de los rayos como lista 
    private static int TOTAL_RAYOS = 10;
    private PanelRayo[] listaRayo;
    //Instancia la chatarra como lista 
    private static int TOTAL_CHATARRA = 4;
    private PanelBlanco[] listaChatarra;
    //Instancia los satélites como lista 
    private static int TOTAL_SATELITE = 4;
    private PanelBlanco[] listaSatelite;

    //Nombre del jugador
    private String jugador;

    //puntaje del juego 
    private int puntaje = 0;
    //puntaje del planeta
    private int vidaPlaneta = 20;

    //valor x del mouse anterior 
    private double mousePosXAnterior = 0;

    //Recursos de imagen y sonido 
//    GestionRecursos recurso = new GestionRecursos();
    private GestionRecursos recurso;
    private AudioClip sonidoEjecuta;

    //Activa la captura de eventos 
    public PanelAnimar(InterfazJuego unJuego) {
        juego = unJuego;

        //inicializa componentes necesarios del juego
        objBaseLanzador = new PanelBaseLanzador();
        objLanzador = new PanelLanzador();
        listaRayo = new PanelRayo[TOTAL_RAYOS];
        listaChatarra = new PanelBlanco[TOTAL_CHATARRA];
        listaSatelite = new PanelBlanco[TOTAL_SATELITE];
        recurso = new GestionRecursos();

        objLanzador.setPosX(objBaseLanzador.getAncho() - objLanzador.getAncho());
        
        setFocusable(true);
        
        addMouseMotionListener(new MouseAdapter() {

            public void mouseMoved(MouseEvent e) {
                moverCanon(e);
            }

        });

        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    moverCanon(e);
                    dispararRayo();
                }
            }

            public void mouseEntered(MouseEvent e) {
//                this.requestFocus();
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    objBaseLanzador.moverDerecha(PanelBaseLanzador.DESPLAZAMIENTO, getSize().width);
                    objLanzador.moverDerecha(PanelLanzador.DESPLAZAMIENTO, getSize().width);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    objBaseLanzador.moverIzquierda(PanelBaseLanzador.DESPLAZAMIENTO);
                    objLanzador.moverIzquierda(PanelLanzador.DESPLAZAMIENTO);
                }

                //limita cuanto se puede girar el cañon 
                if (angulo > ANGULO_DERECHO) {
                    angulo = ANGULO_DERECHO;
                }
                if (angulo < ANGULO_IZQUIERDO) {
                    angulo = ANGULO_IZQUIERDO;
                }

                //Si se dispara un rayo 
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    dispararRayo();
                }
            }
        });

        

        //Inicia la lista de rayos, chatarra, satelite
        for (int i = 0; i < TOTAL_RAYOS; i++) {
            listaRayo[i] = new PanelRayo();
        }

        for (int i = 0; i < TOTAL_CHATARRA; i++) {
            listaChatarra[i] = new PanelBlanco(1);
        }

        for (int i = 0; i < TOTAL_SATELITE; i++) {
            listaSatelite[i] = new PanelBlanco(1);
        }

        //Carga el sonido de la explosición de la colisión 
        sonidoEjecuta = recurso.retornaSonido("explosion.wav");

    }

    public void cambiarJugador(String unJugador) {
        jugador = unJugador;
    }

    public String darJugador() {
        return jugador;
    }

    public void cambiarPuntaje(int unPuntaaje) {
        puntaje = unPuntaaje;
    }

    public int darPuntaje() {
        return puntaje;
    }

    public void cambiarVidaPlaneta(int unaVida) {
        vidaPlaneta = unaVida;
    }

    public int darVidaPlaneta() {
        return vidaPlaneta;
    }

    //Metodo ejecutado por el hilo
    @Override
    public void run() {
        while (ejecuta) {
            //chequea si algun rayo colisiona con alguna chatarra o satelite 
            juego.vidaPlaneta();
            vidaPlaneta = vidaPlaneta - chequeVida();

            puntaje += chequeaColisiones();

            repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }

    private void dispararRayo() {

        //busca algun rayo inactivo 
        for (int i = 0; i < TOTAL_RAYOS; i++) {
            if (listaRayo[i].getEstado() == INACTIVO) {
                listaRayo[i].disparar((float) objLanzador.getPosX() + (objLanzador.getAncho() / 2), (float) objLanzador.getPosY() + (objLanzador.getAlto() / 2), (float) objLanzador.getAngulo(), 3.0f);
                break;
            }
        }
    }

    public int chequeVida() {
        for (int i = 0; i < TOTAL_CHATARRA; i++) {
            if (listaChatarra[i].getEstado() == INACTIVO) {
                return listaChatarra[i].getChocoPlaneta();
            }
        }
        for (int i = 0; i < TOTAL_SATELITE; i++) {
            if (listaSatelite[i].getEstado() == INACTIVO) {
                return listaSatelite[i].getChocoPlaneta();
            }
        }
        return 0;
    }

    //Chequea si algun rayo colisiona con chatarra o satelite
    public int chequeaColisiones() {
        //Va de rayo en rayo 
        for (int i = 0; i < TOTAL_RAYOS; i++) {
            //si el rayo esta inactivo pasa al siguiente 
            if (listaRayo[i].getEstado() == INACTIVO) {
                continue;
            }

            //Deduce un angulo para un rayo 
            Rectangle rec1 = new Rectangle(listaRayo[i].getPosX(), listaRayo[i].getPosY(), listaRayo[i].getAncho(), listaRayo[i].getAlto());

            //va de chatarra en chatarra 
            for (int j = 0; j < TOTAL_CHATARRA; j++) {
                //Si la chatarra esta inactiva pasa a la siguiente
                if (listaChatarra[j].getEstado() == INACTIVO) {
                    continue;
                }

                //Deduce un rectangulo para esa chatarra
                Rectangle rec2 = new Rectangle(listaChatarra[j].getPosX(), listaChatarra[j].getPosY(), listaChatarra[j].getAncho(), listaChatarra[j].getAlto());

                if (rec1.intersects(rec2)) {
                    //inactiva el rayo
                    listaRayo[i].setEstado(INACTIVO);

                    //inactiva la chatarra
                    listaChatarra[j].setEstado(INACTIVO);

                    sonidoEjecuta.play();

                    //retorna 1 para el puntaje
                    return 1;
                }
            }
            //va de satelite en satelite
            for (int j = 0; j < TOTAL_SATELITE; j++) {
                //si el satelite esta inactiva pasa al siguiente
                if (listaSatelite[j].getEstado() == INACTIVO) {
                    continue;
                }

                //Deduce un rectangulo para ese satelite 
                Rectangle rec2 = new Rectangle(listaSatelite[j].getPosX(), listaSatelite[j].getPosY(), listaSatelite[j].getAncho(), listaSatelite[j].getAlto());

                //si hay interseccion entre esos dos rectangulos, entonces hay colision 
                if (rec1.intersects(rec2)) {
                    //inactiva el rayo
                    listaRayo[i].setEstado(INACTIVO);

                    listaSatelite[j].setEstado(INACTIVO);

//                    //ejecuta el sonido de explosion
                    sonidoEjecuta.play();

                    //retorna 1 para el puntaje 
                    return 1;
                }
            }
        }
        //no hubo colisión, luego no hay punto
        return 0;
    }

    //Actualiza y pinta los protagonistas del videojuego
    @Override
    public void paintComponent(Graphics objGrafico) {
        ImageIcon img = new ImageIcon(new ImageIcon(getClass().getResource(GestionRecursos.URL_RECURSO_IMAGEN + IMAGEN_FONDO_JUEGO)).getImage());
        objGrafico.drawImage(img.getImage(), 0, 0, getSize().width, getSize().height, this);

//        fondo.pintar(objGrafico, getSize().width,getSize().height);
//        //Limpia la pantalla primero
//        objGrafico.setColor(Color.WHITE);
//        objGrafico.fillRect(0, 0, getSize().width, getSize().height);
//        
        objBaseLanzador.setPosY(getSize().height - objBaseLanzador.getAlto());
        objBaseLanzador.pintar(objGrafico);
        objLanzador.setPosY(objBaseLanzador.getPosY() - objLanzador.getAlto());
//        objLanzador.setPosX(objBaseLanzador.getAncho() - objLanzador.getAncho());
        objLanzador.pintar(objGrafico, angulo);

        //Actualiza los rayos, chatarra, satelites y los dibuja 
        for (int i = 0; i < TOTAL_RAYOS; i++) {
            listaRayo[i].pintar(objGrafico, getSize().width, getSize().height);
        }

        for (int i = 0; i < TOTAL_CHATARRA; i++) {
            listaChatarra[i].pintar(objGrafico, azar, getSize().width, getSize().height, (int) (getSize().height * 0.01), (int) (getSize().height * 0.5), 2, 5);
        }

        for (int i = 0; i < TOTAL_SATELITE; i++) {
            listaSatelite[i].pintar(objGrafico, azar, getSize().width, getSize().height, (int) (getSize().height * 0.1), (int) (getSize().height * 0.5), 1, 4);
        }

        objGrafico.setFont(new Font("Arial", Font.BOLD, 13));
        objGrafico.setColor(Color.red);
        //Muestra el puntaje
        objGrafico.drawString("Puntaje " + darJugador() + ": " + puntaje, 20, 20);
        objGrafico.drawString("Vida: " + vidaPlaneta, getSize().width - 70, 20);
    }

    //Inicia el hilo
    public void iniciar() {
        if (ejecuta == false) {
            objHilo = new Thread(this);
            ejecuta = true;
            objHilo.start();
        }
    }

    //Detiene el hilo
    public void detener() {
        ejecuta = false;
    }

    public void moverCanon(MouseEvent e) {

        angulo += e.getPoint().x - mousePosXAnterior;
        if (angulo > ANGULO_DERECHO) {
            angulo = ANGULO_DERECHO;
        }
        if (angulo < ANGULO_IZQUIERDO) {
            angulo = ANGULO_IZQUIERDO;
        }

        mousePosXAnterior = e.getPoint().x;
    }

}
