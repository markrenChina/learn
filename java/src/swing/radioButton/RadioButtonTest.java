package swing.radioButton;

import javax.swing.*;
import java.awt.*;

/**
 * @version 1.35 2018-04-10
 * @author Cay Horstmann
 */
public class RadioButtonTest
{
   public static void main(String[] args)
   {
      EventQueue.invokeLater(() -> {
         var frame = new RadioButtonFrame();
         frame.setTitle("RadioButtonTest");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setVisible(true);
      });
   }
}
