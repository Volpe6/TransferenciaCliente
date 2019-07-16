/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.view;

import cliente.view.listeners.ViewListenerEnviarArquivo;
import cliente.view.listeners.ViewListenerSelecionarArquivo;
import controller.ControllerTransferencia;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 *
 * @author Drew
 * @since  09/07/2019
 */
public class ViewFrameCliente extends JFrame implements PropertyChangeListener {
    
    private static ViewFrameCliente oInstance;
    
    private ViewPanelCliente pPanel;
    
    private ViewFrameCliente() {}
    
    public static ViewFrameCliente getInstance() {
        if(oInstance == null) {
            oInstance = new ViewFrameCliente();
        }
        return oInstance;
    }
    
    public void initComponentes() {
        pPanel = new ViewPanelCliente();
        
        this.add(pPanel);
        this.setSize(new Dimension(300, 350));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        ControllerTransferencia.getInstance().iniciar();
        
        adicionaListeners();
    }
    
    private void adicionaListeners() {
        JButton btnSelecionar = (JButton)pPanel.getComponenteByName("btnSelecionar")[1];
        JButton btnEnviar     = (JButton)pPanel.getComponenteByName("btnenviar")[1];
        
        btnSelecionar.addActionListener(new ViewListenerSelecionarArquivo(pPanel));
        
        btnEnviar.addActionListener(new ViewListenerEnviarArquivo(pPanel));
    }
    
    public ViewPanelCliente getPanel() {
        return pPanel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if("progress" == evt.getPropertyName()) {
            JProgressBar oBar = (JProgressBar)getPanel().getComponente("barpro");
            
            int progress = (Integer) evt.getNewValue();
            
            oBar.setValue(progress);
        }
    }
}
