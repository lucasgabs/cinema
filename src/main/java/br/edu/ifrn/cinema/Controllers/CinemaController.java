package br.edu.ifrn.cinema.Controllers;

import br.edu.ifrn.cinema.models.Ingresso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CinemaController {
    
    @GetMapping("/")
    public String index(Model model) {
        List<Ingresso> ingressos =
                Ingresso.listar(jdbc);
        model.addAttribute("ingressos",
                ingressos);
        return "index";
    }
    
    @Autowired JdbcTemplate jdbc;
    
    @PostMapping("/adicionar")
    public String adicionar(String nome, float preco) {
        Ingresso i = new Ingresso(nome, preco);
        i.salvar(jdbc);
        return "redirect:/";
    }
    
    @PostMapping("/atualizar")
    public String atualizar(int id, 
            String nome, float preco) {
        Ingresso i = Ingresso.buscar(id, jdbc);
        i.setNome(nome);
        i.setValor(preco);
        i.salvar(jdbc);
        return "redirect:/";
    }
    
    @GetMapping("/excluir")
    public String excluir(int id) {
        Ingresso.remover(id, jdbc);
        return "redirect:/";
    }
    
    @GetMapping("/editar")
    public String editar(int id, Model model) {
        List<Ingresso> ingressos =
                Ingresso.listar(jdbc);
        Ingresso i = Ingresso.buscar(id, jdbc);
        model.addAttribute("ingressos",
                ingressos);
        model.addAttribute("i", i);
        return "index";
    }
    
}