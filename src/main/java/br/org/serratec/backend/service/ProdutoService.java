package br.org.serratec.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.backend.dto.AlterarProdutoDTO;
import br.org.serratec.backend.dto.InserirProdutoDTO;
import br.org.serratec.backend.dto.ProdutoDTO;
import br.org.serratec.backend.exception.RecursoBadRequestException;
import br.org.serratec.backend.exception.RecursoNotFoundException;
import br.org.serratec.backend.model.Produto;
import br.org.serratec.backend.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository produtoRepository;

	/**
	 * METODO PARA INSERIR UM PRODUTO
	 * 
	 * @param produto
	 * @return UM NOVO PRODUTO
	 */
	public ProdutoDTO inserir(InserirProdutoDTO inserirProdutoDTO) throws RecursoBadRequestException {

		if (produtoRepository.findByNome(inserirProdutoDTO.getNome()) != null) {
			throw new RecursoBadRequestException("Produto ja cadastrado! Insira outro");
		}
			Produto produto = new Produto();
			produto.setNome(inserirProdutoDTO.getNome());
			produto.setCategoria(inserirProdutoDTO.getCategoria());
			produto.setDataCadastro(inserirProdutoDTO.getDataCadastro());
			produto.setDescricao(inserirProdutoDTO.getDescricao());
			produto.setQtdEstoque(inserirProdutoDTO.getQtdEstoque());
			produto.setValorUnitario(inserirProdutoDTO.getValorUnitario());
			produtoRepository.save(produto);

			return new ProdutoDTO(produto);
			
		}

	/**
	 * METODO PARA LISTAR OS PRODUTOS
	 * 
	 * @return UMA LISTA DE PRODUTOS
	 */
	public List<ProdutoDTO> listar() {
		List<Produto> produtos = produtoRepository.findAll();
		List<ProdutoDTO> produtosDTO = new ArrayList<ProdutoDTO>();

		for (Produto produto : produtos) {
			ProdutoDTO produtoDTO = new ProdutoDTO(produto);
			produtosDTO.add(produtoDTO);
		}
		return produtosDTO;
	}

	/**
	 * METODO PARA ALTERAR UM PRODUTO
	 * 
	 * @param alterarProdutoDTO
	 * @return UM NOVO PRODUTO ALTERADO
	 */
	public ProdutoDTO alterar(AlterarProdutoDTO alterarProdutoDTO) {

		if (produtoRepository.findByNome(alterarProdutoDTO.getNome()) != null) {

			Produto produto = new Produto();
			produto.setNome(alterarProdutoDTO.getNome());
			produto.setDescricao(alterarProdutoDTO.getDescricao());
			produto.setQtdEstoque(alterarProdutoDTO.getQtdEstoque());
			produto.setValorUnitario(alterarProdutoDTO.getValorUnitario());
			produto.setCategoria(alterarProdutoDTO.getCategoria());

			return new ProdutoDTO(produto);
		} else {
			throw new RecursoNotFoundException("Produto não encontrado");
		}
	}

	/**
	 * METODO PARA DELETAR UM PRODUTO
	 * 
	 * @param id
	 */
	public void deletar(Long id) {
		if (produtoRepository.existsById(id)) {
			produtoRepository.deleteById(id);
		}
	}

}