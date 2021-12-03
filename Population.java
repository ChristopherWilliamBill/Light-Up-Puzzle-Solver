import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.*;

public class Population {

    public ArrayList<Individual> population; //arraylist of individual
    private Random random; //objek random
    private int maxPopulationSize; //jumlah individual maksimum
    private int populationSize; //jumlah individu di populasi ini
    private int[][] soal; //soal
    private int NumberOfBlackSquares; //jumlah kotak clue (kotak 0 1 2 3 4)
    private LinkedList<Integer> linkedList; //linkedlist untuk menyimpan isi kotak clue tersebut

    //Constructor
    public Population(Random random, int maxPopulationSize) {
        this.population = new ArrayList<Individual>();
		this.random = random;
        this.maxPopulationSize = maxPopulationSize;

        //mengimport array soal
        try {
            this.soal = importFile("Test/10x10_hard");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //inisialisi variabel NumberOfBlackSquares (NBS) untuk menghitung banyaknya kotak  0 1 2 3 4 yang menjadi soal/clue penempatan lampu
        this.NumberOfBlackSquares = 0;

        //jumlah individu awalnya 0
        this.populationSize = 0;
        
        //linked list digunakan untuk menyimpan nilai setiap kotak NBS
        this.linkedList = new LinkedList<Integer>();
        
        //iterasi seluruh soal
        for(int i = 0; i < soal.length; i++){
            for(int j = 0; j < soal[i].length; j++){
                //jika ada kotak > -1, artinya kotak tersebut adalah kotak NBS
                if(soal[i][j] > -1){
                    NumberOfBlackSquares++; //maka banyaknya NBS bertambah
                    linkedList.add(soal[i][j]); //dan catat nilai kotak tersebut di linkedlist
                }
            }
        }
    }

    public void generateRandomPopulation() {
        //looping membuat individu sebanyak maxPopulationSize
        for (int i = 0; i < this.maxPopulationSize; i++) {
            //membuat array yang berisi kombinasi NBS seperti pada KombinasiNBS.txt
            int[] NBS = new int[this.NumberOfBlackSquares];
            //looping sepanjang array NBS
            for(int j = 0; j < this.NumberOfBlackSquares; j++){
                //mengisi variabel temp dengan nilai linkedlist berisi numbered square
                int temp = this.linkedList.get(j);
                //jika nilai temp 0 atau 4, maka hanya ada kemungkinan 1 kombinasi penempatan lampu
                if(temp == 0 || temp == 4){
                    NBS[j] = 1;
                }
                //jika nilai temp 1 atau 3, ada 4 kemungkinan kombinasi penempatan lampu
                else if(temp == 1 || temp == 3){
                    NBS[j] = this.random.nextInt(5 - 1) + 1;
                //jika nilai temp 2, ada 6 kemungkinan kombinasi penempatan lampu
                }else if(temp == 2){
                    NBS[j] = this.random.nextInt(7 - 1) + 1;
                }
            }

            //membuat objek individual baru dengan array NumberedBlackSquare dan array2d soal yang didapat dari file txt
            Individual individual = new Individual(NBS, this.soal, this.linkedList, this.random);
            this.addIndividual(individual);
        }
    }

    //menambahkan individu baru ke populasi
    public boolean addIndividual(Individual newInd) {
        //jika sudah penuh, maka return false
        if (this.populationSize >= this.maxPopulationSize){
            return false;
        } 
        //jika belum penuh, tambah individu, besar populasi bertambah, dan return true
        this.population.add(newInd);
        this.populationSize++;
        return true;
    }

    //memilih parent berdasarkan roulette wheel
    public Individual[] selectParent() {
        //dipilih 2 parent dan disimpan di array dengan panjang 2
        Individual[] parents = new Individual[2];

        //jumlah seluruh fitness dari individu
        int sumfitness = 0;
        //iterasi seluruh individu 
        for (int i = 0; i < this.population.size(); i++) {
            //dan jumlahkan semua fitnessnya
            sumfitness += this.population.get(i).getFitness();
        }

        //menentukan kemungkinan setiap individu dipilih menjadi parent
        for (int i = 0; i < this.population.size(); i++) {
            //kemungkinan dipilih menjadi parent adalah fitness individu tersebut dibagi jumlah fitness seluruh individu
            ((Individual) this.population.get(i)).setParentProbability(1.0 * this.population.get(i).getFitness() / sumfitness);
        }
        //memilih 2 parent
        for (int n = 0; n < 2; n++) {
            int i = -1; //index untuk mendapat individu dari arraylist
            double prob = this.random.nextDouble(); //kemungkinan yang dirandom
            double sum = 0.0;
            do {
                i++; //index bertambah
                sum = sum + this.population.get(i).getParentProbability(); //sum bertambah dengan nilai kemungkinan parent tersebut dipilih
            } while(sum < prob); //looping jika sum < prob
            //jika keluar dari loop, akan didapat suatu index i
            parents[n] = this.population.get(i); //individu di index tersebut akan menjadi parent
        }
        return parents;
    }

    //method untuk mendapatkan individual terbaik dari suatu populasi
    public Individual getBestIndividual(){
        int best = Integer.MAX_VALUE; //best awalnya adalah max value integer
        int index = 0; //index untuk iterasi dimulai dari 0
        int bestIndex = 0; //index dari individual terbaik

        //loop jika belum didapat individu dengan fitness 0 (individu terbaik) atau jika seluruh individu belum dicek
        while(best != 0 && index < this.population.size()){
            //jika mendapat fitness yang lebih kecil dari best, maka ubah nilai best menjadi fitness tersebut
            if(this.population.get(index).getFitness() < best){
                best = this.population.get(index).getFitness();
                bestIndex = index; //catat index dari individu terbaik
            }
            index++; //index bertambah
        }
        return this.population.get(bestIndex); //return individu dengan index best (individu terbaik)
    }

    //membuat populasi baru dengan elitism dengan kemungkinan terjadinya elitism adalah elitismRate
    public Population generateNewPopulationWithElitism(double elitismRate){
        Population newPopulation = new Population(this.random, this.maxPopulationSize); //membuat populasi baru

        //jika nextDouble() < elitismRate, maka terjadi elitism dan best individual populasi ini langsung dimasukkan ke populasi baru
        if (this.random.nextDouble() < elitismRate){
            newPopulation.addIndividual(this.getBestIndividual());
        }

        //return populasi baru
        return newPopulation;
    }

    //mereturn true jika besar populasi sudah = besar populasi maksimal
    public boolean isFilled() {
        return this.maxPopulationSize == this.population.size();
    }

    //Method untuk mengimport soal dari file .txt menjadi array 2D
    public int[][] importFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int[][] numArray;
        String line = reader.readLine();
        if (line == null) {
            throw new IllegalArgumentException("Tidak ada baris 1");
        }
        String[] dimensions = line.split("\\s+");
        try {
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);
            numArray = new int[rows][cols];
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Baris 1 menyatakan jumlah baris dan kolom");
        }
    
        int row = 0; 
    
        while ((line = reader.readLine()) != null && row < numArray.length) {
            String[] tokens = line.split("\\s+");
            if (tokens.length > numArray[row].length) {
                throw new IllegalArgumentException("Terlalu banyak nilai di baris " + row);
            }

            for(int column = 0; column < tokens.length; column++) {
                try {
                    int value = Integer.parseInt(tokens[column]);
                    numArray[row][column] = value; 
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Nilai non numeric ditemukan di baris " + row + ", kolom " + column);
                }
            }
            row++;
        }
        //close reader
        try {
            reader.close();
        } catch (IOException e) { }

        //return array2D yang menjadi soal
        return numArray;
    }
}
