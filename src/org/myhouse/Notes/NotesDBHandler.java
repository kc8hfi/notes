package org.myhouse.Notes;

import android.content.Context;  
import android.content.ContentValues;
import android.database.Cursor;  
import android.database.sqlite.SQLiteDatabase;  
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class NotesDBHandler extends SQLiteOpenHelper
{
     //version and database filename
     private static final int DB_VERSION=1;
     private static final String DB_NAME="notes.db";
     
     //table name
     private static final String TABLE_NAME = "notes";
     
     //column names
     private static final String ID = "id";
     private static final String TITLE = "title";
     private static final String THE_DATA = "the_data";

     //constructor
     public NotesDBHandler(Context context)
     {
          super(context,DB_NAME,null,DB_VERSION);
     }
     
     @Override
     public void onCreate(SQLiteDatabase db)
     {
          String q = "create table " + TABLE_NAME 
               + "(" + ID + " integer primary key on conflict fail autoincrement,"
               + TITLE + " text, "
               + THE_DATA + " text )";
          db.execSQL(q);
     }
     
     @Override
     public void onUpgrade(SQLiteDatabase db, int old,int newVersion)
     {
     }
     
     //insert a notes
     public void addNote(Note n)
     {
          SQLiteDatabase db = this.getWritableDatabase();
          ContentValues values = new ContentValues();
          //values.put(ID,n.getId());
          values.put(TITLE,n.getTitle());
          values.put(THE_DATA,n.getNote());
          long retval = db.insert(TABLE_NAME,null,values);
          if(retval != -1)
               n.setId(retval);
          db.close();
     }
     
     //update a note
     public int updateNote(Note n)
     {
          SQLiteDatabase db = this.getWritableDatabase();
          ContentValues values = new ContentValues();
          values.put(TITLE,n.getTitle());
          values.put(THE_DATA,n.getNote());
          return db.update(TABLE_NAME,values,ID+" = ?", 
                           new String[]{String.valueOf(n.getId()) });          
     }
     
     //delete
     public void deleteNote(Note n)
     {
          SQLiteDatabase db = this.getWritableDatabase();
          db.delete(TABLE_NAME,ID+"= ?",
                           new String[]{String.valueOf(n.getId()) });
          db.close();
     }
     
     //get all the notes
     public ArrayList<Note> getAllNotes()
     {
          SQLiteDatabase db = this.getReadableDatabase();
          ArrayList<Note> notes = new ArrayList<Note>(0);
          //table, columns, selection,selectionArgs, groupBy, having, orderBy
          Cursor cursor = db.query(TABLE_NAME,
                                   new String[]{ID,TITLE,THE_DATA},
                                   null,
                                   null,
                                   null,
                                   null,
                                   TITLE
          );
          if (cursor.moveToFirst())
          {
               do
               {
                    Note n = new Note(cursor);
                    notes.add(n);
               }while(cursor.moveToNext());
          }
          db.close();
          return notes;
     }     

     
     
     
}//end class
