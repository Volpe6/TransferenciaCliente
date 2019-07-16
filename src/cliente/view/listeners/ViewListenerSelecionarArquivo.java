/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.view.listeners;

import cliente.view.ViewPanelCliente;
import controller.ControllerTransferencia;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JTextField;
import utils.TratamentoArquivo;

/**
 *
 * @author Drew
 * @since  10/07/2019
 */
public class ViewListenerSelecionarArquivo implements ActionListener {

    private ViewPanelCliente jpPanel;
    
    public ViewListenerSelecionarArquivo(ViewPanelCliente panel) {
        this.jpPanel = panel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ControllerTransferencia.getInstance().selecionarArquivo();
    }
    
}
