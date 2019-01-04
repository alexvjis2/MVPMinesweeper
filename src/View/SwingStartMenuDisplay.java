package View;

import Presenter.Difficulty;
import Presenter.Presenter;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class SwingStartMenuDisplay implements StartMenuDisplay {

    JFrame levelSelect;
    Presenter presenter;
    
    private void addLevels() {
        for (Difficulty diff : Difficulty.values()) {
            JButton jB = new JButton(diff.toString());
            jB.setSize(new Dimension(200, 50));
            levelSelect.add(jB);
            jB.addActionListener((e) -> { 
                presenter.newGame(diff, new SwingBoardDisplay());
                levelSelect.dispose();
            });
        }
    }

    @Override
    public void addPresenter(Presenter presenter) {
        this.presenter = presenter;
        presenter.addStartMenuDisplay(this);
        levelSelect = new JFrame("MVPMinesweeper");
        levelSelect.setLayout( new GridLayout(Difficulty.values().length, 1) );
        levelSelect.setMinimumSize(new Dimension(300, 150));
        addLevels();
        
        levelSelect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        levelSelect.setResizable(false);
        levelSelect.pack();
        levelSelect.setVisible(true);
    }
    
}
