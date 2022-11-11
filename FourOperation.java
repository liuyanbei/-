package One;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.String;

public class FourOperation extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JCheckBox checkbox[];
    private JComboBox<Integer> comb_num,comb_max;
    private int num=1;
    private int max=10;
    private int style =0;
    private int I_Con=0;
    private JButton Button_Creat,Button_Print;
    private JTextField[] quiz=new JTextField[50];
    private Operation[] t=new Operation[50];

    public FourOperation()
    {
        this.setTitle("//四则运算生成器//");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JToolBar toolbar1 = new JToolBar();   //建立容器

        this.getContentPane().add(toolbar1, "North");

        String[][] str={{"题目个数 ："},{"加","减","乘","除","括号","小数"},{" 范围：0~"}};

        toolbar1.add(new JLabel(str[0][0]));
        Integer[] num1={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50};
        this.comb_num=new JComboBox<Integer>(num1);
        this.comb_num.addActionListener(this);
        toolbar1.add(this.comb_num);
        toolbar1.add(new JLabel("      选择运算符："));
        this.checkbox=new JCheckBox[str[1].length];

        for (int i=0;i<str[1].length;i++)
        {
            this.checkbox[i]=new JCheckBox(str[1][i]);
            toolbar1.add(this.checkbox[i]);
            this.checkbox[i].addActionListener(this);
        }
        toolbar1.add(new JLabel("       "));
        toolbar1.add(new JLabel(str[2][0]));
        Integer[] max1={10,20,30,40,50,60,70,80,90,100};
        this.comb_max=new JComboBox<Integer>(max1);
        this.comb_max.addActionListener(this);
        toolbar1.add(this.comb_max);
        toolbar1.add(new JLabel("     "));


        this.Button_Creat=new JButton("出题");
        this.Button_Creat.addActionListener(this);
        toolbar1.add(Button_Creat);
        toolbar1.add(new JLabel("     "));


        this.Button_Print=new JButton("打印");
        this.Button_Print.addActionListener(this);
        toolbar1.add(Button_Print);
        toolbar1.add(new JLabel(""));
        JPanel equ=new JPanel();
        this.getContentPane().add(equ, "Center");
        equ.setLayout(new GridLayout(0,5));


        for (int i=0;i<50;i++)
        {
            this.quiz[i]=new JTextField (" ");
            equ.add(this.quiz[i]);
        }

        this.setVisible(true);

    }

    public void Button_Creat_Test()
    {
        if (style==0)
        {
            JOptionPane.showMessageDialog(this, "请选择运算符号");
            return;
        }
        if (style==16||style==32)
        {
            if (style==16)
                JOptionPane.showMessageDialog(this, "(只选择了括号)"
                        + "请选择四则运算符号");
            if (style==32)
                JOptionPane.showMessageDialog(this, "(只选择了小数)"
                        + "请选择四则运算符号");
            return;
        }

        JOptionPane.showMessageDialog(this, "生成新的题目");
        for (int i=0;i<num;i++)
        {

            t[i]=new Operation(max,style);//
            t[i].Creat();
            this.quiz[i].setText(t[i].QuiZ);
        }
        I_Con++;
    }

    public  void Print() throws Exception{
        File file = new File("D:" + File.separator + "JAVA" + File.separator + "Arithmetic_QuiZ.txt");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        Writer out = new FileWriter(file);
        for (int i=0;i<num;i++)
        {

            out.write(t[i].QuiZ);
            out.write("\r\n");
        }
        out.close();
        JOptionPane.showMessageDialog(this, "打印成功,文件保存至D:java");
    }


    public void actionPerformed(ActionEvent ev)
    {
        if (ev.getSource()==this.Button_Creat)
        {
            Button_Creat_Test();//
        }
        if (ev.getSource()==this.Button_Print)
        {
            if (I_Con==0)
            {
                JOptionPane.showMessageDialog(this, "打印失败");
                return ;
            }
            try {
                Print();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (ev.getSource()instanceof JComboBox<?>||ev.getSource()instanceof JCheckBox||ev.getSource()instanceof JMenuItem)
        {
            Object obj1=this.comb_num.getSelectedItem();
            num=((Integer)obj1).intValue();
            Object obj2=this.comb_max.getSelectedItem();
            max=((Integer)obj2).intValue();
            if (ev.getActionCommand().equals("加")) style=style^1;
            if (ev.getActionCommand().equals("减")) style=style^2;
            if (ev.getActionCommand().equals("乘")) style=style^4;
            if (ev.getActionCommand().equals("除")) style=style^8;
            if (ev.getActionCommand().equals("括号")) style=style^16;
            if (ev.getActionCommand().equals("小数")) style=style^32;
        }



    }

    public static void main(String args[])
    {
        new FourOperation();
    }
}

class Operation {
    private int max,Brackets,Decimal;
    private double[] Num_Con=new double[10],shu_new=new double[10];
    public String symbol=" ",QuiZ,symbol_sel,Symbol_Con=" ",symbol_new=" ";

    public Operation(int Max,int tyle)
    {
        max=Max;
        if ((tyle & 1)==1) symbol=symbol+"+";
        if ((tyle & 2)==2) symbol=symbol+"-";
        if ((tyle & 4)==4) symbol=symbol+"*";
        if ((tyle & 8)==8) symbol=symbol+"/";
        if ((tyle & 16)==16) Brackets=1;
        if ((tyle & 32)==32) Decimal=1;
    }


    public void Creat()
    {
        Num_Con[0]=getnum();
        for(int i=1;i<=(int)(Math.random()*3)+1;i++)
        {
            Num_Con[i]=getnum();
            getchar();
            Symbol_Con=Symbol_Con+symbol_sel;
        }
        Creat_File();
        Display();
        QuiZ=QuiZ+"=";
    }

    private void Display()
    {
        int no=1,ni=0;
        QuiZ=" ";
        if (Brackets==1 && Symbol_Con.charAt(1)=='(')
        {
            while(true)
            {
                if (no>=Symbol_Con.length()) break;
                QuiZ=QuiZ+Symbol_Con.substring(no,no+1);
                if (Symbol_Con.charAt(no)==')')
                {
                    no++;
                    if (no>=Symbol_Con.length()) break;
                    QuiZ=QuiZ+Symbol_Con.substring(no,no+1);
                }
                if (Decimal==1) QuiZ=QuiZ+String.valueOf(Num_Con[ni]);
                else QuiZ=QuiZ+String.valueOf((int)Num_Con[ni]);
                ni++;no++;
                if (no==Symbol_Con.length()) break;
            }
        }
        else if (Brackets==1&&Symbol_Con.charAt(1)!='(')
        {
            if (Decimal==1) QuiZ=QuiZ+String.valueOf(Num_Con[0]);
            else QuiZ=QuiZ+String.valueOf((int)Num_Con[0]);
            ni++;
            while(true)
            {
                if (no>=Symbol_Con.length()) break;
                QuiZ=QuiZ+Symbol_Con.substring(no,no+1);
                no++;
                if (no>=Symbol_Con.length()) break;
                if (Symbol_Con.charAt(no)=='(')
                {
                    //no++;
                    QuiZ=QuiZ+Symbol_Con.substring(no,no+1);
                }
                else no--;
                if (Symbol_Con.charAt(no)==')'&&no<Symbol_Con.length())
                {
                    no++;
                    QuiZ=QuiZ+Symbol_Con.substring(no,no+1);
                }
                if (Decimal==1) QuiZ=QuiZ+String.valueOf(Num_Con[ni]);
                else QuiZ=QuiZ+String.valueOf((int)Num_Con[ni]);
                ni++;no++;
                if (no==Symbol_Con.length()) break;
            }
        }
        else if(Brackets==0)
        {
            if (Decimal==1) QuiZ=QuiZ+String.valueOf(Num_Con[ni]);
            else QuiZ=QuiZ+String.valueOf((int)Num_Con[ni]);
            ni++;
            while(true)
            {
                if (no>=Symbol_Con.length()) break;
                QuiZ=QuiZ+Symbol_Con.substring(no,no+1);
                if (Decimal==1)
                {QuiZ=QuiZ+String.valueOf(Num_Con[ni]);}
                else
                {QuiZ=QuiZ+String.valueOf((int)Num_Con[ni]);}
                ni++;
                no++;
                if (no>=Symbol_Con.length()) break;
            }
        }
    }

    private void Creat_File()
    {
        if (Brackets==1)
        {
            int id=Math.abs((int)(Math.random()*(Symbol_Con.length()-1))); //¶¨Òå È¡¾ø¶ÔÖµ
            String temp1,temp_mid,temp_2;
            temp1=Symbol_Con.substring(0, id+1);
            temp_mid=Symbol_Con.substring(id+1,id+2);
            if ((id+2) < (Symbol_Con.length()))
                temp_2=Symbol_Con.substring(id+2,Symbol_Con.length());
            else
                temp_2="";

            Symbol_Con=temp1+"("+temp_mid+")"+temp_2;
            symbol_new=temp1+temp_2;
            if (id==1)
            {
                if (Symbol_Con.charAt(1)=='+')
                    shu_new[0] = Num_Con[0]+Num_Con[1];
                else if (Symbol_Con.charAt(1)=='-')
                    shu_new[0] = Num_Con[0]-Num_Con[1];
                else if (Symbol_Con.charAt(1)=='*')
                    shu_new[0] = Num_Con[0]*Num_Con[1];
                else if (Symbol_Con.charAt(1)=='/')
                    shu_new[0] = Num_Con[0]/Num_Con[1];
                for (int i=2;i<Num_Con.length;i++)
                {
                    shu_new[i-1]=Num_Con[i];
                }
            }
            else if (id==2)
            {
                shu_new[0]=Num_Con[0];
                if (Symbol_Con.charAt(3)=='+')
                    shu_new[1]=Num_Con[1]+Num_Con[2];
                else if (Symbol_Con.charAt(3)=='-')
                    shu_new[1]=Num_Con[1]-Num_Con[2];
                else if (Symbol_Con.charAt(3)=='*')
                    shu_new[1]=Num_Con[1]*Num_Con[2];
                else if (Symbol_Con.charAt(3)=='/')
                    shu_new[1]=Num_Con[1]/Num_Con[2];
                for (int i=3;i<Num_Con.length;i++)
                {
                    shu_new[i-1]=Num_Con[i];
                }

            }
            else if (id==3)
            {
                shu_new[0]=Num_Con[0];
                shu_new[1]=Num_Con[1];
                if (Symbol_Con.charAt(3)=='+')
                    shu_new[2]=Num_Con[2]+Num_Con[3];
                else if (Symbol_Con.charAt(3)=='-')
                    shu_new[2]=Num_Con[2]-Num_Con[3];
                else if (Symbol_Con.charAt(3)=='*')
                    shu_new[2]=Num_Con[2]*Num_Con[3];
                else if (Symbol_Con.charAt(3)=='/')
                    shu_new[2]=Num_Con[2]/Num_Con[3];
            }
        }
        else
        {
            symbol_new=Symbol_Con;
            for (int i=0;i<Num_Con.length;i++)
            {
                shu_new[i]=Num_Con[i];
            }
        }
        if (Decimal==0)
        {
            for (int i=0;i<shu_new.length;i++)
                shu_new[i]=(int) shu_new[i];
        }
    }

    private double getnum()
    {
        double a=(Math.random()*max)+1;
        a=((int)(a*100))/100.0;
        return (a);
    }

    public void getchar()
    {
        int Mark=(int)(Math.random()*(symbol.length()-1))+1;
        symbol_sel=symbol.substring(Mark, Mark+1);
    }
}