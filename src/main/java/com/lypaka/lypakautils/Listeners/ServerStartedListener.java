package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.ConfigGetters;
import com.lypaka.lypakautils.LypakaUtils;
import com.lypaka.lypakautils.MiscHandlers.PixelmonHelpers;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonBuilder;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = LypakaUtils.MOD_ID)
public class ServerStartedListener {

    @SubscribeEvent
    public static void onServerStarted (ServerStartedEvent event) {

        if (!ConfigGetters.loadPokemonTypeMap) return;
        PixelmonSpecies.getAll().forEach(species -> {

            species.getForms().forEach(form -> {

                form.getTypes().forEach(type -> {

                    List<Pokemon> speciesList = new ArrayList<>();
                    if (PixelmonHelpers.pokemonTypeMap.containsKey(type.getName())) speciesList = PixelmonHelpers.pokemonTypeMap.get(type.getName());
                    speciesList.add(PokemonBuilder.builder().species(species).form(form).build());
                    PixelmonHelpers.pokemonTypeMap.put(type.getName(), speciesList);

                });

            });

        });

    }

}
