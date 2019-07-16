/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedInputStream;
import transferencia.Arquivo;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import javax.swing.JFileChooser;

/**
 *
 * @author Drew
 * @deprecated 
 */
public class TratamentoArquivo {
    
    private static TratamentoArquivo oInstance;
    
    private Arquivo oArquivo;
    
    private TratamentoArquivo() {}
    
    public static TratamentoArquivo getInstance() {
        if(oInstance == null) {
            oInstance = new TratamentoArquivo();
        }
        return oInstance;
    }
    
    public File selecionaArquivo() {
        FileInputStream oFileInput    = null;
        File            oFileSelected = null;       
        try {
            JFileChooser jfChoser = new JFileChooser();
            jfChoser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jfChoser.setDialogTitle("Escolha o arquivo");

            if(jfChoser.showOpenDialog(null) == jfChoser.OPEN_DIALOG) {
                oFileSelected = jfChoser.getSelectedFile();

                byte[] aByteFile = new byte[(int) oFileSelected.length()];
                oFileInput = new FileInputStream(oFileSelected);
                oFileInput.read(aByteFile);
                oFileInput.close();
                
                oArquivo = new Arquivo();
                oArquivo.setConteudo(aByteFile);
                oArquivo.setDataHoraUpload(new Date());
                oArquivo.setNome(oFileSelected.getName());
                oArquivo.setTamanhoKB(oFileSelected.length()/1024);
                oArquivo.setTamanhoArquivo((int) oFileSelected.length());

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return oFileSelected;
    }
    
    private void enviar(String ip, int porta, String diretorioDestino) {
        try {
            Socket soc = new Socket(ip, porta);
            
            
        } catch (Exception e) {
        }
    }
    
    public void enviarArquivo(String ip, int porta, String diretorioDestino) {
        try {
            Socket sockete = new Socket(ip, porta); 
            
//            BufferedOutputStream bufOut = new BufferedOutputStream(sockete.getOutputStream());
//            BufferedInputStream  bufIn  = new BufferedInputStream(sockete.getInputStream());
            DataOutputStream oDtaOutput = new DataOutputStream(sockete.getOutputStream());
            
            oArquivo.setIpDestino(ip);
            oArquivo.setPortaDestino(porta + "");
            oArquivo.setDiretorioDestino(diretorioDestino);
            
//            byte[] aObjetoByte = codificarArquivo(oArquivo);
            
            byte[] aObjetoByte = serializarArquivo();
//            bufOut.write(aObjetoByte);

            oDtaOutput.writeInt(aObjetoByte.length);
            int cont = 0;
            byte[] buffer = new byte[1024];
            while(cont != aObjetoByte.length) {
                int tamanho = 0;
                for(int i = 0; i< buffer.length; i++) {
                    if(cont == aObjetoByte.length) {
                        break;
                    }
                    buffer[i] = aObjetoByte[cont];
                    cont++;
                    tamanho++;
                }
                oDtaOutput.write(buffer, 0, tamanho);
            }

            //int count;
//            byte[] buffer = new byte[8192];
//            while ((count = bufIn.read(buffer)) > 0)
//            {
//              bufOut.write(buffer, 0, count);
//            }
            
//            while(bufIn.read()) {}
            
//            oDtaOutput.writeInt(oArquivo.getTamanhoArquivo());
//            oDtaOutput.write(aObjetoByte);
            oDtaOutput.flush();
            oDtaOutput.close();
            
            sockete.close();
            oArquivo = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private byte[] serializarArquivo() {
        try {
            ByteArrayOutputStream aByteOut = new ByteArrayOutputStream();
            ObjectOutputStream oObjectOut  = null;
            
            oObjectOut = new ObjectOutputStream(aByteOut);
            oObjectOut.writeObject(oArquivo);
            
            return aByteOut.toByteArray();
            
         } catch (IOException e) {
            e.printStackTrace();
         }

         return null;
    }
}
