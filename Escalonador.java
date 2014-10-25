import java.io.*;
import java.util.Scanner;

public class Escalonador{

	static Processo tabela[] = new Processo[10]; /* tabela de procesos */
	static int quantum;
	static Processo executando = null;
	static Fila pronto = new Fila();
	static Fila bloqueado = new Fila();
	
	public static void main(String[] args){
		try{
			criaProcessos();
			leQuantum();
			while(!estaVazia()){ /* enquanto houver processos na tabela de processos */
				decrementaBloqueados(); /* decrementa o contador da fila dos bloqueados */
				executando = pronto.pop(); /* obtem o proximo a executar */
				executando.status = "Executando";
				if(executando == null){
					continue;
				}
				boolean paraPronto = true;
				for(int c = 0; c < quantum; c++){ /* operacoes que o processo tem direito */
					String instrucao = executando.DS[executando.PC];
					executando.PC++;
					switch(instrucao){
							case "COM": 
							if(quantum - c == 1){ /* depois da ultima iteracao, inserir na fila de prontos */
								executando.status = "Pronto";
								pronto.push(executando);	
							}
							break;
						case "E/S":
							executando.status = "Bloqueado";
							bloqueado.push(executando);
							c = quantum;
							break;
						case "SAIDA":
							finalizaProcesso(executando); /* remove processo da fila de bloqueados */
							c = quantum;
							break;
						default:
							switch(instrucao.substring(1,1)){
								case "X":
									executando.X = Integer.parseInt(instrucao.substring(3));
									if(quantum - c == 1){ /* depois da ultima iteracao, inserir na fila de prontos */
										executando.status = "Pronto";
										pronto.push(executando);	
									}
									break;
								case "Y":
									executando.Y = Integer.parseInt(instrucao.substring(3));
									if(quantum - c == 1){ /* depois da ultima iteracao, inserir na fila de prontos */
										executando.status = "Pronto";
										pronto.push(executando);	
									}
									break;
							}
					}
				}
			}
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	
	static void criaProcessos() throws IOException{
		FileReader fr;
		Processo p;
		for(int i = 1; i <= 10; i++){
			if(i < 10) fr = new FileReader("processos/0" + i + ".txt");
			else fr = new FileReader("processos/" + i + ".txt");
			p = new Processo(fr); 
			tabela[i - 1] = p;
			pronto.push(p);
		}
	}
	
	static void leQuantum() throws IOException{
		FileReader fr = new FileReader("processos/quantum.txt");
		Scanner sc = new Scanner(fr);
		quantum = sc.nextInt();
	}
	
	/* verifica se a tabela de processos esta vazia */
	static boolean estaVazia(){
		for(int i = 0; i < 10; i++)
			if(tabela[i] != null) return false;
		return true;
	}
	
	/* decrementa o contador da fila de bloqueados
	se algum processo tiver, ao final do procedimento, cont == 0, ele eh adicionado a fila de prontos */
	static void decrementaBloqueados(){
		for(No n = pronto.inicio; n != null; n = n.prox) n.indice--;
		No n = pronto.inicio;
		while(n.indice == 0){ /* os mais antigos da fila de bloqueados esta sempre no inicio*/
			Processo p = n.src;
			p.status = "Pronto";
			pronto.push(p);
			pronto.inicio = n.prox;
			n = n.prox;
		}
	}

	/* remove processo da fila de processos, obtendo estatisticas */
	static void finalizaProcesso(Processo p){
		for(int i = 0; i < 10; i++)
			if(tabela[i] == p) /* comparacao de ponteiros */
				tabela[i] = null;
		/* OBTEM ESTATISTICAS */ 
	}

}

class Processo{

	public String nome;
	public String status = "Pronto";
	public String DS[] = new String[21]; /* segmento de dados */
	public int PC = 0; /* contador de programa */
	public int X = 0;
	public int Y = 0;
	
	Processo(FileReader fr){
		Scanner sc = new Scanner(fr);
		int i = 0;
		nome = sc.nextLine();
		while(sc.hasNextLine()){
			DS[i] = sc.nextLine();
			i++;
		}
	}
	
}

class Fila{
	
	public No inicio;
	private No ultimo;
	
	Fila(){
		inicio = null;
		ultimo = null;
	}
	
	/* adiciona no a fila */
	public void push(Processo p){
		if(inicio == null){
			No n = new No(p);
			inicio = n;
			ultimo = n;
		}
		else{
			ultimo.prox = new No(p);
		}
	}
	
	/* remove no da fila, retornando o respectivo processo */
	public Processo pop(){
		No aux = inicio;
		if(inicio != null) inicio = inicio.prox;
		else inicio = null;
		return aux.src;	
	}
	
}

class No{

	public Processo src;
	public No prox;
	private int indice = 3; /* desbloqueia depois de outros dois processos*/
	
	No(Processo p){
		src = p;
		prox = null;
	}

}