package org.myhouse.Notes;

import android.database.Cursor;

public class Note
{
     public Note()
     {
          id = 0;
          title = "";
          note = "";
     }
     
     public Note(long i,String t,String n)
     {
          id = i;
          title = t;
          note = n;
     }
     
     public Note(Cursor c)
     {
          id = c.getLong(0);
          title = c.getString(1);
          note = c.getString(2);
     }
     
     public long getId()
     {
          return id;
     }
     public String getTitle()
     {
          return title;
     }
     public String getNote()
     {
          return note;
     }
     
     public void setId(long i)
     {
          id = i;
     }
     public void setTitle(String t )
     {
          title = t;
     }
     public void setNote(String n)
     {
          note = n;
     }
     
     public String toString()
     {
          String s = "id: " + Long.toString(id) + "\n";
          s += "title: " + title + "\n";
          s += "note: " + note + "\n";
          return s;
     }
     
     private long id;
     private String title;
     private String note;
}
