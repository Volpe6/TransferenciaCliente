/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

/**
 *
 * @author Drew
 * @since  09/07/2019
 */
public class ViewPanelCliente extends JPanel {
    
    private List<JComponent[]> aCampos;
    
    public ViewPanelCliente() {
        this.aCampos = new ArrayList<>();
        this.setLayout(new GridBagLayout());
        this.initComponents();
    }
    
    public void initComponents() {
        addCampos();
    }
    
    private void addCampos() {
        this.addCampo("arquivo", "Arquivo carregado:");
        this.addCampo("ip"     , "IP:");
        this.addCampo("porta"  , "Porta:");
        this.addCampo("dirdest", "Diretorio de Destino:");
        
        this.addComponente("btnselecionar", ""           , new JButton("Selecionar"));
        this.addComponente("lbtamanho"    , "Tamanho kb:", null);
        this.addComponente("btnenviar"    , ""           , new JButton("Enviar"));
        this.addComponente("barpro"       , "Progresso:" , new JProgressBar(0, 100));
    }
    
    /**
     * Adiciona um novo campo na view(JTextField)
     * 
     * @param sNome   
     * @param sTitulo Titulo que aparece na label
     */
    public void addCampo(String sNome, String sTitulo) {
        JLabel jbTitulo = new JLabel(sTitulo);
        jbTitulo.setName(sNome);
        
        JTextField jtCampo = new JTextField();
        
        /* GridBagConstrains usado no label*/
        GridBagConstraints lConstrains = new GridBagConstraints();
        
        lConstrains.fill   = GridBagConstraints.HORIZONTAL;
        lConstrains.gridx  = 0;
        lConstrains.insets = new Insets(2, 1, 2, 4);
                
        /*GridBagConstrains usado no jTextField*/
        GridBagConstraints cConstrains = new GridBagConstraints();
        
        cConstrains.fill    = GridBagConstraints.HORIZONTAL;
        cConstrains.gridx   = 1;
        cConstrains.weightx = 0.9f;
        cConstrains.insets  = new Insets(2, 1, 2, 4);
        
        /*Adicionado os componentes no panel*/
        this.add(jbTitulo, lConstrains);
        this.add(jtCampo , cConstrains);
        
        aCampos.add(new JComponent[] {jbTitulo, jtCampo});
    }
    
    /**
     * Adiciona um novo componente na view
     * 
     * @param sNome   Nome do componente
     * @param sTitulo Titulo do componente(Aparece no label)
     * @param oComp   Componente a ser adicionado
     */
    public void addComponente(String sNome, String sTitulo, JComponent oComp) {
        JLabel jbTitulo = new JLabel(sTitulo);
        jbTitulo.setName(sNome);
        
        /* GridBagConstrains usado no label*/
        GridBagConstraints lConstrains = new GridBagConstraints();
        
        lConstrains.fill   = GridBagConstraints.HORIZONTAL;
        lConstrains.gridx  = 0;
        lConstrains.insets = new Insets(2, 1, 2, 4);
        
        /* GridBagConstrains usado no componente*/
        GridBagConstraints cConstrains = new GridBagConstraints();
        
        cConstrains.fill   = GridBagConstraints.HORIZONTAL;
        cConstrains.gridx  = 0;
        cConstrains.insets = new Insets(2, 1, 2, 4);
        
        this.add(jbTitulo, lConstrains);
        
        if(oComp == null) {
            aCampos.add(new JComponent[] {jbTitulo});
            return;
        }
        
        this.add(oComp   , cConstrains);
        aCampos.add(new JComponent[] {jbTitulo, oComp});
    }
    
    /**
     * Retorna um array contendo o label e o componente
     * 
     * @param sName Nome do componente
     * @return JComponent[]
     */
    public JComponent[] getComponenteByName(String sName) {
        for(JComponent[] oComp : aCampos) {
            if(oComp[0].getName().equals(sName.toLowerCase())) {
                return oComp;
            }
        }
        return null;
    }
    
    /**
     * Retona o valor do campo cujo o nome foi informado
     * 
     * @param sName Nome do campo
     * @return String
     */
    public String getValueCampoByName(String sName) {
        for(JComponent[] oComp : aCampos) {
            if(oComp[0].getName().equals(sName.toLowerCase())) {
                return ((JTextField)oComp[1]).getText();
            }
        }
        return null;
    }
    
    /**
     * Retorna o componente de acordo com o nome informado
     * 
     * @param sNome Nome do componente
     * @return Component
     */
    public Component getComponente(String sNome) {
        for(JComponent[] oComp : aCampos) {
            if(oComp[0].getName().equals(sNome.toLowerCase())) {
                switch(oComp.length) {
                    case 1:
                        return oComp[0];
                    case 2:
                        return oComp[1];
                }
            }
        }
        return null;
    }
    
    /**
     * Seta o texto que aparece no campo
     * 
     * @param sNome Nome do campo
     * @param valor String que deve aparecer no campo
     */
    public void setValueCampo(String sNome, String valor) {
        for(JComponent[] oComp : aCampos) {
            if(oComp[0].getName().equals(sNome.toLowerCase())) {
                switch(oComp.length) {
                    case 1:
                        ((JLabel)oComp[0]).setText(valor);
                        break;
                    case 2:
                        ((JTextField)oComp[1]).setText(valor);
                        break;
                }
            }
        }
    }
    
    /**
     * Limpa os campos
     */
    public void limparCampos() {
        for(JComponent[] oComp : aCampos) {
            
                switch(oComp.length) {
                    case 1:
                        ((JLabel)oComp[0]).setText(((JLabel)oComp[0]).getName().substring(0, 1).toUpperCase().concat(((JLabel)oComp[0]).getName().substring(1)));
                        break;
                    case 2:
                        ((JTextField)oComp[1]).setText("");
                        break;
                }
            
        }
    }
}
