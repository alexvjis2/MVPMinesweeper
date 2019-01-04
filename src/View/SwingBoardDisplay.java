package View;

import Presenter.CellState;
import Presenter.Presenter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class SwingBoardDisplay implements BoardDisplay { 
    protected static final Dimension CELL_SIZE = new Dimension(32, 32);
    protected static ImageIcon flag, mine;
    protected static final Color[] CELL_COLORS = {
        new Color(230, 230, 230), Color.BLUE, Color.GREEN, Color.RED, Color.MAGENTA,
        Color.CYAN, Color.ORANGE, Color.ORANGE, Color.PINK
    };
    
    static {
        try {
            flag = new ImageIcon(ImageIO.read(new File("src/Resources/flag.png")));
            mine = new ImageIcon(ImageIO.read(new File("src/Resources/mine.png")));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }
    
    private Presenter presenter;
    private JFrame mainFrame;
    private FixedJButton[][] cells;
    private int rows, cols;
    
    @Override
    public void updateCell(int row, int col, CellState newState) {
        JButton button = cells[row][col];
        switch (newState) {
            case CLOSED:
                button.setIcon(null);
                break;
            case FLAGGED:
                button.setIcon(flag);
                break;
            case MINE:
                button.setIcon(mine);
                break;
            case NOMINE:
                button.setBorder(BorderFactory.createLineBorder(CELL_COLORS[0]));
                button.setEnabled(false);
                break;
        }
    }
    
    @Override
    public void endGame(boolean win) {
        JOptionPane.showMessageDialog(mainFrame, 
                win ? "Congratulations. You've completed this in much less"
                    + " time than I've lost programming it.\n- Álex"
                    : "BOOM! =(");
        SwingStartMenuDisplay ssmd = new SwingStartMenuDisplay();
        presenter.backToStartMenu(ssmd);
        mainFrame.dispose();
    }
    private MouseListener mouseListener(int row, int col) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    presenter.openCell(row, col);
                } else {
                    presenter.toggleFlag(row, col);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
            
            
        };
    }
    
    private void addButtons() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                FixedJButton jB = new FixedJButton(
                        presenter.getMinesAround(i, j));
                jB.addMouseListener(mouseListener(i, j));
                cells[i][j] = jB;
                mainFrame.add(jB);
            }
        }
    }
    
    @Override
    public void addPresenter(Presenter presenter) {
        this.presenter = presenter;
        rows = presenter.getRows();
        cols = presenter.getCols();
        cells = new FixedJButton[rows][cols];
        presenter.addBoardDisplay(this);

        mainFrame = new JFrame("MVPMineSweeper");
        mainFrame.setLayout(new GridLayout(rows, cols));
        addButtons();

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setSize(
                new Dimension(cols * CELL_SIZE.width, rows * CELL_SIZE.height)
        );
        mainFrame.setVisible(true);
    }

    
    
    private class FixedJButton extends JButton {
        private final int minesAround;
        
        public FixedJButton (int minesAround) {
            super();
            this.minesAround = minesAround;
            setSize(CELL_SIZE);
            setMargin(new Insets(0, 0, 0, 0));
        }
        
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (!this.isEnabled() && minesAround != 0) {
                g.setFont(new Font("Monospaced", Font.BOLD, 20));
                g.setColor(CELL_COLORS[minesAround]);
                g.drawString(
                    String.valueOf(minesAround), 
                    getWidth() / 2 - 5, getHeight() / 2 + 8
                );
            }
        }
    }
}
