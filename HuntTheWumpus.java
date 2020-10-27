import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class HuntTheWumpus extends JPanel implements KeyListener {

    JFrame frame;

    private enum PlayState { PLAY, WIN, LOSE }
    private HuntTheWumpus.PlayState state;

    private BufferedImage victory, defeat, background;

    private Landscape scape;

    public HuntTheWumpus() {

        this.state = PlayState.PLAY;

        this.scape = new Landscape(this, 15);

        this.frame = new JFrame("Hunt the Wumpus"); //creates new frame with header "Hunt the Wumpus"
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //sets x button operation to close
        this.frame.add(this); //adds this JPanel to frame
        this.frame.pack(); //packs frame
        this.frame.setVisible(true); //sets frame to be visible
        this.setFocusable(true); //sets frame to be focusable (ie can accept keyboard input)
        this.addKeyListener(this); //adds this class as an implementation of KeyListener to this class as an extension of JPanel

        try {
            victory = ImageIO.read(new File("src/victory.png"));
            defeat = ImageIO.read(new File("src/defeat.png"));
            background = ImageIO.read(new File("src/shadow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Dimension getPreferredSize() {
        return new Dimension(scape.tilesToRow()*40, scape.tilesToRow()*40+40);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch(state) {
            case PLAY:
                g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
                scape.draw( g, Math.min(getWidth()/(scape.tilesToRow()), getHeight()/(scape.tilesToRow()+1)) );
                break;
            case WIN:
                g.drawImage(victory, 0, 0, getWidth(), getHeight(), null);
                break;
            case LOSE:
                g.drawImage(defeat, 0, 0, getWidth(), getHeight(), null);
                break;
        }

    }

    public void win() {
        state = PlayState.WIN;
    }

    public void lose() {
        state = PlayState.LOSE;
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    //type a character. Does not detect shift, for example.
    public void keyTyped(KeyEvent e) {
        if(state == PlayState.PLAY) {
            switch (("" + e.getKeyChar()).toLowerCase()) {
                case "w":
                    scape.inputNorth();
                    break;
                case "a":
                    scape.inputWest();
                    break;
                case "s":
                    scape.inputSouth();
                    break;
                case "d":
                    scape.inputEast();
                    break;
                case " ":
                    scape.toggleArmed();
                    break;
                default:
                    break;
            }
        } else {
            scape = new Landscape(this, 15);
            state = PlayState.PLAY;
        }

    }

    //main method. Needs to throw InterruptedException for Thread.sleep() to work.
    public static void main(String[] argv) throws InterruptedException {
        HuntTheWumpus htw = new HuntTheWumpus();
        while (true) {
            htw.repaint();
            Thread.sleep(33);
        }
    }

}