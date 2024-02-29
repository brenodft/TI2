import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogoDAO {
    private Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/jogos";
        String user = "postgres";
        String password = "1509";
        return DriverManager.getConnection(url, user, password);
    }

    public List<Jogo> listar() {
        List<Jogo> jogos = new ArrayList<>();
        String sql = "SELECT * FROM Jogos";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                jogos.add(new Jogo(rs.getInt("id"), rs.getString("titulo"), rs.getString("desenvolvedor"),
                        rs.getString("plataforma"), rs.getInt("ano_lancamento"), rs.getBoolean("disponivel")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jogos;
    }

    public boolean inserir(Jogo jogo) {
        String sql = "INSERT INTO Jogos (titulo, desenvolvedor, plataforma, ano_lancamento, disponivel) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jogo.getTitulo());
            stmt.setString(2, jogo.getDesenvolvedor());
            stmt.setString(3, jogo.getPlataforma());
            stmt.setInt(4, jogo.getAnoLancamento());
            stmt.setBoolean(5, jogo.isDisponivel());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Jogos WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean atualizar(Jogo jogo) {
        String sql = "UPDATE Jogos SET titulo = ?, desenvolvedor = ?, plataforma = ?, ano_lancamento = ?, disponivel = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jogo.getTitulo());
            stmt.setString(2, jogo.getDesenvolvedor());
            stmt.setString(3, jogo.getPlataforma());
            stmt.setInt(4, jogo.getAnoLancamento());
            stmt.setBoolean(5, jogo.isDisponivel());
            stmt.setInt(6, jogo.getId());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
