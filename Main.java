import java.util.Random;
import java.io.*;
import java.util.Scanner;

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

    Parameter didapatkan dari file Param.txt
    Hasil algoritma akan ditulis di Result.txt
    Referensi yang digunakan dalam pembuatan kode ini ada di file Referensi.txt

    Anggota Kelompok:
    Nicholas Khrisna S. - 6181801060
    Jason - 6181801061
    Christopher William - 6181901048
    Filipus - 6181901074

    */

    public static void main(String[] args) throws IOException,FileNotFoundException {

        Scanner sc = new Scanner(new File("Param.txt")); //membuat objek scanner;

        //membaca parameter yang akan digunakan dari file Param.txt menggunakan scanner;
        int totalGeneration = sc.nextInt();
        double crossoverRate = sc.nextDouble();
        double mutationRate = sc.nextDouble();
        double elitismRate = sc.nextDouble();
        int populationSize = sc.nextInt();
        
        //Seed yang digunakan untuk object random di seluruh algoritma
        int seed = sc.nextInt();
        Random random = new Random(seed); //membuat objek random

        String namaSoal = sc.next();

        sc.close(); //menutup scanner

        System.out.println("Menjalankan algoritma genetik . . .");
        System.out.println("Hasil akan terlihat pada file Result.txt saat algoritma selesai.");

        LightUp lightUp = new LightUp(random, totalGeneration, crossoverRate, mutationRate, elitismRate, populationSize, namaSoal); //membuat objek LightUp untuk memecahkan tahap 1 
        Individual best = lightUp.run(); //mendapatkan individu terbaik dari tahap 1.

        LightUpStepTwo lightUpTwo = new LightUpStepTwo(random, totalGeneration, crossoverRate, mutationRate, best.getArrayJawaban(), elitismRate, populationSize); //membuat objek LightUpStepTwo untuk memecahkan tahap 2
        IndividualStepTwo bestTwo = lightUpTwo.run(); //mendapatkan individu terbaik dari tahap 2 (puzzle selesai).

        PrintWriter outputFile = new PrintWriter("Result.txt"); // membuat objek printwriter
        //inisialiasi variabel string
        String string = "";
        //memasukkan nilai string 
        string = "Seed: " + seed + "\n" + 
        "Total generation: " + totalGeneration+ "\n" + 
        "Population size: " + populationSize + "\n" + 
        "Crossover rate: " + crossoverRate + "\n" + 
        "Mutation rate: " + mutationRate + "\n" + 
        "Elitism rate: " + elitismRate + "\n" + "\n" +   
        "Step One : " + "\n" + "Fitness Value : " + best.getFitness() + " (semakin mendekati 0 semakin bagus)" + "\n" + best.getArrayJawabanAsString() + "\n" + "================================" + "\n" + "\n" + 
        "Step Two : " + "\n" +"Fitness Value : "+ bestTwo.getFitness() + " (semakin mendekati 0 semakin bagus)" + "\n" + bestTwo.getArrayJawabanAsString() + "\n" ;

        //memasukkan isi variabel string ke outputfile dan menampilkannya
        outputFile.println(string);
        //menutup outputfile
        outputFile.close();
    }
}