package org.example.gerarxmlkt.controller

import org.example.gerarxmlkt.domain.Usuario
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDate
import java.time.Period
import java.util.*

@Controller
@RequestMapping("/api")
class GerarController {

    @RequestMapping("/info")
    @ResponseBody
    fun sistem():String {
        return "Sistema de pé";
    }

    @RequestMapping("/gerar")
    @ResponseBody
     fun gerar():String {
        val start = System.currentTimeMillis()
        val usuarios: MutableList<Usuario> = ArrayList()
        val random = Random()
        for (i in 0..499999) {
            val usuario = Usuario()
            usuario.nome = "Usuário $i"
            usuario.idade = random.nextInt(120)
            usuario.email = "usuario$i@gmail.com"
            usuario.endereco = "Rua do usuario " + i + ", " + random.nextInt(5000)
            usuario.telefone = random.nextInt(999999999).toString()
            usuario.dataNascimento = randomBirthday().toString()
            usuario.sexo = if (i % 2 == 0) "m" else "f"
            usuarios.add(usuario)
        }

        // Salvando os usuários em arquivos XML
        for (i in usuarios.indices) {
            val usuario = usuarios[i]
            salvarArquivoXML(usuario, "arquivo $i.xml")
        }

        val elapsed = System.currentTimeMillis() - start

        println("Tempo de execucao em segundos " + elapsed / 1000 + "s")
        return "Tempo de execucao em segundos " + elapsed / 1000 + "s";
    }
      fun randomBirthday(): LocalDate {
        return LocalDate.now().minus(Period.ofDays((Random().nextInt(365 * 70))))
    }
     fun salvarArquivoXML(usuario: Usuario, nomeArquivo: String) {
        try {
            // Criação do arquivo XML
            val writer = FileWriter(nomeArquivo)
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
            writer.write("<usuario>\n")
            writer.write("    <nome>" + usuario.nome + "</nome>\n")
            writer.write("    <idade>" + usuario.idade + "</idade>\n")
            writer.write("    <email>" + usuario.email + "</email>\n")
            writer.write("    <endereco>" + usuario.endereco + "</endereco>\n")
            writer.write("    <telefone>" + usuario.telefone + "</telefone>\n")
            writer.write("    <dataNascimento>" + usuario.dataNascimento + "</dataNascimento>\n")
            writer.write("    <sexo>" + usuario.sexo + "</sexo>\n")
            // Outros atributos do usuário, se houver
            writer.write("</usuario>\n")
            writer.close()
            println("Arquivo XML $nomeArquivo salvo com sucesso.")
        } catch (e: IOException) {
            System.err.println("Erro ao salvar o arquivo XML " + nomeArquivo + ": " + e.message)
        }
     }
}