package com.github.QVBA.Gui;

import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.github.QVBA.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;

/**

 * @credits zmaster587, Boann.
 */
public class CalculatorGui extends GuiScreen{
	
	private String display;
	private GuiTextField text;
	private boolean firstTyped;
	
	public void initGui() {
		display = "0";
		this.buttonList.clear();
		
		//Credits to zmaster587 for centering algorithm. https://github.com/zmaster587
	    int offsetX = width/4;
	    int offsetY = height/4;
	    final int numButtonsX = 4;
        final int numButtonsY = 4; //Total of 16 buttons.
        int sizeX = width/(2*numButtonsX);
        int sizeY = height/(2*numButtonsY);
        Object[] calcSigns = {
        		7, 8, 9, "/",
        		4, 5, 6, "*",
        		1, 2, 3, "+",
        		0, ".", "C", "-",
        };
        ArrayList<Object> calcSigns1 = new ArrayList();
        for(Object a : calcSigns) {
        	calcSigns1.add(a);
        }
        int i = 0;
        System.out.println("-----");
        for(int yi = 0; yi < numButtonsY; yi++) {
        	for(int xi = 0; xi < numButtonsX; xi++) {
        		this.buttonList.add(new GuiImageButton(i, offsetX + sizeX*xi, offsetY + sizeY*yi, sizeX - 2, sizeY - 2,new ResourceLocation[]{new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/button.png")}, calcSigns1.get(i).toString()));
        		i++;
        	}
        }
        
        this.buttonList.add(new GuiImageButton(16, offsetX, offsetY * 3, (offsetX * 2) -2 , sizeY -2, new ResourceLocation[]{new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/button.png")}, "="));
        text = new GuiTextField(Minecraft.getMinecraft().fontRenderer, offsetX, offsetY - sizeY, (offsetX * 2) - 2, sizeY - 2);
        text.setText("0");
        firstTyped = true;     
	}
	
	protected void actionPerformed(GuiButton button) {
		int id = button.id;
		if(!button.enabled) {
			return;
		}
		String key = getKeyFromID(id);
		if(key.equals("C")) {
			text.setText("0");
			firstTyped = true;
		}else if(key.equals("=")){
			text.setText(String.valueOf(eval(text.getText())));
		}else {
			if(firstTyped) {
				text.setText(key);
				firstTyped = false;
			}else {
				text.setText(text.getText() + key);
			}
		}
	}
	
    /**
     * Draws the screen and all the components in it.
     */
	public void drawScreen(int a, int b, float c) {
		super.drawScreen(a, b, c);
		text.drawTextBox();
		
	}
	
	public String getKeyFromID(int id) {
		switch(id) {
		case 0: //7
			return String.valueOf(7);
		case 1: //8
			return String.valueOf(8);
		case 2: //9
			return String.valueOf(9);
		case 3: // '/'
			return "/";
		case 4: //4
			return String.valueOf(4);
		case 5: //5
			return String.valueOf(5);
		case 6: //6
			return String.valueOf(6);
		case 7: // '*'
			return "*";
		case 8: //1
			return String.valueOf(1);
		case 9: //2
			return String.valueOf(2);
		case 10: //3
			return String.valueOf(3);
		case 11: //+
			return "+";
		case 12: //0
			return String.valueOf(0);
		case 13: //.
			return ".";
		case 14: //C
			return "C";
		case 15: //- 
			return "-";
		case 16: //=
			return "=";
		}
		return null;
	}
	
	/**
	 * @author Boann
	 * http://stackoverflow.com/users/964243/boann
	 */
	public static double eval(final String str) {
	    class Parser {
	        int pos = -1, c;

	        void eatChar() {
	            c = (++pos < str.length()) ? str.charAt(pos) : -1;
	        }

	        void eatSpace() {
	            while (Character.isWhitespace(c)) eatChar();
	        }

	        double parse() {
	            eatChar();
	            double v = parseExpression();
	            if (c != -1) throw new RuntimeException("Unexpected: " + (char)c);
	            return v;
	        }

	        // Grammar:
	        // expression = term | expression `+` term | expression `-` term
	        // term = factor | term `*` factor | term `/` factor | term brackets
	        // factor = brackets | number | factor `^` factor
	        // brackets = `(` expression `)`

	        double parseExpression() {
	            double v = parseTerm();
	            for (;;) {
	                eatSpace();
	                if (c == '+') { // addition
	                    eatChar();
	                    v += parseTerm();
	                } else if (c == '-') { // subtraction
	                    eatChar();
	                    v -= parseTerm();
	                } else {
	                    return v;
	                }
	            }
	        }

	        double parseTerm() {
	            double v = parseFactor();
	            for (;;) {
	                eatSpace();
	                if (c == '/') { // division
	                    eatChar();
	                    v /= parseFactor();
	                } else if (c == '*' || c == '(') { // multiplication
	                    if (c == '*') eatChar();
	                    v *= parseFactor();
	                } else {
	                    return v;
	                }
	            }
	        }

	        double parseFactor() {
	            double v;
	            boolean negate = false;
	            eatSpace();
	            if (c == '+' || c == '-') { // unary plus & minus
	                negate = c == '-';
	                eatChar();
	                eatSpace();
	            }
	            if (c == '(') { // brackets
	                eatChar();
	                v = parseExpression();
	                if (c == ')') eatChar();
	            } else { // numbers
	                StringBuilder sb = new StringBuilder();
	                while ((c >= '0' && c <= '9') || c == '.') {
	                    sb.append((char)c);
	                    eatChar();
	                }
	                if (sb.length() == 0) throw new RuntimeException("Unexpected: " + (char)c);
	                v = Double.parseDouble(sb.toString());
	            }
	            eatSpace();
	            if (c == '^') { // exponentiation
	                eatChar();
	                v = Math.pow(v, parseFactor());
	            }
	            if (negate) v = -v; // unary minus is applied after exponentiation; e.g. -3^2=-9
	            return v;
	        }
	    }
	    return new Parser().parse();
	}
}
