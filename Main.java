import java.util.Random;
import java.io.*;

public class Main{

    /*
    Puzzle Light Up diencode menjadi array 2D

    CONTOH:
    -1  -5  -1  -1   3   -1  -1
    -1   0  -1  -1  -1   -5   0
     1  -1  -1  -1  -1   -1  -1
    -1  -1  -1  -5  -1   -1  -1
    -1  -1  -1  -1  -1   -1   1
     1  -5  -1  -1  -1   -5  -1
    -1  -1   1  -1  -1    0  -1

    KETERANGAN
    0 1 2 3 4 : kotak hitam yang menyatakan jumlah lampu yang ada di sekitar kotak tersebut
    -1 : tidak ada lampu
    -5 : tembok
     5 : ada lampu

    Lampu akan ditempatkan pada kotak dengan nilai -1 (kotak yang belum ada lampu), 
    sehingga nilai kotak tersebut menjadi 5 (ada lampu).
    Kotak lainnya (selain tembok dan kotak 0 1 2 3 4) yang disinari oleh lampu, nilainya menjadi -2.

    Algoritma dibagi menjadi 2 tahap.
    Tahap 1: menempatkan lampu yang sudah ada clue nya (di sekitar kotak 0 1 2 3 4).  --> LightUp.java
    Tahap 2: menempatkan lampu di sisa tempat yang belum terkena sinar. --> LightUpStepTwo.java

    */

    public static void main(String[] args) throws IOException,FileNotFoundException {

        //Seed yang digunakan untuk object random di seluruh algoritma
        int seed = 1234120;
        Random random = new Random(seed); //membuat objek random

        LightUp lightUp = new LightUp(random, 100, 0.85, 0.75, 0.5); //membuat objek LightUp untuk memecahkan tahap 1 
        Individual best = lightUp.run(); //mendapatkan individu terbaik dari tahap 1.

        LightUpStepTwo lightUpTwo = new LightUpStepTwo(random, 100, 0.85,0.75,best.getArrayJawaban(),0.5); //membuat objek LightUpStepTwo untuk memecahkan tahap 2
        IndividualStepTwo bestTwo = lightUpTwo.run(); //mendapatkan individu terbaik dari tahap 2 (puzzle selesai).

        PrintWriter outputFile = new PrintWriter("Result.txt"); // membuat objek printwriter
        //inisialiasi variabel string
        String string = " ";
        //memasukkan nilai string 
        string = "Step One : " +"\n" + "Fitness Value : " + best.getFitness() + "\n" + best.getArrayJawabanAsString() + "\n" + "================================" + "\n" + 
        "Step Two : " + "\n" +"Fitness Value : "+ bestTwo.getFitness() + "\n" + bestTwo.getArrayJawabanAsString() + "\n" ;
        //memasukkan isi variabel string ke outputfile
        outputFile.println(string);
        //menutup outputfile
        outputFile.close();
    }
}