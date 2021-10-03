package net.cozycosmos.midensfoods.items;



import java.util.ArrayList;

import net.cozycosmos.midensfoods.files.Foodvalues;
import net.cozycosmos.midensfoods.files.SaturationValues;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;


import net.cozycosmos.midensfoods.Main;

public class LegacyFoodCreator implements Listener {
	
	private final Main plugin = Main.getPlugin(Main.class);
	FileConfiguration config = plugin.getConfig();  //Accessing the config file


	public void ItemRecipe() {

		config = plugin.getConfig();
		config.getConfigurationSection("Recipes").getKeys(false).forEach(recipe -> {

			//make the food

			ItemStack foodItem = new ItemStack(Material.getMaterial(config.getString("Recipes." + recipe + ".Base")));  //set the base item to the object defined as base in the config
			ItemMeta meta = foodItem.getItemMeta(); //get the item's meta


			//set the name
			meta.setDisplayName(config.getString("Recipes." + recipe + ".Name").replace("&", "§"));

			//create and set the lore
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(config.getString(ChatColor.translateAlternateColorCodes('&', "Recipes." + recipe + ".Lore")).replace("&", "§"));
			meta.setLore(lore);
			foodItem.setItemMeta(meta);

			//IF the recipe is a furnace recipe
			if(config.getString("Recipes." + recipe + ".Type").equals("Furnace")) {
				@SuppressWarnings("deprecation")
				FurnaceRecipe newFurnaceRecipe = new FurnaceRecipe(foodItem, Material.getMaterial(config.getString("Recipes." + recipe + ".Recipe.Ingredient")));
				newFurnaceRecipe.setExperience(config.getInt("Recipes." + recipe + ".Recipe.Experience"));
				newFurnaceRecipe.setCookingTime(config.getInt("Recipes." + recipe + ".Recipe.Cook-Time"));
				plugin.getServer().addRecipe(newFurnaceRecipe);

			}

			//IF the "Recipes." + recipe is a crafting table recipe
			else if(config.getString("Recipes." + recipe + ".Type").equalsIgnoreCase("Shaped")) {

				NamespacedKey Key = new NamespacedKey(plugin, recipe);
				ShapedRecipe theRecipe = new ShapedRecipe(Key, foodItem);
				theRecipe.shape(config.getString("Recipes." + recipe + ".Recipe.TableLine1"),config.getString("Recipes." + recipe + ".Recipe.TableLine2"),config.getString("Recipes." + recipe + ".Recipe.TableLine3"));


				config.getConfigurationSection("Recipes." + recipe + ".Recipe.LetterKeys").getKeys(false).forEach(Rletter -> {


					theRecipe.setIngredient(Rletter.charAt(0), Material.valueOf(config.getString("Recipes." + recipe + ".Recipe.LetterKeys." + Rletter)));


				});

				plugin.getServer().addRecipe(theRecipe);
			} else if (config.getString("Recipes." + recipe + ".Type").equalsIgnoreCase("None")) {
				//Do nothing, no recipe needed! This statement is only here to prevent the error


			}  else if(config.getString("Recipes." + recipe + ".Type").equalsIgnoreCase("Shapeless")) {
				NamespacedKey Key = new NamespacedKey(plugin, recipe);
				ShapelessRecipe newShapelessRecipe = new ShapelessRecipe(Key, foodItem);
				config.getConfigurationSection("Recipes." + recipe + ".Ingredients").getKeys(false).forEach(Ingredient -> {
					if(config.getInt("Recipes." + recipe + ".Ingredients." + Ingredient) == 1) {
						newShapelessRecipe.addIngredient(Material.getMaterial(Ingredient));
					} else if (config.getInt("Recipes." + recipe + ".Ingredients." + Ingredient) > 1) {
						newShapelessRecipe.addIngredient(config.getInt("Recipes." + recipe + ".Ingredients." + Ingredient), Material.getMaterial(Ingredient));
					}
				});
				plugin.getServer().addRecipe(newShapelessRecipe);
			}

			else {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Invalid Recipe Type At " + recipe + ". Make sure there are no typos!");
			}

		});
	}
	@EventHandler
	public void ConfigFoodItem(FoodLevelChangeEvent event) {

		config = plugin.getConfig();
		config.getConfigurationSection("Recipes").getKeys(false).forEach(food -> {
		
			//make the Chocolate Bar
			ItemStack Efood = new ItemStack(Material.getMaterial(config.getString("Recipes." + food + ".Base")));
			ItemMeta meta = Efood.getItemMeta();
			
			//set the name
			meta.setDisplayName(config.getString("Recipes." + food + ".Name").replace("&", "§"));
			
			//create and set the lore
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(config.getString(ChatColor.translateAlternateColorCodes('&', "Recipes." + food + ".Lore")).replace("&", "§"));
			meta.setLore(lore);
			
			
			//set the item meta
			Efood.setItemMeta(meta);
			
			//if the entity is a player
			Player p = (Player) event.getEntity();
			if(event.getEntity() instanceof Player && p.getFoodLevel() < event.getFoodLevel()) {
				
				if(event.getEntity().getItemInHand().getItemMeta().equals(meta)) {
					//add the amount of hunger the food needs to add, remove the hunger from the food
					int foodlevel = event.getFoodLevel();
					float satlevel = p.getSaturation();
					foodlevel += config.getInt("Recipes." + food + ".Hunger-Fill");
					foodlevel -= Foodvalues.get().getInt(config.getString("Recipes." + food + ".Base"));
					satlevel += config.getInt("Recipes." + food + ".Saturation");
					satlevel -= SaturationValues.get().getInt(config.getString("Recipes." + food + ".Base"));

					event.setFoodLevel(foodlevel);
					p.setSaturation(satlevel);
				}
			
			}
		});

		}
}

	 


