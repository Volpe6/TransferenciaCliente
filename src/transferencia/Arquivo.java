package transferencia;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Drew
 * @since  09/07/2019
 */
public class Arquivo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String           sNome;
    private byte[]           aConteudo;
    private transient long   lTamanhoKB;// transient indica que este atributo nao deve ser serializado
    private transient Date   oDataHoraUpload;
    private transient String sIpDestino;
    private transient String sPortaDestino;
    private String           sDiretorioDestino;
    private transient int    iTamanhoArquivo;

    public String getNome() {
        return sNome;
    }

    public void setNome(String sNome) {
        this.sNome = sNome;
    }

    public byte[] getConteudo() {
        return aConteudo;
    }

    public void setConteudo(byte[] aConteudo) {
        this.aConteudo = aConteudo;
    }

    public long getTamanhoKB() {
        return lTamanhoKB;
    }

    public void setTamanhoKB(long lTamanhoKB) {
        this.lTamanhoKB = lTamanhoKB;
    }

    public Date getDataHoraUpload() {
        return oDataHoraUpload;
    }

    public void setDataHoraUpload(Date oDataHoraUpload) {
        this.oDataHoraUpload = oDataHoraUpload;
    }

    public String getIpDestino() {
        return sIpDestino;
    }

    public void setIpDestino(String sIpDestino) {
        this.sIpDestino = sIpDestino;
    }

    public String getPortaDestino() {
        return sPortaDestino;
    }

    public void setPortaDestino(String sPortaDestino) {
        this.sPortaDestino = sPortaDestino;
    }

    public String getDiretorioDestino() {
        return sDiretorioDestino;
    }

    public void setDiretorioDestino(String sDiretorioDestino) {
        this.sDiretorioDestino = sDiretorioDestino;
    }
    
    public void setTamanhoArquivo(int iTamanho) {
        this.iTamanhoArquivo = iTamanho;
    }
    
    public int getTamanhoArquivo() {
        return iTamanhoArquivo;
    }
    
}
