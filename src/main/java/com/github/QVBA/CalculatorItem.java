package com.github.QVBA;

import com.github.QVBA.Gui.CalculatorGui;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CalculatorItem extends Item{
	
	public CalculatorItem() {
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("Calculator");
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		Minecraft.getMinecraft().displayGuiScreen(new CalculatorGui());
		return stack;
	}

}
