import ui.Login;
import ui.UIUtil;
import db.DBConnection;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        UIUtil.applySystemTheme();

        // Auto-Initialize SQLite Database before launching UI
        DBConnection.initializeDatabase();

        // Run UI safely on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            Login loginPage = new Login();
            loginPage.setVisible(true);
        });
    }
}
