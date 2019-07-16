/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.view.listeners;

import cliente.ViewCliente;
import cliente.view.ViewPanelCliente;
import controller.ControllerTransferencia;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import utils.TratamentoArquivo;

/**
 *
 * @author Drew
 * @since  10/07/2019
 */
public class ViewListenerEnviarArquivo implements ActionListener {

    private ViewPanelCliente panel;
    
    public ViewListenerEnviarArquivo(ViewPanelCliente panel) {
        this.panel = panel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ControllerTransferencia.getInstance().enviarArquivo();
    }
    
}
