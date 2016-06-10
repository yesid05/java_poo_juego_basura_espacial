/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JPanel;
import modelo.*;
/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class PanelBlanco extends JPanel {
    
    public static String IMAGEN_BASURA = "basuraEspacial.png";

    //activo o inactivo el blanco 
    boolean estado;

    //posicion del blanco 
    int posX, posY;

    //desplazamiento del blanco
    int velocidad;

    int chocoPlaneta = 0;
    //recurso de imagen 
    GestionRecursos recurso = new GestionRecursos();

    //imagen de la chatarra
    BufferedImage imagenBasura;

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
        return imagenBasura.getWidth();
    }

    public int getAlto() {
        return imagenBasura.getHeight();
    }

    //da valores a los atributos
    public void setEstado(boolean unEstado) {
        estado = unEstado;
    }

    //carga la imagen del blanco 
    public PanelBlanco(int tipoBlanco) {
        if (tipoBlanco == 1) {
            imagenBasura = recurso.retornaImagen(IMAGEN_BASURA);
        }
//        if (tipoBlanco == 2) {
//            imagenBasura = recurso.retornaImagen("satelite.png");
//        }

        posX = 0;
        posY = 0;
        velocidad = 0;
        estado = false;

    }

    public void pintar(Graphics objGrafico, Random azar, int ancho, int alto, int alturaMaxima, int alturaMinima, int velocidadMaxima, int velocidadMinima) {
        if (estado == true) {

            //mueve lachatarra
            posY += velocidad;

            //chequea si se sale de la ventana 
            if (posY < 0 || posY + imagenBasura.getHeight() > alto) {
                estado = false;
                chocoPlaneta = 1;
            }
            //muestra la imagen del blanco 
            objGrafico.drawImage(imagenBasura, posX, posY, this);
        } else {
            //activa la chatarra  / satelite con altura y velocidad al azar
            chocoPlaneta = 0;
            estado = true;
            this.posY = 0;
            // 150 +
            posX = (int) (azar.nextFloat() * (alturaMaxima - alturaMinima) + alturaMinima);
            velocidad = (int) (azar.nextFloat() * (velocidadMaxima - velocidadMinima) + velocidadMinima);
        }
    }
    public int getChocoPlaneta(){
        return chocoPlaneta;
    }
}
