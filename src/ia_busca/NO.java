/*
 * Classe ou Estrutura de cada no de nosso tabuleiro contento todas as informacoes referentes ao no
 */
package ia_busca;

/**
 * @author Jaime
 */
public class NO {
    //---variaveis da classe no
    //custos
    int custo_heuristica = 0;
    int custo_g = 0;
    int custo_total = 0;
    //pai do no
    NO pai = null;
    //pocicao dos nos
    int x = 0;
    int y = 0;
    
    //---construtores
    //setar as coordenadas
    public NO(int aux_x, int aux_y){
        this.x = aux_x;
        this.y = aux_y;
    }
    //setar as coordenadas
    void setX(int aux_x){
        this.x = aux_x;
    }
    void setY(int aux_y){
        this.y = aux_y;
    }
    //pegar as coordenadas
    int getX(){
        return this.x;
    }
    int getY(){
        return this.y;
    }
    //setar custos
    void setCustoHeuristica(int custo){
        this.custo_heuristica = custo;
    }
    void setCustoG(int custo){
        this.custo_g = custo;
    }
    void setCustoTotal(){
        this.custo_total = this.custo_g+this.custo_heuristica;;
    }
    //pegar os custos
    int getCustoHeuristica(){
        setCustoTotal();
        return this.custo_heuristica;
    }
    int getCustoG(){
        return this.custo_g;
    }
    int getCustoTotal(){
        //esse custo eh a soma da distacia do pai ate o no q sera aberto mais o custo da heuristica
        custo_total = this.custo_g + this.custo_heuristica;
        return this.custo_total;
    }
    //seta o pai do no
    void setPai(NO aux_pai){
        this.pai = aux_pai;
    }
    //pega o pai do no
    NO getPai(){
       return this.pai;
    }
}
