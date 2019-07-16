/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import cliente.view.ViewFrameCliente;
import javax.swing.JFrame;

/**
 *
 * @author Drew
 * @since  10/07/2019
 */
public class ViewCliente {
    
    public static void main(String[] args) {
        ViewFrameCliente v = ViewFrameCliente.getInstance();
        v.initComponentes();
        v.setTitle("Transferencia");
        v.setVisible(true);
    }
}
