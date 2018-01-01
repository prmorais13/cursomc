package com.pauloroberto.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pauloroberto.cursomc.domains.Categoria;
import com.pauloroberto.cursomc.domains.Cidade;
import com.pauloroberto.cursomc.domains.Cliente;
import com.pauloroberto.cursomc.domains.Endereco;
import com.pauloroberto.cursomc.domains.Estado;
import com.pauloroberto.cursomc.domains.Produto;
import com.pauloroberto.cursomc.domains.enums.TipoCliente;
import com.pauloroberto.cursomc.repositories.CategoriaRepository;
import com.pauloroberto.cursomc.repositories.CidadeRepository;
import com.pauloroberto.cursomc.repositories.ClienteRepository;
import com.pauloroberto.cursomc.repositories.EnderecoRepository;
import com.pauloroberto.cursomc.repositories.EstadoRepository;
import com.pauloroberto.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		this.categoriaRepository.save(Arrays.asList(cat1, cat2));
		this.produtoRepository.save(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Paraíba");
		Estado est2 = new Estado(null, "Rio Grande do Norte");
		
		Cidade c1 = new Cidade(null, "João Pessoa", est1);
		Cidade c2 = new Cidade(null, "Natal", est2);
		Cidade c3 = new Cidade(null, "Parnamirim", est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		this.estadoRepository.save(Arrays.asList(est1, est2));
		this.cidadeRepository.save(Arrays.asList(c1, c2, c3));
		
		
		Cliente cli1 = new Cliente(null, "Paulo Roberto", "prmorais_13@hotmail.com", "39135810491", TipoCliente.PESSOAFISICA);
		
		Endereco e1 = new Endereco(null, "Rua Parque Paraúna", "79", "Parque Verde", "Nova Esperança", "59144170", cli1, c3);
		Endereco e2 = new Endereco(null, "Rua Princesa Isabel", "799", null, "Cidade Alta", "59025400", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli1.getTelefones().addAll(Arrays.asList("32328877", "987015547"));
		
		this.clienteRepository.save(cli1);
		this.enderecoRepository.save(Arrays.asList(e1, e2));
		
		
	}
}
