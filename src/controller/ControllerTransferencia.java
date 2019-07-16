/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cliente.view.ViewFrameCliente;
import java.awt.Cursor;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import transferencia.Arquivo;
import utils.Tarefa;

/**
 *
 * @author Drew
 * @since  15/07/2019
 */
public class ControllerTransferencia {
    
    private static ControllerTransferencia oInstance;
    
    private Arquivo          oArquivo;
    private ViewFrameCliente oView;
    private int              iProgresso;
    
    private final int CAPACIDADE_MAXIMA = 2147480000;
    
    private ControllerTransferencia() {
        oView = ViewFrameCliente.getInstance();
    }
    
    public static ControllerTransferencia getInstance() {
        if(oInstance == null) {
            oInstance = new ControllerTransferencia();
        }
        return oInstance;
    }
    
    public void iniciar() {
        preencheCampos();
        trataCampos();
    }
    
    private void preencheCampos() {
        oView.getPanel().setValueCampo("ip"     , "127.0.0.1");
        oView.getPanel().setValueCampo("porta"  , "5566");
        oView.getPanel().setValueCampo("dirdest", "C:\\teste");
    }
    
    private void trataCampos() {
        trataBarraProgresso();
    }
    
    private void trataBarraProgresso() {
        JProgressBar oBar = (JProgressBar)oView.getPanel().getComponente("barpro");
        oBar.setValue(0);
        oBar.setStringPainted(true);
    }
    
    /**
     * Evento chamado ao selecionar um arquivo
     */
    public void selecionarArquivo() {
        FileInputStream oFileInput    = null;
        File            oFileSelected = null;
        try {
            JFileChooser jfChosser = new JFileChooser();
            jfChosser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jfChosser.setDialogTitle("Escolha um arquivo");
            
            if(jfChosser.showOpenDialog(null) == jfChosser.OPEN_DIALOG) {
                oFileSelected = jfChosser.getSelectedFile();
                
                if(oFileSelected.length() > CAPACIDADE_MAXIMA) {
                    throw new IllegalStateException("Tamanho do arquivo ultrapassa o tamanho maximo permitido");
                }
                
                byte[] aByteFile = new byte[(int) oFileSelected.length()];
                oFileInput       = new FileInputStream(oFileSelected);
                oFileInput.read(aByteFile);
                oFileInput.close();
                
                oArquivo = new Arquivo();
                oArquivo.setConteudo(aByteFile);
                oArquivo.setDataHoraUpload(new Date());
                oArquivo.setNome(oFileSelected.getName());
                oArquivo.setTamanhoKB(oFileSelected.length()/1024);
                oArquivo.setTamanhoArquivo((int) oFileSelected.length());
                
                oArquivo.setIpDestino(oView.getPanel().getValueCampoByName("ip"));
                oArquivo.setPortaDestino(oView.getPanel().getValueCampoByName("porta"));
                oArquivo.setDiretorioDestino(oView.getPanel().getValueCampoByName("dirdest"));
                
                oView.getPanel().setValueCampo("arquivo"  , oFileSelected.getName());
                oView.getPanel().setValueCampo("lbTamanho", "Tamanho KB: " + oArquivo.getTamanhoKB()+" KB");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Evento chamado ao enviar um arquivo
     */
    public void enviarArquivo() {
        try {
            
            if(oArquivo == null) {
                return;
            }
            
            Socket oSoc = new Socket(oArquivo.getIpDestino(), Integer.parseInt(oArquivo.getPortaDestino()));
            
            DataOutputStream oDtaOut = new DataOutputStream(oSoc.getOutputStream());
            
            byte[] aByteObjeto = serializarArquivo(oArquivo);
            
            oDtaOut.writeInt(aByteObjeto.length);
            
            transferirArquivo(oDtaOut, aByteObjeto);
            
            oDtaOut.flush();
            oDtaOut.close();
            oSoc.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Transforma o objeto passado em um array de bytes
     * 
     * @param oObj Objeto a ser convertido
     * @return byte[]
     */
    private byte[] serializarArquivo(Object oObj) {
        try {
            ByteArrayOutputStream aByteOut = new ByteArrayOutputStream();
            ObjectOutputStream    oObjOut  = new ObjectOutputStream(aByteOut);
            
            oObjOut.writeObject(oObj);
            
            return aByteOut.toByteArray();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Transfere o arquivo por partes
     * 
     * @param oDtaOut Output para a saida de dados
     * @param oObj    Array de bytes correspondente ao objeto a ser enviado
     */
    private void transferirArquivo(DataOutputStream oDtaOut, byte[] oObj) {
        try {
//            JProgressBar oBar = (JProgressBar)oView.getPanel().getComponente("barpro");
            oView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            Tarefa tf = new Tarefa();
            tf.addPropertyChangeListener(oView);
            tf.execute();
            
            int    iCount = 0;
            byte[] buffer = new byte[1024];//quantidade de dados enviados por vez(1KB)
            
            while(iCount != oObj.length) {
                int iTamanho = 0;//tamanho do array de dados a ser enviado
                for(int i = 0; i < buffer.length; i++) {
                    if(iCount == oObj.length) {
                        break;
                    }
                    buffer[i] = oObj[iCount];
                    iCount++;
                    iTamanho++;
                }
                
                double n1 = iCount/1024;
                double n2 = oObj.length/1024;
                double per = (n1/n2)*100;
                
                iProgresso =  (int) per;
                
                /*
                    buffer   - array de bytes a ser enviado; 
                    0        - posicao da qual comecar a ler os bytes;  
                    iTamanho - tamanho dos dados enviados
                */
                oDtaOut.write(buffer, 0, iTamanho);
            }
            
            oView.setCursor(null);
            iProgresso = 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getProgressoTransferencia() {
        return iProgresso;
    }
}
