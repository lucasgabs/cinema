package br.edu.ifrn.cinema.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class CinemaController {
    @GetMapping("/")
    public String index(Model model){
        List<Ingresso> ingressos = Ingresso.listar(jdbc); 
        model.addAttribute("ingresso", ingresso);
        return "index";
    }
    
    @Autowired JdbcTemplate jdbc;
    
    @PostMapping("/adicionar")
    public String adicionar(String nome, int quantidade, float preco){
        Ingresso i = new Ingresso(nome, quantidade, preco);
        i.salvar(jdbc);
        return "redirect:/";
    }
    
    @PostMapping("/atualizar")
    public String atualizar(int id, String nome, int quantidade, float preco){
        Produto p = Produto.buscar(id, jdbc);
        p.setNome(nome);
        p.setQuantidade(quantidade);
        p.setPreco(preco);
        p.salvar(jdbc);
        return "redirect:/";
        
    }
    
    @GetMapping("/excluir")
    public String excluir(int id){
        Produto.remover(id, jdbc);
        return "redirect:/";
        
    }
    
    @GetMapping("/editar")
    public String editar(int id, Model model){
        List<Produto> produtos = Produto.listar(jdbc); 
        Produto p = Produto.buscar(id, jdbc);
        model.addAttribute("produtos", produtos);
        model.addAttribute("p", p);
        return "index";
        
    }
}
