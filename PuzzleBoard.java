package com.example.hw3;

import android.view.SurfaceView;
import android.view.View;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.content.ClipData;
import android.content.Context;
import android.widget.Button;

public class PuzzleBoard extends SurfaceView implements View.OnTouchListener, View.OnClickListener,
        View.OnDragListener {

    private int rowData;
    private int colData;
    private int puzzleWidth;
    private int puzzleHeight;
    private int boardDimension;
    private int[][] board = new int[4][4];
    private boolean isGameOver;
    private Button randomButton;

    /**
     * External Citation:
     *  Date 5 November 2019
     *  Problem: Had trouble implementing bitmaps
     *  Resource: Project Group
     *  Solution: I figured out how to use bitmaps by going off our Stratego game since we also used
     *  them for the group project
     */
    Bitmap resetGraphic = BitmapFactory.decodeResource(getResources(), R.drawable.reset_button);
    Bitmap squareOne = BitmapFactory.decodeResource(getResources(), R.drawable.image1);
    Bitmap squareTwo = BitmapFactory.decodeResource(getResources(), R.drawable.image2);
    Bitmap squareThree = BitmapFactory.decodeResource(getResources(), R.drawable.image3);
    Bitmap squareFour = BitmapFactory.decodeResource(getResources(), R.drawable.image4);
    Bitmap squareFive = BitmapFactory.decodeResource(getResources(), R.drawable.image5);
    Bitmap squareSix = BitmapFactory.decodeResource(getResources(), R.drawable.image6);
    Bitmap squareSeven = BitmapFactory.decodeResource(getResources(), R.drawable.image7);
    Bitmap squareEight = BitmapFactory.decodeResource(getResources(), R.drawable.image8);
    Bitmap squareNine = BitmapFactory.decodeResource(getResources(), R.drawable.image9);
    Bitmap squareTen = BitmapFactory.decodeResource(getResources(), R.drawable.image10);
    Bitmap squareEleven = BitmapFactory.decodeResource(getResources(), R.drawable.image11);
    Bitmap squareTwelve = BitmapFactory.decodeResource(getResources(), R.drawable.image12);
    Bitmap squareThirteen = BitmapFactory.decodeResource(getResources(), R.drawable.image13);
    Bitmap squareFourteen = BitmapFactory.decodeResource(getResources(), R.drawable.image14);
    Bitmap squareFifteen = BitmapFactory.decodeResource(getResources(), R.drawable.image15);
    Bitmap blankSqauare = BitmapFactory.decodeResource(getResources(), R.drawable.square);
    Bitmap scaledResetGraphic;
    Bitmap scaledSquareOne;
    Bitmap scaledSquareTwo;
    Bitmap scaledSquareThree;
    Bitmap scaledSquareFour;
    Bitmap scaledSquareFive;
    Bitmap scaledSquareSix;
    Bitmap scaledSquareSeven;
    Bitmap scaledSquareEight;
    Bitmap scaledSquareNine;
    Bitmap scaledSquareTen;
    Bitmap scaledSquareEleven;
    Bitmap scaledSquareTwelve;
    Bitmap scaledSquareThirteen;
    Bitmap scaledSquareFourteen;
    Bitmap scaledSquareFifteen;
    Bitmap scaledSquareBlank;


    /**
     * constructor class
     * @param context
     * @param attr
     */
    public PuzzleBoard(Context context, AttributeSet attr, Button initButton) {
        super(context, attr);
        isGameOver = false;
        setWillNotDraw(false);
        rowData = -1;
        colData = -1;
        randomButton = initButton;
        boardDimension = puzzleWidth * puzzleHeight;
    }


    /**
     * called when the size of the puzzle has changed
     * never called in current implementation
     * @param width
     * @param height
     * @param x
     * @param y
     */
    @Override
    protected void onSizeChanged(int width, int height, int x, int y) {
        // shuffles board
        shuffle();
        puzzleHeight=height;
        puzzleWidth =width;
        scaleBitmaps();
    }

    /**
     * shuffles the puzzle pieces in a random format
     */
    public void shuffle() {
        defaultBoard();
        for(int piece=1; piece<=16; piece++) {
            // creates random integers for the coordinates of the piece
            int x = (int)(Math.random() * 4);
            int y = (int)(Math.random() * 4);
            // if the coordinates are already taken, a spot is found
            if(spotTaken(x, y)) {
                piece--;
                continue;
            }
            // inserts piece into random spot in puzzle
            else {
                board[x][y] = piece;
            }
        }

        //if the board cannot be solved rerandomizes the board
        if(!(this.isLegal())) {
            shuffle();
        }
    }

    /**
     * sets puzzle pieces to their original format by incrementing through every piece of the puzzle
     * and setting it to the default coordinates
     */
    public void defaultBoard() {
        for(int x = 0; x < 4; x ++) {
            for(int y = 0; y < 4; y ++) {
                board[x][y] = -1;
            }
        }
    }

    public boolean spotTaken(int row, int col) {
        return board[row][col] != -1;
    }

    public boolean isLegal() {
        int legal = -1;
        // illegal piece counter
        int illegalPieces = 0;
        // increments through board
        for(int i = 0; i < 16; i++) {
            //if that position contains the empty position store the row
            if(board[i / 4][i % 4] == 16) {
                legal = i/4 + 1;
            }
            else {
                // illegal board
                return false;
            }
            // increments the counter for illegal pieces when the coordinates are outside of the
            // legal board coordinates
            for(int j = i + 1; j < 16; j ++) {
                if(board[i / 4][i % 4] > board[j / 4][j % 4]) {
                    illegalPieces++;
                }
            }
        }
        illegalPieces += legal;

        // if the number of illegal pieces is odd the it the board is illegal
        if(illegalPieces % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void scaleBitmaps() {
        scaledResetGraphic = Bitmap.createScaledBitmap(resetGraphic,
                puzzleHeight/4, puzzleHeight, false);
        scaledSquareOne = Bitmap.createScaledBitmap(squareOne,
                puzzleHeight/4,puzzleHeight/4, false);
        scaledSquareTwo = Bitmap.createScaledBitmap(squareTwo,
                puzzleHeight/4,puzzleHeight/4, false);
        scaledSquareThree = Bitmap.createScaledBitmap(squareThree,
                puzzleHeight/4,puzzleHeight/4, false);
        scaledSquareFour = Bitmap.createScaledBitmap(squareFour,
                puzzleHeight/4,puzzleHeight/4, false);
        scaledSquareFive = Bitmap.createScaledBitmap(squareFive,
                puzzleHeight/4,puzzleHeight/4, false);
        scaledSquareSix = Bitmap.createScaledBitmap(squareSix,
                puzzleHeight/4,puzzleHeight/4, false);
        scaledSquareSeven = Bitmap.createScaledBitmap(squareSeven,
                puzzleHeight/4,puzzleHeight/4, false);
        scaledSquareEight = Bitmap.createScaledBitmap(squareEight,
                puzzleHeight/4,puzzleHeight/4, false);
        scaledSquareNine = Bitmap.createScaledBitmap(squareNine,
                puzzleHeight/4,puzzleHeight/4, false);
        scaledSquareTen = Bitmap.createScaledBitmap(squareTen,
                puzzleHeight/4,puzzleHeight/4, false);
        scaledSquareEleven = Bitmap.createScaledBitmap(squareEleven,
                puzzleHeight/4,puzzleHeight/4, false);
        scaledSquareTwelve = Bitmap.createScaledBitmap(squareTwelve,
                puzzleHeight/4,puzzleHeight/4, false);
        scaledSquareThirteen = Bitmap.createScaledBitmap(squareThirteen,
                puzzleHeight/4,puzzleHeight/4, false);
        scaledSquareFourteen = Bitmap.createScaledBitmap(squareFourteen,
                puzzleHeight/4,puzzleHeight/4, false);
        scaledSquareFifteen = Bitmap.createScaledBitmap(squareFifteen,
                puzzleHeight/4,puzzleHeight/4, false);
        scaledSquareBlank = Bitmap.createScaledBitmap(blankSqauare,
                puzzleHeight/4,puzzleHeight/4, false);
    }

    public Bitmap assignProperBitmap(int row, int col) {
        switch (board[row][col]) {
            case 1: //piece is a 1
                if (isGameOver == false) {
                    return scaledSquareOne;
                }
            case 2: //piece is a 2
                if (isGameOver == false) {
                    return scaledSquareTwo;
                }
            case 3: //piece is a 3
                if (isGameOver == false) {
                    return scaledSquareThree;
                }
            case 4: //piece is a 4
                if (isGameOver == false) {
                    return scaledSquareFour;
                }
            case 5: //piece is a 5
                if (isGameOver == false) {
                    return scaledSquareFive;
                }
            case 6: //piece is a 6
                if (isGameOver == false) {
                    return scaledSquareSix;
                }
            case 7: //piece is a 7
                if (isGameOver == false) {
                    return scaledSquareSeven;
                }
            case 8: //piece is a 8
                if (isGameOver == false) {
                    return scaledSquareEight;
                }
            case 9: //piece is a 9
                if (isGameOver == false) {
                    return scaledSquareNine;
                }
            case 10: //piece is a 10
                if (isGameOver == false) {
                    return scaledSquareTen;
                }
            case 11: //piece is a 11
                if (isGameOver == false) {
                    return scaledSquareEleven;
                }
            case 12: //piece is a 12
                if (isGameOver == false) {
                    return scaledSquareTwelve;
                }
            case 13: //piece is a 13
                if (isGameOver == false) {
                    return scaledSquareThirteen;
                }
            case 14: //piece is a 14
                if (isGameOver == false) {
                    return scaledSquareFourteen;
                }
            case 15: //piece is a 15
                if (isGameOver == false) {
                    return scaledSquareFifteen;
                }
            default: //defaults to empty graphic
                return scaledSquareBlank;
        }

}

    private void checkIfGameOver() {
        //goes through each block and makes sure it contains the right number
        for(int x = 1; x<17; x++) {
            //if number is incorrect exit method
            if(board[(x-1)/4][(x-1)%4] != x) {
                break;
            }
            //if all 16 blocks contain the correct number, assert that the game is over
            if(x == 16) {
                isGameOver = true;
            }
        }
    }

    @Override
    public void onClick(View view) {

            shuffle();
            invalidate();

    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        //finds the place that contains the empty block
        int indexOfSixteen=-1;
        for(int x=0; x<16; x++) {
            if(board[x/4][x%4] == 16) {
                indexOfSixteen = x;
            }
        }

        //gets the type of drag the user inputs
        int action = dragEvent.getAction();
        //the user has just started a drag
        if(action == DragEvent.ACTION_DRAG_STARTED) {
            //stores the row and col of the action
            rowData = (int) (dragEvent.getY() * 4 / puzzleHeight);
            colData = (int) ((dragEvent.getX() - (puzzleWidth - puzzleHeight)/2) * 4 / puzzleHeight);
            /*if the tapped block is not one off of the blank square return that
            the action has been handle */
            if ((rowData * 4 + colData) != indexOfSixteen + 1
                    || (rowData * 4 + colData) != indexOfSixteen - 1
                    || (rowData * 4 + colData) != indexOfSixteen + 4
                    || (rowData * 4 + colData) != indexOfSixteen - 4) {
                return true;
            }
        }
        //the user stops a drag
        else if (action == DragEvent.ACTION_DROP) {
            //reads the position of the drop
            int row2 = (int) (dragEvent.getY() * 4 / puzzleHeight);
            int col2 = (int) ((dragEvent.getX() - (puzzleWidth - puzzleHeight)/2) * 4 / puzzleHeight);
            //makes sure the drag started in a legal position
            if(rowData > 3 || rowData < 0 || colData < 0 || colData > 3) {
                return true;
            }
            //if the user drops on the empty square register a tap
            if((row2 * 4) + col2 == indexOfSixteen) {
                tapSquare(rowData, colData);
            }
            //otherwises return that the drag and drop has been handled
            else {
                return true;
            }
        }
        return true;
    }

    private void tapSquare(int row, int col) {
        //does not do anything if the game is over
        if(isGameOver == true) {
            return;
        }

        //stores index of the tap
        int index = row * 4 + col;

        //moves the tapped square up if possible
        if(index-4 >= 0 && board[row-1][col] == 16) {
            int temp = board[row][col];
            board[row][col] = board[row-1][col];
            board[row-1][col] = temp;
        }
        //moves the tapped square left if possible
        else if ((index + 1) % 4 != 1 && board[row][col-1] == 16){
            int temp = board[row][col];
            board[row][col] = board[row][col-1];
            board[row][col-1] = temp;
        }
        //moves the tapped square right if possible
        else if ((index + 1) % 4 != 0 && board[row][col+1] == 16){
            int temp = board[row][col];
            board[row][col]=board[row][col+1];
            board[row][col+1]=temp;
        }
        //moves the tapped square down if possible
        else if ((index + 4) < 16 && board[row+1][col] == 16){
            int temp = board[row][col];
            board[row][col]=board[row+1][col];
            board[row+1][col]=temp;
        }
        //reevaluates if the games over and updates the screen
        checkIfGameOver();
        this.invalidate();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //tells the board to reset the board when the reset button is touched
        if(motionEvent.getX() > puzzleHeight + (puzzleWidth -puzzleHeight)/2) {
            isGameOver=false;
            this.shuffle();
            this.invalidate();
            return true;
        }

        //tells the game what to happen when a drag has been started
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            DragShadowBuilder shadowBuilder = new DragShadowBuilder();
            view.startDrag(data, shadowBuilder, view, 0);
            return true;
        }

        return true;
    }
    @Override
    public void onDraw(Canvas canvas) {
        //goes through each board and draws the proper bitmap in the correct spot
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Bitmap bitmapToDraw;
                Log.i("msg", "" + board[row][col]);
                bitmapToDraw = assignProperBitmap(row, col);

                canvas.drawBitmap(bitmapToDraw, (col * puzzleHeight) / 4 + (puzzleWidth - puzzleHeight) / 2, (row * puzzleHeight) / 4, null);
            }
        }

        //draws the reset button
        canvas.drawBitmap(scaledResetGraphic, puzzleHeight + (puzzleWidth - puzzleHeight) / 2, 0, null);
    }
}
