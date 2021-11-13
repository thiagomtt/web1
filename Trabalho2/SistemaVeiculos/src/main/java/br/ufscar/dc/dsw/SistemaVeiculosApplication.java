package br.ufscar.dc.dsw;

import br.ufscar.dc.dsw.dao.ClienteDAO;
import br.ufscar.dc.dsw.dao.LojaDAO;
import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SistemaVeiculosApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaVeiculosApplication.class, args);
    }

    private static final Logger log = LoggerFactory.getLogger(SistemaVeiculosApplication.class);

    @Bean
    public CommandLineRunner demo(UsuarioDAO usuarioDAO, LojaDAO lojaDAO, ClienteDAO clienteDAO, BCryptPasswordEncoder encoder) {
        return (args) -> {

            Loja loja1 = new Loja();
            loja1.setEmail("loja1");
            loja1.setSenha(encoder.encode("loja1"));
            loja1.setNome("LOJA 1");
            loja1.setPapel("LOJA");
            loja1.setCnpj("1");
            loja1.setDescricao("essa é a loja 1");
            lojaDAO.save(loja1);

            Loja loja2 = new Loja();
            loja2.setEmail("loja2");
            loja2.setSenha(encoder.encode("loja2"));
            loja2.setNome("LOJA 2");
            loja2.setPapel("LOJA");
            loja2.setCnpj("2");
            loja2.setDescricao("essa é a loja 2");
            lojaDAO.save(loja2);

            Usuario user = new Usuario();
            user.setEmail("admin");
            user.setSenha(encoder.encode("admin"));
            user.setNome("Thiago");
            user.setPapel("ADMIN");
            usuarioDAO.save(user);

            Cliente cliente = new Cliente();
            cliente.setEmail("tmoraes505@gmail.com");
            cliente.setSenha(encoder.encode("thg"));
            cliente.setNome("Thiago");
            cliente.setPapel("CLIENTE");
            cliente.setCpf("44175184830");
            cliente.setSexo("M");
            cliente.setTelefone("+5514981216643");
            cliente.setDataNascimento(LocalDate.parse("2000-05-05"));
            clienteDAO.save(cliente);

            List<Usuario> usuarios = usuarioDAO.findAll();
            log.info("Imprimindo Usuarios - findALL()");
            for (Usuario u : usuarios) {
                log.info(u.toString());
            }

            List<Loja> lojas = lojaDAO.findAll();
            log.info("Imprimindo Lojas");
            for (Loja l : lojas) {
                log.info(l.toString());
            }

            List<Cliente> clientes = clienteDAO.findAll();
            log.info("Imprimindo Clientes");
            for (Cliente c : clientes) {
                log.info(c.toString());
            }

        };
    }

}
