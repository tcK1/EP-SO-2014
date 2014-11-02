class No{
	public Processo processo;
	public No prox;
	public int indice = 3; /* desbloqueia depois de outros dois processos*/
	
	No(Processo p){
		processo = p;
		prox = null;
	}
}