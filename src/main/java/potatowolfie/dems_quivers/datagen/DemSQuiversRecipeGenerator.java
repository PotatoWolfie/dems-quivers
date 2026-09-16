package potatowolfie.dems_quivers.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.TransmuteRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import potatowolfie.dems_quivers.DemSQuivers;
import potatowolfie.dems_quivers.item.DemSQuiversItems;
import potatowolfie.dems_quivers.item.DemSQuiversTags;

import java.util.concurrent.CompletableFuture;

public class DemSQuiversRecipeGenerator extends FabricRecipeProvider {
    public DemSQuiversRecipeGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                quiverDyeRecipe(Items.DYE.white(), DemSQuiversItems.DYED_QUIVER.white());
                quiverDyeRecipe(Items.DYE.orange(), DemSQuiversItems.DYED_QUIVER.orange());
                quiverDyeRecipe(Items.DYE.magenta(), DemSQuiversItems.DYED_QUIVER.magenta());
                quiverDyeRecipe(Items.DYE.lightBlue(), DemSQuiversItems.DYED_QUIVER.lightBlue());
                quiverDyeRecipe(Items.DYE.yellow(), DemSQuiversItems.DYED_QUIVER.yellow());
                quiverDyeRecipe(Items.DYE.lime(), DemSQuiversItems.DYED_QUIVER.lime());
                quiverDyeRecipe(Items.DYE.pink(), DemSQuiversItems.DYED_QUIVER.pink());
                quiverDyeRecipe(Items.DYE.gray(), DemSQuiversItems.DYED_QUIVER.gray());
                quiverDyeRecipe(Items.DYE.lightGray(), DemSQuiversItems.DYED_QUIVER.lightGray());
                quiverDyeRecipe(Items.DYE.cyan(), DemSQuiversItems.DYED_QUIVER.cyan());
                quiverDyeRecipe(Items.DYE.purple(), DemSQuiversItems.DYED_QUIVER.purple());
                quiverDyeRecipe(Items.DYE.blue(), DemSQuiversItems.DYED_QUIVER.blue());
                quiverDyeRecipe(Items.DYE.brown(), DemSQuiversItems.DYED_QUIVER.brown());
                quiverDyeRecipe(Items.DYE.green(), DemSQuiversItems.DYED_QUIVER.green());
                quiverDyeRecipe(Items.DYE.red(), DemSQuiversItems.DYED_QUIVER.red());
                quiverDyeRecipe(Items.DYE.black(), DemSQuiversItems.DYED_QUIVER.black());

                shaped(RecipeCategory.MISC, DemSQuiversItems.QUIVER, 1)
                        .pattern("  S")
                        .pattern(" L ")
                        .pattern("L  ")
                        .define('L', Items.LEATHER)
                        .define('S', Items.STRING)
                        .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                        .save(output, String.valueOf(Identifier.fromNamespaceAndPath(DemSQuivers.MOD_ID, "quiver_regular")));

                shaped(RecipeCategory.MISC, DemSQuiversItems.FROZEN_ARROW, 4)
                        .pattern(" I ")
                        .pattern("IAI")
                        .pattern(" I ")
                        .define('I', Blocks.ICE)
                        .define('A', Items.ARROW)
                        .unlockedBy(getHasName(Items.ARROW), has(Items.ARROW))
                        .save(output, String.valueOf(Identifier.fromNamespaceAndPath(DemSQuivers.MOD_ID, "frozen_arrows")));
            }

            private void quiverDyeRecipe(Item dye, Item dyedResult) {
                TransmuteRecipeBuilder.transmute(RecipeCategory.TOOLS, this.tag(DemSQuiversTags.QUIVERS), Ingredient.of(dye), dyedResult)
                        .group("quiver_dye")
                        .unlockedBy(getHasName(dye), this.has(dye))
                        .save(this.output);
            }
        };
    }

    @Override
    public String getName() {
        return "ModRecipeGenerator";
    }
}