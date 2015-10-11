/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filosofosfamintos;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Ricardo Corassa email : ricardo_corassa@hotmail.com
 *
 * Implementação segundo o código do Tanenbaum pagina 67 (é uma das soluções
 * para o problema)
 */
public class FilosofosFamintos extends Thread {

    private static int n = 5;          /*numero de filosofos*/


    public int left(int i) {    /*numero do vizinho da esquerda*/

        return (i - 1) % n;
    }

    public int rigth(int i) {   /*numero do vizinho da direita*/

        return (i + 1) % n;
    }

    private int thinking = 0;   /*fisólofo esta pensando*/

    private int hungry = 1;     /*filósofo esta tentando pegar os garfos*/

    private int eating = 2;     /*fisósofo está comendo*/

    int state[] = new int[5];   /*matriz para moniturar o estado de todos*/

    /*semaforo*/
    int mutex = 1;              /*exclusao mutua para regioes criticas*/

    /*semaforo*/
    int s[] = new int[5];       /*um semaforo por filósofo*/


    public void philosopher(int i) {
        while (true) {          /*repete eternamente*/

            think();            /*filosofo esta pensando*/

            take_forks(i);      /*pega dois garfos e bloqueia*/

            eat();              /*nha-nham, espaguete*/

            put_forks(i);       /*coloca de volta os garfos na mesa*/

        }
    }

    private void entrarNaRegiaoCritica(int mutex) {
        System.out.println(mutex + "entrando na regiao critica");
        think();
        System.out.println(mutex + "sainda da regiao critica");
    }

    private void sairDaRegiaoCritica(int mutex) {
        System.out.println(mutex + "entrando na regiao critica");
        think();
    }

    private void think() {
        try {
            Thread.sleep((long) (Math.random() * 10000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eat() {
        try {
            Thread.sleep((long) (Math.random() * 10000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void take_forks(int i) {
        entrarNaRegiaoCritica(mutex);   /*entra na regiao critica*/

        state[i] = hungry;              /*registra o fato de que o filosofo esta com fome*/

        test(i);                        /*testar pegar dois garfos*/

        sairDaRegiaoCritica(mutex);      /*sai da regiao critica*/

        entrarNaRegiaoCritica(s[i]);    /*bloqueia se os garfos não foram pegos*/

    }

    public void put_forks(int i) {
        entrarNaRegiaoCritica(mutex);   /*entra na regiao critica*/

        state[i] = thinking;            /*filósofo terminou de comer*/

        test(left(i));                  /*vê se o vizinho esquerdo agora pode comer*/

        test(rigth(i));                 /*ve se o vizinho direto agora pode comer*/

        sairDaRegiaoCritica(mutex);     /*sai da regiao critica*/

    }

    public void test(int i) {
        if (state[i] == hungry && state[left(i)] != eating && state[rigth(i)] != eating) {
            state[i] = eating;
            entrarNaRegiaoCritica(s[i]);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        for (int i = 1; i < n; i++) {
           //philosopher(i);
        }
    }

}
