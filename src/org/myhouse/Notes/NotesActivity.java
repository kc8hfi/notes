package org.myhouse.Notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.util.Log;
import java.util.ArrayList;
import android.widget.Toast;

public class NotesActivity extends Activity
{
     /** Called when the activity is first created. */
     @Override
     public void onCreate(Bundle savedInstanceState)
     {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.main);
          
          note = new Note();
          database = new NotesDBHandler(this);
          
          title = (EditText)findViewById(R.id.title);
          data = (EditText)findViewById(R.id.data);
          
          //get all the notes
          notes = database.getAllNotes();
          
          //set up the adapter for the listview of notes
          adapter = new NotesAdapter(this,notes);
          
          title.addTextChangedListener(new TextWatcher() 
          {
               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) 
               {
                    // TODO Auto-generated method stub
                    note.setTitle(title.getText().toString());
                    doWork();
               }

               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after)
               {
                    // TODO Auto-generated method stub
               }

               @Override
               public void afterTextChanged(Editable s)
               {
                    // TODO Auto-generated method stub
               }
          });          
          
          data.addTextChangedListener(new TextWatcher() 
          {
               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) 
               {
                    // TODO Auto-generated method stub
                    note.setNote(data.getText().toString());
                    doWork();
               }

               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after)
               {
                    // TODO Auto-generated method stub
               }

               @Override
               public void afterTextChanged(Editable s)
               {
                    // TODO Auto-generated method stub
               }
          });
          
     }//end onCreate
     
     private void doWork()
     {
          //insert
          if (note.getId() == 0)
          {
               if(!(note.getTitle().equals("") && note.getNote().equals("")))
                    database.addNote(note);
          }
          //update
          else
          {
               database.updateNote(note);
          }
     }
     
     
     //options menu
     @Override
     public boolean onCreateOptionsMenu(Menu menu)
     {
          MenuInflater inflater = getMenuInflater();
          inflater.inflate(R.menu.mymenu,menu);
          return true;
     }
     
     //menu handler
     @Override
     public boolean onOptionsItemSelected(MenuItem item)
     {
          switch(item.getItemId())
          {
               case R.id.new_note:
                    newNote();
                    return true;
               case R.id.get_note:
                    showNoteDialog(clicker);
                    return true;
               default:
                    return super.onOptionsItemSelected(item);
          }
     }
     
     /**
      * show the note selection dialog box
      */
     public void showNoteDialog(DialogInterface.OnClickListener clicker)
     {
          refreshNoteList();
          if (notes.size() > 0)
          {
               AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_TRADITIONAL);
               builder.setAdapter(adapter,clicker);  
               builder.setTitle("Pick one");
               builder.show();
          }
          else
          {
               Toast.makeText (getApplicationContext(), "Type some notes first!", Toast.LENGTH_SHORT).show ();
          }
     }
     

     private void newNote()
     {
          note = new Note();
          title.setText(note.getTitle());
          data.setText(note.getNote());
     }

     private void refreshNoteList()
     {
          notes.clear();
          notes.addAll(database.getAllNotes());
          adapter.notifyDataSetChanged();
     }
     
     /**
      * what to do when they select a note
      */
     private DialogInterface.OnClickListener clicker = new DialogInterface.OnClickListener()
     {
          public void onClick(DialogInterface dialog, int item)
          {
               Note n = notes.get(item);
               
               note.setId(n.getId());
               note.setTitle(n.getTitle());
               note.setNote(n.getNote());
               
               title.setText(note.getTitle());
               data.setText(note.getNote());
          }
     };
     
     protected ArrayList<Note> notes;
     private Note note;
     private NotesDBHandler database;
     private NotesAdapter adapter;
     private EditText data;
     private EditText title;
}
