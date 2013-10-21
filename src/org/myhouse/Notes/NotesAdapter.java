package org.myhouse.Notes;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;

public class NotesAdapter extends BaseAdapter
{
     public NotesAdapter(Activity context, ArrayList<Note> names) 
     {
          this.context = context;
          this.names = names;
     }
     
     @Override
     public long getItemId(int position)
     {
          return position;
     }
      
     @Override    
     public Object getItem(int position)
     {

          return names.get(position);
     }

     @Override
     public int getCount()
     {
          return (names == null) ? 0 : names.size();
     }
  
     @Override
     public View getView(int position, View convertView, ViewGroup parent)
     {
          View rowView = convertView;
          //ImageView image = null;
          TextView text = null;
          if (rowView == null)
          {
               LayoutInflater inflater = context.getLayoutInflater();
               rowView = inflater.inflate(R.layout.note_selection, null);
               //image = (ImageView) rowView.findViewById(R.id.imageView);
               text = (TextView) rowView.findViewById(R.id.note);
               
               rowView.setTag(text);
          }
          //image = (ImageView) rowView.findViewById(R.id.imageView);
          text = (TextView) rowView.getTag();
          Note v = names.get(position);
          
          //set background color
          if ( (position %2) == 0)
          {
               rowView.setBackgroundResource(R.color.alternate);
          }
          else
          {
               rowView.setBackgroundResource(R.color.white);
          }
          //image.setImageResource(R.drawable.ic_pump);
          if (v.getTitle().equals(""))
          {
               text.setText("No title");
          }
          else
          {
               text.setText(v.getTitle());
          }
    
          rowView.setId((int)v.getId());
          
          return rowView;
     }
     
     private final Activity context;
     private final ArrayList<Note> names;
     
}//end NoteAdapter