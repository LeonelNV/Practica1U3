
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class HilosLoteria extends Thread{
    String[] cartas = {"","EL GALLO", "EL DIABLITO", "LA DAMA", "EL CATRÍN", "EL PARAGUAS", "LA SIRENA", "LA ESCALERA", "LA BOTELLA", 
                        "EL BARRIL", "EL ÁRBOL", "EL MELÓN", "EL VALIENTE", "EL GORRITO", "LA MUERTE", "LA PERA", "LA BANDERA", "EL BANDOLÓN", 
                        "EL VIOLONCELLO", "LA GARZA", "EL PÁJARO", "LA MANO", "LA BOTA", "LA LUNA", "EL COTORRO", "EL BORRACHO", "EL NEGRITO", 
                        "EL CORAZÓN", "LA SANDÍA", "EL TAMBOR", "EL CAMARÓN", "LAS JARAS", "EL MÚSICO", "LA ARAÑA", "EL SOLDADO", "LA ESTRELLA", 
                        "EL CAZO", "EL MUNDO", "EL APACHE", "EL NOPAL", "EL ALACRÁN", "LA ROSA", "LA CALAVERA", "LA CAMPANA", "EL CANTARITO", 
                        "EL VENADO", "EL SOL", "LA CORONA", "LA CHALUPA", "EL PINO", "EL PESCADO", "LA PALMA", "LA MACETA", "EL ARPA", "LA RANA"};
    String[] imagenes = new String[55];
    AudioClip[] audios = new AudioClip[55];
    int[] repetidos = new int[55];
    int numAleatorio = 0; 
    int contadorRun=1;
    int contadorBarajear=1;
    boolean rep;
    boolean despausado=true;
    boolean ejecutar = true;
    Ventana_Loteria puntero;
    
    public HilosLoteria(Ventana_Loteria direccionMemoriaVentanaJFRAME){
        puntero  = direccionMemoriaVentanaJFRAME;
        audios();
        imagenes();
    }
    
    @Override
    public void run(){
        super.run();  
        while(ejecutar){
            try {
                if(despausado){
                    puntero.jLabel6.setText(cartas[repetidos[contadorRun]]);
                    puntero.imagenCartas(imagenes[repetidos[contadorRun]]);
                    audios[repetidos[contadorRun]].play();
                    contadorRun++; 

                    puntero.btnPausar.setText("Pausar");
                    sleep(1900);
                   // audios[repetidos[contadorRun - 1]].stop();
                }else{
                    puntero.btnPausar.setText("Despausar");
                }
            sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(HilosLoteria.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
   
    public void Barajear(){
        ArrayList<Integer> temp = new ArrayList<Integer>(54);
        for (int i = 1; i <= 54; i++) {
            temp.add(i);
        }
        
        //desordena el arraylist temp
        Collections.shuffle(temp);

        //pasa el arraylist temp al arreglo repetidos
        for (int i = 0; i < temp.size(); i++){
            repetidos[i+1] = temp.get(i);
        }
//        repetidos //numeros aleatorios del 1 al 54
        AudioInputStream audioIn;
        try {
            audioIn = AudioSystem.getAudioInputStream(getClass().getResource("/audioss/10.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            Logger.getLogger(HilosLoteria.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void imagenes(){
        for(int i = 1; i < cartas.length; i++){
           imagenes[i] = "/imagenes/" + i + ".png";
       }
    }
    
    public void audios(){
        for(int i = 1; i < cartas.length; i++){
            audios[i]=java.applet.Applet.newAudioClip(getClass().getResource("/audioss/" + i + ".wav"));
        }
        
    }
    
    public void pausar(){
        despausado =! despausado;
    }
    
    public void anterior(){
        despausado = false;
        contadorRun = contadorRun - 1;
        puntero.jLabel6.setText(cartas[repetidos[contadorRun]]);
        puntero.imagenCartas(imagenes[repetidos[contadorRun]]);
    }
    
    public void siguiente(){
        despausado = false;
        contadorRun = contadorRun + 1;
        puntero.jLabel6.setText(cartas[repetidos[contadorRun]]);
        puntero.imagenCartas(imagenes[repetidos[contadorRun]]);
    }
}
