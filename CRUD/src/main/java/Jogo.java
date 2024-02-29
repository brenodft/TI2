public class Jogo {
    private int id;
    private String titulo;
    private String desenvolvedor;
    private String plataforma;
    private int anoLancamento;
    private boolean disponivel;


    public Jogo(int id, String titulo, String desenvolvedor, String plataforma, int anoLancamento, boolean disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.desenvolvedor = desenvolvedor;
        this.plataforma = plataforma;
        this.anoLancamento = anoLancamento;
        this.disponivel = disponivel;
    }


    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDesenvolvedor() {
        return desenvolvedor;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public boolean isDisponivel() {
        return disponivel;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDesenvolvedor(String desenvolvedor) {
        this.desenvolvedor = desenvolvedor;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return String.format("Jogo [id=%d, titulo=%s, desenvolvedor=%s, plataforma=%s, anoLancamento=%d, disponivel=%b]",
                id, titulo, desenvolvedor, plataforma, anoLancamento, disponivel);
    }
}