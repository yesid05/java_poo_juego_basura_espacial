/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class GestionRecursos {
    
    //almacena los sonidos en una lista
    HashMap ListaSonidos = new HashMap();

    //Almacena las imágenes en una lista
    HashMap ListaImagen = new HashMap();
    
    //
    public static String URL_RECURSO_SONIDO = "/recursos/sonido/";
    public static String URL_RECURSO_IMAGEN = "/recursos/imagen/";
    
    //carga el sonido de disco
    public AudioClip cargaSonido(String direccion){
        try {
            URL rutaSonido = getClass().getResource(URL_RECURSO_SONIDO+direccion);
            AudioClip sonido = Applet.newAudioClip(rutaSonido);
            return sonido;
        } catch (Exception e) {
            System.out.println("Problema en cargar archivo");
            return null;
        }
    }
    
    //si el sonido no esta en memoria (verifica la lista)
    //lo carga el disco 
    public AudioClip retornaSonido(String nombreSonido){
        
        //Busca el sonido en la lista 
        AudioClip sonido = (AudioClip) ListaSonidos.get(nombreSonido);
        
        //si no encuentra el sonido en la lista, lo carga del disco
        if(sonido == null){
            sonido = cargaSonido(nombreSonido);
            ListaSonidos.put(nombreSonido, sonido);
        }
        return sonido;
    }
    
    //carga la imagen del disco
    public BufferedImage cargaImagen(String direccion){
        try {
            URL rutaImagen = getClass().getResource(URL_RECURSO_IMAGEN+direccion);
            BufferedImage imagen = ImageIO.read(rutaImagen);
            return imagen;
        } catch (Exception e) {
            System.out.println("Problema en cargar el archivo");
            return null;
        }
    }
    
    //si la imagen no esta enmemoria (verifica la lista)
    //la carga del disco
    public BufferedImage retornaImagen(String nombreImagen){
        
        //Busca la imagen en la lista 
        BufferedImage imagen = (BufferedImage) ListaImagen.get(nombreImagen);
        
        //si no encuentra la imagen en la lista, la carga del disco
        if(imagen==null){
            imagen = cargaImagen(nombreImagen);
            ListaImagen.put(nombreImagen, imagen);
        }
        
        return imagen;
        
    }
    
}
