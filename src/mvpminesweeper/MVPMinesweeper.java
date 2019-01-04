
package mvpminesweeper;

import Presenter.Presenter;
import View.SwingStartMenuDisplay;

public class MVPMinesweeper {
    public static void main (String[] args) {
        Presenter presenter = new Presenter();
        SwingStartMenuDisplay ssmd = new SwingStartMenuDisplay();
        ssmd.addPresenter(presenter); 
    }
}
