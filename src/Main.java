import View.Chart;
import View.Login;

import javax.swing.*;
public class Main {
    public static void main(String[] arg) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        Login login = new Login();
    }

}
