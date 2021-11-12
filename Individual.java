import java.util.Random;
import java.io.*;
public class Individual {
    private int[][] array;
    private int fitness;

    /*

    BENTUK ARRAY SOAL:
    -1  -5  -1  -1   3   -1  -1
    -1   0  -1  -1  -1   -5   0
     1  -1  -1  -1  -1   -1  -1
    -1  -1  -1  -5  -1   -1  -1
    -1  -1  -1  -1  -1   -1   1
     1  -5  -1  -1  -1   -5  -1
    -1  -1   1  -1  -1    0  -1


    BENTUK ARRAY JAWABAN:
         j   j   j   j   j    j   j
    i    5  -5   4   5   3    5   4
    i    4   0   4   4   5   -5   0
    i    1   4   5   4   4    4   4
    i    5   4   4  -5   4    5   4
    i    4   5   4   4   4    4   1
    i    1  -5   4   4   4   -5   5
    i    5   4   1   5   4    4   4

    */

    /*
        0 1 2 3 4 : jumlah lampu yang ada di sekitar array[i][j] tersebut
        -5 : tembok
         5 : lampu
         6 : tidak ada lampu   
    */

    public Individual(int[][] array){
        this.fitness = 0;
        this.array = array;
    }

    public void setFitness(){
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[i].length; j++){

                //JIKA KETEMU 0, TIDAK BOLEH ADA LAMPU DI SEKITARNYA
                if(this.array[i][j] == 0){

                    //JIKA I DAN J BUKAN DI UJUNG
                    if(i != 0 && i != this.array.length && j != 0 && j != this.array[i].length){
                        //JIKA SEKELILINGNYA ADALAH 0 (BENER) MAKA FITNESS += 1
                        if(this.array[i+1][j] == 0 && this.array[i-1][j] == 0 && this.array[i][j+1] == 0 && this.array[i][j-1] == 0){
                            this.fitness += 1;
                        }else{
                            this.fitness -= 1;
                        }
                    }

                    //JIKA I DI UJUNG ATAS DAN J DI TENGAH
                    else if(i == 0 && j != 0 && j != this.array[i].length){
                        if(this.array[i+1][j] == 0 && this.array[i][j+1] == 0 && this.array[i][j-1] == 0){
                            this.fitness += 1;
                        }else{
                            this.fitness -= 1;
                        }
                    }

                    //JIKA I DI UJUNG BAWAH DAN J DI TENGAH
                    else if(i == this.array.length && j != 0 && j != this.array[i].length){
                        if(this.array[i-1][j] == 0 && this.array[i][j+1] == 0 && this.array[i][j-1] == 0){
                            this.fitness += 1;
                        }else{
                            this.fitness -= 1;
                        }
                    }

                    //JIKA J DI UJUNG KIRI DAN I DI TENGAH
                    else if(j == 0 && i != 0 && i != this.array.length){
                        if(this.array[i][j+1] == 0 && this.array[i+1][j] == 0 && this.array[i-1][j] == 0){
                            this.fitness += 1;
                        }else{
                            this.fitness -= 1;
                        }
                    }

                    //JIKA J DI UJUNG KANAN DAN I DI TENGAH
                    else if(j == this.array[i].length && i != 0 && i != this.array.length){
                        if(this.array[i][j-1] == 0 && this.array[i+1][j] == 0 && this.array[i-1][j] == 0){
                            this.fitness += 1;
                        }else{
                            this.fitness -= 1;
                        }
                    }

                    //JIKA I J DI UJUNG KIRI ATAS
                    else if(j == 0 && i == 0){
                        if(this.array[i+1][j] == 0 && this.array[i][j+1] == 0){
                            this.fitness += 1;
                        }else{
                            this.fitness -= 1;
                        }
                    }

                    //JIKA I J DI UJUNG KANAN ATAS
                    else if(j == this.array[j].length && i == 0){
                        if(this.array[i+1][j] == 0 && this.array[i][j-1] == 0){
                            this.fitness += 1;
                        }else{
                            this.fitness -= 1;
                        }
                    }

                    //JIKA I J DI UJUNG KIRI BAWAH
                    else if(j == 0 && i == this.array[i].length){
                        if(this.array[i][j+1] == 0 && this.array[i-1][j] == 0){
                            this.fitness += 1;
                        }else{
                            this.fitness -= 1;
                        }
                    }

                    //JIKA I J DI UJUNG KANAN BAWAH
                    else if(j == this.array[j].length && i == this.array[i].length){
                        if(this.array[i-1][j] == 0 && this.array[i][j-1] == 0){
                            this.fitness += 1;
                        }else{
                            this.fitness -= 1;
                        }
                    }
                }
            }
        } 
    }

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
            // TODO: check for negative values.
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

    public static void main(String[] args) throws IOException {

        Random r = new Random();

        int low = 4;
        int high = 6;

        // int temp[][] = new int[7][7];
        // for(int i = 0; i < 7; i++){
        //     for(int j = 0; j < 7; j++){
        //         temp[i][j] = r.nextInt(high - low) + low;
        //     }
        // }
        
        int temp[][] = importFile("Array.txt");
        Individual i = new Individual(temp);

        i.setFitness();

        System.out.println(i.fitness);
        System.out.println();

    }
}