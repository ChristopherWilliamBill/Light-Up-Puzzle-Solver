import java.util.Random;
import java.io.*;

public class Individual {
    private int[] arrayNBS;
    private int[][] arraySoal;
    private int fitness;
    private Random random;

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

    public Individual(int[] arrayNBS, int[][] arraySoal, Random random){
        this.random = random;
        this.arraySoal = arraySoal;
        this.arrayNBS = arrayNBS;
        this.fitness = 0;
    }

    private int[][] generateArrayJawaban(){
        int length = this.arraySoal.length;
        int[][] jawaban = new int[length][length];
        int NBSCounter = 0;

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                if(arraySoal[i][j] == 0){
                    NBSCounter++;
                    try {
                        jawaban[i][j+1] = 6;
                    }catch (Exception e){
                        fitness += 100;
                    }

                    try {
                        jawaban[i][j-1] = 6;
                    }catch (Exception e){
                        fitness += 100;
                    }

                    try {
                        jawaban[i+1][j] = 6;
                    }catch (Exception e){
                        fitness += 100;
                    }

                    try {
                        jawaban[i-1][j] = 6;
                    }catch (Exception e){
                        fitness += 100;
                    }

                }else if(arraySoal[i][j] == 4){
                    NBSCounter++;
                    try {
                        jawaban[i][j+1] = 5;
                    }catch (Exception e){
                        fitness += 100;
                    }

                    try {
                        jawaban[i][j-1] = 5;
                    }catch (Exception e){
                        fitness += 100;
                    }

                    try {
                        jawaban[i+1][j] = 5;
                    }catch (Exception e){
                        fitness += 100;
                    }

                    try {
                        jawaban[i-1][j] = 5;
                    }catch (Exception e){
                        fitness += 100;
                    }
                }else if(arraySoal[i][j] == 1){
                    NBSCounter++;
                    if(arrayNBS[NBSCounter] == 1){
                        try {
                            jawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i+1][j] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j-1] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j+1] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }

                    else if(arrayNBS[NBSCounter] == 2){
                        try {
                            jawaban[i-1][j] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i+1][j] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j-1] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }

                    else if(arrayNBS[NBSCounter] == 3){
                        try {
                            jawaban[i-1][j] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j-1] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j+1] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }

                    else if(arrayNBS[NBSCounter] == 4){
                        try {
                            jawaban[i-1][j] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i+1][j] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j+1] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                } else if (arraySoal[i][j] == 2){
                    NBSCounter++;
                    if(arrayNBS[NBSCounter] == 1){
                        try {
                            jawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i+1][j] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j-1] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 2){
                        try {
                            jawaban[i-1][j] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j-1] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 3){
                        try {
                            jawaban[i-1][j] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j+1] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 4){
                        try {
                            jawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i+1][j] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j+1] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 5){
                        try {
                            jawaban[i-1][j] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i+1][j] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 6){
                        try {
                            jawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j-1] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j+1] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                } else if (arraySoal[i][j] == 3){
                    NBSCounter++;
                    if(arrayNBS[NBSCounter] == 1){
                        try {
                            jawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j-1] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 2){
                        try {
                            jawaban[i-1][j] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 3){
                        try {
                            jawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j+1] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 4){
                        try {
                            jawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i+1][j] = 6;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                }
            }
        }
    }
}
