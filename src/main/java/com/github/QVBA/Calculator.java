package com.github.QVBA;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(name = Reference.MOD_NAME, modid = Reference.MOD_ID, version = Reference.MOD_VERSION)
public class Calculator {
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Item calc = new CalculatorItem().setTextureName(Reference.MOD_ID + ":calculator");
		registerItem(calc, "Calculator");
		ItemStack calculatorStack = new ItemStack(calc);
		GameRegistry.addRecipe(
				calculatorStack, 
				"xxx",
				"bbb",
				"bbb",
				'b', new ItemStack(Blocks.stone_button), 
				'x', new ItemStack(Blocks.glass_pane)
		);
	}
	
	public void init(FMLInitializationEvent event) {
		
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		System.out.println(Reference.MOD_NAME + " Credits: Gui Centering Algorithm (zmaster587) http://github.com/zmaster587");
		System.out.println(Reference.MOD_NAME + " Credits: Math expression evaluator (Boann) http://stackoverflow.com/users/964243/boann");
	}
	
	private void registerItem(Item item, String name) {
		try {
			GameRegistry.registerItem(item, name);
			System.out.println("Successfully registered " + name);
		}catch (Exception e) {
			System.out.println("Failed to register " + name);
			e.printStackTrace();
		}
	}

}
