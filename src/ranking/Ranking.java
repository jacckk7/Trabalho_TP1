package ranking;

// File IO
import java.util.*;
import java.util.stream.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.table.TableModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;

interface Sortable {
  boolean isSmaller(Sortable other);
}

class Sorter {
  public void sort(ArrayList<? extends Sortable> a) {
    for (int i = 0; i < a.size(); i++)
      for (int j = 0; j < a.size(); j++)
        if (!a.get(i).isSmaller(a.get(j)))
          Collections.swap(a, i, j);
  }
}

class Record implements Sortable {
  public String name;
  public int score;
  public Date date;
  public DateFormat df;

  public Record(String line) {
    df = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<String> fields = new ArrayList<>(Arrays.asList(line.split(",")));
    this.name = fields.get(0);
    this.score = Integer.parseInt(fields.get(1));
    try {
      this.date = df.parse(fields.get(2));
    } catch (ParseException e) {
      System.out.println("Error parsing date:");
      e.printStackTrace();
    }
  }

  public Record(String name, int score, Date date) {
    df = new SimpleDateFormat("yyyy-MM-dd");
    this.name = name;
    this.score = score;
    this.date = date;
  }

  public String encode() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.name);
    sb.append(',');
    sb.append(this.score);
    sb.append(',');
    sb.append(df.format(this.date));
    sb.append('\n');
    return sb.toString();
  }

  @Override
  public boolean isSmaller(Sortable other) {
		Record o = (Record) other;
		return this.score < o.score;
	}
}

public class Ranking {
  public static ArrayList<Record> records;
  public static String filename;

  public Ranking(String filename) {
    this.filename = filename;
    records = new ArrayList<Record>();
    try {
      Stream<String> lines = Files.lines(Paths.get(filename));
      lines.forEach(line -> records.add(new Record(line)));
    } catch (IOException e) {
      System.out.println("Error reading records:");
      e.printStackTrace();
    }
    Sorter s = new Sorter();
    s.sort(records);
  }

  public static void save(String filename) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
      for (Record rr : records) writer.write(rr.encode());
      writer.close();
    } catch (IOException e) {
      System.out.println("Error saving ranking:");
      e.printStackTrace();
    }
  }

  public static void add(String name, int score) {
    records.add(new Record(name, score, new Date()));
    Sorter s = new Sorter();
    s.sort(records);
    save(filename);
  }

  public void createWindow() {
    TableModel dataModel = new AbstractTableModel() {
      public int getColumnCount() { return 3; }
      public String getColumnName(int col) {
        if (col == 0) return "Name";
        else if (col == 1) return "Score";
        else return "Date";
      }
      public int getRowCount() { return records.size();}
      public Object getValueAt(int row, int col) {
        if (col == 0) return records.get(row).name;
        else if (col == 1) return records.get(row).score;
        else return records.get(row).df.format(records.get(row).date);
      }
    };
    JTable table = new JTable(dataModel);
    JScrollPane scrollpane = new JScrollPane(table);

    JFrame frame = new JFrame("lol");
    frame.setLayout(new GridBagLayout());
    frame.add(scrollpane);
    frame.setSize(500, 500);
    frame.setVisible(true);
  }
}
