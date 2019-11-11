package com.example.hw3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button randomButton = findViewById(R.id.randomButtonID);
        PuzzleBoard puzzle = (PuzzleBoard)findViewById(R.id.boardView);
        //Context context = null;
        // attrs = null;
        //PuzzleBoard controller = new PuzzleBoard(context, attrs, randomButton);
        //randomButton.setOnClickListener(controller);
        puzzle.setOnTouchListener(puzzle);
        puzzle.setOnDragListener(puzzle);
        randomButton.setOnClickListener(puzzle);
}
}
