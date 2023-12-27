package br.edu.ifrn.cinema.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.jdbc.core.JdbcTemplate;

public class Ingresso {
    
    private int id;
    private String nome;
    private float preco;

    public Ingresso(String nome, float preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public float getPreco() {
        return preco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }
    
    public void salvar(JdbcTemplate jdbc) {
        if (id > 0) {
            jdbc.update(
            "UPDATE ingressos SET nome = ?, "
            + "preco = ? "
            + "WHERE id = ?;", (ps) -> {
                ps.setString(1, nome);
                ps.setFloat(2, preco);
                ps.setInt(3, id);
            });
        } else {
            jdbc.update(
            "INSERT INTO ingressos (nome, preco) " +
            "VALUES (?, ?);", (ps) -> {
                ps.setString(1, nome);
                ps.setFloat(2, preco);
            });
        }
    }
    
    public static List<Ingresso> listar(JdbcTemplate
            jdbc) {
        List<Ingresso> ingressos =
                new ArrayList<>();
        jdbc.query("SELECT * FROM ingressos;",
        (rs) -> {
            do {
                Ingresso i = new Ingresso(
                        rs.getString("nome"),
                        rs.getFloat("preco"));
                i.id = rs.getInt("id");
                ingressos.add(i);
            } while(rs.next());
        });
        return ingressos;
    }
    
    public static void remover(int id,
            JdbcTemplate jdbc) {
        jdbc.update("DELETE FROM ingressos "
        + "WHERE id = ?", (ps) -> {
            ps.setInt(1, id);
        });
    }
    
    public static Ingresso buscar(int id,
            JdbcTemplate jdbc) {
        AtomicReference<Ingresso> ingresso
                = new AtomicReference<>();
        jdbc.query("SELECT * FROM ingressos "
        + "WHERE id = ?;", (ps) -> {
            ps.setInt(1, id);
        }, (rs) -> {
            Ingresso i = new Ingresso(
                    rs.getString("nome"),
                    rs.getFloat("preco"));
            i.id = rs.getInt("id");
            ingresso.set(i);
        });
        return ingresso.get();
    }
    
}