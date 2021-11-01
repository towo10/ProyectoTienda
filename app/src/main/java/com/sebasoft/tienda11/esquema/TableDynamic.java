package com.sebasoft.tienda11.esquema;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class TableDynamic {
    private TableLayout tableLayout;
    private Context context;
    private String[] header;
    private ArrayList<String[]> data;
    private TableRow tableRow;
    private TextView txtcell;
    private int indexC,indexR;
    private boolean multicolor=false;
    private int firstColor,secondColor;

    public TableDynamic(TableLayout tableLayout, Context context){
        this.tableLayout = tableLayout;
        this.context = context;
    }

    public void addHeader(String[] header){
        this.header=header;
        createHeader();
    }
    public void addData(ArrayList<String[]> data){
        this.data=data;
        createDataTable();
    }
    private void newRow(){
        tableRow = new TableRow(context);
    }
    private void newCell(){
        txtcell = new TextView(context);
        txtcell.setGravity(Gravity.CENTER);
        txtcell.setTextSize(16);
    }
    private void createHeader(){
        indexC=0;
        newRow();
        while (indexC< header.length){
            newCell();
            txtcell.setText(header[indexC++]);
            tableRow.addView(txtcell,newTableRowParams());
        }
        tableLayout.addView(tableRow);
    }
    private void createDataTable(){
        String info;
        for(indexR=1;indexR<= data.size();indexR++){
            newRow();
            for(indexC=0;indexC< header.length;indexC++){
                newCell();
                String[] colums=data.get(indexR-1);
                info =(indexC<colums.length)?colums[indexC]:"";
                txtcell.setText(info);
                tableRow.addView(txtcell,newTableRowParams());
            }
            tableLayout.addView(tableRow);
        }
    }

    private TableRow.LayoutParams newTableRowParams(){
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(1,1,1,1);
        params.weight = 1;
        return params;
    }

    private TableRow getRow(int index){
        return (TableRow) tableLayout.getChildAt(index);
    }
    private TextView getCell(int rowIndex,int columIndex){
        tableRow = getRow(rowIndex);
        return (TextView) tableRow.getChildAt(columIndex);
    }
    public  void backGroundHeader(int color){
        indexC=0;
        while (indexC< header.length){
            txtcell=getCell(0,indexC++);
            txtcell.setBackgroundColor(color);
            txtcell.setTextColor(Color.WHITE);
        }
    }

    public void backGroundData(int firstColor,int secondColor){
        for(indexR=1;indexR<= data.size();indexR++){
            multicolor=!multicolor;
            for(indexC=0;indexC< header.length;indexC++){
                txtcell=getCell(indexR,indexC);
                txtcell.setBackgroundColor((multicolor)?firstColor:secondColor);
                txtcell.setTextColor(Color.BLACK);
            }
        }
        this.firstColor = firstColor;
        this.secondColor = secondColor;
    }

    public  void reColoring(){
        indexC=0;
        multicolor=!multicolor;
        while (indexC< header.length){
            txtcell=getCell(data.size() - 1, indexC++);
            txtcell.setBackgroundColor((multicolor)?firstColor:secondColor);
        }
    }

}
