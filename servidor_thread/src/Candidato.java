/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gustavo
 */

import java.io.Serializable;


public class Candidato implements Serializable{
    
    public int codigo_votacao;
    public String nome_candidato;
    public String partido;
    public int num_votos = 0;
    
}
