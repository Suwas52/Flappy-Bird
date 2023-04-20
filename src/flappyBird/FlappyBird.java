package flappyBird;

import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements ActionListener, MouseListener {

    public static FlappyBird flappyBird;

    public  final int WIDTH = 1200,  HEIGHT = 800;

    public Renderer renderer;

    public Rectangle bird;

    public ArrayList<Rectangle> columns;

    public int ticks, yMotion,score;

    public boolean gameOver,started=true;

    public Random rand;
    public FlappyBird(){


        JFrame jFrame = new JFrame();
        renderer = new Renderer();
        Timer timer = new Timer(20,this);

        columns = new ArrayList<>();

        bird = new Rectangle(WIDTH/2-10,HEIGHT / 2-10,20,20);

        rand = new Random();

        jFrame.add(renderer);
        jFrame.addMouseListener(this);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setSize(WIDTH,HEIGHT);
        jFrame.setVisible(true);

        timer.start();

        AddColumn(true);
        AddColumn(true);
        AddColumn(true);
        AddColumn(true);
        AddColumn(true);
        AddColumn(true);






    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int speed = 10;
        ticks++;

        if(started){
            for (int i = 0; i < columns.size(); i++){
                Rectangle column = columns.get(i);
                column.x -= speed;
            }

            if(ticks % 2 ==0 && yMotion <20){
                yMotion +=2;

            }

            for(int i = 0; i < columns.size();i++){
                Rectangle column = columns.get(i);

                if(column.x + column.width < 0 ){
                    columns.remove(column);
                    if(column.y == 0){
                        AddColumn(false);
                    }
                }
            }

            bird.y += yMotion;

            for(Rectangle column: columns){
                if(column.intersects(bird)){
                    gameOver = true;
                    bird.x = column.x - bird.width;
                }

            }

            if(bird.y > HEIGHT -120 || bird.y <0){
                gameOver = true;
            }
            if(gameOver){
                bird.y = HEIGHT- 150 - bird.height;
            }
        }
        

        renderer.repaint();
    }

    public void AddColumn(boolean start){
        int space = 300;
        int width =  100;
        int height = 50+rand.nextInt(300);

        if(start){
            columns.add(new Rectangle(WIDTH+width+ columns.size()*300,HEIGHT - height-120,width,height));
            columns.add(new Rectangle(WIDTH+width + (columns.size()-1)*300,0,width,HEIGHT-height-space));

        }
        else {
            columns.add(new Rectangle(columns.get(columns.size()-1).x +600,HEIGHT - height-120,width,height));
            columns.add(new Rectangle(columns.get(columns.size()+1).x,0,width,HEIGHT-height-space));
        }

    }
    public void paintColumn(Graphics g, Rectangle columns ){
        g.setColor(Color.green.darker());
        g.fillRect(columns.x,columns.y , columns.width, columns.height);
    }

    public void jump(){
        if(gameOver){
            bird = new Rectangle(WIDTH/2-10,HEIGHT / 2-10,20,20);
            columns.clear();
            yMotion = 0;
            score = 0;

            AddColumn(true);
            AddColumn(true);
            AddColumn(true);
            AddColumn(true);
            AddColumn(true);
            AddColumn(true);
            gameOver = false;
        }

        if(!started){
            started = true;
        }
        else if (!gameOver){
            if (yMotion > 0) {

                yMotion = 0;
            }
            yMotion -= 10;
        }
    }
    public void repaint(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(0,0, WIDTH, HEIGHT);

        g.setColor(Color.orange);
        g.fillRect(0,HEIGHT-150,WIDTH,150);

        g.setColor(Color.green);
        g.fillRect(0,HEIGHT-150,WIDTH,25);

        g.setColor(Color.red);
        g.fillRect(bird.x,bird.y, bird.width,bird.height);

        for(Rectangle Column: columns){
            paintColumn(g, Column);
        }

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", 1, 100));
        if(!started){
            g.drawString("Click to Start!",300,HEIGHT/2-100);
        }
        if(gameOver){
            g.drawString("Game Over!",300,HEIGHT/2-100);
        }
    }

    public static void main(String[] args){




        flappyBird = new FlappyBird();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        jump();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
