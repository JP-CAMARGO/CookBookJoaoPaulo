package br.com.letscode.cookbook.view;

import br.com.letscode.cookbook.domain.Ingrediente;
import br.com.letscode.cookbook.domain.Receita;
import br.com.letscode.cookbook.domain.Rendimento;
import br.com.letscode.cookbook.enums.Categoria;
import br.com.letscode.cookbook.enums.TipoMedida;
import br.com.letscode.cookbook.enums.TipoRendimento;

import java.util.Locale;

public class EditReceitaView {
    private Receita receita;

    public EditReceitaView(Receita receita) {
        if (receita.getIngredientes()==null) {
            this.receita = new Receita(receita.getNome(), receita.getCategoria());
        } else {
            this.receita = new Receita(receita);
        }
    }

    public Receita edit() {
        boolean confirm = false;
        do {
            //Mostra a receita em cada etapa
            new ReceitaView(receita).fullView(System.out);
            //Exibe o menu de opções.
        } while (showMenuEdit());

        String opcao = ConsoleUtils.getUserOption("Confirma as alterações feitas na receita?%nS - Sim   N - Não", "S", "N");
        //Se confirmar, retorna a receita alterada; senão, retorna null
        if (opcao.equalsIgnoreCase("S")) {
            return receita;
        } else {
            return null;
        }
    }

    private boolean showMenuEdit() {
        String[] options = new String[7];
        StringBuilder sb = new StringBuilder("#".repeat(36));
        sb.append( " MENU DE EDIÇÃO DA RECEITA ");
        sb.append("#".repeat(37));

        sb.append("%n  N : alterar Nome  %n");
        options[0] = "N";
        sb.append("  C : alterar Categoria  %n");
        options[1] = "C";
        sb.append("  T : alterar Tempo de preparo  %n");
        options[2] = "T";
        sb.append("  R : alterar Rendimento  %n");
        options[3] = "R";
        sb.append("  I : alterar lista de Ingredientes  %n");
        options[4] = "I";
        sb.append("  P : alterar modo de Preparo  %n");
        options[5] = "P";
        sb.append("  # ").append("# ".repeat(48)).append("%n");
        sb.append("  X : voltar ao menu principal  %n");
        options[6] = "X";
        sb.append("#".repeat(100)).append("%n");

        String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
        switch (opcao) {
            case "N":
                alteraNome();
                break;
            case "C":
                alteraCategoria();
                break;
            case "T":
                alteraTempoPreparo();
                break;
            case "R":
                alteraRendimento();
                break;
            case "I":
                alteraIngredientes();
                break;
            case "P":
                alteraPreparo();
                break;
            case "X":
                return false;
            default:
                System.out.println("Opção inválida!!!");
        }
        return true;
    }

    private void alteraNome() {
        // mostra o nome atual
        System.out.println(">> O nome atual desta receita é :'"+ receita.getNome()+"'");
        //boolean nomeValido=false;
        //while (!nomeValido) {
            String newName = ConsoleUtils.getUserInput("Informe o novo nome (ou deixe em branco para manter a atual):");
            if (!newName.isBlank()) {
                // neste ponto deveria verificar se esse nome já existe; será feito no retorno ao catalogoView
                receita.setNome(newName);
                //nomeValido=true;

            } //else {
                // deixou em branco - não altera
                //nomeValido=true;
           // }
        //}

    }
    private void alteraCategoria() {

        // mostra a categoria atual
        System.out.println(">> A categoria atual desta receita é :'"+ receita.getCategoria()+"'");

        StringBuilder sb = new StringBuilder("Qual é a nova categoria?\n");
        String[] options = new String[Categoria.values().length];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, Categoria.values()[i]));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        Categoria categoria = null;
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                categoria = Categoria.values()[i];
                break;
            }
        }
        receita.setCategoria(categoria);
    }
    private void alteraTempoPreparo() {
        // mostra o tempo de preparo atual
        System.out.println(">> O tempo de preparo atual desta receita é : "+ receita.getTempoPreparo() +" minutos");

        boolean tempoValido=false;
        while (!tempoValido) {
            String newTimeStr = ConsoleUtils.getUserInput("Informe o novo tempo de preparo, em minutos (ou 0 para manter o atual) :");
            newTimeStr.replaceAll(",", ".");  // caso o usuário informe vírgula separando inteiro de decimais
            if (ConsoleUtils.isNumeric(newTimeStr)) {
                double newTime = Double.parseDouble(newTimeStr);
                if (newTime > 0) {
                    receita.setTempoPreparo(newTime);
                    tempoValido = true;
                } else {
                    if (newTime == 0) {
                        tempoValido = true;
                    }
                }
            } else {
                System.out.println(">>Tempo de preparo inválido");
            }
        }
    }
    private void alteraRendimento(){
        // mostra o rendimento atual
        if (receita.getRendimento()!=null) {
                if (receita.getRendimento().getMinimo() != receita.getRendimento().getMaximo()) {
                    System.out.printf("Rendimento atual : de %s a %s %s%n", receita.getRendimento().getMinimo(), receita.getRendimento().getMaximo(), receita.getRendimento().getTipo().name());
                } else {
                    System.out.printf("Rendimento atual : %s %s%n", receita.getRendimento().getMinimo(), receita.getRendimento().getTipo().name());
                }
        } else {
            System.out.println("Ainda não há rendimento cadastrado para esta receita");
        }

        Rendimento novoRendimento = new Rendimento(0,TipoRendimento.PORCOES);

        // pega quantidade mínima, quantidade máxima e tipo_rendimento
        boolean entradaValida =false;
        while(!entradaValida) {
            String newQtdMinStr = ConsoleUtils.getUserInput("Informe a quantidade mínima:");
            if (ConsoleUtils.isNumeric(newQtdMinStr)) {
                int newQtdMin = Integer.parseInt(newQtdMinStr);
                if (newQtdMin > 0) {
                    novoRendimento.setMinimo(newQtdMin);
                    entradaValida = true;
                } else {
                    System.out.println(">>Quantidade inválida - informe um número inteiro maior que zero");
                }
            } else {
                System.out.println(">>Quantidade inválida - informe um número");
            }
        }

        entradaValida =false;
        while(!entradaValida) {
            String newQtdMaxStr = ConsoleUtils.getUserInput("Informe a quantidade máxima:");
            if (ConsoleUtils.isNumeric(newQtdMaxStr)) {
                int newQtdMax = Integer.parseInt(newQtdMaxStr);
                if (newQtdMax > 0) {
                    novoRendimento.setMaximo(newQtdMax);
                    entradaValida = true;
                } else {
                    System.out.println(">>Quantidade inválida - informe um número inteiro maior que zero");
                }
            } else {
                System.out.println(">>Quantidade inválida - informe um número");
            }
        }

        StringBuilder sb = new StringBuilder("Qual o tipo do rendimento?\n");
        String[] options = new String[TipoRendimento.values().length];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, TipoRendimento.values()[i]));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);

        TipoRendimento novoTipo = null;
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                novoTipo = TipoRendimento.values()[i];
                break;
            }
        }
        novoRendimento.setTipo(novoTipo);

        receita.setRendimento(novoRendimento);
    }
    private void alteraIngredientes(){
        // enquanto não pedir pra sair
        //    mostra a lista de ingredientes identificados com números
        //    mostra menu com opções - incluir, alterar, excluir, sair

        do {
            new ReceitaView(receita).ingredientesView(System.out);
        } while (showMenuIngredientes());
    }

    private boolean showMenuIngredientes() {
        String[] options = new String[4];
        StringBuilder sb = new StringBuilder("#".repeat(38));
        sb.append( " MENU DE INGREDIENTES ");
        sb.append("#".repeat(39));

        sb.append("%n  I : Incluir ingrediente  %n");
        options[0] = "I";

        if (!receita.getIngredientes().isEmpty()) {
            sb.append("  A : Alterar ingrediente  %n");
            options[1] = "A";
            sb.append("  E : Excluir ingrediente  %n");
            options[2] = "E";
        }
        sb.append("  # ").append("# ".repeat(48)).append("%n");
        sb.append("  X : voltar ao menu de edição da receita  %n");
        options[3] = "X";
        sb.append("#".repeat(100)).append("%n");

        String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
        switch (opcao) {
            case "I":
                adicionaIngrediente();
                break;
            case "A":
                alteraIngrediente();
                break;
            case "E":
                excluiIngrediente();
                break;
            case "X":
                return false;
            default:
                System.out.println("Opção inválida!!!");
        }

        return true;
    }
    private void adicionaIngrediente(){
        // pede nome, quantidade e tipo
        // inclui na List de Ingredientes de receita

        Ingrediente novoIngrediente = new Ingrediente("",0, TipoMedida.GRAMA);

        boolean nomeValido=false;
        while (!nomeValido) {
           String newName = ConsoleUtils.getUserInput("Informe o nome do  ingrediente :");
            if (!newName.isBlank()) {
                //Procura na receita um ingrediente com o mesmo nome.
                Ingrediente other = receita.getIngrediente(newName);
                //Se encontrar, mostra mensagem.
                if (other != null) {
                    System.out.println("Já existe ingrediente com esse nome na receita; por favor, escolha outro");
                } else {
                    novoIngrediente.setNome(newName);
                    nomeValido=true;
                }
            }
        }

        boolean entradaValida =false;
        while(!entradaValida) {
            String newQtdStr = ConsoleUtils.getUserInput("Informe a quantidade :");
            if (ConsoleUtils.isNumeric(newQtdStr)) {
                int newQtd = Integer.parseInt(newQtdStr);
                if (newQtd > 0) {
                    novoIngrediente.setQuantidade(newQtd);
                    entradaValida = true;
                } else {
                    System.out.println(">>Quantidade inválida - informe um número maior que zero");
                }
            } else {
                System.out.println(">>Quantidade inválida - informe um número");
            }
        }

        StringBuilder sb = new StringBuilder("Qual o tipo de medida do ingrediente?\n");
        String[] options = new String[TipoMedida.values().length];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, TipoMedida.values()[i]));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);

        TipoMedida novoTipo = null;
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                novoTipo = TipoMedida.values()[i];
                break;
            }
        }
        novoIngrediente.setTipo(novoTipo);

        receita.addIngrediente(novoIngrediente);

    }
    private void alteraIngrediente(){
        // seleciona o ingrediente


        String[] options = new String[receita.getIngredientes().size()];
        for (int i = 0; i < receita.getIngredientes().size(); i++) {
            options[i] = String.valueOf(i+1);
        }
        String indexStr = ConsoleUtils.getUserOption("Qual ingrediente deseja alterar? (número) :",options);

        int ingredientIndex = Integer.parseInt(indexStr);

        // exclui
        receita.delIngrediente(ingredientIndex-1);

        // insere um novo
        adicionaIngrediente();

    }

    private void excluiIngrediente(){
        // seleciona o ingrediente

        String[] options = new String[receita.getIngredientes().size()];
        for (int i = 0; i < receita.getIngredientes().size(); i++) {
            options[i] = String.valueOf(i+1);
        }
        String indexStr = ConsoleUtils.getUserOption("Qual ingrediente deseja excluir? (número) :",options);

        int ingredientIndex = Integer.parseInt(indexStr);

        // exclui
        receita.delIngrediente(ingredientIndex-1);

    }
    private void alteraPreparo(){
        // enquanto não pedir pra sair
        //    mostra a lista de passos do preparo, identificados com números
        //    mostra menu com opções - incluir, alterar, excluir, sair

        do {
            new ReceitaView(receita).preparoView(System.out);
        } while (showMenuPreparo());
    }
    private boolean showMenuPreparo() {
        String[] options = new String[4];
        StringBuilder sb = new StringBuilder("#".repeat(37));
        sb.append( " MENU DO MODO DE PREPARO ");
        sb.append("#".repeat(38));

        sb.append("%n  I : Incluir linha no modo de preparo  %n");
        options[0] = "I";

        if (!receita.getPreparo().isEmpty()) {
            sb.append("  A : Alterar linha do modo de preparo  %n");
            options[1] = "A";
            sb.append("  E : Excluir linha do modo de preparo  %n");
            options[2] = "E";
        }
        sb.append("  # ").append("# ".repeat(48)).append("%n");
        sb.append("  X : voltar ao menu de edição da receita  %n");
        options[3] = "X";
        sb.append("#".repeat(100)).append("%n");

        String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
        switch (opcao) {
            case "I":
                adicionaLinhaPreparo(0);  // 0 indica que é nova linha
                break;
            case "A":
                alteraLinhaPreparo();
                break;
            case "E":
                excluiPreparo();
                break;
            case "X":
                return false;
            default:
                System.out.println("Opção inválida!!!");
        }
        return true;
    }
    private void adicionaLinhaPreparo(int posicaoAtual){

        String novaLinha = ConsoleUtils.getUserInput("Digite a linha do modo de preparo :");

        // verifica em que posição ele quer incluir essa linha; se já veio como parâmetro, usa o que veio
        int posicao;


        if (posicaoAtual==0) {
            int posicoes = receita.getPreparo().size()+1;
            if(posicoes==1) {
                posicao=1;
            } else {
                String[] options = new String[posicoes];
                for (int i = 1; i <= posicoes; i++) {
                    options[i - 1] = String.valueOf(i);
                }
                String indexStr = ConsoleUtils.getUserOption("Em que posição na lista essa nova linha deve entrar? (1 a " + Integer.toString(posicoes) + ") :", options);

                posicao = Integer.parseInt(indexStr);
            }
        } else {
            posicao=posicaoAtual;
        }
        if (!novaLinha.isEmpty()){
            receita.addPreparo(novaLinha,posicao-1);
        }

    }
    private void alteraLinhaPreparo(){
        // seleciona a linha

        String[] options = new String[receita.getPreparo().size()];
        for (int i = 0; i < receita.getPreparo().size(); i++) {
            options[i] = String.valueOf(i+1);
        }
        String indexStr = ConsoleUtils.getUserOption("Qual linha do preparo deseja alterar? (número) :",options);

        int linhaPreparoIndex = Integer.parseInt(indexStr);

        // exclui
        receita.delPreparo(linhaPreparoIndex-1);

        // insere uma nova linha
        adicionaLinhaPreparo(linhaPreparoIndex);
    }
    private void excluiPreparo() {
        // seleciona a linha

        String[] options = new String[receita.getPreparo().size()];
        for (int i = 0; i < receita.getPreparo().size(); i++) {
            options[i] = String.valueOf(i+1);
        }
        String indexStr = ConsoleUtils.getUserOption("Qual linha do preparo deseja excluir? (número) :",options);

        int linhaPreparoIndex = Integer.parseInt(indexStr);

        // exclui
        receita.delPreparo(linhaPreparoIndex-1);
    }
}
