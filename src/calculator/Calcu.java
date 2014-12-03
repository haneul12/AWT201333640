package calculator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Calcu extends JFrame implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private double num1, num2, mem;
	private String s1, flag, sign, temp;
	private char key;
	JPanel jp = new JPanel();
	JTextField jf  = new JTextField(20);
	JButton jl = new JButton();
	JButton jb[] = new JButton[27];
	Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
	
	Calcu(){
		jp.setLayout(null);
		
		jb[0] = new JButton("Backspace");
		jb[1] = new JButton("CE");
		jb[2] = new JButton("C");
		jb[3] = new JButton("MC");
		jb[4] = new JButton("MR");
		jb[5] = new JButton("MS");
		jb[6] = new JButton("M+");
		jb[7] = new JButton("7");
		jb[8] = new JButton("8");
		jb[9] = new JButton("9");
		jb[10] = new JButton("/");
		jb[11] = new JButton("sqrt");
		jb[12] = new JButton("4");
		jb[13] = new JButton("5");
		jb[14] = new JButton("6");
		jb[15] = new JButton("*");
		jb[16] = new JButton("%");
		jb[17] = new JButton("1");
		jb[18] = new JButton("2");
		jb[19] = new JButton("3");
		jb[20] = new JButton("-");
		jb[21] = new JButton("1/x");
		jb[22] = new JButton("0");
		jb[23] = new JButton("¡¾");
		jb[24] = new JButton(".");
		jb[25] = new JButton("+");
		jb[26] = new JButton("=");
		
		jf.setBounds(5, 3, 300, 25);
		jf.setHorizontalAlignment(JTextField.RIGHT);
		jf.setFocusable(false);
		
		jl.setBounds(5, 35, 52, 30);
		jl.setBackground(new Color(210,220,250));
		
		jb[0].setBounds(65, 35, 100, 30);
		jb[1].setBounds(170, 35, 65, 30);
		jb[2].setBounds(240, 35, 65, 30);
		
		for(int i = 3, j = 70; i < 7; i++, j+=30){
			jb[i].setBounds(5, j, 52, 25);
		}
		for(int i = 0, k = 30; i < 20; i+=5, k+=30){
			for(int ii = 7, j = 65; ii<11; ii++, j+=45){
				jb[ii+i].setBounds(j, 40+k, 41, 25);
			}
		}
		for(int i = 11, j = 70; i<27; i+=5, j+=30){
			jb[i].setBounds(245, j, 60, 25);
		}
		
		jp.add(jf);
		jp.add(jl);
		for(int i = 0; i<27; i++){
			jp.add(jb[i]);
			jb[i].addActionListener(this);
			jb[i].setFocusable(false);
		}
		addKeyListener(this);
		
		add(jp);
		
		setFocusable(true);
		setTitle("Calculator");
		setBounds(res.width/2-157, res.height/2-120,315,240);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
		init();
	}
	
	public void actionPerformed(ActionEvent ae){
		flag = ae.getActionCommand();
		process();
	}
	
	public void keyPressed(KeyEvent ke){
		try{
			key = ke.getKeyChar();
			if(key!=65535){
				if(key==8)flag="Backspace";
				if(key==12)flag="MC";
				if(key==18)flag="MR";
				if(key==13)flag="MS";
				if(key==16)flag="M+";
				if(key==27)flag="C";
				if(key==37)flag="%";
				if(key==42)flag="*";
				if(key==43)flag="+";
				if(key==45)flag="-";
				if(key==46)flag=".";
				if(key==47)flag="/";
				if(key==64)flag="sqrt";
				if(key==114)flag="1/x";
				if(key==115)flag="¡¾";
				if(key==127)flag="CE";
				if(key==61 || key==10)flag="=";
				if(key >= 48 && key<=57){
					key -= 48;
					flag=Integer.toString(key);
				}
				process();
			}
		}catch(Exception e){
			System.out.println("KeyListener Error" + "\n" + e);
		}
	}
	
	public void keyReleased(KeyEvent ke){
	}
	public void keyTyped(KeyEvent ke){
	}
	
	private void process(){
		try{
			if(flag.equals("Backspace")){
				if(is_hidedouble()) s1 = get_intstr();
				if(s1.length() != 1){
					s1 = s1.substring(0, s1.length()-1);
				}
				num2 = Double.parseDouble(s1);
				print();
			}
			if("0123456789".indexOf(flag) != -1){
				if(s1.isEmpty()){
					s1 = flag;
				}else{
					if((num2 % 1 == 0 && s1.indexOf(".") == -1) || 
							(s1.charAt(s1.length()-2) == '.' &&s1.charAt(s1.length()-1)== '0')){
						s1 = get_intstr();
						s1 += flag;
					}else{
						s1+=flag;
					}
				}
				num1 = Double.parseDouble(s1);
				jf.setText(s1);
			}
			if("+-/*%".indexOf(flag) != -1){
				if(temp.equals("+")){
					operation('+', true);
				}else if(temp.equals("-")){
					operation('-', true);
				}else if(temp.equals("*")){
					operation('*', true);
				}else if(temp.equals("/")){
					operation('/', true);
				}else if(temp.equals("%")){
					operation('%', true);
				}else{
					if(temp.isEmpty()) temp = flag;
					if(!temp.equals("=")) num2 = num1;
					else temp = flag;
				}
				sign = flag;
				num1 = 0;
				s1 = "";
			}
			if(flag.equals(".")){
				if(is_hidedouble()) s1 = get_intstr();
				if(s1.indexOf(".") == -1){
					s1+=flag;
					jf.setText(s1);					
				}
			}
			if(flag.equals("CE")){
				num1 = 0; num2 = 0;
				s1 = ""; flag = ""; sign = ""; temp = "";
				jf.setText("0");
			}
			if(flag.equals("C")){
				init();
			}
			if(flag.equals("=")){
				if(sign=="+") operation('+', false);
				if(sign=="-") operation('-', false);
				if(sign=="*") operation('*', false);
				if(sign=="/") operation('/', false);
				if(sign=="%") operation('%', false);
			}
		}catch(Exception e){
			System.out.println("process() Error" + "\n" + e);
		}
	}
	
	private String get_intstr(){
		return Integer.toString((int)Double.parseDouble(s1));
	}
	private boolean is_hidedouble(){
		if(num2 % 1 == 0 && ((s1.charAt(s1.length()-1)=='0') && s1.charAt(s1.length()-2) == '.')) return true;
		else return false;
	}
	
	private void operation(char swit, boolean swit2){
		switch(swit){
		case '+': num2 += num1; break;
		case '-': num2 -= num1; break;
		case '*': num2 *= num1; break;
		case '/': num2 /= num1; break;
		case '%': num2 %= num1; break;
		}
		temp = swit2?flag : "=";
		print();
	}
	
	private void init(){
		num1 = 0; num2 = 0; mem = 0;
		s1 = ""; flag = ""; sign=""; temp="";
		jf.setText("0");
		jl.setText("");
	}
	
	private void print(){
		try{
			s1 = Double.toString(num2);
			if(num2 % 1 == 0){
				jf.setText(Integer.toString((int)num2));
			}else{
				jf.setText(Double.toString(num2));
			}
		}catch(Exception e){
			System.out.println("print() Error" + "\n" + e);
		}
	}
}
