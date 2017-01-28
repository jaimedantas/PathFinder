/*
 * Algoritimo de busca da A estrela
 */
package ia_busca;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * @author Jaime
 */
public class A_ESTRELA {
    NO inicio;//ponto inicial da busca
    NO fim;//ponto final da busca
    NO[][] matriz;//matriz de nos representada no tableiro
    //---listas
    ArrayList<NO> visitados;//todos os nos ja checados
    ArrayList<NO> obstaculos;//todos os nos q sao obstaculos
    ArrayList<NO> caminho;//caminho final da origem para o destino
    ArrayList<NO> abertos;//nos que ainda nao foram acessados
    ArrayList<NO> fechados;//nos que ja foram acessados

    //definicao dos pesos que serao a distancia euclidiana da origem para o destino
    int peso_diagonal = 14;//arredondamento de uma diagonal
    int peso_lado = 10;
    
    //construtor principal
    A_ESTRELA(NO aux_inico, NO aux_fim, NO aux_matriz[][]){
        this.inicio = aux_inico;
        this.fim = aux_fim;
        this.matriz = aux_matriz;
        //criacao das listas
        visitados = new ArrayList();
        abertos = new ArrayList();
        obstaculos= new ArrayList();
        caminho= new ArrayList();
        fechados= new ArrayList();
    }
    //adicionar nos nas listas
    void setVisitados(ArrayList<NO> _aux){
        this.visitados = _aux;   
    }
    void setFechados(ArrayList<NO> _aux){
        this.fechados = _aux;   
    }
    void setAbertos(ArrayList<NO> _aux){
        this.abertos = _aux;   
    }
    void setCaminho(ArrayList<NO> _aux){
        this.caminho = _aux;   
    }
    void setObstaculos(ArrayList<NO> _aux){
        this.obstaculos = _aux;   
    }
    //retornas as listas 
    ArrayList<NO> getVisitados(){
        return this.visitados;   
    }
    ArrayList<NO> getFechados(){
        return this.fechados;   
    }
    ArrayList<NO> getAbertos(){
        return this.abertos;   
    }
    ArrayList<NO> getCaminho(){
        return this.caminho;   
    }
    ArrayList<NO> getObstaculos(){
        return this.obstaculos;   
    }
    //seta o inico e fim e obstaculos
    void setInicio(NO _aux){
        this.inicio = _aux;
    }
    void setFim(NO _aux){
        this.fim = _aux;
    }
    void setObstaculos(NO _aux){
        this.obstaculos.add(_aux);//adiciona na lista
    }
    //pega o fim e inico
    NO getInicio(){
        return this.inicio;
    }
    NO getFim(){
        return this.fim;
    }
    //setar matriz
    void setMatriz(NO[][] aux_matriz){
       this.matriz= aux_matriz;
    }
    //retorna a matriz
    NO[][] getMatriz(){
       return this.matriz;
    }
    //metodo que encontra o caminho
    boolean EncontrarCaminho(){
    //primeira condicao
    NO aux_inicio;
    NO aux_fim;
    aux_inicio = getInicio();
    aux_fim = getFim();
    //se o no de origem for igual ao de busca a solucao sera caminho de custo 0
    if(aux_inicio == aux_fim){
        JOptionPane.showMessageDialog(null, "Inicio = Fim!\nCusto total = 0", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);//joga uma mensagem de popup
        return true;
    }
  
    else{
        //comecar pelo no de origem
        this.abertos.add(aux_inicio);
 
        //--------
        if(BuscarCaminho()==true){
            return CaminhoEncontrado();
        }
    }
    //caso nao entre nem nos IFs, ele nao encontrou solucao.
    return false;
    }
    
//metodo q cria a lista de caminho quando a algoritomo ja encoutrou a solucao   
boolean CaminhoEncontrado(){
        NO atual = getFim();

        if (atual == null) {
            return false;
        }

        while(atual != null) {//se ele for null, nao tem pai, logo so pode ser a origem
            this.caminho.add(atual);//adiciona o no na lista caminho
            atual = atual.getPai();//vai fazendo o caminho reverso para criar a lista caminho
        }
        return true;
     }
//calculo de heusistica, que para esse caso sera a distancia de manhathan
int CalculeCustoH(NO adjAux){
    int retorno;
    retorno = 10*(Math.abs(this.fim.getX() - adjAux.getX())) + 10*(Math.abs(fim.getY() - adjAux.getY()));       
    return retorno;
}
//metodo que faz a busca do caminho em si
//se nao encontrar um caminho ele retorna false
boolean BuscarCaminho(){
   //pega o primeiro no e abre ele
   NO atual = this.abertos.get(0);
   //procura na lista o no com o menor custo total
   for (int i = 0; i < this.abertos.size(); i++) {
            this.visitados.add(this.abertos.get(i));
            if (atual.getCustoTotal() > this.abertos.get(i).getCustoTotal()) {
                //entao se o no testado tem um custo menor que o atual, o atual eh atualizado para ele
                atual = this.abertos.get(i);
            }
        }
   this.fechados.add(atual);//adiciona o no na lista de fechados
   this.abertos.remove(atual);//remove o no atual da lista de abertos ja que ele ja foi fechado
   //agora temos que verificar se ja cheamos ao destimo
   if(atual == this.fim){
       return true;
   }
   //coordenadas do no atual
   int x = atual.getX();
   int y = atual.getY();
   //coordenadas dos nos adjacentes
   //veja que esse calculo eh trivial
   int up = y - 1;
   int down = y + 1;
   int right = x + 1;
   int left = x - 1;
   
   //-----Analise de caso para no a DIREITA
   if(right < matriz[0].length){//condicao para nao passar da parede da matriz
        NO adjRight = matriz[right][y];//cria esse no da direita
        //ele so vai checar se o no nao estiver na lista de obstaculos ou fechados
        if ((this.fechados.contains(adjRight)==false) && (obstaculos.contains(adjRight)==false)) {  
            int custo_G;
            custo_G = atual.getCustoG() + 10;
            //Calculo da heuristica, que eh o custo mahatham ou distancia euclidiana
            int custo_heuristica;
            custo_heuristica = CalculeCustoH(adjRight);
            //checa se o adjRight esta na lista aberta, e atualiza seu respectivo pai e custos           
            if (this.abertos.contains(adjRight) == false) {    
                adjRight.setPai(atual);           
                this.abertos.add(adjRight);           
                adjRight.setCustoG(custo_G);
                adjRight.setCustoHeuristica(custo_heuristica);
                adjRight.setCustoTotal();
                System.out.println("Custo g: "+adjRight.getCustoG()+" Custo h "+adjRight.getCustoHeuristica()+" Custo Total: "+adjRight.getCustoTotal());
            } 
            // se o no adjRight ja esta na lista, entao temos que checar se o custo H e comparar
            //com o calculado para vermos se eh maior ou menor. Caso menor, ele tem um caminho mais curto,
            //entao temos q atualizar o pai e custos para o novo no.
            else {                                       
                if (adjRight.getCustoG() > custo_G) { 
                    adjRight.setPai(atual);       
                    adjRight.setCustoG(custo_G);      
                    adjRight.setCustoHeuristica(custo_heuristica);
                    adjRight.setCustoTotal();
                    }
                }
            }   
    }
   //-----Analise de caso para no a ESQUERDA
   if(left >= 0){//condicao para nao passar da parede da matriz. 0 eh o limite
        NO adjLeft = matriz[left][y];//cria esse no da direita
        //ele so vai checar se o no nao estiver na lista de obstaculos ou fechados
        if ((this.fechados.contains(adjLeft)==false) && (obstaculos.contains(adjLeft)==false)) {  
            int custo_G;
            custo_G = atual.getCustoG() + 10;
            //Calculo da heuristica, que eh o custo mahatham ou distancia euclidiana
            int custo_heuristica;
            custo_heuristica = CalculeCustoH(adjLeft);
              //checa se o adjRight esta na lista aberta, e atualiza seu respectivo pai e custos           
            if (this.abertos.contains(adjLeft) == false) {    
                adjLeft.setPai(atual);           
                this.abertos.add(adjLeft);           
                adjLeft.setCustoG(custo_G);
                adjLeft.setCustoHeuristica(custo_heuristica);
                adjLeft.setCustoTotal();
            } 
            // se o no adjleft ja esta na lista, entao temos que checar se o custo H e comparar
            //com o calculado para vermos se eh maior ou menor. Caso menor, ele tem um caminho mais curto,
            //entao temos q atualizar o pai e custos para o novo no.
            else {                                        
                if (adjLeft.getCustoG() > custo_G) { 
                    adjLeft.setPai(atual);       
                    adjLeft.setCustoG(custo_G);      
                    adjLeft.setCustoHeuristica(custo_heuristica);
                    adjLeft.setCustoTotal();
                    }
                }
            }   
   }
   //-----Analise de caso para no a CIMA
   if(up >= 0){//condicao para nao passar da parede da matriz. 0 eh o limite
        NO adjup = matriz[x][up];//cria esse no da direita
        //ele so vai checar se o no nao estiver na lista de obstaculos ou fechados
        if ((this.fechados.contains(adjup)==false) && (obstaculos.contains(adjup)==false)) {  
            int custo_G;
            custo_G = atual.getCustoG() + 10;
            //Calculo da heuristica, que eh o custo mahatham ou distancia euclidiana
            int custo_heuristica;
            custo_heuristica = CalculeCustoH(adjup);
              //checa se o adjup esta na lista aberta, e atualiza seu respectivo pai e custos           
            if (this.abertos.contains(adjup) == false) {    
                adjup.setPai(atual);           
                this.abertos.add(adjup);           
                adjup.setCustoG(custo_G);
                adjup.setCustoHeuristica(custo_heuristica);
                adjup.setCustoTotal();
            } 
            // se o no adjRight ja esta na lista, entao temos que checar se o custo H e comparar
            //com o calculado para vermos se eh maior ou menor. Caso menor, ele tem um caminho mais curto,
            //entao temos q atualizar o pai e custos para o novo no.
            else {                                        
                if (adjup.getCustoG() > custo_G) { 
                    adjup.setPai(atual);       
                    adjup.setCustoG(custo_G);      
                    adjup.setCustoHeuristica(custo_heuristica);
                    adjup.setCustoTotal();
                    }
                }
            }   
   }
   //-----Analise de caso para no a BAIXO
   if(down < matriz.length){//condicao para nao passar da parede da matriz.
        NO adjdown = matriz[x][down];//cria esse no da direita
        //ele so vai checar se o no nao estiver na lista de obstaculos ou fechados
        if ((this.fechados.contains(adjdown)==false) && (obstaculos.contains(adjdown)==false)) {  
            int custo_G;
            custo_G = atual.getCustoG() + 10;
            //Calculo da heuristica, que eh o custo mahatham ou distancia euclidiana
            int custo_heuristica;
            custo_heuristica = CalculeCustoH(adjdown);
            //checa se o adjdown esta na lista aberta, e atualiza seu respectivo pai e custos           
            if (this.abertos.contains(adjdown) == false) {    
                adjdown.setPai(atual);           
                this.abertos.add(adjdown);           
                adjdown.setCustoG(custo_G);
                adjdown.setCustoHeuristica(custo_heuristica);
                adjdown.setCustoTotal();
            } 
            // se o no adjRight ja esta na lista, entao temos que checar se o custo H e comparar
            //com o calculado para vermos se eh maior ou menor. Caso menor, ele tem um caminho mais curto,
            //entao temos q atualizar o pai e custos para o novo no.
            else {                                        
                if (adjdown.getCustoG() > custo_G) { 
                    adjdown.setPai(atual);       
                    adjdown.setCustoG(custo_G);      
                    adjdown.setCustoHeuristica(custo_heuristica);
                    adjdown.setCustoTotal();
                    }
                }
            }   
   }
    //-----Analise de caso para no a CIMA->DIREITA
    if((right < matriz[0].length) && (up>=0)){//condicao para nao passar da parede da matriz.
        NO adjUPright = matriz[right][up];//cria esse no da direita
        //observe que a localizacao geografica dele eh somando 1 em cada coordenada 
        //ele so vai checar se o no nao estiver na lista de obstaculos ou fechados
        if ((this.fechados.contains(adjUPright)==false) && (obstaculos.contains(adjUPright)==false)) {  
            int custo_G;
            custo_G = atual.getCustoG() + 14;
            //Calculo da heuristica, que eh o custo mahatham ou distancia euclidiana
            int custo_heuristica;
            custo_heuristica = CalculeCustoH(adjUPright);
              //checa se o adjdown esta na lista aberta, e atualiza seu respectivo pai e custos           
            if (this.abertos.contains(adjUPright) == false) {    
                adjUPright.setPai(atual);           
                this.abertos.add(adjUPright);           
                adjUPright.setCustoG(custo_G);
                adjUPright.setCustoHeuristica(custo_heuristica);
                adjUPright.setCustoTotal();
            } 
            // se o no adjRight ja esta na lista, entao temos que checar se o custo H e comparar
            //com o calculado para vermos se eh maior ou menor. Caso menor, ele tem um caminho mais curto,
            //entao temos q atualizar o pai e custos para o novo no.
            else {                                        
                if (adjUPright.getCustoG() > custo_G) { 
                    adjUPright.setPai(atual);       
                    adjUPright.setCustoG(custo_G);      
                    adjUPright.setCustoHeuristica(custo_heuristica);
                    adjUPright.setCustoTotal();
                    }
                }
            }   
   }
    //-----Analise de caso para no a ESQUERDA->CIMA
    if((left >= 0) && (up>=0)){//condicao para nao passar da parede da matriz.
        NO adjUPleft = matriz[left][up];//cria esse no da esquerda-cima
        //observe que a localizacao geografica dele eh somando 1 em cada coordenada 
        //ele so vai checar se o no nao estiver na lista de obstaculos ou fechados
        if ((this.fechados.contains(adjUPleft)==false) && (obstaculos.contains(adjUPleft)==false)) {  
            int custo_G;
            custo_G = atual.getCustoG() + 14;
            //Calculo da heuristica, que eh o custo mahatham ou distancia euclidiana
            int custo_heuristica;
            custo_heuristica = CalculeCustoH(adjUPleft);
              //checa se o adjdown esta na lista aberta, e atualiza seu respectivo pai e custos           
            if (this.abertos.contains(adjUPleft) == false) {    
                adjUPleft.setPai(atual);           
                this.abertos.add(adjUPleft);           
                adjUPleft.setCustoG(custo_G);
                adjUPleft.setCustoHeuristica(custo_heuristica);
                adjUPleft.setCustoTotal();
            } 
            //se o no adjUPleft ja esta na lista, entao temos que checar se o custo H e comparar
            //com o calculado para vermos se eh maior ou menor. Caso menor, ele tem um caminho mais curto,
            //entao temos q atualizar o pai e custos para o novo no.
            else {                                        
                if (adjUPleft.getCustoG() > custo_G) { 
                    adjUPleft.setPai(atual);       
                    adjUPleft.setCustoG(custo_G);      
                    adjUPleft.setCustoHeuristica(custo_heuristica);
                    adjUPleft.setCustoTotal();
                    }
                }
            }   
   }
    //-----Analise de caso para no a BAIXO->DIREITA
    if((right < matriz[0].length) && (down < matriz.length)){//condicao para nao passar da parede da matriz.
        NO adjDOWNright = matriz[right][down];//cria esse no da esquerda-cima
        //observe que a localizacao geografica dele eh somando 1 em cada coordenada 
        //ele so vai checar se o no nao estiver na lista de obstaculos ou fechados
        if ((this.fechados.contains(adjDOWNright)==false) && (obstaculos.contains(adjDOWNright)==false)) {  
            int custo_G;
            custo_G = atual.getCustoG() + 14;
            //Calculo da heuristica, que eh o custo mahatham ou distancia euclidiana
            int custo_heuristica;
            custo_heuristica = CalculeCustoH(adjDOWNright);
              //checa se o adjdown esta na lista aberta, e atualiza seu respectivo pai e custos           
            if (this.abertos.contains(adjDOWNright) == false) {    
                adjDOWNright.setPai(atual);           
                this.abertos.add(adjDOWNright);           
                adjDOWNright.setCustoG(custo_G);
                adjDOWNright.setCustoHeuristica(custo_heuristica);
                adjDOWNright.setCustoTotal();
            } 
            //se o no adjDOWNright ja esta na lista, entao temos que checar se o custo H e comparar
            //com o calculado para vermos se eh maior ou menor. Caso menor, ele tem um caminho mais curto,
            //entao temos q atualizar o pai e custos para o novo no.
            else {                                        
                if (adjDOWNright.getCustoG() > custo_G) { 
                    adjDOWNright.setPai(atual);       
                    adjDOWNright.setCustoG(custo_G);      
                    adjDOWNright.setCustoHeuristica(custo_heuristica);
                    adjDOWNright.setCustoTotal();
                    }
                }
            }   
   }
   //-----Analise de caso para no a ESQURDA->BAIXO
    if((left >=0) && (down < matriz.length)){//condicao para nao passar da parede da matriz.
        NO adjDOWNleft = matriz[left][down];//cria esse no da esquerda-cima
        //observe que a localizacao geografica dele eh somando 1 em cada coordenada 
        //ele so vai checar se o no nao estiver na lista de obstaculos ou fechados
        if ((this.fechados.contains(adjDOWNleft)==false) && (obstaculos.contains(adjDOWNleft)==false)) {  
            int custo_G;
            custo_G = atual.getCustoG() + 14;
            //Calculo da heuristica, que eh o custo mahatham ou distancia euclidiana
            int custo_heuristica;
            custo_heuristica = CalculeCustoH(adjDOWNleft);
              //checa se o adjdown esta na lista aberta, e atualiza seu respectivo pai e custos           
            if (this.abertos.contains(adjDOWNleft) == false) {    
                adjDOWNleft.setPai(atual);           
                this.abertos.add(adjDOWNleft);           
                adjDOWNleft.setCustoG(custo_G);
                adjDOWNleft.setCustoHeuristica(custo_heuristica);
                adjDOWNleft.setCustoTotal();
            } 
            //se o no adjDOWNleft ja esta na lista, entao temos que checar se o custo H e comparar
            //com o calculado para vermos se eh maior ou menor. Caso menor, ele tem um caminho mais curto,
            //entao temos q atualizar o pai e custos para o novo no.
            else {                                        
                if (adjDOWNleft.getCustoG() > custo_G) { 
                    adjDOWNleft.setPai(atual);       
                    adjDOWNleft.setCustoG(custo_G);      
                    adjDOWNleft.setCustoHeuristica(custo_heuristica);
                    adjDOWNleft.setCustoTotal();
                    }
                }
            }   
   }
    
    //se a lista nao encontrar nenhum caminho
    if(this.abertos.isEmpty()==true){
        return false;
    }
    
    
    
    //----RECURSAO
    return BuscarCaminho();    
}
    

    
}
