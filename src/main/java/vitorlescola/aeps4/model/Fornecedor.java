package vitorlescola.aeps4.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "fornecedor")
public class Fornecedor extends Pessoa{
    @OneToMany(
            mappedBy = "fornecedor",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<Produto> produtos;

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}