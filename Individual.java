import java.util.Random;

public class Individual {
    private int[] arrayNBS;
    private int[][] arraySoal;
    private int[][] arrayJawaban;
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


    // YANG HARUS DICEK:
    // light bulb illuminating another light bulb
    // violation of a numbered black squares constraint

    public Individual(int[] arrayNBS, int[][] arraySoal){
        this.arraySoal = arraySoal;
        this.arrayNBS = arrayNBS;
        this.fitness = 0;
        this.arrayJawaban = this.placeLamp();
        this.checkTembok();
        this.checkLightingOtherLamp();
    }

    public int getFitness(){
        return this.fitness;
    }

    public void printNBS(){
        for(int i = 0; i < arrayNBS.length; i++){
            System.out.print(arrayNBS[i] + " ");
            System.out.println();
        }
    }

    public void printArrayJawaban(){
        int length = this.arraySoal.length;

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                if(arrayJawaban[i][j] < 0){
                    System.out.print(arrayJawaban[i][j] + "  ");
                }else{
                    System.out.print(" " + arrayJawaban[i][j] + "  ");
                }
            }
            System.out.println();
        }
    }

    private void checkLightingOtherLamp(){
        int length = this.arraySoal.length;

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                if(arrayJawaban[i][j] == 5){
                    int currentColumn = j;
                    int currentRow = i;

                    while(currentRow < length -1){
                        if(arrayJawaban[currentRow+1][j] == 0 || arrayJawaban[currentRow+1][j] == 1 || arrayJawaban[currentRow+1][j] == 2 || arrayJawaban[currentRow+1][j] == 3 || arrayJawaban[currentRow+1][j] == 4){
                            break;
                        }
                        if(arrayJawaban[currentRow+1][j] == -5){
                            break;
                        }
                        if(arrayJawaban[currentRow+1][j] == 5){
                            this.fitness += 100;
                            break;
                        }
                        arrayJawaban[currentRow+1][j] = -2;
                        currentRow++;
                    }

                    currentColumn = j;
                    currentRow = i;
    
                    while(currentRow != 0){
                        if(arrayJawaban[currentRow-1][j] == 0 || arrayJawaban[currentRow-1][j] == 1 || arrayJawaban[currentRow-1][j] == 2 || arrayJawaban[currentRow-1][j] == 3 || arrayJawaban[currentRow-1][j] == 4){
                            break;
                        }
                        if(arrayJawaban[currentRow-1][j] == -5){
                            break;
                        }
                        if(arrayJawaban[currentRow-1][j] == 5){
                            this.fitness += 100;
                            break;
                        }
                        arrayJawaban[currentRow-1][j] = -2;
                        currentRow--;
                    }

                    currentColumn = j;
                    currentRow = i;

                    while(currentColumn < length - 1){
                        if(arrayJawaban[i][currentColumn + 1] == 0 || arrayJawaban[i][currentColumn + 1] == 1 || arrayJawaban[i][currentColumn + 1] == 2 || arrayJawaban[i][currentColumn + 1] == 3 || arrayJawaban[i][currentColumn + 1] == 4){
                            break;
                        }
                        if(arrayJawaban[i][currentColumn + 1] == -5){
                            break;
                        }
                        if(arrayJawaban[i][currentColumn + 1] == 5){
                            this.fitness += 100;
                            break;
                        }
                        arrayJawaban[i][currentColumn+1] = -2;
                        currentColumn++;
                    }

                    currentColumn = j;
                    currentRow = i;

                    while(currentColumn != 0){
                        if(arrayJawaban[i][currentColumn - 1] == 0 || arrayJawaban[i][currentColumn - 1] == 1 || arrayJawaban[i][currentColumn - 1] == 2 || arrayJawaban[i][currentColumn - 1] == 3 || arrayJawaban[i][currentColumn - 1] == 4){
                            break;
                        }
                        if(arrayJawaban[i][currentColumn - 1] == -5){
                            break;
                        }
                        if(arrayJawaban[i][currentColumn - 1] == 5){
                            this.fitness += 100;
                            break;
                        }
                        arrayJawaban[i][currentColumn - 1] = -2;
                        currentColumn--;
                    }
                }
            }
        }
    }

    private void checkTembok(){
        int length = this.arraySoal.length;

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                if(arrayJawaban[i][j] == 5 && arraySoal[i][j] == -5){
                    this.fitness += 100;
                }
            }
        }
    }


    private int[][] placeLamp(){
        int length = this.arraySoal.length;
        int[][] jawaban = this.arraySoal;
        int NBSCounter = 0;

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                if(arraySoal[i][j] == 0){
                    NBSCounter++;
                }else if(arraySoal[i][j] == 4){
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
                    NBSCounter++;
                }else if(arraySoal[i][j] == 1){
                    if(arrayNBS[NBSCounter] == 1){
                        try {
                            jawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 2){
                        try {
                            jawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 3){
                        try {
                            jawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 4){
                        try {
                            jawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    NBSCounter++;
                } else if (arraySoal[i][j] == 2){
                    if(arrayNBS[NBSCounter] == 1){
                        try {
                            jawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 2){
                        try {
                            jawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 3){
                        try {
                            jawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i][j-1] = 5;
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
                            jawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 5){
                        try {
                            jawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
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
                        }
                    }
                    NBSCounter++;

                } else if (arraySoal[i][j] == 3){
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
                        }
                        try {
                            jawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 2){
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
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 4){
                        try {
                            jawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                        try {
                            jawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }try {
                            jawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 100;
                        }
                    }
                    NBSCounter++;
                }
            }
        }

        return jawaban;
    }
}
