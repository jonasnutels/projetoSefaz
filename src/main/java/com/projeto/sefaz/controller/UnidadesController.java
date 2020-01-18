package com.projeto.sefaz.controller;

import com.projeto.sefaz.model.Unidades;
import com.projeto.sefaz.repository.funcionarioRepository;
import com.projeto.sefaz.repository.unidadesRepository;
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
@RequestMapping("/unidades")
public class UnidadesController {

    @Autowired
    private unidadesRepository unidadesRepository;


    @Autowired
    private funcionarioRepository funcionarioRepository;

    @GetMapping("/adicionar")
    public ModelAndView adicionar () {
        ModelAndView mv = new ModelAndView("AdicionaUnidade");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        mv.addObject("nomeUsuarioLogado",authentication.getName());
        mv.addObject("unidades",new Unidades());

        return mv;
    }

    @PostMapping("/adicionar")
    public ModelAndView adicionar (@Valid Unidades unidades, BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()){
            ModelAndView mv2 = new ModelAndView("AdicionaUnidade");
            mv2.addObject("turma",new Unidades());
            return mv2;
        }
        ModelAndView mv = new ModelAndView("redirect:/unidades/listar");
        if(unidades != null){
            attributes.addFlashAttribute("mensagem", "Unidades editado com sucesso.");
        } else {
            attributes.addFlashAttribute("mensagem", "Unidades adicionado com sucesso.");
        }
        this.unidadesRepository.save(unidades);
        return mv;
    }

    @GetMapping("/listar")
    public ModelAndView listar () {
        ModelAndView mv = new ModelAndView("ListarUnidades");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        mv.addObject("nomeUsuarioLogado",authentication.getName());
        mv.addObject("unidades", unidadesRepository.findAll());
        return mv;
    }
    @GetMapping("/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id,
                                RedirectAttributes attributes){
        unidadesRepository.deleteById(id);
        ModelAndView mv = new ModelAndView("redirect:/turmas/listar");
        attributes.addFlashAttribute("mensagem", "Unidades removida com sucesso.");
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView("AdicionaUnidade");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        mv.addObject("nomeUsuarioLogado",authentication.getName());
        mv.addObject("turma", unidadesRepository.findById(id));
        mv.addObject("alunos", funcionarioRepository.findAll());
        mv.addObject("id", id);
        return mv;
    }
}
