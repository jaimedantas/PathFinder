/*
 * Classe usanda para criar uma matriz dentro do jPanel (Tipo um grid)
 */
package ia_busca;
import ia_busca.A_ESTRELA;
import ia_busca.NO;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
/**
 * @author Jaime
 */
public class GRID_TELA extends JPanel{
    
    //esse grid tem 3 dimensoes. Essa dimensao extra armazena detalhes do no
    static int[][][] M = new int[25][25][4];//alocada dinamicamente
    ArrayList<NO> caminho;
    
     int x_inicio = 0;
     int y_inicio = 0;
     int xAUX_inicio = 0;//variavel auxiliar
     int yAUX_inicio = 0;//variavel auxiliar
    
     int x_fim = 0;
     int y_fim = 0;
     int xAUX_fim = 0;//variavel auxiliar
     int yAUX_fim = 0;//variavel auxiliar
    
     int x_obstaculo = 0;
     int y_obstaculo = 0;
     int xAUX_obstaculo = 0;//variavel auxiliar
     int yAUX_obstaculo = 0;//variavel auxiliar
     //lista caminho
     int x_caminho = 0;
     int y_caminho = 0;
     //lista fechada
     int x_fechada = 0;
     int y_fechada = 0;
     //lista aberto
     int x_aberto = 0;
     int y_aberto = 0;
     int x_visitado = 0;
     int y_visitado = 0;
     int linhas, colunas;
     
     
     
     @Override//reescreve esse metodo
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      
      CriarGrid(g);
      
      if (x_obstaculo > 0){
        setObstaculo(g);
      }
      if(x_fechada > 0){
        gerarFechado(g);
      }
      if(x_aberto > 0){
        gerarAberto(g);
      }
      if(x_caminho > 0){
        gerarCaminho(g);
      }
      if(x_inicio > 0){
        setInicio(g);
      }
      if(x_fim > 0){
        setFim(g);
      }
      
    }
    public void geraMatriz(int _linhas, int _colunas){
     this.linhas = _linhas;
     this.colunas = _colunas;
     repaint();
    }
    private void CriarGrid(Graphics g){
    g.setColor(Color.white);//backgound branco
    int largura = 25;
    int altura = 25;
    int pos_x = 0, pos_y = 0;
    
    for(int i  = 0; i < linhas; i++){
        for(int j = 0; j < colunas; j++){
            switch (M[i][j][2]) {
                case 1:
                    //marca o ponto de preto que sao obstaculos
                    g.setColor(Color.black);
                    g.fillRect(pos_x, pos_y, largura, altura);
                    g.setColor(Color.white);//backgraound   
                    break;
                case 2:
                    //marca o ponto de vermelho que sao o caminho
                    g.setColor(Color.blue);
                    g.fillRect(pos_x, pos_y, largura, altura);
                    g.setColor(Color.white);
                    break;
                case 3:
                    //marca o ponto de cinza que sao os fechados
                    g.setColor(Color.pink);
                    g.fillRect(pos_x, pos_y, largura, altura);
                    g.setColor(Color.white);
                    break;
                case 4:
                    //marca o ponto de destino de rosa que sao os abertos
                    g.setColor(Color.gray);
                    g.fillRect(pos_x, pos_y, largura, altura);
                    g.setColor(Color.white);
                    break;
                default:
                    g.fillRect(pos_x, pos_y, largura, altura);
                    M[i][j][2] = 0;
                    break;
            }
            M[i][j][0] = pos_x;
            M[i][j][1] = pos_y;
            //condicao limite
            pos_x = pos_x + largura + 1;
            if(pos_x > 500){
                pos_y = pos_y + altura + 1;
                pos_x = 0;
            }
            
        }
    } 
    }
    
    
void setObstaculo(Graphics g){
    g.setColor(Color.black);
    g.fillRect(M[this.xAUX_obstaculo][this.yAUX_obstaculo][0], M[this.xAUX_obstaculo][this.yAUX_obstaculo][1], 500/linhas, 500/colunas);
}
void setInicio(Graphics g){
        g.setColor(Color.green);
        g.fillRect(M[this.xAUX_inicio][this.yAUX_inicio][0], M[this.xAUX_inicio][this.yAUX_inicio][1], 500/linhas, 500/colunas); 
}
void setFim(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect(M[this.xAUX_fim][this.yAUX_fim][0], M[this.xAUX_fim][this.yAUX_fim][1], 500/linhas, 500/colunas);   
}
void gerarCaminho(Graphics g){
    g.setColor(Color.blue);
    g.fillRect(M[this.x_caminho][this.y_caminho][0], M[this.x_caminho][this.y_caminho][1], 500/linhas, 500/colunas);    
}
void gerarFechado(Graphics g){
        g.setColor(Color.pink);
        g.fillRect(M[this.x_fechada][this.y_fechada][0], M[this.x_fechada][this.y_fechada][1], 500/linhas, 500/colunas);  
}
void gerarAberto(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(M[this.x_aberto][this.y_aberto][0], M[this.x_aberto][this.y_aberto][1], 500/linhas, 500/colunas);  
}

public void setObstaculo(int aux_x, int aux_y){
        x_obstaculo = aux_x;
        y_obstaculo = aux_y;
         for(int i = 0; i < linhas; i++){
            for(int j = 0; j < colunas; j++){
                if((x_obstaculo < (M[i][j][0]+500/linhas)) && ((y_obstaculo-40) < (M[i][j][1]+500/colunas))){
                    M[i][j][2] = 1;//obstaculo eh 1
                    xAUX_obstaculo = i;
                    yAUX_obstaculo = j;
                    i = linhas;
                    j = colunas;
                }
            }
        }   
        repaint();
}
int getXInicio(){
        return x_inicio; 
}
int getYInicio(){
        return y_inicio; 
}
int getXFim(){
        return x_fim; 
}
int getYFim(){
        return y_fim; 
}
void setInicio(int aux_x, int aux_y){
        x_inicio = aux_x;
        y_inicio = aux_y;
         for(int i = 0; i < 25; i++){
            for(int j = 0; j < 25; j++){
                if((x_inicio < (M[i][j][0]+500/linhas)) && ((y_inicio-40) < (M[i][j][1]+500/colunas))){
                    M[i][j][2] = 1;//obstaculo eh 1
                    xAUX_inicio = i;
                    yAUX_inicio = j;
                    i = linhas;
                    j = colunas;
                }
            }
        }   
        repaint();
}
void setFim(int aux_x, int aux_y){
        x_fim = aux_x;
        y_fim = aux_y;
         for(int i = 0; i < 25; i++){
            for(int j = 0; j < 25; j++){
                if((x_fim < (M[i][j][0]+500/linhas)) && ((y_fim-40) < (M[i][j][1]+500/colunas))){
                    M[i][j][2] = 1;//obstaculo eh 1
                    xAUX_fim = i;
                    yAUX_fim = j;
                    i = linhas;
                    j = colunas;
                }
            }
        }   
        repaint();
}

void gerarCaminho(NO aux_caminho){
   this.x_caminho = aux_caminho.getX();
   this.y_caminho = aux_caminho.getY();
   M[this.x_caminho][this.y_caminho][2] = 2;//seta como pinta de caminho
   repaint();   
}
void gerarFechado(NO aux_fechado){
   this.x_fechada = aux_fechado.getX();
   this.y_fechada = aux_fechado.getY();
   M[this.x_fechada][this.y_fechada][2] = 3;//seta como pinta de caminho
   repaint();   
}
void gerarAberto(NO aux_aberto){
   this.x_aberto = aux_aberto.getX();
   this.y_aberto = aux_aberto.getY();
   M[this.x_aberto][this.y_aberto][2] |= 4;//seta como pinta de caminho
   repaint();   
}

void setCaminho(ArrayList<NO> _caminho){
    this.caminho = _caminho;
} 
public ArrayList<NO> getCaminho(){
    return caminho;
}
    
}
