package com.pauloroberto.cursomc.domains;

import java.util.Date;

import javax.persistence.Entity;

import com.pauloroberto.cursomc.domains.enums.EstadoPagamento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class PagamentoComBoleto extends Pagamento {
	private static final long serialVersionUID = 1L;

	@Getter @Setter
	private Date dataVencimento;
	@Getter @Setter
	private Date dataPagamento;
	
	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(id, estado, pedido);
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
	}
	
	
}
