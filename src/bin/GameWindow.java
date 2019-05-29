package bin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow{
	static final int WIDTH=200;
	static final int HEIGHT=360;
	static final int W_dialog=200;
	static final int H_dialog=130;
	
	int ansa=0;
	int ansb=0;
	int ansc=0;
	int ansd=0;
	
	int counta=0;
	int countb=0;
	int count=0;
	
	JFrame mainFrame=new JFrame("4A0B������С��Ϸ");
	JPanel mainPane=new JPanel();
	JPanel inputPane=new JPanel();
	JPanel resPane=new JPanel();
	JPanel numPane=new JPanel();
	GridLayout numLayout=new GridLayout(4, 3);
	
	List lres=new List(10);
	JTextField[] tinput=new JTextField[]{new JTextField(2),new JTextField(2),new JTextField(2),new JTextField(2)};
	JButton[] bnum=new JButton[]{new JButton("0"),new JButton("1"),new JButton("2"),new JButton("3"),new JButton("4"),
			new JButton("5"),new JButton("6"),new JButton("7"),new JButton("8"),new JButton("9"),};
	JButton bt_delete=new JButton("ɾ��");
	JButton bt_submit=new JButton("�ύ");
	
	GameWindow() {
		
		initGame();//��ʼ��
		
		Number num=new Number();
		ansa=num.numa;
		ansb=num.numb;
		ansc=num.numc;
		ansd=num.numd;
		
//		System.out.println("ans="+ansa+ansb+ansc+ansd);//�����ã�������ɾ��
		
		mainFrame.setSize(WIDTH,HEIGHT);
		mainFrame.setResizable(false);
		mainFrame.setContentPane(mainPane);
		mainPane.setLayout(new BorderLayout());
		mainPane.add(inputPane, "North");
		mainPane.add(resPane, "Center");
		mainPane.add(numPane, "South");
		numPane.setLayout(numLayout);
		
		lres.setFont(new Font("����", Font.PLAIN, 14));
		lres.setFocusable(false);
		resPane.add(lres);
		
		for(JTextField i:tinput) {
			i.setHorizontalAlignment(SwingConstants.CENTER);
			i.setFont(new Font("΢���ź�", Font.BOLD, 15));
			i.setBorder(BorderFactory.createLineBorder(Color.black));
			i.setEditable(false);
			inputPane.add(i);
		}
		
		for(int i=1;i<=9;i++) {
			bnum[i].setFocusable(false);
			numPane.add(bnum[i]);
		}
		bt_delete.setFocusable(false);
		numPane.add(bt_delete);
		bnum[0].setFocusable(false);
		numPane.add(bnum[0]);
		bt_submit.setFocusable(false);
		numPane.add(bt_submit);

		Toolkit kit=Toolkit.getDefaultToolkit();//���ô��ھ���
		Dimension screenSize=kit.getScreenSize();
		mainFrame.setLocation((screenSize.width-WIDTH)/2, (screenSize.height-HEIGHT)/2);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		
		ActionListener clickNum=new ClickNumber();
		FocusListener ansNum=new FocusListener() {
			public void focusLost(FocusEvent e) {	
				((JTextField)e.getSource()).setBorder(BorderFactory.createLineBorder(Color.black));
			}
			public void focusGained(FocusEvent e) {
				((JTextField)e.getSource()).setBorder(BorderFactory.createLineBorder(Color.red));
				if(((JTextField)e.getSource()).getText().equals("")==false)
					bt_delete.setEnabled(true);//����ɾ����״̬
				else bt_delete.setEnabled(false);
			}
		};
		
		for(JTextField i:tinput) {
			i.addFocusListener(ansNum);
		}
		
		for(JButton i:bnum) {//�����ּ���ӵ���¼�
			i.addActionListener(clickNum);
		}
		
		
		bt_delete.addActionListener(new ActionListener() {//��ɾ��������¼�
			public void actionPerformed(ActionEvent e) {
				for(JTextField i:tinput) {
					if(i.isFocusOwner()) {
						i.setText("");
						break;
					}
				}
				((JButton)e.getSource()).setEnabled(false);
				fresh();
			}
		});
		
		bt_submit.addActionListener(new ActionListener() {//���ύ������¼�
			public void actionPerformed(ActionEvent e) {
				if(!tinput[0].getText().equals("") && !tinput[1].getText().equals("") && !tinput[2].getText().equals("") && !tinput[3].getText().equals("")) {
					count++;
					int a=tinput[0].getText().charAt(0)-'0';
					int b=tinput[1].getText().charAt(0)-'0';
					int c=tinput[2].getText().charAt(0)-'0';
					int d=tinput[3].getText().charAt(0)-'0';
					countAB(a,b,c,d);
					lres.add("��"+count+"��"+"  "+a+b+c+d+"  "+counta+"A"+countb+"B");
					if(judge()==1) {
						mainFrame.dispose();
						JDialog resDialog=new JDialog(mainFrame, "��Ϸ���",true);
						JLabel resText=new JLabel("�ɹ���������"+count+"�β������֡�");
						JLabel ansText=new JLabel("��ȷ������"+ansa+ansb+ansc+ansd+"��");
						JButton bsure=new JButton("ȷ��");
						resDialog.add("North",resText);
						resDialog.add("Center",ansText);
						resDialog.setSize(W_dialog, H_dialog);
						resDialog.setLocation((screenSize.width-W_dialog)/2, (screenSize.height-H_dialog)/2);
						resDialog.setVisible(true);
						new GameWindow();
					}
					else if(judge()==-1) {
						mainFrame.dispose();
						JDialog resDialog=new JDialog(mainFrame, "��Ϸ���",true);
						JLabel resText=new JLabel("���ź�����û�в³�����.");
						JLabel ansText=new JLabel("��ȷ������"+ansa+ansb+ansc+ansd+"��");
						resDialog.add("North",resText);
						resDialog.add("Center",ansText);
						resDialog.setSize(W_dialog, H_dialog);
						resDialog.setLocation((screenSize.width-W_dialog)/2, (screenSize.height-H_dialog)/2);
						resDialog.setVisible(true);
						new GameWindow();
					}
					else initGame();
				}
			}
		});
			
	}
	
	
	class ClickNumber implements ActionListener{
		//���ּ��¼�������
		public void actionPerformed(ActionEvent e) {
			//������
			for(int i=0;i<4;i++) {
				if(tinput[i].isFocusOwner()) {
					tinput[i].setText(((JButton)e.getSource()).getText());
					if(i<3) tinput[i+1].requestFocus();
					break;
				}
			}
			if(!tinput[0].getText().equals("") && !tinput[1].getText().equals("") && !tinput[2].getText().equals("") && !tinput[3].getText().equals(""))
				bt_submit.setEnabled(true);//�����ύ��״̬
			fresh();//�������ּ�״̬
		}
	}
	
	private void initGame() {
		counta=0;
		countb=0;
		for(JTextField i:tinput) {
			i.setText("");
		}
		for(int i=0;i<=9;i++) {
			bnum[i].setEnabled(true);
		}
		bt_delete.setEnabled(false);
		bt_submit.setEnabled(false);
		tinput[0].requestFocus();
	}
	
	private void fresh() {
		//�������ּ�״̬
		for(int i=0;i<=9;i++) {
			bnum[i].setEnabled(true);
		}	
		int n;
		for(JTextField i:tinput) {
			if(!i.getText().equals("")) {
				n=i.getText().charAt(0)-'0';
				bnum[n].setEnabled(false);
			}
		}
	}
	
	private void countAB(int a,int b,int c,int d) {
		//����A��B�ĸ���
		if(ansa==a)counta++;
		else if(ansa==b || ansa==c || ansa==d)countb++;
		if(ansb==b)counta++;
		else if(ansb==a || ansb==c || ansb==d)countb++;
		if(ansc==c)counta++;
		else if(ansc==a || ansc==b || ansc==d)countb++;
		if(ansd==d)counta++;
		else if(ansd==a || ansd==b || ansd==c)countb++;
	}
	
	private int judge() {
		if(count<=10 && counta==4)
			return 1;
		if(count==10 && counta!=4 || count>10)
			return -1;
		return 0;
	}
}
