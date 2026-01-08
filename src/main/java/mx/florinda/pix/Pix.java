package mx.florinda.pix;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Pix implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private BigDecimal valor;
    private String chaveDestino;
    private Instant dataHora;
    private String mensagem;

    public Pix() {
    }

    public Pix(Long id, BigDecimal valor, String chaveDestino, Instant dataHora, String mensagem) {
        this.id = id;
        this.mensagem = mensagem;
        this.dataHora = dataHora;
        this.chaveDestino = chaveDestino;
        this.valor = valor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setChaveDestino(String chaveDestino) {
        this.chaveDestino = chaveDestino;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getChaveDestino() {
        return chaveDestino;
    }

    @Override
    public String toString() {
        return "Pix{" +
                "id=" + id +
                ", valor=" + valor +
                ", chaveDestino='" + chaveDestino + '\'' +
                ", dataHora=" + dataHora +
                ", mensagem='" + mensagem + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Pix pix = (Pix) object;
        return Objects.equals(id, pix.id) && Objects.equals(valor, pix.valor) && Objects.equals(chaveDestino, pix.chaveDestino) && Objects.equals(dataHora, pix.dataHora) && Objects.equals(mensagem, pix.mensagem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valor, chaveDestino, dataHora, mensagem);
    }
}
