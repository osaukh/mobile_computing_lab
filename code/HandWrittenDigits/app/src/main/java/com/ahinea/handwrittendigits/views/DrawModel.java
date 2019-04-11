package com.ahinea.handwrittendigits.views;

//ArrayList is part of collection framework in Java.
//Therefore array members are accessed using [], while ArrayList has a set of methods
// //to access elements and modify them.
import java.util.ArrayList;
import java.util.List;

//a collection of getter and set functions
//to draw a character model
public class DrawModel {

    //initialize beginning of the line coordinate
    public static class LineElem {
        public float x;
        public float y;

        //internal repreesntation for manipulation
        private LineElem(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    //for a single line
    public static class Line {
        //a line consits of a set of elements (small parts of a line)
        private List<LineElem> elems = new ArrayList<>();

        private Line() {
        }
        //add, get, and get index of an element
        private void addElem(LineElem elem) {
            elems.add(elem);
        }

        public int getElemSize() {
            return elems.size();
        }

        public LineElem getElem(int index) {
            return elems.get(index);
        }
    }


    private Line mCurrentLine;

    private int mWidth;  // pixel width = 28
    private int mHeight; // pixel height = 28

    //so a model consits of lines which consists of elements
    //a line begins when a user starts drawing and ends when
    //they lift their finger up
    private List<Line> mLines = new ArrayList<>();

    //given a set 28 by 28 sized window
    public DrawModel(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    //start drawing line and add it to memory
    public void startLine(float x, float y) {
        mCurrentLine = new Line();
        mCurrentLine.addElem(new LineElem(x, y));
        mLines.add(mCurrentLine);
    }

    public void endLine() {
        mCurrentLine = null;
    }

    public void addLineElem(float x, float y) {
        if (mCurrentLine != null) {
            mCurrentLine.addElem(new LineElem(x, y));
        }
    }

    public int getLineSize() {
        return mLines.size();
    }

    public Line getLine(int index) {
        return mLines.get(index);
    }

    public void clear() {
        mLines.clear();
    }
}