package swing.tableRowColumn;

import javax.swing.*;
import java.awt.*;

/**
 * This program demonstrates how to work with rows and columns in a table.
 * @version 1.23 2018-05-01
 * @author Cay Horstmann
 */
public class TableRowColumnTest
{
   public static void main(String[] args)
   {
      EventQueue.invokeLater(() ->
         {
            var frame = new PlanetTableFrame();
            frame.setTitle("TableRowColumnTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
         });
   }
}
