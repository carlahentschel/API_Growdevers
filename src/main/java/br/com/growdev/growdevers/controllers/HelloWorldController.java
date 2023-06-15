package br.com.growdev.growdevers.controllers;

import br.com.growdev.growdevers.models.Growdever;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("helloworld")
public class HelloWorldController {

    @GetMapping
    public String helloWorld() {
        return "Olá mundo!! :) ";
    }

    @PostMapping
    public String recebeMensagem(@RequestBody Growdever data) {
        System.out.println(data.getName());
        System.out.println(data.getEmail());
        return "Growdever recebido.";

    }
}

// CRUD DE GROWDEVERS

/*
- Nome
- CPF
- Email
- Telefone
- Status [ESTUDANDO, CANCELADO, FORMADO]
- Skills -> JS, TS, Html, Node, Java, Spring

Growdever, GrowdeverSkill

Funcionalidades:
- Cadastrar um growdever
    -> Informações: Nome, CPF, Email, Telefone, Status
- Listar todos os growdevers
    -> Resposta: Uma lista contendo as seguintes informações: Nome, status e email
- Obter um growdever por um identificador
    -> Resposta: Todas as informações de um growdever
- Atualizar as informações de um growdever
    -> Informações: Nome, status, telefone e email
    -> Regras: não é preciso informar todas as informações
- Deletar um growdever
- Filtrar os growdevers por nome e por status
- Remover uma skill individualmente
    -> Receber a identificação da skill
- Adicionar skills
     -> Informações: Uma lista de novas skills

- Regras:
    - cpf precisa ser validado
    - email precisa see validado
    - não pode existir 2 growdevers com o mesmo cpf
    - não pode existir 2 growdevers com o mesmo email

 */
