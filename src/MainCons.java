import java.util.*;

public class MainCons {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        JogoDaForca jogo = null;

        try {
            jogo = new JogoDaForca("palavras.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

		jogo.iniciar();

		String letra;

        do {
			System.out.println("palavra = " + jogo.getPalavra());
			System.out.println("dica = " + jogo.getDica());
			System.out.print("\ndigite uma letra da palavra: ");
			letra = input.nextLine();

			try {
                if (jogo.adivinhou(letra)) {
                    System.out.println("voce acertou a letra: " + letra);
                    System.out.println("total de acertos = " + jogo.getAcertos());
			    }
                else {
                    System.out.println("Voce errou a letra: " + letra);
                    System.out.println("total de erros = " + jogo.getErros());
                    System.out.println("Penalidade: " + jogo.getPenalidade());
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
		}
		while(!jogo.terminou());
		
		input.close();
		
		System.out.println("resultado final = " + jogo.getResultado() );
    }
}