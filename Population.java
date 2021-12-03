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

    public boolean addIndividual(Individual newInd) {
        if (this.populationSize >= this.maxPopulationSize){
            return false;
        } 
        this.population.add(newInd);
        this.populationSize++;
        return true;
    }

    public Individual[] selectParent() {    //roulette wheel
        Individual[] parents = new Individual[2];

        int sumfitness = 0;
        for (int i = 0; i < this.population.size(); i++) {
            sumfitness += this.population.get(i).getFitness();
        }

        for (int i = 0; i < this.population.size(); i++) {
            ((Individual) this.population.get(i)).setParentProbability(1.0 * this.population.get(i).getFitness() / sumfitness);
        }
        for (int n = 0; n < 2; n++) {
            int i = -1;
            double prob = this.random.nextDouble();
            double sum = 0.0;
            do {
                i++;
                sum = sum + this.population.get(i).getParentProbability();
            } while(sum < prob);
            parents[n] = this.population.get(i);
        }
        return parents;
    }

    public Individual getBestIndividual(){
        int best = Integer.MAX_VALUE;
        int index = 0;
        int bestIndex = 0;

        while(best != 0 && index < this.population.size()){
            if(this.population.get(index).getFitness() < best){
                best = this.population.get(index).getFitness();
                bestIndex = index;

            }
            index++;
        }

        

        return this.population.get(bestIndex);
    }

    public Population generateNewPopulationWithElitism(double elitismRate){
        Population newPopulation = new Population(this.random, this.maxPopulationSize);

        if (this.random.nextDouble() < elitismRate){
            newPopulation.addIndividual(this.getBestIndividual());
        }

        return newPopulation;
    }

    public boolean isFilled() {
        return this.maxPopulationSize == this.population.size();
    }

    //Method untuk mengimport soal dari file.txt
    public static int[][] importFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int[][] numArray;
        String line = reader.readLine();
        if (line == null) {
            throw new IllegalArgumentException("There was no 1st line.");
        }
        String[] dimensions = line.split("\\s+");
        try {
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);
            numArray = new int[rows][cols];
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("First line of file has to be 'rows cols'");
        }
    
        int row = 0; 
    
        while ((line = reader.readLine()) != null && row < numArray.length) {
            String[] tokens = line.split("\\s+");
            if (tokens.length > numArray[row].length) {
                throw new IllegalArgumentException("Too many values provided in matrix row " + row);
            }
            // to less values will be filled with 0. If you don't want that
            // you have to uncomment the next 3 lines.
            //if (tokens.length < numArray[row].length) {
            //  throw new IllegalArgumentException("Not enough values provided in matrix row " + row);
            //}
            for(int column = 0; column < tokens.length; column++) {
                try {
                    int value = Integer.parseInt(tokens[column]);
                    numArray[row][column] = value; 
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Non numeric value found in matrix row " + row + ", column " + column);
                }
            }
            row++;
        }
        if (line != null) {
            // there were too many rows provided.
            // Superflous ones are ignored.
            // You can also throw exception, if you want.
        }
        if (row < numArray.length) {
            // There were too less rows in the file. If that's OK, the
            // missing rows will be interpreted as all 0.
            // If that's OK with you, you can comment out this whole
            // if block
            throw new IllegalArgumentException("Expected " + numArray.length + " rows, there only were " + row);
        }
        try {
            reader.close(); // never forget to close a stream.
        } catch (IOException e) { }

        return numArray;
    }
}
