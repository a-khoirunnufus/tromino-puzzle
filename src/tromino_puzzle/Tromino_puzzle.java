/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tromino_puzzle;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author user
 */
public class Tromino_puzzle {
    public static int pNum = 1;
    
    public static int ranPNum(){
        Random r = new Random();
        return r.nextInt((4-1)+1)+1;
    }
    
    //mengisi puzzle untuk board 2x2
    public static void fillPuzzle(int[][] board, int pNum, int msx, int msy){
        for(int x=0; x<2; x++){
            for(int y=0; y<2; y++){
                if(x!=msx || y!=msy){
                    board[x][y] = pNum;
                }
            }
        }
    }
    
    //combine board done
    public static void mergeBoard(int n, int[][] result, int[][] board1, int[][] board2, int[][] board3, int[][] board4){
        for(int x=0; x<n; x++){
            if(x<n/2){
                for(int y=0; y<n; y++){
                    if(y<n/2){
                        result[x][y] = board1[x][y];
                    }else if(y<n){
                        result[x][y] = board2[x][y-(n/2)];
                    }
                }
            }else if(x<n){
                for(int y=0; y<n; y++){
                    if(y<n/2){
                        result[x][y] = board3[x-(n/2)][y];
                    }else if(y<n){
                        result[x][y] = board4[x-(n/2)][y-(n/2)];
                    }
                }
            }
        }
    }
    
    public static void divideBoard(int[][] source, int[][] result1, int[][] result2, int[][] result3, int[][] result4){
        int SqSize = source[0].length;
        for(int i=0; i<4; i++){
            switch(i){
                case 1:
                    for(int x=0; x<SqSize/2; x++){
                        for(int y=0; y<SqSize/2; y++){
                            result1[x][y] = source[x][y];
                        }
                    }
                case 2:
                   for(int x=0; x<SqSize/2; x++){
                        for(int y=SqSize/2; y<SqSize; y++){
                            result2[x][y-SqSize/2] = source[x][y];
                        }
                    }
                case 3:
                    for(int x=SqSize/2; x<SqSize; x++){
                        for(int y=0; y<SqSize/2; y++){
                            result3[x-SqSize/2][y] = source[x][y];
                        }
                    }
                case 4:
                    for(int x=SqSize/2; x<SqSize; x++){
                        for(int y=SqSize/2; y<SqSize; y++){
                            result4[x-SqSize/2][y-SqSize/2] = source[x][y];
                        }
                    }
            }
        }
    }
    
    public static void viewBoard(int n, int[][] board){
        for(int x=0; x<n; x++){
            for(int y=0; y<n; y++){
                System.out.print(board[x][y]+" ");
            }
            System.out.println("");
        }
    }
    
    public static int getMCPosX(int[][] board){
        for(int x=0; x<board[0].length; x++){
            for(int y=0; y<board[0].length; y++){
                if(board[x][y] == 5 || board[x][y] == 9){
                    return x;
                }
            }
        }
        return 404;
    }
    
    public static int getMCPosY(int[][] board){
        for(int x=0; x<board[0].length; x++){
            for(int y=0; y<board[0].length; y++){
                if(board[x][y] == 5 || board[x][y] == 9){
                    return y;
                }
            }
        }
        return 404;
    }
    
    public static int getMCQuad(int n, int mcx, int mcy){
        int msPos;
        int subSqSize = n/2;
       
        //cari posisi missing cell
        if(mcx < subSqSize){
            if(mcy < subSqSize){
                msPos = 1;
            }else{
                msPos = 2;
            }
        }else{
            if(mcy < subSqSize){
                msPos = 3;
            }else{
                msPos = 4;
            }
        }
        
        return msPos;
    }
    
    public static void tile(int[][] board, int mcx, int mcy, int pNum){
        if(board[0].length==2){
            fillPuzzle(board,pNum,mcx,mcy);
            
        }else if(board[0].length>2){
            int msPos = getMCQuad(board[0].length, mcx,mcy);
            int subSqSize = board[0].length/2;

            //fill shaped tile in center square
            for(int i = 1; i<=4 ;i++){
                if(i!=msPos){
                    if(i==1){
                        board[subSqSize-1][subSqSize-1] = 5;
                    }else if(i==2){
                        board[subSqSize-1][subSqSize] = 5;
                    }else if(i==3){
                        board[subSqSize][subSqSize-1] = 5;
                    }else if(i==4){
                        board[subSqSize][subSqSize] = 5;
                    }
                }
            }

            int[][] subSquare1 = new int[subSqSize][subSqSize];
            int[][] subSquare2 = new int[subSqSize][subSqSize];
            int[][] subSquare3 = new int[subSqSize][subSqSize];
            int[][] subSquare4 = new int[subSqSize][subSqSize];

            divideBoard(board,subSquare1,subSquare2,subSquare3,subSquare4);

            tile(subSquare1,getMCPosX(subSquare1),getMCPosY(subSquare1),ranPNum());
            tile(subSquare2,getMCPosX(subSquare2),getMCPosY(subSquare2),ranPNum());
            tile(subSquare3,getMCPosX(subSquare3),getMCPosY(subSquare3),ranPNum());
            tile(subSquare4,getMCPosX(subSquare4),getMCPosY(subSquare4),ranPNum());
 
            mergeBoard(board[0].length,board,subSquare1,subSquare2,subSquare3,subSquare4);
        }
    }
    
    public static void main(String[] args) {
        int msx = 2;
        int msy = 2;
        
        int[][] board = new int[16][16];
        board[2][2] = 9;
        tile(board,msx,msy,ranPNum());
        viewBoard(board[0].length,board);
    }
    
}
