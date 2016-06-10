/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import modelo.*;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class PanelRayo extends JPanel {
    
    public static String SONIDO_RAYO = "disparo.wav";
    
    public static String IMAGEN_RAYO = "rayo.png";

    //estado del rayo 
    boolean estado;

    //posicion del rayo 
    float posX, posY;

    //avance del rayo
    float angulo, incX, incY;

    //sonido del despegue
    GestionRecursos recursos = new GestionRecursos();
    AudioClip sonidoEjecuta;

    //imagen del rayo 
    BufferedImage imagen;

    //giro de la imagen 
    AffineTransformOp transformacion;

    //retorna los valores de los atributos
    public boolean getEstado() {
        return estado;
    }

    public int getPosX() {
        return (int) posX;
    }

    public int getPosY() {
        return (int) posY;
    }

    public int getAncho() {
        return imagen.getWidth();
    }

    public int getAlto() {
        return imagen.getHeight();
    }

    /**
     * Da valores a los atributos
     */
    public void setEstado(boolean unEstado) {
        estado = unEstado;
    }

    //carga la imagen y sonido del rayo, inicializa valores 
    public PanelRayo() {
        sonidoEjecuta = recursos.retornaSonido(SONIDO_RAYO);
        imagen = recursos.retornaImagen(IMAGEN_RAYO);

        posX = 0;
        posY = 0;
        angulo = 0;
        incX = 0;
        incY = 0;
        estado = false;
    }

    //dispara el rayo, lo activa y pone los valores iniciales 
    public void disparar(float unaPosX, float unaPosY, float unAngulo, float unaVelocidad) {

        estado = true;
        posX = unaPosX;
        posY = unaPosY;
        angulo = unAngulo;
        incX = (float) (unaVelocidad * Math.cos(unAngulo * Math.PI / 180));
        incY = (float) (unaVelocidad * Math.sin(unAngulo * Math.PI / 180));

        //transfoma la imagen (girarla)
        AffineTransform giraImagen = new AffineTransform();
        giraImagen.rotate(angulo * Math.PI / 180, imagen.getWidth() / 2, imagen.getHeight() / 2);
        transformacion = new AffineTransformOp(giraImagen, AffineTransformOp.TYPE_BILINEAR);

        //ejecuta sonido de disparo
        sonidoEjecuta.play();

    }

    public void pintar(Graphics objGrafico, int unAncho, int unAlto) {
        if (estado) {
            //mueve el rayo 
            posX += incX;
            posY += incY;

            //se desactiva si sale de la pantalla 
            if (posX > unAncho || posY > unAlto || posX < 0 || posY < 0) {
                estado = false;
            }

            //se requiere java 2d 
            Graphics2D graficos2d = (Graphics2D) objGrafico;
            graficos2d.drawImage(imagen, transformacion, (int) posX, (int) posY);
        }
    }

}
