package com.projeto.sefaz.controller;

import com.projeto.sefaz.model.Funcionario;
import com.projeto.sefaz.repository.funcionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private funcionarioRepository funcionarioRepository;

    @GetMapping("/adicionar")
    public ModelAndView adicionar() {
        ModelAndView mv = new ModelAndView("AdicionaFuncionario");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        mv.addObject("nomeUsuarioLogado",authentication.getName());
        mv.addObject("Funcionario",new Funcionario());
        return mv;
    }

    @PostMapping("/adicionar")
    public ModelAndView adicionar (@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()){
            ModelAndView mv2 = new ModelAndView("AdicionaFuncionario");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            mv2.addObject("nomeUsuarioLogado",authentication.getName());
            mv2.addObject("funcionario",new Funcionario());
            return mv2;
        }
        ModelAndView mv = new ModelAndView("redirect:/funcionarios/listar");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        mv.addObject("nomeUsuarioLogado",authentication.getName());
        if(funcionario.getId() != null){
            attributes.addFlashAttribute("mensagem", "Aluno editado com sucesso.");
        } else {
            attributes.addFlashAttribute("mensagem", "Aluno adicionado com sucesso.");
        }
        this.funcionarioRepository.save(funcionario);
        return mv;
    }

    @GetMapping("/listar")
    public ModelAndView listar () {
        ModelAndView mv = new ModelAndView("ListarFuncionarios");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        mv.addObject("nomeUsuarioLogado",authentication.getName());
        mv.addObject("funcionarios", funcionarioRepository.findAll());
        mv.addObject("qntFuncionarios", funcionarioRepository.findAll().size());
        return mv;
    }

    @GetMapping("/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id, RedirectAttributes attributes){
        funcionarioRepository.deleteById(id);
        ModelAndView mv = new ModelAndView("redirect:/alunos/listar");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        mv.addObject("nomeUsuarioLogado",authentication.getName());
        attributes.addFlashAttribute("mensagem", "Aluno removido com sucesso.");
        return mv;
    }
}
