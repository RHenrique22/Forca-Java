/**
 * Projeto1 de POO
 * Grupo de alunos:  Roosevelt Henrique, Paulo Elias
 *
*/

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class JogoDaForca {

    private int numeroDePalavras; // quantidade de palavras do arquivo (lido do arquivo)
    private String[] palavras; // um array com as N palavras (lidas do arquivo)
    private String[] dicas; // um array com as N dicas (lidas do arquivo)
    private String palavra; // a palavra sorteada
    private String dica; // a dica da palavra sorteada
    private int indice = 0; // indice da palavra sorteadado jogo
    private int acertos; // total de acertos do jogo
    private int erros; // total de erros do jogo
    private String[] auxiliarPalavra; // auxiliar para ocultar e desocultar a palavra
    private String[] letrasUsadas; // saber se a letra já foi usada no jogo
    private String[] penalidades = {"perna","perna","braço","braço","tronco","cabeça"}; // punições por erros 

    public JogoDaForca (String nomeArq) throws Exception{
        String jogo;
        String[] parte;

        try (Scanner arq = new Scanner(new File(nomeArq))) {

            this.numeroDePalavras = Integer.parseInt(arq.nextLine());
            this.palavras = new String[numeroDePalavras];
            this.dicas = new String[numeroDePalavras];

            for (int i = 0; i < this.numeroDePalavras; i++) {
                jogo = arq.nextLine();
                parte = jogo.split(";");
                this.palavras[i] = parte[0];
                this.dicas[i] = parte[1];
            }
        }
        catch (FileNotFoundException e) {
            throw new Exception("Arquivo não encontrado");
        }
    }

    public void iniciar() {
        Random random = new Random();
        this.indice = random.nextInt(palavras.length);
        this.letrasUsadas = new String[26];
        this.palavra = this.palavras[indice];
        this.dica = this.dicas[indice];
        this.auxiliarPalavra = new String[this.palavra.length()];
        for (int i = 0; i < this.palavra.length(); i++) {
            this.auxiliarPalavra[i] = "_ ";
        }
    }

    public boolean adivinhou(String letra) throws Exception {
        String[] letras = this.palavra.split("");
        int acertou = 0;

        if (this.validarEntrada(letra)) {
            if (!this.testaLetraRepet(letra)) {
                for (int i = 0; i < letras.length; i++) {
                    if (letras[i].toUpperCase().equals(letra.toUpperCase())) {
                        acertou += 1;
                        this.auxiliarPalavra[i] = letra.toUpperCase();
                    }
                }

                if (acertou > 0) {
                    this.acertos += acertou;
                    return true;
                } else {
                    this.erros += 1;
                    return false;
                }
            }
            else {
                throw new Exception("Letra já usada, digite outra letra");
            }
        }
        else {
            throw new Exception("Digite UMA LETRA");
        }
    }

    public boolean terminou() {
        if (this.acertos == this.palavra.length() || this.erros == 6) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean testaLetraRepet(String letra) {
        boolean repete = false;

        int cont = 0;

        while (cont < letrasUsadas.length) {

            if (letrasUsadas[cont] == null) {
                letrasUsadas[cont] = letra;
                break;
            } 
            else {
                if (letrasUsadas[cont].equals(letra)) {
                    repete = true;
                    break;
                }
                else {
                    repete = false;
                }
            }
            
            cont++;
        }

        if (repete) {
            return true;
        } 
        else {
            return false;
        }
    }

    public String getPalavra() {
        return String.join("", this.auxiliarPalavra);
    }

    public String getDica() {
        return dica;
    }

    public String getPenalidade() {
        return this.penalidades[this.erros - 1];
    }

    public int getAcertos() {
        return this.acertos;
    }

    public int getErros() {
        return this.erros;
    }

    public String getResultado() {
        String resultado = "";

        if (this.acertos == this.palavra.length()) {
            resultado = "Venceu";
        }
        else {
            resultado = "Perdeu";
        }

        return resultado;
    }

    public boolean validarEntrada(String letra){
        return Pattern.matches("[a-zA-Z]", letra);
    }
}
