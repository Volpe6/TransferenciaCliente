
package utils;

import controller.ControllerTransferencia;
import javax.swing.SwingWorker;

/**
 *
 * @author Drew
 * @since  16/07/2019
 */
public class Tarefa extends SwingWorker<Void, Void> {

    @Override
    protected Void doInBackground() throws Exception {
        int progress = 0;
        
        setProgress(0);
        while(progress < 100) {
            progress = ControllerTransferencia.getInstance().getProgressoTransferencia();
            setProgress(progress);
        }
        return null;
    }
    
}
