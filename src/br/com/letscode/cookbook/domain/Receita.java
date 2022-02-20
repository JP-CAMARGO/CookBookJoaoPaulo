package br.com.letscode.cookbook.domain;

import br.com.letscode.cookbook.enums.Categoria;
import br.com.letscode.cookbook.enums.TipoRendimento;

import java.util.ArrayList;
import java.util.List;

public class Receita {
    private String nome;
    private Categoria categoria;
    private double tempoPreparo;
    private Rendimento rendimento;
    private List<Ingrediente> ingredientes;
    private List<String> preparo;

    public Receita(String nome, Categoria categoria) {
        this.nome = nome;
        this.categoria = categoria;
        this.ingredientes = new ArrayList<>();
        this.preparo = new ArrayList<>();
    }

    public Receita(Receita origem) {
        this.nome = origem.nome;
        this.categoria = origem.categoria;
        this.tempoPreparo = origem.tempoPreparo;
        this.rendimento = origem.rendimento;
        this.ingredientes = new ArrayList<>(origem.ingredientes);
        this.preparo = new ArrayList<>(origem.preparo);
    }


    public String getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public double getTempoPreparo() {
        return tempoPreparo;
    }

    public Rendimento getRendimento() {
        return rendimento;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public List<String> getPreparo() {
        return preparo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setTempoPreparo(double tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public void setRendimento(Rendimento rendimento) {
        this.rendimento = rendimento;
    }

    @Override
    public String toString() {
        return "br.com.letscode.cookbook.domain.Receita{" +
                "nome='" + nome + '\'' +
                ", categoria=" + categoria +
                ", tempoPreparo=" + tempoPreparo +
                ", rendimento=" + rendimento +
                ", ingredientes=" + ingredientes +
                ", preparo=" + preparo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receita receita = (Receita) o;

        return nome != null ? nome.equals(receita.nome) : receita.nome == null;
    }

    @Override
    public int hashCode() {
        return nome != null ? nome.hashCode() : 0;
    }

    public Ingrediente getIngrediente(String nomeIngrediente) {
        if (nomeIngrediente == null || nomeIngrediente.isBlank()) throw new IllegalArgumentException();
        for (Ingrediente ingrediente: ingredientes) {
            if (ingrediente.getNome().equalsIgnoreCase(nomeIngrediente)) {
                return ingrediente;
            }
        }
        return null;
    }

    public int getIngredientIndex(Ingrediente ingrediente){
        int index=0;
        for (Ingrediente i:ingredientes) {

            if(i.equals(ingrediente)){
                break;
            }
            index++;
        }
        return index;
    }

    public void addIngrediente(Ingrediente ingrediente) {
        if (ingrediente == null) throw new IllegalArgumentException();


        ingredientes.add(ingrediente);
    }
    public void delIngrediente(int ingredientIndex) {
        Ingrediente ingrediente = getIngrediente(ingredientes.get(ingredientIndex).getNome());

        ingredientes.remove(ingrediente);

    }

    public int getPreparoIndex(String linhaPreparo){
        int index=0;
        for (String i:preparo) {

            if(i.equals(linhaPreparo)){
                break;
            }
            index++;
        }
        return index;
    }
    public void addPreparo(String linhaPreparo,int posicao) {


        preparo.add(posicao,linhaPreparo);
    }
    public void delPreparo(int linhaPreparoIndex) {
        preparo.remove(linhaPreparoIndex);
    }
}
