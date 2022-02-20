package br.com.letscode.cookbook;

import br.com.letscode.cookbook.controller.Catalogo;
import br.com.letscode.cookbook.domain.Receita;
import br.com.letscode.cookbook.enums.Categoria;
import br.com.letscode.cookbook.view.CatalogoView;
import br.com.letscode.cookbook.view.ConsoleUtils;

public class CookBook {
    Catalogo catalogo;

    public static void main(String[] args) {
        Catalogo catalogo = new Catalogo();
        catalogo.add(new Receita("Cookies da Lara 1", Categoria.DOCE));
        catalogo.add(new Receita("Cookies da Lara 2", Categoria.DOCE));
        catalogo.add(new Receita("Cookies da Lara 3", Categoria.DOCE));
        catalogo.add(new Receita("Cookies da Lara 4", Categoria.DOCE));
        CatalogoView view = new CatalogoView(catalogo);

        view.view();
    }
}
