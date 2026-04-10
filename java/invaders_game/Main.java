/**
 * ファイル名：Main.java
 * 説明：アプリケーションのエントリーポイント。ウィンドウ生成とGamePanelの表示のみを行う。
 * 作成日：2026-04-10
 * 作者：takashioikawa
 */

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/** Swingウィンドウを開きゲームパネルを表示する。 */
public class Main {

    /** GCでウィンドウ参照が切れないよう保持する（トップレベルでも通常は不要だが保険）。 */
    private static JFrame mainWindow;

    public static void main(String[] args) {
        // invokeLater のみだと main が先に終わり、EDT が動く前に JVM が終了することがある。
        // invokeAndWait でウィンドウ表示まで完了してから main を抜け、AWT スレッドが生きたままにする。
        try {
            SwingUtilities.invokeAndWait(() -> {
                JFrame frame = new JFrame("スペースインベーダー風");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GamePanel panel = new GamePanel();
                frame.setContentPane(panel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setVisible(true);
                mainWindow = frame;
                panel.requestFocusInWindow();
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Throwable c = e.getCause();
            if (c != null) {
                c.printStackTrace();
            } else {
                e.printStackTrace();
            }
        }
    }
}
