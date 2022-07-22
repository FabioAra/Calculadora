import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class Calculadora {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                CalculatoraFrame frame = new CalculatoraFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
class CalculatoraFrame extends JFrame {
    public CalculatoraFrame() {
        setTitle("Calculadora javadesdecero.es");
        PainelCalculadora painel = new PainelCalculadora();
        add(painel);
        pack();
        int width = 350;
        int height = 350;
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();
        setBounds(screenWidth / 2 - width / 2, screenHeight / 2 - height / 2, width, height);
    }
}
class JanelaCalculadora extends JPanel {
    private JButton display;
    private JPanel painel;
    private BigDecimal resultado;
    private String Comando;
    private boolean start;
    public JanelaCalculadora() {
        setLayout(new BorderLayout());
        resultado = BigDecimal.ZERO;
        Comando = "=";
        start = true;
        display = new JButton("0");
        display.setEnabled(false);
        display.setFont(display.getFont().deriveFont(50f));
        add(display, BorderLayout.NORTH);
        ActionListener insert = new InsertAction();
        ActionListener comando = new CommandAction();
        painel = new JPanel();
        painel.setLayout(new GridLayout(4, 4));
        addButton("7", insert);
        addButton("8", insert);
        addButton("9", insert);
        addButton("/", comando);
        addButton("4", insert);
        addButton("5", insert);
        addButton("6", insert);
        addButton("*", comando);
        addButton("1", insert);
        addButton("2", insert);
        addButton("3", insert);
        addButton("-", comando);
        addButton("0", insert);
        addButton(".", insert);
        addButton("=", comando);
        addButton("+", comando);
        add(painel, BorderLayout.CENTER);
    }
      private class InsertAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String input = event.getActionCommand();
            if (start) {
                display.setText("");
                start = false;
            }
            display.setText(display.getText() + input);
        }
    }
    private class CommandAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String comando = event.getActionCommand();
            if (start) {
                if (comando.equals("-")) {
                    display.setText(comando);
                    start = false;
                } else Comando = comando;
            } else {
                calculate(new BigDecimal(display.getText()));
                Comando = comando;
                start = true;
            }
        }
    }
    
    private void addButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.setFont(button.getFont().deriveFont(20f));
        button.addActionListener(listener);
        painel.add(button);
    }
  
    public void calculate(BigDecimal x) {
        if (Comando.equals("+")) resultado = resultado.add(x);
        else if (Comando.equals("-")) resultado = resultado.subtract(x);
        else if (Comando.equals("*")) resultado = resultado.multiply(x);
        else if (Comando.equals("/")) resultado = resultado.divide(x);
        else if (Comando.equals("=")) resultado = x;
        if (resultado.compareTo(BigDecimal.ZERO) == 0) {
            resultado = BigDecimal.ZERO;
        }
        display.setText(resultado.toString());
    }
}